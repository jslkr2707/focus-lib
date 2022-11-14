package ages.world.blocks.defense;

import arc.*;
import arc.struct.*;
import arc.util.io.Writes;
import mindustry.gen.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;

public class DefenseTower extends ItemTurret {
    public int unitLimit = 2;
    public int leastUnits = 1;

    public DefenseTower(String name) {
        super(name);

        targetAir = false;
        playerControllable = false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.abilities, "@", Core.bundle.format("stat.leastunit", leastUnits));
        stats.add(Stat.abilities, "@", Core.bundle.format("stat.unitlimit", unitLimit));
    }

    public class DefenseTowerBuild extends ItemTurretBuild{
        public Seq<Unit> inUnits = new Seq<>();

        @Override
        public void updateTile(){
            super.updateTile();

            removeUnit();
        }

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
