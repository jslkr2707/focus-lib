package ages.world.blocks.ancient;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class AncientFence extends Wall {
    public float fenceDamage = 5f;
    public float damageTime = 30f;
    public float speedMultiplier = 0.2f;

    public boolean hitEffect = true;
    public TextureRegion effectRegion;

    public AncientFence(String name) {
        super(name);

        solid = false;
        solidifes = false;
        update = true;
        rotate = false;
        buildCostMultiplier = 3f;
    }

    @Override
    public void load(){
        super.load();

        effectRegion = Core.atlas.find(name+"-effect");
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.abilities, t -> {
            t.left().defaults().padRight(3).left();
            t.row();
            t.add(Core.bundle.format("stat.fencedamage", fenceDamage));
        });
    }

    public class AncientFenceBuild extends WallBuild{
        public float damages;

        @Override
        public void update(){
            super.update();

            damages -= Time.delta;
        }
        @Override
        public void unitOn(Unit unit){
            unit.speedMultiplier *= speedMultiplier;
            if (unit.hitTime < -(damageTime / 9) + 1){
                unit.damage(fenceDamage / 60 * Time.delta * healthf());
                damages = damageTime;
            }
        }

        @Override
        public void draw(){
            super.draw();

            if (hitEffect){
                Draw.color(Color.white);
                Draw.alpha(Mathf.clamp((damages - (damageTime - 10)) / 10f));
                Draw.blend(Blending.additive);
                Draw.rect(effectRegion, x, y, tilesize*size, tilesize*size);
                Draw.blend();
                Draw.reset();
            }
        }
    }
}
