package industrial.content;

import mindustry.ctype.ContentList;

public class ModLoader implements ContentList {
    private final ContentList[] contents = {
            new ExItems(),
            new ExBullets(),
            new ExBlocks(),
            new IndTechTree()
    };
    public void load(){
        for(ContentList list : contents){
            list.load();
        }
    }
}
