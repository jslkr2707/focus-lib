package industrial.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.graphics.Pal;
import mindustry.type.Item;

public class ExItems implements ContentList {
    public static Item
    wood, stone, dirt,
    /* region iron */
    ironOre, iron,
    /* region tin */
    tinOre, tin;


    public void load(){
        dirt = new Item("dirt", Color.rgb (115, 118, 83)){{
            hardness = -1;
            alwaysUnlocked = true;
        }};

        wood = new Item("wood", Color.brown){{
            hardness = 0;
            alwaysUnlocked = true;
            flammability = 0.3f;
        }};

        stone = new Item("stone", Color.gray){{
            hardness = 1;
            alwaysUnlocked = false;
        }};

        ironOre = new Item("ironOre", Pal.metalGrayDark){{
            hardness = 2;
        }};

        iron = new Item("iron", Color.lightGray){{
            hardness = 2;
        }};
    }
}
