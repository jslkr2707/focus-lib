package industrial.content;

import industrial.type.Focus;
import mindustry.content.Blocks;
import mindustry.content.Items;
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
        defFirst = new Focus("def-first"){{
                opposite = IndFocuses.atkFirst;
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.titaniumWall);
                addUnlocks(Blocks.titaniumWallLarge);

        }};

        atkFirst = new Focus("atk-first"){
            {
                opposite = IndFocuses.defFirst;
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.door);
                addUnlocks(Blocks.scorch);
            }
        };
    }
}
