package example.content;

import arc.graphics.Color;
import example.world.blocks.heatMulti;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.world.Block;
import multilib.MultiCrafter;
import multilib.Recipe;

import static mindustry.type.ItemStack.*;

public class ExBlocks implements ContentList {
    public static Block
    //stone age
    firepit;
    @Override
    public void load(){
        firepit = new heatMulti("firepit", 2){{
            requirements(Category.crafting, with(ExItems.tree, 10, ExItems.stone, 10));
            size = 2;
            isSmelter = true;
            craftEffect = Fx.smeltsmoke;
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 1)),
                    new Recipe.OutputContents(with(Items.coal, 2)), 600f
            );
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 5, Items.copper, 3, ExItems.tin, 1)),
                    new Recipe.OutputContents(with(ExItems.stone, 2)), 1000f
            );
        }};
    }
}
