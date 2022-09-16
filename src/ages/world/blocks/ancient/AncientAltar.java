package ages.world.blocks.ancient;

import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;

import static mindustry.Vars.indexer;

public class AncientAltar extends Block {
    public ObjectMap<Item, StatusEffect> effectTypes = new ObjectMap<Item, StatusEffect>();
    public float effProb = 0.2f, range = 1200f, reload = 60f;
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
                return build instanceof AncientAltarBuild b && b.affecting ? 1f : 0f;
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
        public float effectTime = 0;
        public StatusEffect effect;
        public boolean blockEffected = false;
        public boolean affecting;

        public TextureRegion placeRegion;
        public Item currentItem;

        @Override
        public int acceptStack(Item item, int amount, Teamc source){
            return !this.acceptItem(this, item) || !this.block.hasItems || source != null && source.team() != this.team || !items.empty() ? 0 : 1;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            if (affecting || effectTypes.get(item) == null /* || !timer(timerUse, reload */) return false;
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

            if (affecting){
                //disable timer during effect
                // timer.reset(timerUse, 0);

                if (effectTime > 0){
                    //block effects
                    indexer.eachBlock(this, range, b -> b.block.canOverdrive && effect != null, b -> {
                        b.applyBoost(effect.speedMultiplier, 2f);
                        if (b.damaged()) b.heal((b.maxHealth - b.health) / effectTime);
                    });

                    Units.nearby(team, x, y, range, u -> {
                        u.apply(effect);
                    });

                    effectTime -= 1f;
                } else {
                    items.remove(currentItem, 1);
                    disableBoost();
                }
            } else {
                if (currentItem != null && !items.empty()){
                    enableBoost();
                }
            }
        }

        public float effTimef(){
            return effectTime / effCapacity;
        }

        public void enableBoost(){
            effectTime = effCapacity;

            if (Mathf.chance(effProb)){
                effect = effectTypes.get(currentItem);
                placeRegion = currentItem.fullIcon;
                affecting = true;

                //boost blocks, only once
                if (!blockEffected){
                    Units.nearbyBuildings(x, y, range, b -> {
                        b.applyBoost(effect.speedMultiplier, effCapacity);
                    });
                    blockEffected = true;
                }
            }
        }

        public void disableBoost(){
            affecting = false;
            placeRegion = null;
            currentItem = null;
            effect = null;
            blockEffected = false;
        }

        @Override
        public void draw(){
            drawer.draw(this);

            if (affecting && placeRegion != null) {
                Draw.rect(placeRegion, x, y);
                Drawf.circles(x, y, range, effect.color);
            }
        }

        @Override
        public void read(Reads read){
            super.read(read);

            effectTime = read.f();
            affecting = read.bool();
            effect = (StatusEffect) TypeIO.readObject(read);
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(effectTime);
            write.bool(affecting);
            TypeIO.writeObject(write, effect);
        }
    }
}
