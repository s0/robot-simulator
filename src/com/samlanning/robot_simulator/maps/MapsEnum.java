package com.samlanning.robot_simulator.maps;

import com.samlanning.robot_simulator.iface.RobotMap;

public enum MapsEnum {
    
    BASIC_1(new BasicMap1()),
    BASIC_2(new BasicMap2()),
    BASIC_3(new BasicMap3()),
    RANDOM_START(new RandomStartMap(new BasicMap3())),
    HARD_1(new HardMap1()),
    HARD_2(new HardMap2()),
    HARD_2_RANDOM(new RandomStartMap(new HardMap2())),
    ;
    
    public final RobotMap map;
    
    private MapsEnum(RobotMap map){
        this.map = map;
    }
}
