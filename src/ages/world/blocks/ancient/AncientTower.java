package ages.world.blocks.ancient;

import ages.content.AgesUnitTypes;
import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;

public class AncientTower extends ItemTurret {
    public int unitLimit = 2;
    public int leastUnits = 1;

    public AncientTower(String name) {
        super(name);

        targetAir = false;
        playerControllable = false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.abilities, "@", Core.bundle.format("ages.leastunit", leastUnits));
        stats.add(Stat.abilities, "@", Core.bundle.format("ages.unitlimit", unitLimit));
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add("unitLimit", (AncientTowerBuild e) -> new Bar(Core.bundle.format("ages.stat.unitlimit", e.unitAmount(), unitLimit), Pal.lightOrange, e::inUnitf));
        bars.add("leastUnit", (AncientTowerBuild e) -> new Bar("ages.stat.leastunit", Pal.command, () -> Math.min(e.leastUnitf(), 1)));
    }

    public class AncientTowerBuild extends ItemTurretBuild{
        public Unit[] inUnits = {};

        @Override
        public float handleDamage(float amount){
            if (unitAmount() > 0) {
                for (Unit u : inUnits) {
                    u.damage(amount / (unitAmount() + 1));
                }
            }
            return amount / (unitAmount() + 1);
        }

        @Override
        public boolean validateTarget(){ return super.validateTarget() && unitActive(); }

        @Override
        public void draw(){
            Draw.rect(baseRegion, x, y);
            Draw.color();

            Draw.z(Layer.turret);

            Drawf.shadow(region, x + tr2.x - elevation, y + tr2.y - elevation);
        }

        public boolean unitActive(){ return unitAmount() >= leastUnits; }

        public int inUnitf(){
            return unitAmount() / unitLimit;
        }

        public int leastUnitf(){
            return unitAmount() / leastUnits;
        }

        public int unitAmount(){ return inUnits.length; }

        public boolean acceptUnit(Unit unit){ return unitAmount() < unitLimit && unit.type == AgesUnitTypes.slinger; }
    }
}
