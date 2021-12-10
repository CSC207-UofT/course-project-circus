package application.desktop.adapters;


import warehouse.WarehouseState;
import warehouse.geometry.grid.GridWarehouseCoordinateSystem;
import warehouse.geometry.grid.Point;
import warehouse.logistics.optimization.graph.TileNode;
import warehouse.logistics.optimization.graph.converters.SimpleWarehouseGraphConverter;
import warehouse.logistics.orders.NavigateOrder;
import warehouse.logistics.orders.Order;
import warehouse.logistics.orders.OrderStatus;
import warehouse.robots.Robot;
import warehouse.robots.RobotAdapter;
import warehouse.robots.RobotMapper;
import warehouse.tiles.Tile;

import java.util.ArrayList;
import java.util.List;

/**
 * An adapter between the robot entity and a physical robot in the world. This can be thought of as a class that
 * interprets real-world signals and updates the Robot model accordingly. In our case, this is simply going to take
 * the floating-point position of the robot and associate it with the nearest tile in the warehouse.
 */
public class PhysicalGridRobot extends RobotAdapter<GridWarehouseCoordinateSystem, Point> {
    /**
     * The speed of the Robot.
     */
    private float speed = 0.05f;

    /**
     * The X position of the Robot, in metres. A tile is 1x1.
     */
    private float x;
    /**
     * The Y position of the Robot, in metres. A tile is 1x1.
     */
    private float y;

    private List<Tile> currentWaypoints;
    private int currentWaypointIndex;
    private float routeNodeLerpAlpha;

    private Tile currentSource;
    /**
     * The current route that the robot is following.
     */
    private List<Tile> currentRouteNodes;
    private int currentRouteNodeIndex;

    private final SimpleWarehouseGraphConverter<GridWarehouseCoordinateSystem, Point> graphConverter;

    /**
     * Construct a PhysicalGridRobot.
     * @param robotModel The Robot model.
     * @param warehouseModel The Warehouse model.
     */
    public PhysicalGridRobot(Robot robotModel, WarehouseState<GridWarehouseCoordinateSystem, Point> warehouseModel) {
        super(robotModel, warehouseModel);
        Point point = warehouseModel.getRobotMapper().getRobotPosition(robotModel);
        this.x = point.getX();
        this.y = point.getY();
        graphConverter = new SimpleWarehouseGraphConverter<>();
    }

    /**
     * Update the Robot associated with this adapter.
     */
    @Override
    public void update() {
        handleOrder();

        RobotMapper<Point> robotMapper = warehouseStateModel.getRobotMapper();
        robotMapper.setRobotPosition(robotModel, new Point(Math.round(x), Math.round(y)));
    }

    /**
     * Handle current order.
     */
    private void handleOrder() {
        Order order = robotModel.getOrder();
        if (order == null || order.getStatus() == OrderStatus.COMPLETE) {
            return;
        }

        if (!(order instanceof NavigateOrder)) {
            return;
        }
        if (order.getStatus() == OrderStatus.ASSIGNED) {
            // Make the order in progress
            order.setInProgress();
            currentWaypoints = ((NavigateOrder) order).getWaypoints();

            RobotMapper<Point> robotMapper = warehouseStateModel.getRobotMapper();
            Tile robotTile = warehouseStateModel.getLayout().getTileAt(robotMapper.getRobotPosition(robotModel));
            currentWaypoints.add(0, robotTile);
            currentWaypointIndex = 0;
        }

        // Compute path
        if (currentWaypointIndex < currentWaypoints.size() - 1) {
            if (currentRouteNodes == null) {
                Tile source = currentWaypoints.get(currentWaypointIndex);
                Tile destination = currentWaypoints.get(currentWaypointIndex + 1);
                List<TileNode> nodes = robotModel.getRoutefinder().findRoute(
                        graphConverter.convert(warehouseStateModel),
                        new TileNode(source),
                        new TileNode(destination));
                currentRouteNodes = new ArrayList<>();
                for (TileNode node : nodes) {
                    currentRouteNodes.add(node.getTile());
                }
                currentSource = source;
                currentRouteNodeIndex = 0;
            } else {
                // Move in the direction of the route tiles...
                if (currentRouteNodeIndex < currentRouteNodes.size() - 1) {
                    GridWarehouseCoordinateSystem coordinateSystem = warehouseStateModel.getCoordinateSystem();
                    Tile source = currentRouteNodes.get(currentRouteNodeIndex);
                    Point p1 = coordinateSystem.projectIndexToCoordinate(source.getIndex());
                    Tile destination = currentRouteNodes.get(currentRouteNodeIndex + 1);
                    Point p2 = coordinateSystem.projectIndexToCoordinate(destination.getIndex());
                    // Apply linear interpolation on p1 and p2
                    x = p1.getX() + routeNodeLerpAlpha * (p2.getX() - p1.getX());
                    y = p1.getY() + routeNodeLerpAlpha * (p2.getY() - p1.getY());
                    // Increment linear interpolation parameter
                    routeNodeLerpAlpha += speed;
                    if (routeNodeLerpAlpha >= 1) {
                        routeNodeLerpAlpha = 0;
                        currentRouteNodeIndex += 1;
                    }
                } else {
                    currentRouteNodes = null;
                    currentWaypointIndex += 1;
                }
            }
        } else {
            // We're done!
            order.setComplete();
        }
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public List<Tile> getCurrentRouteNodes() {
        return currentRouteNodes;
    }

    public Tile getCurrentSource() {
        return currentSource;
    }
}
