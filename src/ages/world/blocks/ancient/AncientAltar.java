package ages.world.blocks.ancient;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.Log;
import arc.util.io.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.production.Separator;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;

import javax.swing.*;

import static mindustry.Vars.indexer;

public class AncientAltar extends Block {
    public ObjectMap<Item, StatusEffect> effectTypes = new ObjectMap<Item, StatusEffect>();
    public float chance = 0.5f, range = 1200f, reload = 60f;
    public float effCapacity;

    public DrawBlock drawer = new DrawDefault();

    public AncientAltar(String name) {
        super(name);

        hasPower = false;
        hasLiquids = false;
        hasItems = true;
        rotate = false;
        update = true;
        sync = true;
        canOverdrive = false;
    }

    public void addEffect(Object... obj){
        effectTypes = ObjectMap.of(obj);
    }

    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> effectTypes.containsKey(i)){
            @Override
            public float efficiency(Building build){
                return build instanceof AncientAltarBuild b && b.efficiency > 0 ? 1f : 0f;
            }
        });

        super.init();
    }


    @Override
    public void setBars(){
        super.setBars();

        addBar("effectTime", (AncientAltarBuild e) -> new Bar("stat.effectTime", Pal.accent, e::effTimef));
    }

    public class AncientAltarBuild extends Building {
        public float effectTime = 0, reloadTimer = 0f, heat, warmup;
        public StatusEffect effect;
        public boolean affecting;

        public TextureRegion placeRegion;
        public Item currentItem;

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return !this.acceptItem(this, item) || !this.block.hasItems || source != null && source.team() != this.team || !items.empty() || !reloaded() ? 0 : 1;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            if (affecting || effectTypes.get(item) == null || !items.empty() || !reloaded()) return false;
            currentItem = item;
            // timer.reset(timerUse, 0);
            return true;
        }

        @Override
        public int removeStack(Item item, int amount){
            return 0;
        }

        @Override
        public void updateTile(){
            super.updateTile();

            warmup = Mathf.approachDelta(warmup, efficiency > 0 ? 1f : 0f, 0.05f);

            if (affecting){
                if (effectTime > 0){
                    //block effects
                    indexer.eachBlock(this, range, b -> b.block.canOverdrive && effect != null, b -> {
                        b.applyBoost(effect.speedMultiplier * warmup, 2f);
                        if (b.damaged()) b.heal((b.maxHealth - b.health) / effectTime * warmup);
                    });

                    //apply the effect to units within range
                    Units.nearby(team, x, y, range, u -> {
                        u.apply(effect);
                    });
                } else {
                    // consume item
                    items.remove(currentItem, 1);
                    disableBoost();
                }
            } else {
                if (currentItem != null && !items.empty() && effectTime <= 0f) {
                    enableBoost();
                }

                reloadTimer += reloaded() ? 0f : warmup * delta();
            }

            if (effectTime > 0){
                effectTime -= warmup * delta();
                reloadTimer = 0f;
            } else {
                reloadTimer += reloaded() ? 0f : warmup * delta();
            }

            Log.info(reloadTimer);
        }

        public float effTimef(){
            return effectTime / effCapacity;
        }

        public void enableBoost(){
            effectTime = effCapacity;

            if (Mathf.randomBoolean(chance)){
                effect = effectTypes.get(currentItem);
                placeRegion = currentItem.fullIcon;
                affecting = true;
            }
        }

        public void disableBoost(){
            affecting = false;
            placeRegion = null;
            currentItem = null;
            effect = null;
        }

        public boolean reloaded(){
            return reloadTimer >= reload;
        }

        @Override
        public void draw(){
            drawer.draw(this);

            if (affecting && placeRegion != null) {
                Draw.rect(placeRegion, x, y);
                Draw.z(Layer.effect);
                Draw.alpha(Mathf.absin(6f, 6f));
                Drawf.circles(x, y, range, effect.color);
            }
        }

        @Override
        public void read(Reads read){
            super.read(read);

            effectTime = read.f();
            reloadTimer = read.f();
            affecting = read.bool();
            // currentItem = TypeIO.readItem(read);
            /* if (currentItem != null && affecting){
                placeRegion = currentItem.fullIcon;
                effect = effectTypes.get(currentItem);
            }
             */
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(effectTime);
            write.f(reloadTimer);
            write.bool(affecting);
            // if (currentItem != null) TypeIO.writeItem(write, currentItem);
        }
    }
}
