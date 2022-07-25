package ages.world.blocks.ancient;

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

        stats.add(Stat.abilities, "@", Core.bundle.format("stat.leastunit", leastUnits));
        stats.add(Stat.abilities, "@", Core.bundle.format("stat.unitlimit", unitLimit));
    }

    public class AncientTowerBuild extends ItemTurretBuild{
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
