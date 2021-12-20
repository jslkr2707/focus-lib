package industrial.content;

import arc.util.Log;
import mindustry.ctype.ContentList;

public class ModLoader implements ContentList {
    private final ContentList[] contents = {
            new ExItems(),
            new ExBullets(),
            new ExBlocks(),
            new IndFocuses(),
            new IndTechTree()
    };

    public void showOp(){
        Log.info(IndFocuses.defFirst.opposite);
        Log.info(IndFocuses.atkFirst.opposite);
    }
    public void load(){
        for(ContentList list : contents){
            list.load();
        }
        showOp();
    }
}
