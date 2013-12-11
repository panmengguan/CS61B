package trip;

import graph.Weightable;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** The location class.
 *  @author Kiet Lam.*/
class Location implements Weightable {

    /** The name of this location.*/
    private String _name;

    /** The x coordinate of this location.*/
    private double _x;

    /** The y coordinate of this location.*/
    private double _y;

    /** The location pattern used to parse in from map.*/
    public static final String LOCATION_PATTERN =
        "^L\\s+([^\\s]+)\\s+([-+]?[0-9]*\\.?[0-9]+)\\s+([-+]?[0-9]*\\.?[0-9]+)";

    /** Create a location object basede on the string LOCATION.*/
    Location(String location) {
        Pattern pattern = Pattern.compile(LOCATION_PATTERN);
        Matcher matcher = pattern.matcher(location);
        matcher.matches();

        _name = matcher.group(1);
        _x = Double.parseDouble(matcher.group(2));
        _y = Double.parseDouble(matcher.group(3));
    }

    /** Create a location from NAMEC, XC, and YC.*/
    Location(String nameC, double xC, double yC) {
        _name = nameC;
        _x = xC;
        _y = yC;
    }

    /** Returns the name of this location.*/
    String name() {
        return _name;
    }

    /** Returns the x coordinate of this location.*/
    double x() {
        return _x;
    }

    /** Returns the y coordinate of this location.*/
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
