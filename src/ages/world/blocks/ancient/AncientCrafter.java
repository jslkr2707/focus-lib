package ages.world.blocks.ancient;

import arc.*;
import arc.math.Mathf;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

import static ages.AgesVars.*;

public class AncientCrafter extends GenericCrafter{
    protected Item fuel;
    protected int fuelUse = 1;
    protected int fuelCapacity = 15;
    protected Effect fuelBurnEffect = Fx.smoke;
    protected float heatCapacity = 600f;
    protected float minCraftHeat = 100f;

    public AncientCrafter(String name) {
        super(name);

        hasPower = false;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, StatValues.items(heatCapacity, new ItemStack(fuel, fuelUse)));
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add(Core.bundle.format("fuel"), (AncientCrafterBuild e) -> new Bar("ages.fuel", Pal.accent, e::heatf));
        bars.add(Core.bundle.format("heat"), (AncientCrafterBuild e) -> new Bar("ages.heat", Pal.heal, e::heathf));
    }

    public class AncientCrafterBuild extends GenericCrafterBuild{
        protected float heat = 0;
        protected float heath = 0;

        @Override
        public boolean consValid(){
            return super.consValid() && (hasFuel() || heath >= minCraftHeat);
        }

        @Override
        public boolean shouldConsume(){ return super.shouldConsume() && (hasFuel() || heath >= minCraftHeat); }

        @Override
        public boolean acceptItem(Building source, Item item){ return super.acceptItem(source, item) || (item == fuel && items.get(fuel) < fuelCapacity * fuelCapMulti); }

        @Override
        public void updateTile(){
            super.updateTile();

            if (hasFuel() && heat <= 0 && consValid()){
                items.remove(fuel, fuelUse * fuelUseMulti);
                heat = heatCapacity * heatCapMulti;
                fuelBurnEffect.at(this);
                if (heath < 1f){
                    heath = Mathf.lerpDelta(heath, heatCapacity * heatCapMulti, 0.02f);
                }
            }

            if (heat > 0){
                heat -= 1;
            }else{
                heath -= 1;
            }
        }

        public boolean hasFuel(){ return items.get(fuel) > 0; }

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
