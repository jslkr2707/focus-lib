package industrial.world.blocks.defense;

import mindustry.content.UnitTypes;
import mindustry.entities.Units;
import mindustry.gen.Unit;
import mindustry.world.Tile;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.meta.Stat;

public class Thorn extends Wall {
    public float thornDmg;
    public float critMultiplier = 1f;
    public float cooltime = 90f;
    public boolean status;
    public UnitTypes[] units;

    public Thorn(String name) {
        super(name);
    }

    @Override
    public void setStats(){
        super.setStats();
        stats.add(Stat.damage, thornDmg + " ~ " + "[red]" + thornDmg * critMultiplier);
        stats.add(Stat.cooldownTime, cooltime / 60);
    }

    public class ThornBuild extends WallBuild{
        public boolean thornOn(){
            return tile.nearby((rotation + 2) % 4) == null;
        }

        public void damage(Unit enemy){
            Tile tile = front().tile;
            enemy.damagePierce(thornDmg);
            // push(enemy);
        }
    }
}
