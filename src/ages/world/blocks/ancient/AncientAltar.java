package ages.world.blocks.ancient;

import arc.Core;
import arc.graphics.Texture;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
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
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.tilesize;

public class AncientAltar extends Block {
    public ObjectMap<Item, StatusEffect> effectTypes = new ObjectMap<Item, StatusEffect>();
    public float effProb = 0.5f;
    public float range = 1200f;
    public float effCapacity;
    public boolean blockOnly = true;

    public AncientAltar(String name) {
        super(name);

        hasPower = false;
        hasLiquids = false;
        hasItems = true;
        rotate = false;
        configurable = true;
        update = true;
        sync = true;
        flags = EnumSet.of(BlockFlag.repair);
    }

    public void addEffect(Object... obj){
        effectTypes = ObjectMap.of(obj);
    }

    /*
    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> effectTypes.containsKey(i)){
            @Override
            public float efficiency(Building build){
                return build instanceof AncientAltarBuild b && b.canEffect()? 1f : 0f;
            }
        });
    }
     */

    @Override
    public void setBars(){
        super.setBars();

        addBar("effTime", (AncientAltarBuild e) -> new Bar("stat.effTime", Pal.accent, e::effTimef));
    }

    public class AncientAltarBuild extends Building {
        public float effTime = 0;
        public StatusEffect effect;

        public TextureRegion placeRegion;
        public Item currentItem;

        public boolean canEffect(){
            return effTime <= 0;
        }

        public boolean isAffecting(){
            return effect != null;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return canEffect() && effectTypes.containsKey(item);
        }

        @Override
        public void handleItem(Building source, Item item){
            super.handleItem(source, item);

            effTime = effCapacity;
            if (Mathf.chance(effProb)){
                currentItem = item;
                initialBoost(currentItem);
                placeRegion = currentItem.fullIcon;
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();

            if (effTime > 0){
                if (!blockOnly && isAffecting()){
                    Units.nearby(team, x, y, range, u -> {
                        u.apply(effect);
                    });
                }

                effTime -= 1f;
            } else {
                if (isAffecting()){
                    effect = null;
                }
                items.remove(currentItem, 1);
            }
        }

        public float effTimef(){
            return effTime / effCapacity;
        }

        public void initialBoost(Item item){
            effect = effectTypes.get(item);
            Units.nearbyBuildings(x, y, range, b -> {
                b.applyBoost(effect.speedMultiplier, effCapacity);
            });
        }

        @Override
        public void draw(){
            super.draw();

            if (isAffecting() && placeRegion != null) {
                Draw.rect(placeRegion, x, y);
                Drawf.circles(x, y, range, effect.color);
            }
        }

        @Override
        public void read(Reads read){
            super.read(read);

            effTime = read.f();
            effect = (StatusEffect) TypeIO.readObject(read);
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(effTime);
            TypeIO.writeObject(write, effect);
        }
    }
}
