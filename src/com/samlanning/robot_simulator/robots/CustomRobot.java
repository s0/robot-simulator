package com.samlanning.robot_simulator.robots;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;

public class CustomRobot implements Robot {

    @Override
    public void run(final RobotControl control) {
        
        while(true){
            
            control.turnLeft();
            while(control.lookAhead() == MapBlock.WALL)
                control.turnRight();
            
            control.moveForward();
            
        }
        
    }
    
}
