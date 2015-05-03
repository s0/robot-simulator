package com.samlanning.robot_simulator.iface;

import com.samlanning.robot_simulator.maps.MapBlock;

public interface RobotControl {
    
    public void moveForward();
    
    public void turnLeft();
    
    public void turnRight();
    
    public MapBlock lookAhead();
    
}
