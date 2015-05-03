package com.samlanning.robot_simulator.simulator.executor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

import com.samlanning.robot_simulator.iface.Robot;
import com.samlanning.robot_simulator.iface.RobotControl;
import com.samlanning.robot_simulator.maps.MapBlock;
import com.samlanning.robot_simulator.maps.RobotMap;

public class SimulatorExecutor {
    
    private final Listener listener;
    
    private final RobotMap map;
    private final Robot[] robots;
    private final RobotExecutor[] robotExecutors;
    
    private State state = new State();
    
    public SimulatorExecutor(RobotMap map, Collection<Robot> robots, Listener listener) {
        this.map = map;
        this.robots = robots.toArray(new Robot[robots.size()]);
        this.robotExecutors = new RobotExecutor[this.robots.length];
        
        for (int i = 0; i < this.robots.length; i++) {
            this.robotExecutors[i] = new RobotExecutor(this.robots[i]);
            this.robotExecutors[i].start();
        }
        
        this.listener = listener;
    }
    
    public void setRunning(boolean running) {
        state.setRunning(running);
    }
    
    public void stop() {
        state.stop();
    }
    
    public static interface Listener {
        public void newRobotState(Robot robot, RobotState state);
    }
    
    private class RobotExecutor extends Thread {
        
        private final Robot robot;
        
        private int x = 0;
        private int y = 0;
        private Direction direction = Direction.LEFT;
        private boolean running = true;
        
        public RobotExecutor(Robot robot) {
            this.robot = robot;
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
                System.out.println("Robot Executor Stopped");
                return;
            }
        }
        
        private synchronized void updateListener(boolean running) {
            if (!this.running)
                throw new InternalStopException();
            this.running = running;
            listener.newRobotState(robot, new RobotState(x, y, direction, running));
        }
        
        private void interruptibleRun() throws InterruptedException, StoppedException {
            System.out.println("Starting Robot Executor");
            
            updateListener(true);
            
            // Start execution of robot
            try {
                robot.run(new RobotControlImpl());
            } catch (InternalStopException e) {
                System.out.println("Robot Executor Stopped By Simulator");
                return;
            }
            System.out.println("Robot Executor Stopped");
            updateListener(false);
        }
        
        private synchronized void doMoveForward() {
            System.out.println("Move Forward");
            
        }
        
        private synchronized void doTurnLeft() {
            System.out.println("Turn Left");
            
        }
        
        private synchronized void doTurnRight() {
            System.out.println("Turn Right");
            
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
                // TODO Auto-generated method stub
                return null;
            }
            
            private void start() {
                // Acquire semaphore while running
                try {
                    moveSemaphore.acquire();
                    state.waitForNextMove();
                    return;
                } catch (InterruptedException e) {
                    // Try Again
                } catch (StoppedException e) {
                    moveSemaphore.release();
                    throw new InternalStopException();
                }
            }
            
            private void finish() {
                moveSemaphore.release();
            }
            
        }
        
    }
    
    private static class State {
        
        private boolean running = false;
        private boolean stopped = false;
        private long interval = 1000;
        
        private Set<Thread> sleeping = new HashSet<>();
        
        public synchronized void waitUntilRunning() throws StoppedException {
            while (!running) {
                if (stopped)
                    throw new StoppedException();
                try {
                    wait();
                } catch (InterruptedException e) {
                    /*
                     * Continue to wait until running, or until exited,
                     * regardless of what may have caused the interruption
                     */
                }
            }
        }
        
        private synchronized void addCurrentThreadToSleeping() {
            sleeping.add(Thread.currentThread());
        }
        
        private synchronized void removeCurrentThreadFromSleeping() {
            sleeping.remove(Thread.currentThread());
        }
        
        /**
         * Interruptable and adjustable wait
         * 
         * @throws StoppedException
         */
        public void waitForNextMove() throws StoppedException {
            waitUntilRunning();
            addCurrentThreadToSleeping();
            long waitingFrom = System.currentTimeMillis();
            while (true) {
                
                long currentTime = System.currentTimeMillis();
                long waitingTo = waitingFrom + interval;
                
                if (currentTime < waitingTo) {
                    try {
                        Thread.sleep(waitingTo - currentTime);
                        break;
                    } catch (InterruptedException e) {
                        synchronized (this) {
                            if (stopped) {
                                removeCurrentThreadFromSleeping();
                                throw new StoppedException();
                            }
                        }
                        /*
                         * Continue waiting if interrupted and not stopped. This
                         * will occur when the interval is changed while threads
                         * are waiting for the next move. The amount they need
                         * to wait will be adjusted.
                         */
                    }
                }
            }
            removeCurrentThreadFromSleeping();
        }
        
        public synchronized void setRunning(boolean running) {
            if (!stopped) {
                this.running = running;
                notifyAll();
            } else {
                throw new RuntimeException("Already Stopped");
            }
        }
        
        public synchronized void stop() {
            this.running = false;
            this.stopped = true;
            notifyAll();
            for (Thread thread : sleeping) {
                thread.interrupt();
            }
            sleeping.clear();
        }
        
    }
    
    public static class StoppedException extends Exception {
    }
    
    private static class InternalStopException extends RuntimeException {
    }
    
}
