package example.content;

import arc.graphics.Color;
import example.world.blocks.heatMulti;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import multilib.MultiCrafter;
import multilib.Recipe;

import static mindustry.type.ItemStack.*;

public class ExBlocks implements ContentList {
    public static Block
    //stone age
    firepit, exaaa;

    @Override
    public void load(){
        firepit = new heatMulti("firepit", 2){{
            requirements(Category.crafting, with(ExItems.tree, 10, ExItems.stone, 10));
            craftEffect = Fx.smeltsmoke;
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 1)),
                    new Recipe.OutputContents(with(Items.coal, 2)), 600f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.copper, 3, ExItems.tin, 1)),
                    new Recipe.OutputContents(with(ExItems.bronze, 2)), 1000f
            );
        }};

        exaaa = new MultiCrafter("ex", 2){{
            requirements(Category.crafting, with(Items.copper, 30, Items.lead, 25));
            craftEffect = Fx.smeltsmoke;

            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 1)),
                    new Recipe.OutputContents(with(Items.coal, 1)), 60f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.copper, 3, ExItems.tin, 1)),
                    new Recipe.OutputContents(with(ExItems.bronze, 2)), 120f
            );
        }};

    }
}
