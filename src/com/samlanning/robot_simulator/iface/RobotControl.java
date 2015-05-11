package com.samlanning.robot_simulator.iface;


public interface RobotControl {
    
    public void moveForward();
    
    public void turnLeft();
    
    public void turnRight();
    
    public void stop();
    
    public MapBlock lookAhead();
    
}
