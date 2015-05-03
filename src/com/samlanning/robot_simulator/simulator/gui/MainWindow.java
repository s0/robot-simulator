package com.samlanning.robot_simulator.simulator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.samlanning.robot_simulator.maps.BasicMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.gui.GUIState.Listener;

public class MainWindow extends JFrame {
    
    private final MapPanel mapPanel;
    private final GUIState state = new GUIState();
    
    public MainWindow(){
        
        this.setTitle("Robot Simulator");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mapPanel = new MapPanel(state);
        this.add(mapPanel, BorderLayout.CENTER);
        
        state.addListener(new Listener(){

            @Override
            public void update() {
                System.out.println("UPDATE!");
            }
            
        });
        
        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                state.init(new BasicMap(), Arrays.asList(RobotsEnum.values()));
                state.setRunning(true);
            }
            
        });
        this.add(start, BorderLayout.LINE_END);
        
        
    }
    
    
}
