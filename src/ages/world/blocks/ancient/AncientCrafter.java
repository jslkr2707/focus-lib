package ages.world.blocks.ancient;

import arc.Core;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.gen.*;
import mindustry.graphics.Pal;
import mindustry.type.*;
import mindustry.ui.Bar;
import mindustry.world.blocks.production.*;

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
    public void setBars(){
        super.setBars();

        bars.add(Core.bundle.format("fuel"), (AncientCrafterBuild e) -> new Bar("ages.fuel", Pal.accent, e::heatf));
    }

    public class AncientCrafterBuild extends GenericCrafterBuild{
        protected float heat = 0;

        @Override
        public boolean consValid(){
            return super.consValid() && hasFuel();
        }

        @Override
        public boolean shouldConsume(){ return super.shouldConsume() && hasFuel(); }

        @Override
        public boolean acceptItem(Building source, Item item){ return super.acceptItem(source, item) || (item == fuel && items.get(fuel) < fuelCapacity); }

        @Override
        public void updateTile(){
            super.updateTile();

            if (hasFuel()){
                if (heat == 0 && consValid()){
                    items.remove(fuel, fuelUse);
                    heat = heatCapacity;
                    fuelBurnEffect.at(this);
                }else{
                    heat -= 1;
                }
            }
        }

        public boolean hasFuel(){ return items.get(fuel) > 0; }

        public float heatf(){ return heat / heatCapacity; }
    }
}
