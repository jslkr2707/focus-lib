package industrial.content;

import arc.Core;
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
    resI, resII, resIII, resIV, resV,
    /* region units */
    unitI, unitII, unitIII;

    @Override
    public void load(){
        defFirst = new Focus("def-first"){{
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.titaniumWall, ExBlocks.steelWall);
                localizedName = Core.bundle.get("focus.def-first.name");
                description = Core.bundle.getOrNull("focus.def-first.description");
        }};

        atkFirst = new Focus("atk-first"){
            {
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.hail, Blocks.scorch);
            }
        };

        resI = new ResourceFocus("res-I"){
            {
                toAdd = with(Items.copper, 1000, Items.lead, 1000);
            }
        };

        resII = new ResourceFocus("res-II"){
            {
                toAdd = with(Items.copper, 1500, Items.lead, 1500, Items.graphite, 500, Items.metaglass, 500, Items.silicon, 500);
            }
        };

        resIII = new ResourceFocus("res-III"){
            {
                toAdd = with(Items.graphite, 1500, Items.metaglass, 1500, Items.silicon, 1500,
                        Items.titanium, 1000, Items.thorium, 500, Items.plastanium, 500);
            }
        };

    }
}
