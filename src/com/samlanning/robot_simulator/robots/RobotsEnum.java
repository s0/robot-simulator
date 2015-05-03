package com.samlanning.robot_simulator.robots;

import java.awt.Color;

import com.samlanning.robot_simulator.iface.Robot;

/**
 * An enum of all robots that you want to make available.
 */
public enum RobotsEnum {
    
    //EMPTY(new EmptyRobot(), new Color(100, 100, 100)),
    RANDOM_1(new RandomRobot(), new Color(0, 100, 100)),
    RANDOM_2(new RandomRobot(), new Color(100, 0, 100)),
    RANDOM_3(new RandomRobot(), new Color(100, 100, 0)),
    RANDOM_4(new RandomRobot(), new Color(0, 0, 100)),
    RANDOM_5(new RandomRobot(), new Color(100, 0, 1)),
    RANDOM_6(new RandomRobot(), new Color(0, 100, 0)),
    ;
    
    public final Robot robot;
    public final Color color;
    
    private RobotsEnum(Robot robot, Color color){
        this.robot = robot;
        this.color = color;
    }
}
