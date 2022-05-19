package ages.world.blocks.ancient;

import mindustry.gen.Unit;
import mindustry.world.blocks.defense.Wall;

public class AncientFence extends Wall {
    public float fenceDamage = 1f;
    public float speedMultiplier = 0.2f;

    public AncientFence(String name) {
        super(name);

        solid = false;
        solidifes = false;
        update = true;
        rotate = false;
        buildCostMultiplier = 3f;
    }

    public class AncientFenceBuild extends WallBuild{
        @Override
        public void unitOn(Unit unit){
            unit.speedMultiplier *= speedMultiplier;

            unit.damagePierce(fenceDamage * healthf(), unit.hitTime < hitDuration - 12);
        }
    }
}
