package industrial.content;

import industrial.type.Focus;
import industrial.type.ResourceFocus;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.ctype.ContentList;

import static mindustry.type.ItemStack.*;

public class IndFocuses implements ContentList {
    public static Focus
    defFirst, atkFirst, advResearch, premResearch,
    /* region resources */
    resI, resII, resIII;

    @Override
    public void load(){
        defFirst = new Focus("def-first"){{
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.titaniumWall);
                addUnlocks(ExBlocks.steelWall);
        }};

        atkFirst = new Focus("atk-first"){
            {
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.arc);
                addUnlocks(Blocks.scorch);
            }
        };

        resI = new ResourceFocus("res-I"){
            {
                toAdd = with(Items.copper, 500, Items.lead, 500);
            }
        };

    }
}
