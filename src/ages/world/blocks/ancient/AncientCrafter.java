package ages.world.blocks.ancient;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
import arc.util.Log;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.production.*;
import mindustry.world.consumers.*;
import mindustry.world.meta.*;

import static ages.AgesVars.*;
import static arc.Core.bundle;

public class AncientCrafter extends GenericCrafter{
    protected Seq<Item> fuel = new Seq<>();
    protected int fuelUse = 1;
    protected int fuelCapacity = 15;
    protected Effect fuelBurnEffect = Fx.smoke;
    protected float heatCapacity = 600f;
    protected float minCraftHeat = 100f;

    public AncientCrafter(String name) {
        super(name);

        hasPower = false;
    }

    public void addFuel(Item... item){
        fuel.add(item);
    }

    public boolean inFuel(Item item){
        for (Item i: overlap()){
            if (item == i) return true;
        }
        return false;
    }

    public Item[] overlap(){
        Item[] overlapItems = new Item[fuel.size];
        int n = 0;

        for (Consume i: consumeBuilder){
            for (ItemStack j: ((ConsumeItems)i).items){
                if (fuel.contains(j.item)){
                    overlapItems[n] = j.item;
                    n++;
                }
            }
        }

        return overlapItems;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, t -> {
            t.row();
            for (Item i: fuel){
                t.add(new ItemImage(i.uiIcon, 1));
                t.add(Core.bundle.format("stat.fuel") + fuelUse + heatCapacity / 60 + StatUnit.seconds.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
            }
        });
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar(bundle.format("fuel"), (AncientCrafterBuild e) -> new Bar("bar.fuel", Pal.accent, e::heatf));
        addBar(bundle.format("heat"), (AncientCrafterBuild e) -> new Bar("bar.heat", Pal.heal, e::heathf));
    }

    public class AncientCrafterBuild extends GenericCrafterBuild{
        public Item currentFuel;
        protected float heat = 0;
        protected float heath = 0;

        @Override
        public boolean canConsume(){
            return super.canConsume() && hasFuel();
        }

        @Override
        public boolean shouldConsume(){ return super.shouldConsume() && (hasFuel() || heath >= minCraftHeat); }

        @Override
        public boolean acceptItem(Building source, Item item){
            if (fuel.contains(item) && currentFuel == null) currentFuel = item;
            return super.acceptItem(source, item) || (fuel.contains(item) && items.get(item) < fuelCapacity * fuelCapMulti) && items.get(currentFuel) < 1;
        }

        @Override
        public void updateTile(){
            super.updateTile();

            if (canConsume()){
                if (heath < heatCapacity * heatCapMulti){
                    heath = Mathf.lerpDelta(heath, heatCapacity * heatCapMulti, 0.02f);
                }

                if (heat <= 0) {
                    items.remove(currentFuel, fuelUse * fuelUseMulti);
                    heat = heatCapacity * heatCapMulti;
                    fuelBurnEffect.at(this);
                }
            }

            if (heat > 0){
                heat -= 1;
            }else{
                heath -= 1;
            }
        }

        public boolean hasFuel(){ return currentFuel != null && items.get(currentFuel) > 0; }

        public float heatf(){ return heat / (heatCapacity * heatCapMulti); }

        public float heathf(){ return heath / (heatCapacity * heatCapMulti); }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(heat);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            heat = read.f();
        }
    }
}
