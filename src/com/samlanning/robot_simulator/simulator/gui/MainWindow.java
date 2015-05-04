package com.samlanning.robot_simulator.simulator.gui;

import java.awt.BorderLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class MainWindow extends JFrame {
    
    private final MapPanel mapPanel;
    private final GUIState state = new GUIState();
    
    public MainWindow(){
        
        this.setTitle("Robot Simulator");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
        
        mapPanel = new MapPanel(state);
        this.add(mapPanel, BorderLayout.CENTER);
        
        Box b = new Box(BoxLayout.Y_AXIS);
        b.add(new SidePanel(state));
        this.add(b, BorderLayout.LINE_END);
        
    }
    
    
}
