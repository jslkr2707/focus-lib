package example.world.blocks;

import arc.Core;
import arc.util.Log;
import arc.util.Strings;
import mindustry.graphics.Pal;
import mindustry.type.ItemStack;
import mindustry.ui.Bar;
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

        bars.add("heat", (heatMulti.heatMultiBuild e) -> new Bar(() ->
                Core.bundle.format("bar.heat", Strings.fixed(e.heat * 400, 1)), () -> Pal.lightishOrange, () -> e.heat));
        bars.add("process", (heatMulti.heatMultiBuild h) -> new Bar(() ->
                Core.bundle.format("bar.progress", Strings.fixed((h.Hprogress / 600), 1)), () -> Pal.powerBar, () -> h.Hprogress));
    }
    public class heatMultiBuild extends MultiCrafterBuild{
        public float heat, Hprogress = 0;
        /* default crafting time */
        public float defCraftTime = 600f;
        public ItemStack[] inputStack;
        public int heatCap = 1;
        public float boostScale = 1f;
        public int recLen = recs.length;
        public boolean isheated;
        public int sad;

        @Override
        public void updateTile(){
            super.updateTile();
            for (int i = 0; i < recLen; i++){
                inputStack = recs[i].input.items;
                isheated = items.has(inputStack);
                if (isheated) break;
            }
            if (isheated && heat <= heatCap) {
                heat += 0.0005f;
            }else{
                if (heat >= 0){heat -= 0.0005f;}
            }

            boostScale = heat * 3;
            applyBoost(boostScale, 120f);
            if (isheated) { Hprogress += 0.0024f; }
            if (Hprogress == 600f) {
                craftTime = 0.1f;
                Hprogress = 0;
            }
            craftTime = 600f;

            sad += 1;
            Log.info(sad);

        }
    }
}
