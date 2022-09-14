package ages.world.blocks.ancient;

import arc.Core;
import arc.graphics.Texture;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.Log;
import arc.util.io.*;
import mindustry.content.Blocks;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.TypeIO;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.liquid.LiquidBlock;
import mindustry.world.consumers.*;
import mindustry.world.draw.*;

import static mindustry.Vars.tilesize;

public class AncientAltar extends Block {
    public ObjectMap<Item, StatusEffect> effectTypes = new ObjectMap<Item, StatusEffect>();
    public float effProb = 0.5f;
    public float range = 1200f;
    public float effCapacity;
    public boolean blockOnly = true;

    public DrawBlock drawer = new DrawDefault();

    public AncientAltar(String name) {
        super(name);

        hasPower = false;
        hasLiquids = false;
        hasItems = true;
        rotate = false;jhjh
        update = true;
        sync = true;
    }

    public void addEffect(Object... obj){
        effectTypes = ObjectMap.of(obj);
    }


    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> effectTypes.containsKey(i)){
            @Override
            public float efficiency(Building build){
                return build instanceof AncientAltarBuild b && !b.itemPlaced() ? 1f : 0f;
            }
        });

        super.init();
    }


    @Override
    public void setBars(){
        super.setBars();

        addBar("effTime", (AncientAltarBuild e) -> new Bar("stat.effTime", Pal.accent, e::effTimef));
    }

    public class AncientAltarBuild extends Building {
        public float effectTime = 0;
        public StatusEffect effect;
        public boolean blockEffected = false;

        public TextureRegion placeRegion;
        public Item currentItem;

        @Override
        public boolean acceptItem(Building source, Item item){
            return !itemPlaced() && effectTypes.get(item) != null;
        }

        @Override
        public void handleItem(Building source, Item item){
            effect = effectTypes.get(item);

            if (effect == null) return;

            effectTime = effCapacity;

            if (Mathf.chance(effProb)){
                currentItem = item;
                placeRegion = currentItem.fullIcon;
            }
        }

        @Override
        public int removeStack(Item item, int amount){
            return 0;
        }

        @Override
        public void updateTile(){
            super.updateTile();

            if (itemPlaced()){
                if (!blockEffected){
                    initialBoost(currentItem);
                    blockEffected = true;
                }

                if (!blockOnly && isAffecting()){
                    Units.nearby(team, x, y, range, u -> {
                        u.apply(effect);
                    });
                }

                effectTime -= 1f;
            } else {
                effect = null;
                items.remove(currentItem, 1);
                blockEffected = false;
            }
        }

        public float effTimef(){
            return effectTime / effCapacity;
        }

        public boolean itemPlaced(){
            return effectTime > 0;
        }

        public boolean isAffecting(){
            return effect != null;
        }

        public void initialBoost(Item item){
            Units.nearbyBuildings(x, y, range, b -> {
                b.applyBoost(effect.speedMultiplier, effCapacity);

            });
        }

        @Override
        public void draw(){
            drawer.draw(this);

            if (isAffecting() && placeRegion != null) {
                Draw.rect(placeRegion, x, y);
                Drawf.circles(x, y, range, effect.color);
            }
        }

        @Override
        public void read(Reads read){
            super.read(read);

            effectTime = read.f();
            effect = (StatusEffect) TypeIO.readObject(read);
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(effectTime);
            TypeIO.writeObject(write, effect);
        }
    }
}
