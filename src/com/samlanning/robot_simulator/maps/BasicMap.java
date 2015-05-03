package com.samlanning.robot_simulator.maps;

public class BasicMap extends RobotMap {
    
    public BasicMap(){
        MapBlock[][] map = new MapBlock[][]{
            {MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
        };
        
        this.setMap(map);
    }
    
}
