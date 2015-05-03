package com.samlanning.robot_simulator.simulator.executor;

public enum Direction {
    UP, RIGHT, DOWN, LEFT;
    
    public Direction clockwise() {
        return Direction.values()[(this.ordinal() + 1) % 4];
    }
    
    public Direction anticlockwise() {
        int ordinal = (this.ordinal() - 1) % 4;
        return Direction.values()[ordinal < 0 ? ordinal + 4 : ordinal];
    }
    
    public int vectorX() {
        switch (this) {
            case LEFT:
                return -1;
            case RIGHT:
                return 1;
            default:
                return 0;
        }
    }
    
    public int vectorY() {
        switch (this) {
            case UP:
                return -1;
            case DOWN:
                return 1;
            default:
                return 0;
        }
    }
    
}