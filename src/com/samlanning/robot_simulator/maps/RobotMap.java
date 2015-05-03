package com.samlanning.robot_simulator.maps;

public abstract class RobotMap {
    
    /**
     * map[y][x]
     */
    private MapBlock[][] map = new MapBlock[0][0];
    
    protected void setMap(MapBlock[][] map){
        this.map = map;
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
    
}
