package ages.world.blocks.ancient;

import mindustry.type.Item;
import mindustry.world.blocks.production.GenericCrafter;

public class AncientCrafter extends GenericCrafter{
    protected Item fuel;

    public AncientCrafter(String name) {
        super(name);

        hasPower = false;
    }

    public class AncientCrafterBuild extends GenericCrafterBuild{
        @Override
        public boolean consValid(){
            return super.consValid() && items.has(fuel);
        }
    }
}
