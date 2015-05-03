package com.samlanning.robot_simulator.robots;

import java.util.Random;

import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;
import com.samlanning.robot_simulator.maps.MapBlock;

public class RandomRobot implements Robot {

    @Override
    public void run(final RobotControl control) {
        
        Random r = new Random();
        
        while(true){
            int nextIntBound = control.lookAhead() == MapBlock.WALL ? 2 : 3;
            switch(r.nextInt(nextIntBound)){
                case 0:
                    control.turnLeft();
                    break;
                case 1:
                    control.turnRight();
                    break;
                case 2:
                    control.moveForward();
                    break;
            }
        }
    }
    
}
