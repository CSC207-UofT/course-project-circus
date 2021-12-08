package query;

import org.junit.jupiter.api.Test;
import query.AndQuery;
import query.Query;
import warehouse.tiles.Tile;
import warehouse.Warehouse;
import warehouse.WarehouseController;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.inventory.PartCatalogue;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QueryTests {
    /**
     * Tests the satisfies method in the AndQuery class
     */
    @Test
    public void andQueryTest()
    {
        Query<Integer>[] q = new Query[2];
        q[0] = new Query<Integer>() {
            @Override
            public boolean satisfies(Integer value) {
                return value != 2;
            }
        };
        q[1] = new Query<Integer>() {
            @Override
            public boolean satisfies(Integer value) {
                return value == 2;
            }
        };

        AndQuery<Integer> aq = new AndQuery(q);

        assertFalse(aq.satisfies(2));
    }

    /**
     * Testing the Queryable interface
     */
    @Test
    public void testQueryable()
    {
        Queryable<Integer> qy = new Queryable<Integer>() {
            @Override
            public Iterable<Integer> getQueryItems() {
                return new ArrayList<Integer>();
            }
        };

        assertEquals(new ArrayList<Integer>(), qy.getQueryItems());
        Query<Integer> q = new Query<Integer>() {
            @Override
            public boolean satisfies(Integer value) {
                return 2 == value;
            }
        };

        ArrayList<Integer> l = new ArrayList<>();
        ArrayList<Integer> lst = (ArrayList<Integer>) qy.query(q);
        assertEquals(l, lst);
    }
}
