package com.samlanning.robot_simulator.robots;

import javax.swing.JOptionPane;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;

public class RemoteRobot implements Robot {
    
    Object[] moves = { "Forwards", "Left", "Right", "Stop" };

    @Override
    public void run(RobotControl control) {
        
        while(true){
            doAStep(control);
        }
        
    }
    
    private void doAStep(RobotControl control){
        
        MapBlock ahead = control.lookAhead();
        
        int selection = JOptionPane.showOptionDialog(null,
                "The next block ahead is " + ahead.name(),
                "Next Move",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                moves,
                null);
            
            if(selection == 0){
                control.moveForward();
            } else if(selection == 1){
                control.turnLeft();
            } else if(selection == 2){
                control.turnRight();
            } else {
                control.stop();
            }
    }
    
}
