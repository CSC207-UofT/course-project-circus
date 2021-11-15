package simplePathfinder;
import warehouse.Tile;

public class Point {
    public Tile tile;
    public Point previous;

    public Point(Tile tile, Point previous) {
        this.tile = tile;
        this.previous = previous;
    }

    @Override
    public boolean equals(Object o) {
        Point point = (Point) o;
        return tile.getX() == point.tile.getX() && tile.getY() == point.tile.getY();
    }

    @Override
    public String toString() { return String.format("(%d, %d)", tile.getX(), tile.getY()); }

    public Point offset(int ox, int oy) {
        return new Point(new Tile(tile.getX() +ox, tile.getY()+oy), this);
    }
}
