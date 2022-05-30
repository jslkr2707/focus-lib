package ages.content;

import mindustry.ctype.ContentList;

public class ModLoader implements ContentList {
    private final ContentList[] contents = {
            new AgesItems(),
            new AgesBullets(),
            new AgesBlocks(),
            new AgesFocus(),
            new AgesTechTree()
    };

    public void load(){
        for(ContentList list : contents){
            list.load();
        }
    }
}
