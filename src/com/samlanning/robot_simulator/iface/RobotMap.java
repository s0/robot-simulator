package com.samlanning.robot_simulator.iface;

import java.awt.Point;

import com.samlanning.robot_simulator.simulator.executor.Direction;

public abstract class RobotMap {
    
    /**
     * map[y][x]
     */
    private MapBlock[][] map = new MapBlock[0][0];
    private int startX;
    private int startY;
    private Direction startDirection;
    
    protected void setMap(MapBlock[][] map, int startX, int startY, Direction startDirection){
        this.map = map;
        this.startX = startX;
        this.startY = startY;
        this.startDirection = startDirection;
    }
    
    public int getWidth(){
        return map.length > 0 ? map[0].length : 0;
    }
    
    public int getHeight(){
        return map.length;
    }
    
    public MapBlock getBlock(int x, int y){
        return map[y][x];
    }
    
    public Point getStartPoint(){
        return new Point(startX, startY);
    }
    
    public Direction getStartDirection(){
        return startDirection;
    }
    
}
