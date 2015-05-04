package com.samlanning.robot_simulator.maps;

public class BasicMap extends RobotMap {
    
    public BasicMap(){
        MapBlock[][] map = new MapBlock[][]{
            {MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.WALL , MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.WALL , MapBlock.WALL , MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
        };
        
        this.setMap(map, 0, 0);
    }
    
}
