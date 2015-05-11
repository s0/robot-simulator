package com.samlanning.robot_simulator.robots;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;
import com.samlanning.robot_simulator.simulator.executor.Direction;
import com.samlanning.robot_simulator.util.ExpandableGrid;

public class AdvancedRobot implements Robot {
    
    @Override
    public void run(final RobotControl control) {
        
        ExpandableGrid<GridState> grid = new ExpandableGrid<>();
        LinkedList<Point> positionsToCheck = new LinkedList<>();
        InternalPosition positionAndDirection = new InternalPosition();
        positionsToCheck.addFirst(positionAndDirection.position);
        grid.set(0, 0, GridState.EMPTY);
        
        while (!positionsToCheck.isEmpty()) {
            Point nextPosition = positionsToCheck.removeFirst();
            // Check if we actually need to scan surrounding positions
            if (grid.get(nextPosition.x, nextPosition.y + 1) == null
                || grid.get(nextPosition.x, nextPosition.y - 1) == null
                || grid.get(nextPosition.x + 1, nextPosition.y) == null
                || grid.get(nextPosition.x - 1, nextPosition.y) == null) {
                goToPoint(control, grid, positionAndDirection, nextPosition);
                scanSurrounding(control, positionAndDirection, grid, positionsToCheck);
            }
        }
        
    }
    
    private void goToPoint(RobotControl control, ExpandableGrid<GridState> grid,
        InternalPosition positionAndDirection, Point target) {
        if (positionAndDirection.position != target) {
            Set<Point> queued = new HashSet<>();
            LinkedList<PartialRoute> routes = new LinkedList<>();
            queued.add(positionAndDirection.position);
            routes.add(new PartialRoute(null, positionAndDirection.position));
            while (!routes.isEmpty()) {
                PartialRoute next = routes.removeFirst();
                for (Direction direction : Direction.values()) {
                    Point checkPoint = getPointAtDirection(next.position, direction);
                    if (grid.get(checkPoint.x, checkPoint.y) == GridState.EMPTY
                        && !queued.contains(checkPoint)) {
                        queued.add(checkPoint);
                        PartialRoute newRoute = new PartialRoute(next, checkPoint);
                        if (checkPoint.equals(target)) {
                            performRoute(control, positionAndDirection, newRoute);
                            return;
                        } else {
                            routes.addLast(newRoute);
                        }
                    }
                }
            }
        }
    }
    
    private void performRoute(RobotControl control, InternalPosition positionAndDirection,
        PartialRoute route) {
        if (route.previous != null)
            performRoute(control, positionAndDirection, route.previous);
        
        if (route.position.equals(positionAndDirection.position))
            // No Action Required
            return;
        
        // Work out required action
        for (Direction direction : Direction.values()) {
            Point checkPoint = getPointAtDirection(positionAndDirection.position, direction);
            if (checkPoint.equals(route.position)) {
                // Move in this direction
                turnTo(control, positionAndDirection, direction);
                control.moveForward();
                positionAndDirection.position = route.position;
                return;
            }
        }
        throw new InternalError("Unable to reach route segment");
    }
    
    private void scanSurrounding(RobotControl control, InternalPosition pos,
        ExpandableGrid<GridState> grid, LinkedList<Point> positionsToCheck) {
        
        Direction checkDirection = pos.direction;
        for (int i = 0; i < 4; i++) {
            Point checkPoint = getPointAtDirection(pos.position, checkDirection);
            if (grid.get(checkPoint.x, checkPoint.y) == null) {
                turnTo(control, pos, checkDirection);
                
                // Check what is infront
                switch (control.lookAhead()) {
                    case EMPTY:
                        positionsToCheck.addFirst(checkPoint);
                        grid.set(checkPoint.x, checkPoint.y, GridState.EMPTY);
                        break;
                    case WALL:
                        grid.set(checkPoint.x, checkPoint.y, GridState.WALL);
                        break;
                    case FINISH:
                        // Finish Immidiately
                        control.moveForward();
                        
                }
                
            }
            checkDirection = checkDirection.clockwise();
        }
    }
    
    private void turnTo(RobotControl control, InternalPosition pos, Direction direction) {
        while (pos.direction != direction) {
            if(pos.direction.anticlockwise() == direction){
                pos.direction = pos.direction.anticlockwise();
                control.turnLeft();
            } else {
                pos.direction = pos.direction.clockwise();
                control.turnRight();
            }
        }
    }
    
    private static Point getPointAtDirection(Point from, Direction direction) {
        return new Point(from.x + direction.vectorX(), from.y + direction.vectorY());
    }
    
    private static class InternalPosition {
        Point position = new Point(0, 0);
        Direction direction = Direction.UP;
    }
    
    private static class PartialRoute {
        private final Point position;
        private final PartialRoute previous;
        
        public PartialRoute(PartialRoute previous, Point position) {
            this.previous = previous;
            this.position = position;
        }
    }
    
    private static enum GridState {
        EMPTY, WALL
    }
    
}
