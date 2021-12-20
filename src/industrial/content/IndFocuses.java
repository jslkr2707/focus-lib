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

    resI, resII, resIII, resIV, resV,

    unitI, unitII, unitIII;

    @Override
    public void load(){
        defFirst = new Focus("def-first"){{
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.titaniumWall, ExBlocks.steelWall);
                opposite = IndFocuses.atkFirst;
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
                setToAdd(with(Items.copper, 1000, Items.lead, 1000));
            }
        };

        resII = new ResourceFocus("res-II"){
            {
                setToAdd(with(Items.copper, 500, Items.lead, 500,
                        Items.graphite, 500, Items.metaglass, 500));
            }
        };

        resIII = new ResourceFocus("res-III"){
            {
                setToAdd(with(Items.graphite, 1500, Items.metaglass, 1500, Items.silicon, 1000,
                        Items.titanium, 1000, Items.thorium, 500, Items.plastanium, 500));
            }
        };

        resIV = new ResourceFocus("res-IV"){
            {
                setToAdd(with(Items.titanium, 1500, Items.thorium, 1000,
                        Items.plastanium, 1000, Items.phaseFabric, 500));
            }
        };

        resV = new ResourceFocus("res-V"){
            {
                setToAdd(with(Items.plastanium, 1000, Items.phaseFabric, 1000, Items.surgeAlloy, 1000));
            }
        };

    }
}
