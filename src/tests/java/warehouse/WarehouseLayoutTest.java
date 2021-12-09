package warehouse;

import org.junit.jupiter.api.Test;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.tiles.EmptyTile;
import warehouse.tiles.Rack;
import warehouse.tiles.Tile;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class WarehouseLayoutTest {
    @Test
    public void testGetTileAt() {
        WarehouseLayout<Point> warehouseLayout = new WarehouseLayout<>(
                new GridWarehouseCoordinateSystem(10, 10));
        // By default, the warehouse is initialised to empty, so every tile should be empty!
        // Let's make sure that getTileAt gives empty tiles for every index in [0, 0] x (10, 10)
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = warehouseLayout.getTileAt(new Point(i, j));
                assertTrue(tile instanceof EmptyTile);
            }
        }
        // Let's try to get an out-of-bounds tile. This should give null!
        assertNull(warehouseLayout.getTileAt(new Point(11, 10)));
        // Let's set a tile in the warehouse, and make sure that the getTileAt method returns that tile.
        Rack rack = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(new Point(5, 5), rack); // Associate rack with the coordinate (5,5)
        assertEquals(warehouseLayout.getTileAt(new Point(5, 5)), rack);
    }

    @Test
    public void testSetTileAt1() {
        WarehouseLayout<Point> warehouseLayout = new WarehouseLayout<>(
                new GridWarehouseCoordinateSystem(10, 10));
        // Let's set a tile in the warehouse, and make sure that the tile list contains it!
        Rack rack = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(new Point(5, 5), rack); // Associate rack with the coordinate (5,5)
        // Now, the layout consists of empty tiles except for a single rack at (5,5).
        List<Tile> tiles = warehouseLayout.getTiles();
        for (int i = 0; i < tiles.size(); i++) {
            if (i == rack.getIndex()) {
                assertEquals(rack, tiles.get(i));
            } else {
                assertTrue(tiles.get(i) instanceof EmptyTile);
            }
        }
    }

    @Test
    public void testSetTileAt2() {
        WarehouseLayout<Point> warehouseLayout = new WarehouseLayout<>(
                new GridWarehouseCoordinateSystem(10, 10));
        // Let's set a tile in the warehouse, and make sure that the tile list contains it!
        Rack rack = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(0, rack); // Associate rack with the coordinate (5,5)
        // Now, the layout consists of empty tiles except for a single rack at index 0 (the coordinate (0, 0)).
        List<Tile> tiles = warehouseLayout.getTiles();
        for (int i = 0; i < tiles.size(); i++) {
            if (i == rack.getIndex()) {
                assertEquals(rack, tiles.get(i));
            } else {
                assertTrue(tiles.get(i) instanceof EmptyTile);
            }
        }
    }

    @Test
    public void testFindTilesOfType1() {
        WarehouseLayout<Point> warehouseLayout = new WarehouseLayout<>(
                new GridWarehouseCoordinateSystem(10, 10));
        // By default, the warehouse is initialised to empty, so every tile should be empty!
        // Therefore, finding empty tiles should give us the whole layout!
        Set<Tile> tiles = new HashSet<>(warehouseLayout.getTiles());
        for (Tile tile : warehouseLayout.findTilesOfType(EmptyTile.class)) {
            assertTrue(tiles.contains(tile));
        }
        // Now, let's set one tile to be a Rack
        Rack rack1 = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(new Point(5, 5), rack1); // Associate rack with the coordinate (5,5)
        // Make sure that we get only one rack when we try finding rack tiles
        List<Rack> racks = warehouseLayout.findTilesOfType(Rack.class);
        assertEquals(1, racks.size());
        assertEquals(rack1, racks.get(0));
        // Let's add a few more racks.
        Rack rack2 = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(new Point(2, 3), rack2); // Associate rack with the coordinate (2,3)
        Rack rack3 = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(new Point(5, 1), rack3); // Associate rack with the coordinate (2,3)
        // Now, let's try again
        racks = warehouseLayout.findTilesOfType(Rack.class);
        racks.sort(Comparator.comparingInt(Tile::getIndex));
        assertEquals(3, racks.size());
        assertEquals(rack3, racks.get(0));
        assertEquals(rack2, racks.get(1));
        assertEquals(rack1, racks.get(2));
    }

    @Test
    public void testIsEmpty() {
        WarehouseLayout<Point> warehouseLayout = new WarehouseLayout<>(
                new GridWarehouseCoordinateSystem(10, 10));
        // By default, the warehouse is initialised to empty, so every tile should be empty!
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Tile tile = warehouseLayout.getTileAt(new Point(i, j));
                assertTrue(warehouseLayout.isEmpty(tile));
            }
        }
        Rack rack = new Rack(); // Make an empty unassociated Rack
        warehouseLayout.setTileAt(new Point(5, 5), rack); // Associate rack with the coordinate (5,5)
        // the Rack isn't empty
        assertFalse(warehouseLayout.isEmpty(rack));
    }
}
