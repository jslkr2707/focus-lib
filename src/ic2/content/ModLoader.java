package ic2.content;

import mindustry.ctype.ContentList;

public class ModLoader implements ContentList {
    private final ContentList[] contents = {
            new ExItems(),
            new ExBlocks()
    };
    public void load(){
        for(ContentList list : contents){
            list.load();
        }
    }
}
