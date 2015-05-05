package com.samlanning.robot_simulator.maps;

import java.awt.Point;
import java.util.Random;

import com.samlanning.robot_simulator.iface.MapBlock;
import com.samlanning.robot_simulator.iface.RobotMap;

public class RandomStartMap extends RobotMap {
    
    public RandomStartMap(RobotMap base){
        this.setMap(base);
    }
    
    @Override
    public Point getStartPoint(){
        Random r = new Random();
        while(true){
            int x = r.nextInt(getWidth());
            int y = r.nextInt(getHeight());
            if(getBlock(x, y) == MapBlock.EMPTY)
                return new Point(x, y);
        }
    }
    
}
