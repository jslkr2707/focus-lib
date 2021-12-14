package industrial.content;

import industrial.type.Focus;
import mindustry.content.Blocks;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;

import static mindustry.type.ItemStack.*;

public class IndFocuses implements ContentList {
    public static Focus
    defFirst, atkFirst, advResearch, premResearch,
    /* region resources */
    resI, resII, resIII;

    @Override
    public void load(){
        defFirst = new Focus("def-first"){
            @Override
            public void onUnlock(){
                Blocks.titaniumWall.unlock();
                Blocks.titaniumWallLarge.unlock();
            }
            {
                opposite = IndFocuses.atkFirst;
                requirements(with(ExItems.wood, 300, ExItems.stone, 100));
            }
        };

        atkFirst = new Focus("atk-first"){
            @Override
            public void onUnlock(){
                Blocks.arc.unlock();
                Blocks.scorch.unlock();
            }
            {
                opposite = IndFocuses.defFirst;
                requirements(with(ExItems.wood, 300, ExItems.stone, 100));
            }
        };
    }
}
