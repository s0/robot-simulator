package com.samlanning.robot_simulator.maps;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.RobotMap;
import com.samlanning.robot_simulator.simulator.executor.Direction;

public class HardMap1 extends RobotMap {
    
    public HardMap1(){
        int[][] intMap = new int[][]{
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 1, 0, 0, 0, 1, 0, 2, 0, 1, 0, 0, 0, 1, 0 },
            { 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0 },
            { 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 0 },
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0 },
            { 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0 },
            { 0, 0, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0 },
            { 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 0 },
            { 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 0, 1, 0 },
            { 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0 },
        };
        
        
        
        MapBlock[][] map = new MapBlock[intMap.length][intMap[0].length];
        
        // Initialise as empty
        for(int y = 0; y < map.length; y++){
            for(int x = 0; x < map[y].length; x++){
                map[y][x] = MapBlock.values()[intMap[y][x]];
            }
        }
        
        this.setMap(map, 2, 12, Direction.UP);
    }
    
}
