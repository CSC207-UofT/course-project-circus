package query;

import org.junit.jupiter.api.Test;
import utils.Pair;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class QueryTests {
    /**
     * Tests the satisfies method in the AndQuery class
     */
    @Test
    public void andQueryTest()
    {
        List<Query<Integer>> q = new ArrayList<>();
        q.add(value -> value != 2);
        q.add(value -> value == 2);

        AndQuery<Integer> aq = new AndQuery<>(q);
        assertFalse(aq.satisfies(2));
    }

    /**
     * Testing the Queryable interface
     */
    @Test
    public void testQueryable()
    {
        Queryable<Integer> qy = ArrayList::new;
        assertEquals(new ArrayList<Integer>(), qy.getQueryItems());
        Query<Integer> q = value -> 2 == value;

        ArrayList<Integer> l = new ArrayList<>();
        ArrayList<Integer> lst = (ArrayList<Integer>) qy.query(q);
        assertEquals(l, lst);
    }
}
