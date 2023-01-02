package ages.content;

import arc.Core;
import ages.type.*;
import mindustry.content.*;

import static mindustry.type.ItemStack.*;

public class AgesFocus{
    public static Focus
    ages,
    /* region industry */
    logging, mining, charcoal, farming,
    forging, sawing, provision,
    goods;

    /* region defense */
    public static void load(){
        ages = new Focus("ages"){{
            requirements(with());
            unlock(AgesItems.wood);
            reward(with(AgesItems.wood, 200));
        }};

        //region tier 1 industry

        logging = new Focus("logging"){{
            requirements(with(AgesItems.wood, 300));
            reward(with());
            unlock(AgesBlocks.lumber);
        }};

        mining = new Focus("mining"){{
            requirements(with(AgesItems.wood, 100));
            reward(with(AgesItems.stone, 100));
            unlock(/*AgesBlocks.basicMine,*/ AgesItems.stone);
        }};

        charcoal = new Focus("charcoal"){{
            requirements(with(AgesItems.wood, 100));
            reward(with(Items.coal, 100));
            unlock(Items.coal/*, AgesBlocks.pitKiln*/);
        }};

        /*
        farming = new Focus("farming"){{

            reward(with());
            unlock(AgesBlocks.farm)
        }};
        */

        //endregion

        //region tier 2 industry

    }
}
