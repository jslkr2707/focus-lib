package ages.world.blocks.ancient;

import ages.world.blocks.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.util.Nullable;
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

        bars.add("unitLimit", (AncientTowerBuild e) -> new Bar(Core.bundle.format("ages.stat.unitlimit", e.getUnits(), unitLimit), Pal.lightOrange, e::maxUnitf));
        bars.add("leastUnit", (AncientTowerBuild e) -> new Bar("ages.stat.leastunit", Pal.command, () -> Math.min(e.leastUnitf(), 1)));
    }

    public class AncientTowerBuild extends ItemTurretBuild implements UnitHolder{
        @Nullable Unit[] inUnits = new Unit[unitLimit];

        @Override
        public int getUnits(){
            return inUnits.length;
        }

        @Override
        public float handleDamage(float amount){
            if (getUnits() > 0) {
                for (Unit u : inUnits) {
                    if (u != null) u.damage(amount / (getUnits() + 1));
                }
            }
            return amount / (getUnits() + 1);
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

        public boolean unitActive(){ return getUnits() >= leastUnits; }

        @Override
        public float maxUnitf(){
            return (float) getUnits() / unitLimit;
        }

        @Override
        public float leastUnitf(){
            return (float) getUnits() / leastUnits;
        }

        @Override
        public boolean acceptUnit(Unit u){
            removeUnit();
            if (getUnits() >= unitLimit){
                for (int i = 0; i < getUnits(); i++){
                    if (inUnits[i] == null){
                        inUnits[i] = u;
                        return true;
                    }
                }
            }else{
                inUnits[getUnits()] = u;
                return true;
            }

            return false;
        }

        public void removeUnit(){
            for (int i = 0; i < getUnits(); i++){
                if (inUnits[i].dead) inUnits[i] = null;
            }
        }
    }
}
