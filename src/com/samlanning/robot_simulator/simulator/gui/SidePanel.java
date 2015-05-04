package com.samlanning.robot_simulator.simulator.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.samlanning.robot_simulator.maps.BasicMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.gui.GUIState.Listener;

public class SidePanel extends JPanel {
    
    private final GUIState state;
    
    protected SidePanel(GUIState state) {
        super(new BorderLayout());
        this.state = state;
        setup();
    }
    
    private void setup() {
        
        this.add(new Config(), BorderLayout.PAGE_START);
        this.add(new Buttons(), BorderLayout.PAGE_END);
        
    }
    
    private class Config extends JPanel {
        
        public Config() {
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            
            JLabel lblFramerate = new JLabel("Framerate:");
            final JTextField fieldFramerate = new JTextField();
            JButton btnFramerate = new JButton("Update");
            this.add(lblFramerate);
            this.add(new JPanel());
            this.add(fieldFramerate);
            this.add(btnFramerate);
            
            lblFramerate.setLabelFor(fieldFramerate);
            
            btnFramerate.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        state.setFramerate(Long.parseLong(fieldFramerate.getText()));
                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            
            JLabel lblAnimationDuration = new JLabel("Animation Duration:");
            final JTextField fieldAnimationDuration = new JTextField();
            JButton btnAnimationDuration = new JButton("Update");
            this.add(lblAnimationDuration);
            this.add(new JPanel());
            this.add(fieldAnimationDuration);
            this.add(btnAnimationDuration);
            
            lblAnimationDuration.setLabelFor(fieldAnimationDuration);
            
            btnAnimationDuration.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        state.setAnimationDuration(Long.parseLong(fieldAnimationDuration.getText()));
                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            
            JLabel lblDelay = new JLabel("Delay:");
            final JTextField fieldDelay = new JTextField();
            JButton btnDelay = new JButton("Update");
            this.add(lblDelay);
            this.add(new JPanel());
            this.add(fieldDelay);
            this.add(btnDelay);
            
            lblDelay.setLabelFor(fieldDelay);
            
            btnDelay.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        state.setDelay(Long.parseLong(fieldDelay.getText()));
                    } catch (NumberFormatException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            
            state.addListener(new Listener() {
                
                @Override
                public void update() {}
                
                @Override
                public void updateFrameDuration(long duration) {
                    fieldFramerate.setText(String.valueOf(1000 / duration));
                }
                
                @Override
                public void updateAnimationDuration(long duration) {
                    fieldAnimationDuration.setText(String.valueOf(duration));
                }
                
                @Override
                public void updateDelay(long delay) {
                    System.out.println("Update Delay: " + delay);
                    fieldDelay.setText(String.valueOf(delay));
                    
                }
                
                @Override
                public void updateRunning(boolean running) {}
                
            });
            
            // Layout
            SpringUtilities.makeCompactGrid(this, 6, 2, 6, 6, 6, 6);
        }
    }
    
    private class Buttons extends JPanel {
        
        public Buttons() {
            SpringLayout layout = new SpringLayout();
            this.setLayout(layout);
            
            final JButton btnStart = new JButton("Start");
            btnStart.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    state.init(new BasicMap(), Arrays.asList(RobotsEnum.values()));
                }
                
            });
            
            this.add(btnStart);
            
            final JButton btnToggle = new JButton("Play");
            btnToggle.addActionListener(new ActionListener() {
                
                @Override
                public void actionPerformed(ActionEvent e) {
                    state.toggle();
                }
                
            });
            
            this.add(btnToggle);
            
            // JButton btnStop = new JButton("Stop");
            // btnToggle.addActionListener(new ActionListener() {
            //
            // @Override
            // public void actionPerformed(ActionEvent e) {
            // state.init(new BasicMap(), Arrays.asList(RobotsEnum.values()));
            // state.stop();
            // }
            //
            // });
            //
            // this.add(btnStop);
            
            state.addListener(new Listener() {
                
                @Override
                public void update() {}
                
                @Override
                public void updateFrameDuration(long duration) {}
                
                @Override
                public void updateAnimationDuration(long duration) {}
                
                @Override
                public void updateDelay(long delay) {}
                
                @Override
                public void updateRunning(boolean running) {
                    if (running) {
                        btnToggle.setText("Pause");
                    } else {
                        btnToggle.setText("Play");
                    }  
                }
                
            });
            
            // Layout
            SpringUtilities.makeCompactGrid(this, 2, 1, 6, 6, 6, 6);
        }
        
    }
    
}
