package example.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
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
        hasItems = true;
    }
    public heatMulti(String name, Recipe[] recs){
        super(name, recs);
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add("heat", (heatMulti.heatMultiBuild e) -> new Bar(() ->
                Core.bundle.format("bar.poweroutput", Strings.fixed(e.heat * 300, 1)), () -> Pal.lightishOrange, () -> e.heat));
        bars.add("process", (heatMulti.heatMultiBuild h) -> new Bar("bar.process", Pal.powerBar, () -> h.process));
    }

    public class heatMultiBuild extends MultiCrafterBuild{
        public float heat, process = 0;
        /* default crafting time */
        public float defCraftTime = 600f;
        public ItemStack[] inputStack;
        public int heatCap = 1;
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

            applyBoost((heat+2), 120f);

            sad += 1;
            Log.info(sad);


        }
    }
}
