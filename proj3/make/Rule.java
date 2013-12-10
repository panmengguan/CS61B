package make;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/** Our make rule class.
 *  @author Kiet Lam.*/
class Rule {

    /** The target for this rule.*/
    private String _target;

    /** The dependencies for this rule.*/
    private List<String> _dependencies;

    /** The commands associated with this rule.*/
    private List<String> _commands;

    /** Construct a rule with target TARGET, dependencies DEPENDENCIES
     *  set of commands COMMANDS.*/
    Rule(String target, List<String> dependencies, List<String> commands) {
        _target = target;
        _dependencies = dependencies;
        _commands = commands;

        _dependencies.removeAll(Collections.singleton(""));
    }

    /** Construct a rule with target TARGET and dependencies DEPENDENCIES.*/
    Rule(String target, String[] dependencies) {
        this(target, new ArrayList<String>(Arrays.asList(dependencies)));
    }

    /** Construct a rule with target TARGET and dependencies DEPENDENCIES.*/
    Rule(String target, List<String> dependencies) {
        this(target, dependencies, new ArrayList<String>());
    }

    /** Add command COMMAND to this rule.*/
    void addCommand(String command) {
        _commands.add(command);
    }

    /** Add the list of COMMANDS to this rule.*/
    void addCommands(List<String> commands) {
        _commands.addAll(commands);
    }

    /** Returns the target of this rule.*/
    String getTarget() {
        return _target;
    }

    /** Returns the list of dependencies for this rule.*/
    List<String> getDependencies() {
        return _dependencies;
    }

    /** Returns the list of commands associated with this rule.*/
    List<String> getCommands() {
        return _commands;
    }
}
