package ages.world.blocks.ancient;

import ages.type.WorkerUnitType;
import arc.Core;
import arc.math.Mathf;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.meta.*;

public class AncientTower extends ItemTurret {
    public WorkerUnitType worker;
    public int unitLimit = 2;
    public int leastUnits = 1;

    public AncientTower(String name) {
        super(name);

        targetAir = false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.charge, worker.charge / 60f, StatUnit.seconds);
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add("unitLimit", (AncientTowerBuild e) -> new Bar("ages.stat.unitlimit", Pal.lightOrange, e::inUnitf));
        bars.add("leastUnit", (AncientTowerBuild e) -> new Bar("ages.stat.leastunit", Pal.command, () -> Math.min(e.leastUnitf(), 1)));
    }

    public class AncientTowerBuild extends ItemTurretBuild{
        public int inUnit;
        public Unit[] inUnits;

        @Override
        public float handleDamage(float amount){
            for (Unit u: inUnits){
                u.damage(amount / (inUnit + 1));
            }
            return amount / (inUnit + 1);
        }

        public int inUnitf(){
            return inUnit / unitLimit;
        }

        public int leastUnitf(){
            return inUnit / leastUnits;
        }
    }
}
