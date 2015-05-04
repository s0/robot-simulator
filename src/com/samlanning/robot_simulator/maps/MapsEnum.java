package com.samlanning.robot_simulator.maps;

import com.samlanning.robot_simulator.iface.RobotMap;

public enum MapsEnum {
    
    BASIC_1(new BasicMap1()),
    BASIC_2(new BasicMap2()),
    ;
    
    public final RobotMap map;
    
    private MapsEnum(RobotMap map){
        this.map = map;
    }
}
