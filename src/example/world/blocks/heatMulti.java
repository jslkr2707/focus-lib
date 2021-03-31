package example.world.blocks;

import arc.Core;
import arc.graphics.g2d.Draw;
import mindustry.type.ItemStack;
import multilib.MultiCrafter;
import multilib.Recipe;

public class heatMulti extends MultiCrafter {

    public heatMulti(String name, int recLen){
        super(name, recLen);
    }

    public heatMulti(String name, Recipe[] recs){
        super(name, recs);
    }

    public class heatMultiBuild extends MultiCrafterBuild{
        public float heat, heatCapacity, timer;
        /* just default scl*/
        public float heatScl = 1f;
        public boolean isheated;

        public float t1 =  heatCapacity / heatScl;
        @Override
        public void draw(){
            Draw.rect(Core.atlas.find(name+"-heat"), x, y);
            super.draw();
        }

        @Override
        public void updateTile(){
            timer += 1f;
            isheated = false;
            for (Recipe rec : recs) {
                ItemStack[] inputStack = rec.input.items;
                if (items.has(inputStack)) isheated = true;
            }
            if (isheated && heat < heatCapacity && timer <= t1) {
                heat += -Math.pow(timer, 2) + 2*t1*timer;
            }

            applyBoost((heat+1)/400, 60f);
        }
    }
}
