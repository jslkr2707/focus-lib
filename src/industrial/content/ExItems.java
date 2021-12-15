package industrial.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.graphics.Pal;
import mindustry.type.Item;

public class ExItems implements ContentList {
    public static Item
    wood, stone,
    /* region iron */
    ironOre, iron,
    /* region tin */
    tinOre, tin;


    public void load(){
        wood = new Item("wood", Color.brown){{
            hardness = 0;
            flammability = 0.3f;
        }};

        stone = new Item("stone", Color.gray){{
            hardness = 1;
        }};

        ironOre = new Item("ironOre", Pal.metalGrayDark){{
            hardness = 2;
        }};

        iron = new Item("iron", Color.lightGray){{
            hardness = 2;
        }};
    }
}
