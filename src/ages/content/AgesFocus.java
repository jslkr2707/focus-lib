package ages.content;

import arc.Core;
import ages.type.*;
import mindustry.content.*;

import static mindustry.type.ItemStack.*;

public class AgesFocus{
    public static Focus
    ages,
    /* region resource */
    resI, resII, resIII, resIV, resV,
    logging, mining, charcoal, farming,
    forging, sawing, provision,
    goods;

    /* region defense */
    public static void load(){
        ages = new Focus("ages"){{
            requirements(with());
            addSectors = 0;
            unlock(AgesItems.wood);
            reward(with(AgesItems.wood, 200));
            unlock(Blocks.copperWall);
        }};

        //region resource
        resI = new Focus("resource-i"){{
            addSectors = 2;
            requirements(with());
            reward(with(AgesItems.wood, 200));
        }};

        resII = new Focus("resource-ii"){{
            addSectors = 2;
            requirements(with());
            reward(with(AgesItems.wood, 300, AgesItems.stone, 200));
        }};

        resIII = new Focus("resource-iii"){{
            addSectors = 2;
            requirements(with());
            reward(with(AgesItems.wood, 300, Items.coal, 100));
        }};

        resIV = new Focus("resource-iv"){{
            addSectors = 3;
            requirements(with());
            reward(with(AgesItems.iron, 200));
        }};

        resV = new Focus("resource-v"){{
            addSectors = 3;
            requirements(with());
            reward(with(AgesItems.tin, 200));
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
            addSectors = 2;
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
