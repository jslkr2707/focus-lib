package ages.world.blocks.production;

import arc.*;
import arc.graphics.*;
import arc.math.*;
import arc.struct.*;
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
import multicraft.MultiCrafter;

import static ages.AgesVars.*;
import static arc.Core.bundle;
import static mindustry.type.ItemStack.with;

public class ModernCrafter extends MultiCrafter{
    public Fuel[] fuel;

    public ModernCrafter(String name) {
        super(name);

        hasPower = false;
    }

    public void consumeFuel(Fuel... items){
        this.fuel = items;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.input, t -> {
            t.row();
            for (Fuel i: fuel){
                t.add(new ItemImage(i.item.uiIcon, 1));
                t.add(Core.bundle.format("stat.fuel") + i.fuelUse + i.heatCapacity / 60 + StatUnit.seconds.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
            }
        });
    }

    @Override
    public void setBars(){
        super.setBars();

        //addBar(bundle.format("fuel"), (ModernCrafterBuild e) -> new Bar("bar.fuel", Pal.accent, e::heatf));
        //addBar(bundle.format("heat"), (ModernCrafterBuild e) -> new Bar("bar.heat", Pal.heal, e::heathf));
    }

    public class ModernCrafterBuild extends MultiCrafterBuild {
        protected float heat = 0;
        protected float heath = 0;

        @Override
        public boolean acceptItem(Building source, Item item){
            if (currentFuel() == null || item != currentFuel().item) return false;
            return super.acceptItem(source, item);
        }

        public Fuel currentFuel(){
            if (fuel.length == 1){
                return fuel[0];
            } else {
                for (Fuel f: fuel){
                    if (items.has(with(f.item, f.fuelUse))) return f;
                }
                return null;
            }
        }
    }
}
