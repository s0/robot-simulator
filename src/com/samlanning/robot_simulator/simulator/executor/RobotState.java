package com.samlanning.robot_simulator.simulator.executor;

public final class RobotState {
    public final int x;
    public final int y;
    public final Direction direction;
    public final boolean running;
    
    protected RobotState(int x, int y, Direction direction, boolean running){
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.running = running;
    }
}