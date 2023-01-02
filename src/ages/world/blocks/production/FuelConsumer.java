package ages.world.blocks.production;

import ages.type.*;
import arc.math.*;
import arc.struct.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.heat.*;
import mindustry.world.consumers.*;

public class FuelConsumer extends Block {
    public Seq<Fuel> fuel;
    public float coolingRate = 0.5f;

    public FuelConsumer(String name) {
        super(name);
        outputsPower = false;
        outputsLiquid = false;
        configurable = true;
        update = true;
        rotate = true;
        hasItems = true;
    }

    public void addFuel(Fuel... fuel){
        this.fuel = Seq.with(fuel);
    }

    /*
    @Override
    public void init(){
        super.init();

        consume(new ConsumeItemFilter(i -> fuel.contains(f -> f.item == i)){
            @Override
            public float efficiency(Building build){
                return super.efficiency(build) * ((FuelConsumerBuild)build).heatf();
            }
        });
    }

     */

    @Override
    public void setBars(){
        super.setBars();

        addBar("heat", (FuelConsumerBuild e) -> new Bar("bar.heat", Pal.lightFlame, e::heatf));
        addBar("lifetime", (FuelConsumerBuild e) -> new Bar("bar.lifetime", Pal.darkFlame, e::progressf));
    }

    public class FuelConsumerBuild extends Building{
        public float heat, progress, warmup;

        public Fuel currentFuel(){
            return fuel.size > 1 ? fuel.find(f -> items.has(f.item)) : fuel.get(0);
        }

        public boolean hasFuel(){
            return currentFuel() != null && items.has(currentFuel().item);
        }

        public float craftingTime(){
            return currentFuel().lifetime;
        }

        public float progressf(){ return progress / craftingTime(); }

        public float heatf(){
            return currentFuel() == null ? 0f : heat / currentFuel().heatCapacity;
        }

        public void consumeFuel(){
            items.remove(currentFuel().item, 1);
            progress = craftingTime();
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return currentFuel() == null ? fuel.contains(f -> f.item == item) : item == currentFuel().item;
        }

        @Override
        public void updateTile(){
            warmup = Mathf.lerpDelta(warmup, Mathf.num(hasFuel()), 0.05f);

            if (progress <= 0.001f) {
                if (hasFuel()){
                    consumeFuel();
                } else {
                    heat = Mathf.approachDelta(heat, 0f, coolingRate);
                }
            } else {
                progress -= 1f;
                heat = Mathf.lerpDelta(heat, currentFuel().heatCapacity, currentFuel().heatRate);
            }
        }
    }
}
