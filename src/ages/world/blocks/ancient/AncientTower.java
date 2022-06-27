package ages.world.blocks.ancient;

import ages.world.blocks.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.io.Writes;
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

        addBar("unitLimit", (AncientTowerBuild e) -> new Bar(Core.bundle.format("ages.stat.unitlimit", e.getUnits(), unitLimit), Pal.lightOrange, e::maxUnitf));
        addBar("leastUnit", (AncientTowerBuild e) -> new Bar("ages.stat.leastunit", Pal.command, () -> Math.min(e.leastUnitf(), 1)));
    }

    public class AncientTowerBuild extends ItemTurretBuild implements UnitHolder{
        public Seq<Unit> inUnits = new Seq<>();

        @Override
        public int getUnits(){
            return inUnits.size;
        }

        @Override
        public float handleDamage(float amount){
            if (unitActive()) {
                for (Unit u: inUnits) {
                    u.damage(amount / (getUnits() + 1));
                }
            }
            return amount / (getUnits() + 1);
        }

        @Override
        public boolean validateTarget(){ return super.validateTarget() && unitActive(); }

        @Override
        public void updateTile(){
            super.updateTile();

            removeUnit();
        }

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
            return getUnits() >= unitLimit;
        }

        public boolean unitActive(){ return getUnits() >= leastUnits; }

        public void removeUnit(){
            for (Unit u: inUnits){
                if (u.dead) inUnits.remove(u);
            }
        }

        @Override
        public void write(Writes write){
            super.write(write);

            for (Unit u: inUnits){
                write.i(u.id);
            }
        }
    }
}
