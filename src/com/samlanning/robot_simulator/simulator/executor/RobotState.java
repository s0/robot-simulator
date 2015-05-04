package com.samlanning.robot_simulator.simulator.executor;

public final class RobotState {
    public final int x;
    public final int y;
    public final Direction direction;
    public final Status status;
    
    protected RobotState(int x, int y, Direction direction, Status status){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.status = status;
    }
    
    public static enum Status {
        RUNNING,
        STOPPED,
        CRASHED,
        FINISHED
    }
}