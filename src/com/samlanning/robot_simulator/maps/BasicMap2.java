package com.samlanning.robot_simulator.maps;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.RobotMap;
import com.samlanning.robot_simulator.simulator.executor.Direction;

public class BasicMap2 extends RobotMap {
    
    public BasicMap2(){
        MapBlock[][] map = new MapBlock[][]{
            {MapBlock.EMPTY,  MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.EMPTY,  MapBlock.WALL,  MapBlock.EMPTY, MapBlock.WALL , MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.EMPTY,  MapBlock.WALL,  MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.WALL,   MapBlock.WALL,  MapBlock.WALL,  MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , },
            {MapBlock.EMPTY,  MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL,  MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.EMPTY,  MapBlock.WALL,  MapBlock.EMPTY, MapBlock.WALL,  MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.FINISH, MapBlock.WALL,  MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
        };
        
        this.setMap(map, 2, 2, Direction.UP);
    }
    
}
