package application.shell.presenters.coordinateparsers;

import warehouse.geometry.grid.Point;

/**
 * Parses a string of the form (x, y).
 */
public class PointParser implements CoordinateParser<Point>  {
    @Override
    public Point parse(String value) {
        if (!value.startsWith("(") || !value.endsWith(")")) {
            return null;
        }
        String[] parts = value.substring(1, value.length() - 1).split(",");
        if (parts.length != 2) return null;
        try {
            int x = Integer.parseInt(parts[0].strip());
            int y = Integer.parseInt(parts[1].strip());
            return new Point(x, y);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
