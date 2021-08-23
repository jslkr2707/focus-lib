package ic2.content;

import ic2.world.blocks.heatMulti;
import ic2.world.blocks.testTable;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.production.GenericCrafter;
import multilib.Recipe;

import static mindustry.type.ItemStack.*;

public class ExBlocks implements ContentList {
    public static Block
    furnace, macerator;
    @Override
    public void load(){
        furnace = new heatMulti("furnace", 3){{
            requirements(Category.crafting, with(ExItems.wood, 10, ExItems.stone, 10));
            size = 2;
            isSmelter = true;
            addRecipe(
                    new Recipe.InputContents(with(ExItems.wood, 1)),
                    new Recipe.OutputContents(with(Items.coal, 2)), 600f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 1, ExItems.ironOre, 1)),
                    new Recipe.OutputContents(with( ExItems.iron, 1)), 1000f
            );
            addRecipe(
                    new Recipe.InputContents (with(Items.coal, 1, ExItems.groundIron, 1)),
                    new Recipe.OutputContents (with(ExItems.iron, 1)), 1000f
            );
        }};

        macerator = new GenericCrafter ("macerator"){{
            size = 2;
            craftEffect = Fx.smeltsmoke;
            requirements(Category.crafting, with(Items.copper, 50));
            outputItem = new ItemStack(ExItems.ironOre, 1);
            consumes.items(with(ExItems.groundIron, 2));
            craftTime = 60f;

        }};
    }
}
