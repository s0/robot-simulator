package com.samlanning.robot_simulator.simulator.gui;

import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.SwingUtilities;

import com.samlanning.robot_simulator.iface.RobotMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.executor.RobotState;
import com.samlanning.robot_simulator.simulator.executor.SimulatorExecutor;

public class GUIState {
    
    private final EnumMap<RobotsEnum, GUIRobotState> robots = new EnumMap<>(RobotsEnum.class);
    
    private RobotMap map;
    private SimulatorExecutor currentSimulation;
    private boolean running;
    
    private long frameDuration = 50;
    private long animationDuration = 100;
    private long robotActionDelay = 100;
    
    /**
     * Must only be accessed from swing dispatch thread
     */
    private Set<Listener> listeners = Collections.synchronizedSet(new HashSet<Listener>());
    
    protected synchronized void addListener(final Listener listener) {
        listeners.add(listener);
        listener.updateFrameDuration(frameDuration);
        listener.updateAnimationDuration(animationDuration);
        listener.updateDelay(robotActionDelay);
    }
    
    protected void init(RobotMap map, Collection<RobotsEnum> activatedRobots) {
        if (!javax.swing.SwingUtilities.isEventDispatchThread())
            throw new RuntimeException("Must be run in swing dispatch thread");
        
        this.map = map;
        
        robots.clear();
        stop();
        
        currentSimulation =
            new SimulatorExecutor(map, activatedRobots, new ExecutorListener(), animationDuration
                + robotActionDelay);
    }
    
    protected void toggle() {
        if (!javax.swing.SwingUtilities.isEventDispatchThread())
            throw new RuntimeException("Must be run in swing dispatch thread");
        
        if (currentSimulation != null) {
            running = !running;
            currentSimulation.setRunning(running);
            for (Listener l : listeners)
                l.updateRunning(running);
        }
    }
    
    protected void stop() {
        if (currentSimulation != null) {
            currentSimulation.stop();
            currentSimulation = null;
            running = false;
            for (Listener l : listeners)
                l.updateRunning(running);
        }
    }
    
    protected RobotMap map() {
        if (!javax.swing.SwingUtilities.isEventDispatchThread())
            throw new RuntimeException("Must be run in swing dispatch thread");
        
        return map;
    }
    
    protected Map<RobotsEnum, GUIRobotState> robots() {
        if (!javax.swing.SwingUtilities.isEventDispatchThread())
            throw new RuntimeException("Must be run in swing dispatch thread");
        
        return Collections.unmodifiableMap(robots);
    }
    
    protected synchronized void setFramerate(long framerate) {
        frameDuration = 1000 / framerate;
        for (Listener l : listeners)
            l.updateFrameDuration(frameDuration);
    }
    
    protected synchronized void setAnimationDuration(long duration) {
        animationDuration = duration;
        if (currentSimulation != null)
            currentSimulation.setDelay(animationDuration + robotActionDelay);
        for (Listener l : listeners)
            l.updateAnimationDuration(animationDuration);
    }
    
    protected synchronized void setDelay(long delay) {
        robotActionDelay = delay;
        if (currentSimulation != null)
            currentSimulation.setDelay(animationDuration + robotActionDelay);
        for (Listener l : listeners)
            l.updateDelay(delay);
    }
    
    public class GUIRobotState {
        public final RobotState state;
        public final RobotState previousState;
        public final long animatingFrom;
        public final long animatingTo;
        
        private GUIRobotState(GUIRobotState previous, RobotState state) {
            this.state = state;
            if (previous == null) {
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
        public void updateFrameDuration(long duration);
        public void updateAnimationDuration(long duration);
        public void updateDelay(long delay);
        public void updateRunning(boolean running);
    }
    
    public static abstract class Adapter implements Listener {

        @Override
        public void update() {}

        @Override
        public void updateFrameDuration(long duration) {}

        @Override
        public void updateAnimationDuration(long duration) {}

        @Override
        public void updateDelay(long delay) {}

        @Override
        public void updateRunning(boolean running) {}
        
    }
    
}
