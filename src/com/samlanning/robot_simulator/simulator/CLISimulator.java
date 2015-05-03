package com.samlanning.robot_simulator.simulator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.maps.BasicMap;
import com.samlanning.robot_simulator.maps.RobotMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.executor.RobotState;
import com.samlanning.robot_simulator.simulator.executor.SimulatorExecutor;

public class CLISimulator {
    
    private final RobotMap map;
    private final Map<Robot, RobotState> robots;
    private final Map<Robot, String> robotNames;
    
    public CLISimulator(RobotMap map) {
        this.map = map;
        this.robots = new HashMap<>();
        this.robotNames = new HashMap<>();
        
        for (RobotsEnum robot : RobotsEnum.values()) {
            System.out.println(robot.robot);
            this.robots.put(robot.robot, null);
            this.robotNames.put(robot.robot, robot.name());
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
        public synchronized void newRobotState(Robot robot, RobotState state) {
            System.out.format("Robot: %s at (%d, %d) facing %s running: %s\n",
                robotNames.get(robot), state.x, state.y, state.direction, state.running);
            
        }
        
    }
    
}
