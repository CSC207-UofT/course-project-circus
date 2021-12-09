package warehouse.transactions;
import messaging.Message;
import org.junit.jupiter.api.Test;
import query.Query;
import warehouse.inventory.Item;
import warehouse.inventory.Part;
import warehouse.tiles.Tile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransactionsTests {

    @Test
    public void testItemDistributedMessageData()
    {
        Part part = new Part("1", "Empty", "An Empty Part");
        Item item = new Item(part.getId(), part);
        Distributable d = new Distributable() {
            @Override
            public Item distributeItem(Query<Item> query) {
                return null;
            }

            @Override
            public Tile getTile() {
                return null;
            }

            @Override
            public Message<ItemDistributedMessageData> getOnItemDistributedMessage() {
                return null;
            }

            @Override
            public Iterable<Item> getQueryItems() {
                return null;
            }
        };
        ItemDistributedMessageData idmd = new ItemDistributedMessageData(item, d);
        assertEquals(item, idmd.getItem());
        assertEquals(d, idmd.getDistributable());
    }

    @Test
    public void testItemReceivedMessageData()
    {
        Part part = new Part("1", "Empty", "An Empty Part");
        Item item = new Item(part.getId(), part);
        Receivable r = new Receivable() {
            @Override
            public boolean receiveItem(Item item) {
                return false;
            }

            @Override
            public Tile getTile() {
                return null;
            }

            @Override
            public Message<ItemReceivedMessageData> getOnItemReceivedMessage() {
                return null;
            }
        };

        ItemReceivedMessageData irmd = new ItemReceivedMessageData(item, r);
        assertEquals(item, irmd.getItem());
        assertEquals(r, irmd.getReceivable());
    }
}
