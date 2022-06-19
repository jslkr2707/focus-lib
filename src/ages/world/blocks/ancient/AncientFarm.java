package ages.world.blocks.ancient;

import mindustry.world.blocks.production.GenericCrafter;

public class AncientFarm extends GenericCrafter{
    public AncientFarm(String name) {
        super(name);

        hasItems = false;
        hasLiquids = true;
        hasPower = false;
    }
}
