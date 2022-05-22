package ages.world.blocks.ancient;

import ages.content.AgesUnitTypes;
import ages.type.WorkerUnitType;
import arc.Core;
<<<<<<< Updated upstream
import arc.math.Mathf;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;
=======
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.UnitType;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
>>>>>>> Stashed changes
import mindustry.world.meta.*;

public class AncientTower extends ItemTurret {
    public float charge;
    public int unitLimit = 2;
    public int leastUnits = 1;

    public AncientTower(String name) {
        super(name);

        targetAir = false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.reload, "[white]" + Core.bundle.format("ages.towercharge", charge / 60f) + Core.bundle.format("unit.seconds") + Core.bundle.format("ages.perunit"));
        stats.add(Stat.abilities, "@", Core.bundle.format("ages.leastunit", leastUnits));
        stats.add(Stat.abilities, "@", Core.bundle.format("ages.unitlimit", unitLimit));
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
            if (inUnits != null) {
                if (inUnits.length > 0) {
                    for (Unit u : inUnits) {
                        u.damage(amount / (inUnit + 1));
                    }
                }
            }
            return amount / (inUnit + 1);
        }

        @Override
        public boolean validateTarget(){
            return super.validateTarget() && (isControlled() || inUnit > leastUnits);
        }

        public int inUnitf(){
            return inUnit / unitLimit;
        }

        public int leastUnitf(){
            return inUnit / leastUnits;
        }
    }
}
