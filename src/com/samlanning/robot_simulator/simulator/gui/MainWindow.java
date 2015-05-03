package com.samlanning.robot_simulator.simulator.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
    
    private final JPanel mapPanel;
    
    public MainWindow(){
        
        this.setTitle("Robot Simulator");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mapPanel = new JPanel();
        this.add(mapPanel, BorderLayout.CENTER);
        
        
    }
    
    
}
