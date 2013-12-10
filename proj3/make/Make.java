package make;

import graph.Graph;
import graph.Weighting;
import graph.Weighter;
import graph.Graphs;
import graph.DirectedGraph;
import graph.Traversal;
import graph.NoLabel;
import graph.StopException;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import java.io.PrintWriter;

/** Class Make to operate Makefile operations.
 *  @author Kiet Lam.*/
class Make {

    /** The dependency graph.*/
    private Graph<String, NoLabel> _graph;

    /** Map of targets to their creation time.*/
    private Map<String, Integer> _creationMap;

    /** Map of target to their vertex in the dependency graph.*/
    private Map<String, Graph<String, NoLabel>.Vertex> _verticesMap;

    /** Map of targets to their rule.*/
    private Map<String, Rule> _rulesMap;

    /** Output printwrite.*/
    private PrintWriter _out;

    /** Error printwriter.*/
    private PrintWriter _err;

    /** The current time to compare against.*/
    private int _currentTime;

    /** Create a make object to process Makefiles using CURRENTTIME, CREATIONMAP
     *  OUT for the output and ERR for the error.*/
    Make(int currentTime, Map<String, Integer> creationMap, PrintWriter out,
         PrintWriter err) {
        _graph = new DirectedGraph<String, NoLabel>();
        _creationMap = creationMap;
        _verticesMap = new HashMap<String, Graph<String, NoLabel>.Vertex>();
        _rulesMap = new HashMap<String, Rule>();
        _out = out;
        _err = err;
        _currentTime = currentTime;
    }

    /** Adds rule RULE to the make.*/
    void addRule(Rule rule) {
        String target = rule.getTarget();

        if (_rulesMap.containsKey(target)) {
            Rule containedRule = _rulesMap.get(target);
            if (containedRule.getCommands().size() > 0
                && rule.getCommands().size() > 0) {
                Main.reportError(_err, "Multiple non-empty commands!");
                return;
            } else {
                containedRule.addCommands(rule.getCommands());
            }
        } else {
            _rulesMap.put(target, rule);
        }

        Graph<String, NoLabel>.Vertex vertex = null;

        if (_verticesMap.containsKey(target)) {
            vertex = _verticesMap.get(target);
        } else {
            vertex = _graph.add(rule.getTarget());
            _verticesMap.put(target, vertex);
        }

        for (String dependency: rule.getDependencies()) {
            Graph<String, NoLabel>.Vertex dependencyVertex = null;
            if (_verticesMap.containsKey(dependency)) {
                dependencyVertex = _verticesMap.get(dependency);
            } else {
                dependencyVertex = _graph.add(dependency);
                _verticesMap.put(dependency, dependencyVertex);
            }

            _graph.add(vertex, dependencyVertex);
        }
    }

    /** Build TARGET by doing a DFS traversal.*/
    public void build(String target) {
        if (!_verticesMap.containsKey(target)) {
            Main.reportError(_err, "target not found");
        } else if (!_creationMap.containsKey(target)
                   || _creationMap.get(target) < _currentTime) {
            Traversal<String, NoLabel> traverser = new MakeTraversal(_graph);
            Graph<String, NoLabel>.Vertex begin = _verticesMap.get(target);
            traverser.depthFirstTraverse(_graph, begin);
        }
    }

    /** Returns the dependency graph.*/
    Graph<String, NoLabel> getGraph() {
        return _graph;
    }

    /** Returns the map of target to their vertex.*/
    Map<String, Graph<String, NoLabel>.Vertex> getVerticesMap() {
        return _verticesMap;
    }

    /** A Depth-First traversal that will enable us to detect cycles.
     *  Will also handle the processing of commands for make files.*/
    private class MakeTraversal extends Traversal<String, NoLabel> {

        /** The dependency graph.*/
        private Graph<String, NoLabel> _graph;

        /** Construct a make traversal using the dependency graph GRAPH.*/
        MakeTraversal(Graph<String, NoLabel> graph) {
            _graph = graph;
        }

        @Override
        public void visit(Graph<String, NoLabel>.Vertex vertex) {
            if (vertex == null) {
                return;
            }

            Weighting<NoLabel> weighter = new Weighting<NoLabel>() {
                @Override
                public double weight(NoLabel h) {
                    return 1.0;
                }
            };

            Weighter<String> vweighter = new Weighter<String>() {
                @Override
                public void setWeight(String h, double v) {
                }

                @Override
                public double weight(String h) {
                    return 1.0;
                }
            };

            for (Graph<String, NoLabel>.Vertex v: _graph.successors(vertex)) {

                List<Graph<String, NoLabel>.Edge> path =
                    Graphs.shortestPath(_graph, v, vertex,
                                        Graphs.ZERO_DISTANCER,
                                        vweighter, weighter);

                if (path != null) {
                    Main.reportError(_err,
                                     "make file has circular dependency!");
                    throw new StopException();
                }
            }
        }

        @Override
        public void postVisit(Graph<String, NoLabel>.Vertex vertex) {
            Map<String, Integer> creation = _creationMap;
            if (vertex == null) {
                return;
            }

            String target = vertex.getLabel();
            Rule rule = _rulesMap.get(target);

            if (!_rulesMap.containsKey(target)
                && !_creationMap.containsKey(target)) {
                Main.reportError(_err, "Missing pre-req!");
                return;
            }

            if (rule == null) {
                return;
            }

            boolean needsBuilt = false;

            int largestTime = Integer.MIN_VALUE;

            for (String dependency: rule.getDependencies()) {
                if (!creation.containsKey(target)) {
                    needsBuilt = true;
                    largestTime = _currentTime;
                    break;
                }

                int myTime = creation.get(target);
                if (!creation.containsKey(dependency)) {
                    continue;
                }

                int dependencyTime = creation.get(dependency);

                if (largestTime < dependencyTime) {
                    largestTime = dependencyTime;
                }
                if (myTime < dependencyTime) {
                    needsBuilt = true;
                    largestTime = _currentTime;
                    break;
                }
            }

            if (rule.getDependencies().size() == 0
                && !creation.containsKey(target)) {
                needsBuilt = true;
            }
            if (needsBuilt) {
                for (String command: rule.getCommands()) {
                    _out.println(command);
                    _out.flush();
                }

                creation.put(target, _currentTime);
            }
        }
    }
}
