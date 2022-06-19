package ages.world.blocks.ancient;

import arc.*;
import arc.struct.Seq;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;

import static mindustry.content.Blocks.*;

public class AncientFarm extends GenericCrafter{
    protected float harvestMultiplier = 1.5f;
    protected Seq<Block> waterBlocks = new Seq<>(new Block[]{water, taintedWater});

    public AncientFarm(String name) {
        super(name);

        hasItems = false;
        hasLiquids = false;
        hasPower = false;

        craftTime = 1800f;
    }

    @Override
    public void setBars(){
        super.setBars();

        bars.add("progress", (AncientFarmBuild e) -> new Bar(Core.bundle.format("farmprogress"), Pal.lightOrange, () -> e.progress));
    }

    public class AncientFarmBuild extends GenericCrafterBuild{
        @Override
        public boolean shouldConsume(){
            return super.shouldConsume() && isWatered();
        }

        public boolean isWatered(){
            return proximity.contains(b -> waterBlocks.contains(b.block));
        }
    }
}
