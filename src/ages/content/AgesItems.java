package ages.content;

import ages.type.*;
import arc.graphics.*;
import mindustry.type.*;

public class AgesItems{
    public static Item
    /* region ancient */
    wood, stone, dirt, brick,
    /* region metal
    ironOre, iron, ironPowder, steel,
    tinOre, tin, tinPowder,
    bauxite, aluminum,
    */
    /* region organic */
    erwat, zibel;
    public static void load(){
        dirt = new Item("dirt", Color.valueOf("46205a")){{
            hardness = 0;
            cost = 1;
            lowPriority = true;
        }};

        wood = new Item("wood", Color.brown){{
            hardness = 0;
            flammability = 0.3f;
            cost = 2;
        }};

        stone = new Item("stone", Color.gray){{
            hardness = 1;
            cost = 3;
        }};

        brick = new Item("brick", Color.valueOf("bc4a3c")){{
            cost = 2;
        }};

        /*
        ironOre = new Item("ironOre", Pal.metalGrayDark){{
            hardness = 2;
        }};

        iron = new Item("iron", Color.lightGray){{
            cost = 3;
        }};

        steel = new Item("steel", Color.gray){{
            cost = 6;
        }};

        ironPowder = new Item("iron-powder", Color.darkGray){{
        }};

        tinOre = new Item("tinOre", Color.rgb(167, 166, 186)){{
            hardness = 2;
            cost = 2;
        }};

        tin = new Item("tin", Color.rgb(180, 207, 202)){{
            cost = 3;
        }};

        tinPowder = new Item("tin-powder", Color.rgb(154, 177, 173)){};

        bauxite = new Item("bauxite", Color.rgb(237,238,218)){{
            hardness = 2;
        }};

        aluminum = new Item("aluminum", Color.rgb(80, 80, 80)){{
            cost = 4;
        }};
         */

        erwat = new OrganicItems("erwat", Color.rgb(248, 180, 35)){{
            flammability = 0.2f;
            cost = 1;
            calories = 1;
            phases = 4;
            growTime = 1800f;
        }};

        zibel = new OrganicItems("zibel", Color.rgb(120, 191, 56)){{
            flammability = 0f;
            cost = 1;
            calories = 1;
            phases = 4;
            growTime = 1800f;
        }};
    }
}
