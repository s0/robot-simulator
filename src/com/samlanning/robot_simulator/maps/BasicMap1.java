package com.samlanning.robot_simulator.maps;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.RobotMap;
import com.samlanning.robot_simulator.simulator.executor.Direction;

public class BasicMap1 extends RobotMap {
    
    public BasicMap1(){
        MapBlock[][] map = new MapBlock[][]{
            {MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.WALL , MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.WALL , MapBlock.WALL , MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.FINISH, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, },
            {MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
            {MapBlock.WALL , MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.EMPTY, MapBlock.WALL , MapBlock.EMPTY, },
        };
        
        this.setMap(map, 0, 0, Direction.DOWN);
    }
    
}
