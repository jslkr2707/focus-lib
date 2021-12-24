package industrial.content;

import arc.Core;
import arc.util.Log;
import industrial.type.Focus;
import industrial.type.ResourceFocus;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.type.ItemStack;

import static mindustry.type.ItemStack.*;

public class IndFocuses implements ContentList {
    public static Focus
    defEffort, wallFirst, turretFirst, advResearch, premResearch,

    resI, resII, resIII, resIV, resV,

    unitI, unitII, unitIII;

    @Override
    public void load(){
        defEffort = new Focus("def-effort"){{
            requirements(ItemStack.empty);
            unlockContents = null;
        }};

        wallFirst = new Focus("wall-first"){{
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.titaniumWall, ExBlocks.steelWall);
                localizedName = Core.bundle.get("focus.wall-first.name");
                description = Core.bundle.getOrNull("focus.wall-first.description");
        }};

        turretFirst = new Focus("turret-first"){{
                requirements(with(Items.copper, 300, Items.lead, 100));
                addUnlocks(Blocks.hail, Blocks.scorch);
                localizedName = Core.bundle.get("focus.turret-first.name");
                description = Core.bundle.getOrNull("focus.turret-first.description");
        }};

        resI = new ResourceFocus("res-I"){{
                setToAdd(with(Items.copper, 1000, Items.lead, 1000));
        }};

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
