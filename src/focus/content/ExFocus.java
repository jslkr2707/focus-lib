package focus.content;

import focus.type.*;
import mindustry.content.*;
import mindustry.type.*;

import static mindustry.type.ItemStack.*;

public class ExFocus {
    public static Focus
    example, advanced;

    /* region defense */
    public static void load(){
        example = new Focus("example"){{
            addSectors = 1;
            requirements(ItemStack.with(Items.copper, 100, Items.lead, 200));
            unlock(Blocks.copperWall, Blocks.copperWallLarge);
            reward(ItemStack.with(Items.coal, 200));
        }};

        advanced = new Focus("advanced"){{
            addSectors = 2;
            requirements(ItemStack.with(Items.graphite, 500, Items.silicon, 300));
            unlock(Items.metaglass, Blocks.kiln);
            reward(ItemStack.with(Items.silicon, 1000));
        }};
    }
}
