package ages.world.blocks;

import mindustry.gen.Unit;

public interface UnitHolder {
    int getUnits();
    boolean acceptUnit(Unit u);

    default float maxUnitf(){
        return 0;
    }

    default float leastUnitf(){
        return 0;
    }
}
