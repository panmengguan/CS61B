package make;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Rule {

    String _target;
    List<String> _dependencies;
    List<String> _commands;

    Rule(String target, List<String> dependencies, List<String> commands) {
        _target = target;
        _dependencies = dependencies;
        _commands = commands;
    }

    Rule(String target, String[] dependencies) {
        this(target, new ArrayList<String>(Arrays.asList(dependencies)));
    }

    Rule(String target, List<String> dependencies) {
        this(target, dependencies, new ArrayList<String>());
    }

    void addCommand(String command) {
        _commands.add(command);
    }

    void addCommands(List<String> commands) {
        _commands.addAll(commands);
    }
}
