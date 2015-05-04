package com.samlanning.robot_simulator.simulator.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import com.samlanning.robot_simulator.maps.RobotMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.executor.RobotState;
import com.samlanning.robot_simulator.simulator.executor.SimulatorExecutor;

public class GUIState {
    
    private final EnumMap<RobotsEnum, GUIRobotState> robots = new EnumMap<>(RobotsEnum.class);
    
    private RobotMap map;
    private SimulatorExecutor currentSimulation;
    
    private long animationDuration = 500;
    
    /**
     * Must only be accessed from swing dispatch thread
     */
    private Set<Listener> listeners = Collections.synchronizedSet(new HashSet<Listener>());
    
    protected void addListener(final Listener listener) {
        listeners.add(listener);
    }
    
    protected void init(RobotMap map, Collection<RobotsEnum> activatedRobots) {
        if(!javax.swing.SwingUtilities.isEventDispatchThread())
            throw new RuntimeException("Must be run in swing dispatch thread");
        
        this.map = map;
        
        robots.clear();
        if (currentSimulation != null)
            currentSimulation.stop();
        
        currentSimulation = new SimulatorExecutor(map, activatedRobots, new ExecutorListener());
    }
    
    protected void setRunning(boolean running) {
        currentSimulation.setRunning(running);
    }
    
    protected RobotMap map(){
        return map;
    }
    
    protected Map<RobotsEnum, GUIRobotState> robots(){
        return Collections.unmodifiableMap(robots);
    }
    
    public class GUIRobotState {
        public final RobotState state;
        public final RobotState previousState;
        public final long animatingFrom;
        public final long animatingTo;
        
        
        private GUIRobotState(GUIRobotState previous, RobotState state){
            this.state = state;
            if(previous == null){
                animatingFrom = animatingTo = System.currentTimeMillis();
                this.previousState = null;
            } else {
                animatingFrom = System.currentTimeMillis();
                animatingTo = animatingFrom + animationDuration;
                this.previousState = previous.state;
            }
        }
    }
    
    private class ExecutorListener implements SimulatorExecutor.Listener {
        
        @Override
        public synchronized void newRobotState(final RobotsEnum robot, final RobotState state) {
            SwingUtilities.invokeLater(new Runnable() {
                
                @Override
                public void run() {
                    
                    robots.put(robot, new GUIRobotState(robots.get(robot), state));
                    
                    for (Listener listener : listeners)
                        listener.update();
                }
                
            });
        }
    }
    
    protected static interface Listener {
        public void update();
    }
    
}
