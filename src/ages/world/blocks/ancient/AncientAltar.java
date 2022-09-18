package ages.world.blocks.ancient;

import arc.graphics.Blending;
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
                return build instanceof AncientAltarBuild b && b.effectTime > 0 ? b.warmup : 0f;
            }
        });

        super.init();
    }


    @Override
    public void setBars(){
        super.setBars();

        addBar("effectTime", (AncientAltarBuild e) -> new Bar("stat.effectTime", Pal.accent, e::effectTimef));
    }

    public class AncientAltarBuild extends Building {
        public float effectTime = 0, reloadTimer = 0f, warmup;
        public StatusEffect effect;
        public boolean affecting;

        public TextureRegion placeRegion;
        public Item currentItem;

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return !this.acceptItem(this, item) || !this.block.hasItems || source != null && source.team() != this.team ? 0 : 1;
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
                    Units.nearby(team, x, y, range, u -> u.apply(effect));
                } else {
                    // consume item
                    items.remove(currentItem, 1);
                    disableBoost();
                }
            } else {
                if (currentItem != null && effectTime <= 0 && items.any()) {
                    if (reloaded()){
                        enableBoost();
                    } else {
                        items.remove(currentItem, 1);
                    }
                }
            }

            if (effectTime > 0){
                effectTime -= delta();
                reloadTimer = 0f;
            } else {
                reloadTimer += delta();
            }

            Log.info(currentItem == null);
        }

        public float effectTimef(){
            return effectTime >= 0 ? effectTime / effCapacity : 0;
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
            super.draw();

            if (affecting) {
                Draw.z(Layer.blockOver+0.001f);
                Draw.alpha(effectTimef());
                Draw.rect(placeRegion, x, y);
                Draw.blend(Blending.additive);

                Draw.color(effect.color, Mathf.absin(24f, 0.15f));
                Draw.z(Layer.floor+0.001f);
                Fill.circle(x, y, range);
                Draw.blend();
                Draw.color();
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
