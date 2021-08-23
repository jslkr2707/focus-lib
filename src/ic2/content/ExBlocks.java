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
    macerator, ex1;
    @Override
    public void load(){
        macerator = new heatMulti("macerator", 2){{
            requirements(Category.crafting, with(ExItems.tree, 10, ExItems.stone, 10));
            size = 2;
            isSmelter = true;
            craftEffect = ExFx.carbondust;
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 1)),
                    new Recipe.OutputContents(with(Items.coal, 2)), 600f
            );
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tree, 1, Items.copper, 3, ExItems.tin, 1)),
                    new Recipe.OutputContents(with(ExItems.stone, 2)), 1000f
            );
        }};

        ex1 = new GenericCrafter ("ex1"){{
            size = 2;
            craftEffect = Fx.smeltsmoke;
            requirements(Category.crafting, with(Items.copper, 50));
            outputItem = new ItemStack (Items.lead, 1);
            consumes.items(with(Items.copper,1));
            craftTime = 60f;

        }};
    }
}
