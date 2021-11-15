package warehouse;

import inventory.Item;
import messaging.Message;
import warehouse.storage.Rack;
import warehouse.storage.StorageUnit;

/**
 * A stateless 2D representation of the layout of a warehouse. A layout is a 2D grid of tiles, where each tile can
 * contain a StorageUnit such as a Rack or ReceiveDepot.
 */
public class Warehouse {
    private final int width;
    private final int height;
    private final Tile[][] tiles;

    private final Message<TileStorageUnitChangedMessageData> anyStorageUnitChangedMessage;
    private final Message<ItemDistributedMessageData> itemDistributedMessage;
    private final Message<ItemReceivedMessageData> itemReceivedMessage;

    /**
     * Construct an empty layout with the given width and height.
     * @param width  The width of the layout, in number of cells.
     * @param height The height of the layout, in number of cells.
     */
    public Warehouse(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        // Messaging
        anyStorageUnitChangedMessage = new Message<>();
        anyStorageUnitChangedMessage.addListener(this::onTileStorageUnitChanged);
        itemDistributedMessage = new Message<>();
        itemReceivedMessage = new Message<>();
        // Initialise tiles
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                tiles[x][y] = new Tile(x, y);
                // Hook into onStorageUnitChanged message
                tiles[x][y].getOnStorageUnitChangedMessage().addListener(anyStorageUnitChangedMessage);
            }
        }
    }

    /**
     * Called when a tile's storage unit changes.
     * @param data Message data.
     */
    private void onTileStorageUnitChanged(TileStorageUnitChangedMessageData data) {
        StorageUnit oldStorageUnit = data.getOldStorageUnit();
        if (oldStorageUnit != null) {
            oldStorageUnit.getOnItemDistributedMessage().removeListener(itemDistributedMessage);
            oldStorageUnit.getOnItemReceivedMessage().removeListener(itemReceivedMessage);
        }
        StorageUnit newStorageUnit = data.getTile().getStorageUnit();
        if (newStorageUnit != null) {
            newStorageUnit.getOnItemDistributedMessage().addListener(itemDistributedMessage);
            newStorageUnit.getOnItemReceivedMessage().addListener(itemReceivedMessage);
        }
    }

    /**
     * Get the tile at the specified coordinate.
     * @param x The horizontal coordinate of the tile.
     * @param y The vertical coordinate of the tile.
     * @return The tile at the specified coordinate.
     * @throws TileOutOfBoundsException if the specified coordinates is out of bounds.
     */
    public Tile getTileAt(int x, int y) throws TileOutOfBoundsException {
        if (isTileCoordinateInRange(x, y)) {
            return tiles[x][y];
        } else {
            throw new TileOutOfBoundsException(x, y, width, height);
        }
    }

    /**
     * Find an available Rack for the given Item.
     * @return a Tile object with a Rack that can contain the given Item.
     */
    public Tile findRackFor(Item item) {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Tile tile = tiles[x][y];
                StorageUnit storageUnit = tile.getStorageUnit();
                if (tile.isEmpty() || !(storageUnit instanceof Rack)) continue;
                if (storageUnit.canAddItem(item)) {
                    return tile;
                }
            }
        }
        return null;
    }

    /**
     * Check whether the given tile coordinate is out of bounds.
     * @param x The horizontal coordinate of the tile.
     * @param y The vertical coordinate of the tile.
     * @return True if the coordinate is within range (not out of bounds), and False otherwise.
     */
    public boolean isTileCoordinateInRange(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Get the width of this Warehouse.
     * @return an integer specifying the width of this Warehouse, in number of tiles.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of this Warehouse.
     * @return an integer specifying the height of this Warehouse, in number of tiles.
     */
    public int getHeight() {
        return height;
    }

    /**
     * This event is called whenever the StorageUnit of a tile in this Warehouse is changed.
     */
    public Message<TileStorageUnitChangedMessageData> getAnyStorageUnitChangedMessage() {
        return anyStorageUnitChangedMessage;
    }

    /**
     * This event is called when any Distributable in this Warehouse distributes an Item.
     */
    public Message<ItemDistributedMessageData> getItemDistributedMessage() {
        return itemDistributedMessage;
    }

    /**
     * This event is called when any Receivable in this Warehouse distributes an Item.
     */
    public Message<ItemReceivedMessageData> getItemReceivedMessage() {
        return itemReceivedMessage;
    }
}
