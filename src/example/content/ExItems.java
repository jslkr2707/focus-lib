package example.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class ExItems implements ContentList {
    public static Item
    //stone age
    tree, stone, dirt,
    //metal age
    tin, bronze;
    public void load(){
        //region stone age
        tree = new Item("tree", Color.brown){{
            hardness = 0;
            alwaysUnlocked = true;
            flammability = 0.3f;
        }};

        stone = new Item("stone", Color.gray){{
            hardness = 1;
            alwaysUnlocked = false;
        }};

        //region metal age

    }
}
