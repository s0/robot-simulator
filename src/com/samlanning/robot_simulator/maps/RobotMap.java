package com.samlanning.robot_simulator.maps;

public abstract class RobotMap {
    
    /**
     * map[y][x]
     */
    private MapBlock[][] map = new MapBlock[0][0];
    private int startX;
    private int startY;
    
    protected void setMap(MapBlock[][] map, int startX, int startY){
        this.map = map;
        this.startX = startX;
        this.startY = startY;
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
    
    public int getStartX(){
        return startX;
    }
    
    public int getStartY(){
        return startY;
    }
    
}
