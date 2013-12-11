package trip;

import graph.Weighted;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

/** The class to represent a distance in a map.
 *  @author Kiet Lam .*/
class Distance implements Weighted {

    /** C0.*/
    private String _c0;

    /** The other place, C1.*/
    private String _c1;

    /** The name of the road connection C0 and C1.*/
    private String _road;

    /** The route of this road, is either NS, EW, WE, or SN.*/
    private String _route;

    /** The distance for the road connection C0 and C1.*/
    private double _distance;

    /** The distance regex pattern.*/
    public static final String DISTANCE_PATTERN =
        "^R\\s+([^\\s]+)\\s+([^\\s]+)\\s+([-+]?[0-9]*\\.?[0-9]+)\\s+"
        + "([^\\s]+)\\s+([^\\s]+)";

    /** Create a distance object on the string DISTANCE.*/
    Distance(String distance) {
        Pattern pattern = Pattern.compile(DISTANCE_PATTERN);
        Matcher matcher = pattern.matcher(distance);
        matcher.matches();

        _c0 = matcher.group(1);
        _road = matcher.group(2);
        _distance = Double.parseDouble(matcher.group(3));
        _route = matcher.group(4);
        _c1 = matcher.group(5);

        if (!_route.equals("NS") && !_route.equals("EW")
            && !_route.equals("WE") && !_route.equals("SN")) {
            Main.reportErrorExit("Invalid compass direction");
        }
    }

    @Override
    public double weight() {
        return _distance;
    }

    /** Returns the direction to get to C.*/
    public String directionTo(String c) {
        if (!c.equals(_c0) && !c.equals(_c1)) {
            Main.reportErrorExit("Invalid location");
        }

        boolean ns = _route.equals("NS");
        boolean ew = _route.equals("EW");
        boolean we = _route.equals("WE");
        boolean sn = _route.equals("SN");
        boolean opposite = c.equals(_c1);

        if (ns) {
            if (opposite) {
                return "north";
            } else {
                return "south";
            }
        } else if (ew) {
            if (opposite) {
                return "east";
            } else {
                return "west";
            }
        } else if (we) {
            if (opposite) {
                return "west";
            } else {
                return "east";
            }
        } else {
            if (opposite) {
                return "south";
            } else {
                return "north";
            }
        }
    }

    /** Returns the identifier for this distance by
     *  combining the name of the road and the route.*/
    public String identifier() {
        return _road + _route;
    }

    /** Returns the opposite of OTHER.*/
    public String getC(String other) {
        if (other.equals(_c1)) {
            return _c0;
        } else if (other.equals(_c0)) {
            return _c1;
        } else {
            Main.reportErrorExit("Location does not exist");
            return "";
        }
    }

    /** Returns C0.*/
    public String c0() {
        return _c0;
    }

    /** Returns the other place, C1.*/
    public String c1() {
        return _c1;
    }

    /** Returns the name of the road connection C0 and C1.*/
    public String road() {
        return _road;
    }

    /** Returns the route of this road, is either NS, EW, WE, or SN.*/
    public String route() {
        return _route;
    }

    /** Returns the distance for the road connection C0 and C1.*/
    public double distance() {
        return _distance;
    }
}
