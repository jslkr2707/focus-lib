package ic2.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class ExItems implements ContentList {
    public static Item
    tree, stone, dirt,
    /* region coal */
    coalPowder, compressedCoal;
    public void load(){

        dirt = new Item("dirt", Color.rgb (115, 118, 83)){{
            hardness = -1;
            alwaysUnlocked = true;
        }};

        tree = new Item("tree", Color.brown){{
            hardness = 0;
            alwaysUnlocked = true;
            flammability = 0.3f;
        }};

        stone = new Item("stone", Color.gray){{
            hardness = 1;
            alwaysUnlocked = false;
        }};

    }
}
