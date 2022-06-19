package ages.world.blocks;

import mindustry.gen.Unit;

public interface UnitHolder {
    int getUnits();

    default float maxUnitf(){
        return 0;
    }

    default float leastUnitf(){
        return 0;
    }

    default boolean acceptUnit(Unit u){
        return false;
    }
}
