package ages.content;

import arc.Core;
import ages.type.Focus;
import ages.type.ResourceFocus;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.ctype.ContentList;

import static mindustry.type.ItemStack.*;

public class AgesFocus implements ContentList {
    public static Focus
    defEffort, wallFirst, turretFirst, advResearch, premResearch,

    resI, resII, resIII, resIV, resV,

    oreI, oreII, oreIII,

    liquidI, liquidII,

    unitI, unitII, unitIII;

    @Override
    public void load(){
        defEffort = new Focus("def-effort"){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            addUnlocks(Blocks.router, Blocks.copperWall);
        }};

        wallFirst = new Focus("wall-first"){{
            requirements(with(Items.copper, 300, Items.lead, 100));
            addUnlocks(Blocks.titaniumWall);
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
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.copper, 100, Items.lead, 100));
            localizedName = Core.bundle.get("focus.res-I.name");
            description = Core.bundle.getOrNull("focus.res-I.description");
        }};

        resII = new ResourceFocus("res-II"){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.graphite, 500, Items.metaglass, 500));
            localizedName = Core.bundle.get("focus.res-II.name");
            description = Core.bundle.getOrNull("focus.res-II.description");
        }};

        resIII = new ResourceFocus("res-III"){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.graphite, 1000, Items.metaglass, 1000, Items.silicon, 1000));
            localizedName = Core.bundle.get("focus.res-III.name");
            description = Core.bundle.getOrNull("focus.res-III.description");
        }};

        resIV = new ResourceFocus("res-IV"){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.silicon, 1500, Items.plastanium, 500, Items.phaseFabric, 500));
            localizedName = Core.bundle.get("focus.res-IV.name");
            description = Core.bundle.getOrNull("focus.res-IV.description");
        }};

        resV = new ResourceFocus("res-V"){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.plastanium, 1000, Items.phaseFabric, 1000, Items.surgeAlloy, 1000));
            localizedName = Core.bundle.get("focus.res-V.name");
            description = Core.bundle.getOrNull("focus.res-V.description");
        }};

        oreI = new ResourceFocus(("ore-I")){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.copper, 1000, Items.lead, 1000));
            localizedName = Core.bundle.get("focus.ore-I.name");
            description = Core.bundle.getOrNull("focus.ore-I.description");
        }};

        oreII = new ResourceFocus(("ore-II")){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.coal, 1000, Items.sand, 1000, Items.scrap, 1000));
            localizedName = Core.bundle.get("focus.ore-II.name");
            description = Core.bundle.getOrNull("focus.ore-II.description");
        }};

        oreIII = new ResourceFocus(("ore-III")){{
            requirements(with(Items.copper, 100, Items.lead, 100));
            setToAdd(with(Items.titanium, 1000, Items.thorium, 1000));
            localizedName = Core.bundle.get("focus.ore-III.name");
            description = Core.bundle.getOrNull("focus.ore-III.description");
        }};

        liquidI = new ResourceFocus(("liquid-I")){{
            requirements(with(Items.graphite, 500, Items.metaglass, 500));
            setToAdd(with(Items.sporePod, 1000));
            addUnlocks(Blocks.liquidTank);
        }};

        liquidII = new ResourceFocus(("liquid-II")){{
            requirements(with(Items.titanium, 2000));
            setToAdd(with(Items.copper, 1000, Items.lead, 1000));
            addUnlocks(Blocks.plastaniumCompressor);
        }};
    }
}
