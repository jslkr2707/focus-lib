package example.content;

import mindustry.ctype.ContentList;

public class ModLoader implements ContentList {
    private final ContentList[] contents = {};
    public void load(){
        for(ContentList list : contents){
            list.load();
        }
    }
}
