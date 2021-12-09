package application.shell.presenters.coordinateparsers;

import warehouse.geometry.WarehouseCoordinate;

/**
 * Parses a string and converts it to a coordinate.
 */
public interface CoordinateParser<T extends WarehouseCoordinate> {
    /**
     * Parse the given string.
     * @param value A string representation of a coordinate.
     * @return the coordinate represented by the given string, or null if the string could not be parsed.
     */
    T parse(String value);
}
