package example.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.util.Log;
import arc.util.Strings;
import example.content.ExItems;
import mindustry.content.Items;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
import mindustry.world.modules.ItemModule;
import multilib.MultiCrafter;
import multilib.Recipe;

public class heatMulti extends MultiCrafter {
    public heatMulti(String name, int recLen){
        super(name, recLen);
    }
    public heatMulti(String name, Recipe[] recs){
        super(name, recs);
    }

    @Override
    public void setBars(){
        super.setBars();
        bars.add("craftHeat", (heatMulti.heatMultiBuild e) -> new Bar(() -> Core.bundle.format("craftHeat", Strings.fixed(e.heat * 400, 1)), () -> Pal.lightishOrange, () -> e.heat));
        bars.add("Calorie", (heatMulti.heatMultiBuild f) -> new Bar("fuelBurn", Color.crimson, () -> f.calorie).blink(Color.white));
        bars.add("craftProgress", (heatMulti.heatMultiBuild h) -> new Bar(() -> Core.bundle.format("craftProgress", Strings.fixed(h.Hprogress, 1)), () -> Pal.powerBar, () -> h.Hprogress));
    }

    public class heatMultiBuild extends MultiCrafterBuild{
        public float heat = 0;
        public ItemModule inItems;
        public float Hprogress = 0;
        /* list of items in input */
        public ItemStack[] inputStack;
        /* amount and item name of fuel */
        public ItemStack fuelStack;
        /* heat capacity */
        public int heatCap = 1;
        /* boosting scale by heat */
        public float boostScale = 1f;
        /* how many recipes in that factory */
        public int recLen = recs.length;
        /* to increase heat or not */
        public boolean isheated;
        /* calorie */
        public float calorie;

        @Override
        public void updateTile(){
            super.updateTile();
            this.
            inItems = this.items;
            for (int i = 1; i < recLen; i++){
                inputStack = recs[i].input.items;
                fuelStack = inputStack[0];
                isheated = items.has(inputStack);
                if (isheated) break;
            }
            if (items.has(fuelStack.item, fuelStack.amount)) {
                if (calorie <= 0) {
                    calorie += 1f;
                    inItems.remove(fuelStack.item, 1);
                }
            }
            if (isheated || items.has(fuelStack.item, fuelStack.amount) && heat <= heatCap && calorie >= 0f) {
                heat += 0.0005f;
            }else if (heat >= 0) heat -= 0.0005f;
            calorie -= 0.0003f;
            boostScale = heat * 3;
            applyBoost(boostScale, 1f);

            Hprogress = this.progress;

        }
    }
}
