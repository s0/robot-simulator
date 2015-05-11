package com.samlanning.robot_simulator.simulator;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.samlanning.robot_simulator.simulator.gui.MainWindow;

public class GUISimulator {
    
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
            | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
            // Ignore
        }
        new MainWindow().setVisible(true);;
    }
    
}
