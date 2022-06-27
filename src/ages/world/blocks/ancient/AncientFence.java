package ages.world.blocks.ancient;

import arc.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.*;

public class AncientFence extends Wall {
    public float fenceDamage = 5f;
    public float damageTime = 30f;
    public float speedMultiplier = 0.2f;

    public AncientFence(String name) {
        super(name);

        solid = false;
        solidifes = false;
        update = true;
        rotate = false;
        buildCostMultiplier = 3f;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.abilities, t -> {
            t.left().defaults().padRight(3).left();
            t.row();
            t.add(Core.bundle.format("ages.fencedamage", fenceDamage));
        });
    }

    public class AncientFenceBuild extends WallBuild{
        @Override
        public void unitOn(Unit unit){
            unit.speedMultiplier *= speedMultiplier;
            unit.damage(fenceDamage / 60 * Time.delta * healthf(), unit.hitTime < -(damageTime / 9) + 1);
        }
    }
}
