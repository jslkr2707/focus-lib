package focus.content;

import focus.type.*;
import mindustry.content.*;
import mindustry.type.*;

public class ExFocus {
    public static Focus
    example, advanced, intermediate;

    /* region defense */
    public static void load(){
        example = new Focus("example"){{
            requirements(ItemStack.with());
            unlock(Blocks.mechanicalDrill);
            reward(ItemStack.with(Items.copper, 500, Items.lead, 500));
        }};

        advanced = new Focus("advanced"){{
            addSectors = 2;
            requirements(ItemStack.with(Items.copper, 2500, Items.lead, 2500));
            unlock(Items.coal, Blocks.copperWall);
            reward(ItemStack.with(Items.coal, 100));

        }};

        intermediate = new Focus("intermediate"){{
            requirements(ItemStack.with());
            reward(ItemStack.with());
            unlock(Blocks.arc);
        }};
    }
}
