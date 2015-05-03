package com.samlanning.robot_simulator.robots;

import com.samlanning.robot_simulator.iface.Robot;

/**
 * An enum of all robots that you want to make available.
 */
public enum RobotsEnum {
    
    EMPTY(new EmptyRobot()),
    ;
    
    public final Robot robot;
    
    private RobotsEnum(Robot robot){
        this.robot = robot;
    }
}
