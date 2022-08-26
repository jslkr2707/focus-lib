package ages.world.blocks.ancient;

import arc.math.*;
import arc.struct.*;
import arc.util.io.*;
import mindustry.content.Blocks;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.io.TypeIO;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.consumers.*;

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
    }

    public void addEffect(Object... obj){
        effectTypes = ObjectMap.of(obj);
    }

    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> effectTypes.containsKey(i)){
            @Override
            public float efficiency(Building build){
                return build instanceof AncientAltarBuild b && b.canEffect()? 1f : 0f;
            }
        });
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("effTime", (AncientAltarBuild e) -> new Bar("stat.effTime", Pal.accent, e::effTimef));
    }

    public class AncientAltarBuild extends Building {
        public float effTime = 0;
        public boolean doEffect;
        public StatusEffect effect;


        public boolean canEffect(){
            return !doEffect;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return super.acceptItem(source, item) && canEffect() && effectTypes.containsKey(item);
        }

        @Override
        public void handleItem(Building source, Item item){
            super.handleItem(source, item);
            if (Mathf.chance(effProb)){
                effTime = effCapacity;
                effect = effectTypes.get(item);
                Units.nearbyBuildings(x, y, range, b -> {
                    b.applyBoost(effect.speedMultiplier, effCapacity);
                });
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();

            doEffect = effTime > 0;
            if(!doEffect) effect = null;

            if (doEffect){
                if (!blockOnly){
                    Units.nearby(team, x, y, range, u -> {
                        u.apply(effect);
                    });
                }

                if (effTime > 0){
                    effTime -= 1;
                }
            }
        }

        public float effTimef(){
            return effTime / effCapacity;
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
