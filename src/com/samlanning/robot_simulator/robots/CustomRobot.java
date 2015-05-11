package com.samlanning.robot_simulator.robots;

import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;

public class CustomRobot implements Robot {

    @Override
    public void run(final RobotControl control) {
        control.moveForward();
        control.moveForward();
        control.turnLeft();
        control.turnRight();
        control.moveForward();
    }
    
}
