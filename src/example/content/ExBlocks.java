package example.content;

import example.world.blocks.heatMulti;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.world.Block;
import multilib.Recipe;

import static mindustry.type.ItemStack.with;

public class ExBlocks implements ContentList {
    public static Block
    //stone age
    firepit;

    @Override
    public void load(){
        firepit = new heatMulti("firepit", 2){{
            isSmelter = true;

            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 1)),
                    new Recipe.OutputContents(with(Items.coal, 1)), 60f
            );
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tin, 1, Items.copper, 3)),
                    new Recipe.OutputContents(with(ExItems.bronze, 2)), 120f
            );
        }};
    }
}
