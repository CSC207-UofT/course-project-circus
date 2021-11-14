package pathfinding;

import warehouse.Rack;
import warehouse.Tile;
import warehouse.TileOutOfBoundsException;
import warehouse.Warehouse;

import java.util.ArrayList;
import java.util.List;

public class SimplePathFinder {


    public static class Point {
        public Tile tile;
        public Point previous;

        public Point(Tile cell, Point previous) {
            this.tile = cell;
            this.previous = previous;
        }

        @Override
        public boolean equals(Object o) {
            Point point = (Point) o;
            return tile.getX() == point.tile.getX() && tile.getY() == point.tile.getY();
        }

        public String toString() { return String.format("(%d, %d)", tile.getX(), tile.getY()); }



        public Point offset(int ox, int oy) {
            return new Point(new Tile(tile.getX() +ox, tile.getY()+oy), this);
        }
    }

    public static boolean IsWalkable(Warehouse map, Point point) throws TileOutOfBoundsException {
        if (point.tile.getY() < 0 || point.tile.getY() > map.getHeight() - 1) return false;
        if (point.tile.getX() < 0 || point.tile.getX() > map.getWidth() - 1) return false;
        return map.getTileAt(point.tile.getX(),point.tile.getY()).isEmpty();
    }

    public static List<Point> FindNeighbors(Warehouse map, Point point) throws TileOutOfBoundsException {
        List<Point> neighbors = new ArrayList<>();
        Point up = point.offset(0,  1);
        Point down = point.offset(0,  -1);
        Point left = point.offset(-1, 0);
        Point right = point.offset(1, 0);
        if (IsWalkable(map, up)) neighbors.add(up);
        if (IsWalkable(map, down)) neighbors.add(down);
        if (IsWalkable(map, left)) neighbors.add(left);
        if (IsWalkable(map, right)) neighbors.add(right);
        return neighbors;
    }

    public static List<Point> FindPath(Warehouse map, Tile start, Tile end) throws TileOutOfBoundsException {
        Point first = new Point(start, null);
        Point last  = new Point(end, null );
        boolean finished = false;
        List<Point> used = new ArrayList<>();
        used.add(first);
        while (!finished) {
            List<Point> newOpen = new ArrayList<>();
            for(int i = 0; i < used.size(); ++i){
                Point point = used.get(i);
                for (Point neighbor : FindNeighbors(map, point)) {
                    if (!used.contains(neighbor) && !newOpen.contains(neighbor)) {
                        newOpen.add(neighbor);
                    }
                }
            }

            for(Point point : newOpen) {
                used.add(point);
                if (last.equals(point)) {
                    finished = true;
                    break;
                }
            }

            if (!finished && newOpen.isEmpty())
                return null;
        }

        List<Point> path = new ArrayList<>();
        Point point = used.get(used.size() - 1);
        while(point.previous != null) {
            path.add(0, point);
            point = point.previous;
        }
        return path;
    }

    public static void main(String[] args) throws TileOutOfBoundsException {
        Warehouse map = new Warehouse(10, 10);
        Tile start = new Tile(0, 0);
        Tile end = new Tile(2, 2);
        map.getTileAt(0,1).setStorageUnit(new Rack(5));
        map.getTileAt(1,1).setStorageUnit(new Rack(5));
        map.getTileAt(2,1).setStorageUnit(new Rack(5));


        List<Point> path = FindPath(map, start, end);
        if (path != null) {
            for (Point point : path) {
                System.out.println(point);
            }
        }
        else
            System.out.println("No path found");
    }
}


