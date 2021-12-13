package industrial.content;

import industrial.type.Focus;
import mindustry.ctype.ContentList;

import static mindustry.type.ItemStack.*;

public class IndFocuses implements ContentList {
    public static Focus
    defFirst, atkFirst, advResearch, premResearch,
    /* region resources */
    resI, resII, resIII;

    @Override
    public void load(){
        defFirst = new Focus("def-first"){
            {
                requirements(with(ExItems.wood, 300, ExItems.stone, 100));
            }
        };
    }
}
