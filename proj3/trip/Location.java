package trip;

import graph.Weightable;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Location implements Weightable {

    String _name;

    double _x;

    double _y;

    public static final String LOCATION_PATTERN =
        "^L\\s+([^\\s]+)\\s+([-+]?[0-9]*\\.?[0-9]+)\\s+([-+]?[0-9]*\\.?[0-9]+)";

    Location(String location) {
        Pattern pattern = Pattern.compile(LOCATION_PATTERN);
        Matcher matcher = pattern.matcher(location);
        matcher.matches();

        _name = matcher.group(1);
        _x = Double.parseDouble(matcher.group(2));
        _y = Double.parseDouble(matcher.group(3));
    }

    Location(String nameC, double xC, double yC) {
        _name = nameC;
        _x = xC;
        _y = yC;
    }

    String name() {
        return _name;
    }

    double x() {
        return _x;
    }

    double y() {
        return _y;
    }

    @Override
    public void setWeight(double d) {
    }

    @Override
    public double weight() {
        return 0.0;
    }
}
