package example.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import example.content.ExItems;
import mindustry.content.Items;
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

        bars.add("heat", (heatMulti.heatMultiBuild e) -> new Bar("bar.heat", Pal.lightOrange, () -> e.heat));
    }

    public class heatMultiBuild extends MultiCrafterBuild{
        public float heat = 0;
        public float timer;
        public int a;
        public float calorie = 0;
        public float heatScl = 1f;
        public ItemStack fuel;
        public ItemStack[] inputStack;
        /* just default scl*/
        public float heatCap = 200f;
        public int recLen = recs.length;
        public boolean isheated;

        @Override
        public void draw(){
            Draw.rect(Core.atlas.find(name+"-heat"), x, y);
            super.draw();
        }


        @Override
        public void updateTile(){
            timer += 0.033f;
            isheated = false;
            for (int i = 0; i < recLen; i++) {
                inputStack = recs[i].input.items;
                for (int j = 0; j < inputStack.length; j++) {
                    if (inputStack[j].item == ExItems.tree || inputStack[j].item == Items.coal) {
                        fuel = inputStack[j];
                        a = j;
                        break;
                    }
                }
                if (items.has(inputStack) && fuel != null) isheated = true;
            }
            calorie += 200 * (fuel != null ? fuel.amount : 0);
            if (isheated && heat <= heatCap && calorie > 0) {
                heat += heatScl * timer;
                calorie -= 0.2f;

            }else{
                heat = -0.09f;
            }

            applyBoost((heat+1)/400, 60f);
        }
    }
}
