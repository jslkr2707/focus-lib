package ages.world.blocks.ancient;

import arc.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.entities.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

public class AncientCrafter extends GenericCrafter{
    protected Item fuel;
    protected int fuelUse = 1;
    protected int fuelCapacity = 15;
    protected Effect fuelBurnEffect = Fx.smoke;
    protected float heatCapacity = 600f;

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
    }

    public class AncientCrafterBuild extends GenericCrafterBuild{
        protected float heat = 0;

        @Override
        public boolean consValid(){
            return super.consValid() && (hasFuel() || heat > 0);
        }

        @Override
        public boolean shouldConsume(){ return super.shouldConsume() && (hasFuel() || heat > 0); }

        @Override
        public boolean acceptItem(Building source, Item item){ return super.acceptItem(source, item) || (item == fuel && items.get(fuel) < fuelCapacity); }

        @Override
        public void updateTile(){
            super.updateTile();

            if (hasFuel() && heat <= 0 && consValid()){
                items.remove(fuel, fuelUse);
                heat = heatCapacity;
                fuelBurnEffect.at(this);
            }

            if (heat > 0){
                heat -= 1;
            }
        }

        public boolean hasFuel(){ return items.get(fuel) > 0; }

        public float heatf(){ return heat / heatCapacity; }

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
