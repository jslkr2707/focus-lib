package ages.world.blocks.ancient;

import arc.Core;
import mindustry.gen.Unit;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;

public class AncientFence extends Wall {
    public float fenceDamage = 10f;
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
            t.add(Core.bundle.format("ages.fencedamage", fenceDamage * damageTime / 60));
        });
    }

    public class AncientFenceBuild extends WallBuild{
        @Override
        public void unitOn(Unit unit){
            unit.speedMultiplier *= speedMultiplier;

            unit.damagePierce(fenceDamage * healthf(), unit.hitTime < -(damageTime / 9) + 1);
        }
    }
}
