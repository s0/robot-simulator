package com.samlanning.robot_simulator.simulator;

import java.io.IOException;
import java.util.EnumMap;

import com.samlanning.robot_simulator.maps.BasicMap;
import com.samlanning.robot_simulator.maps.RobotMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.executor.RobotState;
import com.samlanning.robot_simulator.simulator.executor.SimulatorExecutor;

public class CLISimulator {
    
    private final EnumMap<RobotsEnum, RobotState> robots;
    
    public CLISimulator(RobotMap map) {
        this.robots = new EnumMap<>(RobotsEnum.class);
        
        for (RobotsEnum robot : RobotsEnum.values()) {
            this.robots.put(robot, null);
        }
        
        SimulatorExecutor executor =
            new SimulatorExecutor(map, robots.keySet(), new ExecutorListener());
        executor.setRunning(true);
        
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        executor.stop();
    }
    
    public static void main(String[] args) {
        new CLISimulator(new BasicMap());
    }
    
    public class ExecutorListener implements SimulatorExecutor.Listener {
        
        @Override
        public synchronized void newRobotState(RobotsEnum robot, RobotState state) {
            System.out.format("Robot: %s at (%d, %d) facing %s and: %s\n", robot, state.x, state.y,
                state.direction, state.status);
            
        }
        
    }
    
}
