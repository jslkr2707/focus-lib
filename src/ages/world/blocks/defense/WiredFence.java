package ages.world.blocks.defense;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.*;
import mindustry.world.meta.*;

import static mindustry.Vars.tilesize;

public class WiredFence extends Wall {
    public float fenceDamage = 5f;
    public float damageTime = 30f;
    public float speedMultiplier = 0.2f;
    public float maxDurability = 600f;
    public float regen = 2f;

    public boolean hitEffect = true;
    public Effect regenEffect = Fx.smoke;
    public TextureRegion effectRegion;

    public WiredFence(String name) {
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

    @Override
    public void setBars(){
        super.setBars();

        addBar(Core.bundle.format("durability"), (WiredFenceBuild e) -> new Bar("bar.durability", Color.gold, e::durabilityf));
    }

    public class WiredFenceBuild extends WallBuild{
        public float damages, duraTimer;
        public float durability = maxDurability;

        @Override
        public void update(){
            super.update();

            damages -= Time.delta;
        }

        @Override
        public void updateTile(){
            super.updateTile();

            if (durability <= 0.001f){
                duraTimer = maxDurability * 0.5f;
            }

            if (duraTimer > 0){
                duraTimer -= Time.delta * regen;
                regenEffect.at(this);
            } else {
                durability = Mathf.clamp(durability + regen * delta(), 0f, maxDurability);
            }
        }

        @Override
        public void unitOn(Unit unit){
            unit.speedMultiplier *= speedMultiplier;
            if (unit.hitTime < -(damageTime / 9) + 1){
                unit.damage(fenceDamage / 60 * Time.delta * healthf() * durabilityf());
                damages = damageTime;
                durability -= duraTimer > 0 ? 0f : Math.min(Mathf.round(unit.hitSize / 5), durability);
            }
        }

        @Override
        public void damage(float damage){
            super.damage(damage);

            float d = duraTimer > 0 ? 0f : Math.min(0.5f * damage, durability);

            durability -= d;
            if (d > 0) super.damage(d);
        }

        @Override
        public void draw() {
            super.draw();

            if (hitEffect) {
                Draw.color(Color.white);
                Draw.alpha(Mathf.clamp((damages - (damageTime - 10)) / 10f));
                Draw.blend(Blending.additive);
                Draw.rect(effectRegion, x, y, tilesize * size, tilesize * size);
                Draw.blend();
                Draw.reset();
            }
        }

        public float durabilityf(){
            return durability < 0 ? 0f : durability / maxDurability;
        }
    }
}
