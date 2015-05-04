package com.samlanning.robot_simulator.simulator.executor;

import java.awt.Point;
import java.util.concurrent.Semaphore;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.RobotControl;
import com.samlanning.robot_simulator.iface.RobotMap;
import com.samlanning.robot_simulator.robots.RobotsEnum;
import com.samlanning.robot_simulator.simulator.executor.SimulatorExecutor.Listener;
import com.samlanning.robot_simulator.simulator.executor.exceptions.CrashedException;
import com.samlanning.robot_simulator.simulator.executor.exceptions.InternalStopException;
import com.samlanning.robot_simulator.simulator.executor.exceptions.StoppedException;

class RobotExecutor extends Thread {
    
    private final RobotMap map;
    private final Listener listener;
    private final SimulatorExecutor.State state;
    private final RobotsEnum robot;
    
    private int x;
    private int y;
    private Direction direction;
    private boolean running = true;
    
    public RobotExecutor(RobotMap map, Listener listener, SimulatorExecutor.State state,
        RobotsEnum robot) {
        this.map = map;
        this.listener = listener;
        this.state = state;
        this.robot = robot;
        
        Point start = map.getStartPoint();
        x = start.x;
        y = start.y;
        direction = map.getStartDirection();
    }
    
    @Override
    public void run() {
        try {
            interruptibleRun();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Robot Executor Interrupted");
            return;
        } catch (StoppedException e) {
            // System.out.println("Robot Executor Stopped");
            return;
        }
    }
    
    private synchronized void updateListener(RobotState.Status status) {
        if (!this.running)
            throw new InternalStopException();
        this.running = status == RobotState.Status.RUNNING;
        listener.newRobotState(robot, new RobotState(x, y, direction, status));
    }
    
    private void interruptibleRun() throws InterruptedException, StoppedException {
        // System.out.println("Starting Robot Executor");
        
        updateListener(RobotState.Status.RUNNING);
        
        // Start execution of robot
        try {
            robot.robot.run(new RobotControlImpl());
        } catch (InternalStopException e) {
            // System.out.println("Robot Executor Stopped By Simulator");
            return;
        } catch (CrashedException e) {
            // System.out.println("Robot Executor Stopped after Robot Crash");
            return;
        }
        // System.out.println("Robot Executor Stopped");
        updateListener(RobotState.Status.STOPPED);
    }
    
    private MapBlock getBlockAt(int x, int y) {
        if (x < 0 || x >= map.getWidth() || y < 0 || y >= map.getHeight())
            return MapBlock.WALL;
        return map.getBlock(x, y);
    }
    
    private synchronized void doMoveForward() {
        int newX = x + direction.vectorX();
        int newY = y + direction.vectorY();
        MapBlock block = getBlockAt(newX, newY);
        if (block == MapBlock.WALL) {
            System.out.println("Robot Crashed");
            updateListener(RobotState.Status.CRASHED);
            throw new CrashedException();
        }
        x = newX;
        y = newY;
        if (block == MapBlock.FINISH) {
            updateListener(RobotState.Status.FINISHED);
            throw new InternalStopException();
        } else {
            updateListener(RobotState.Status.RUNNING);
        }
    }
    
    private synchronized void doTurnLeft() {
        this.direction = this.direction.anticlockwise();
        updateListener(RobotState.Status.RUNNING);
    }
    
    private synchronized void doTurnRight() {
        this.direction = this.direction.clockwise();
        updateListener(RobotState.Status.RUNNING);
    }
    
    private synchronized MapBlock doLookAhead() {
        int newX = x + direction.vectorX();
        int newY = y + direction.vectorY();
        return getBlockAt(newX, newY);
    }
    
    private class RobotControlImpl implements RobotControl {
        
        /**
         * Rate limit the actions a robot can do in the case that it is
         * multi-threaded.
         */
        private Semaphore moveSemaphore = new Semaphore(1);
        
        @Override
        public void moveForward() {
            start();
            doMoveForward();
            finish();
        }
        
        @Override
        public void turnLeft() {
            start();
            doTurnLeft();
            finish();
        }
        
        @Override
        public void turnRight() {
            start();
            doTurnRight();
            finish();
        }
        
        @Override
        public MapBlock lookAhead() {
            return doLookAhead();
        }
        
        private void start() {
            try {
                moveSemaphore.acquire();
                /*
                 * Make sure actions are only run when the executor is currently
                 * running, this will throw a StoppedException (before the
                 * action is run) if the execution has stopped.
                 */
                state.waitUntilRunning();
                return;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (StoppedException e) {
                moveSemaphore.release();
                throw new InternalStopException();
            }
        }
        
        private void finish() {
            try {
                state.waitForNextMove();
                moveSemaphore.release();
            } catch (StoppedException e) {
                moveSemaphore.release();
                throw new InternalStopException();
            }
        }
        
    }
    
}