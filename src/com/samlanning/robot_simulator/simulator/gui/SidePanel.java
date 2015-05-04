package com.samlanning.robot_simulator.simulator.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.samlanning.robot_simulator.maps.BasicMap1;
import com.samlanning.robot_simulator.maps.MapsEnum;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.gui.GUIState.GUIRobotState;
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
        this.add(new Info(), BorderLayout.CENTER);
        
        
        final JButton btnToggle = new JButton("Play");
        btnToggle.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                state.toggle();
            }
            
        });
        
        this.add(btnToggle, BorderLayout.PAGE_END);
        
        state.addListener(new GUIState.Adapter() {
            
            @Override
            public void updateRunning(boolean running) {
                if (running) {
                    btnToggle.setText("Pause");
                } else {
                    btnToggle.setText("Play");
                }
            }
            
        });
        
        
        
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
            
            state.addListener(new GUIState.Adapter() {
                
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
                    fieldDelay.setText(String.valueOf(delay));
                    
                }
                
            });
            
            // Layout
            SpringUtilities.makeCompactGrid(this, 6, 2, 6, 6, 6, 6);
        }
    }
    
    private class Info extends JPanel {
        
        public Info() {
            super(new GridBagLayout());
            
            GridBagConstraints c = new GridBagConstraints();
            c.ipadx = 6;
            c.ipady = 6;
            c.gridy = 0;
            c.gridx = 0;
            c.anchor = GridBagConstraints.LINE_START;
            
            c.gridwidth = 2;
            this.add(new JLabel("Robots:"), c);
            c.gridwidth = 1;
            c.gridy++;
            
            // Robots
            
            final Map<RobotsEnum, JLabel> labelMap = new EnumMap<>(RobotsEnum.class);
            
            for (RobotsEnum robot : RobotsEnum.values()) {
                JLabel name = new JLabel(robot.name() + ": ");
                name.setForeground(robot.color);
                this.add(name, c);
                JLabel status = new JLabel("NONE");
                c.gridx = 1;
                this.add(status, c);
                c.gridy++;
                c.gridx = 0;
                labelMap.put(robot, status);
            }
            
            state.addListener(new GUIState.Adapter() {
                
                @Override
                public void update() {
                    for (Map.Entry<RobotsEnum, GUIRobotState> entry : state.robots().entrySet()) {
                        JLabel label = labelMap.get(entry.getKey());
                        label.setText(entry.getValue().state.status.toString());
                        switch(entry.getValue().state.status){
                            case STOPPED:
                                label.setForeground(null);
                                break;
                            case RUNNING:
                                label.setForeground(new Color(0, 200, 0));
                                break;
                            case CRASHED:
                                label.setForeground(new Color(200, 0, 0));
                                break;
                            case FINISHED:
                                label.setForeground(new Color(0, 0, 200));
                                break;
                        }
                    }
                }
                
            });
            
            // Maps
            
            c.gridy++;
            c.gridwidth = 2;
            this.add(new JLabel("Maps:"), c);
            c.gridwidth = 1;
            c.gridy++;
            
            for (final MapsEnum map : MapsEnum.values()) {
                JLabel name = new JLabel(map.name() + ": ");
                this.add(name, c);
                JButton status = new JButton("Load");
                c.gridx = 1;
                this.add(status, c);
                c.gridy++;
                c.gridx = 0;
                status.addActionListener(new ActionListener() {
                    
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        state.init(map.map, Arrays.asList(RobotsEnum.values()));
                    }
                    
                });
            }
            
        }
        
    }
    
}
