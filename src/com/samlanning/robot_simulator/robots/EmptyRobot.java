package com.samlanning.robot_simulator.robots;

import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;

public class EmptyRobot implements Robot {

    @Override
    public void run(final RobotControl control) {
        
        for(int i = 0; i < 3; i++){
            new Thread(){
                @Override
                public void run(){
                    dos(control);
                }
            }.start();
        }
        
        
    }
    
    public void dos(RobotControl control) {
        while(true){
            control.moveForward();
            control.turnLeft();
            control.turnRight();
        }
    }
    
}
