package ages.world.blocks.ancient;

import ages.world.blocks.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.struct.*;
import arc.util.Log;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.gen.Unit;
import mindustry.graphics.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;

import static mindustry.content.Blocks.*;

public class AncientFarm extends GenericCrafter{
    protected Seq<Block> waterBlocks = new Seq<>(new Block[]{water, taintedWater});
    protected Block soil = Blocks.dirt;
    public Seq<TextureRegion> phases = new Seq<>();
    public int unitLimit = 2;

    public AncientFarm(String name) {
        super(name);

        hasItems = false;
        hasLiquids = false;
        hasPower = false;

        rotate = true;

        craftTime = 1800f;
    }

    @Override
    public void load(){
        super.load();

        for (int i = 0;i<4;i++){
            phases.add(Core.atlas.find(outputItem.item.name + "-phase-" + i));
        }
    }

    @Override
    public void setBars(){
        super.setBars();

        addBar("progress", (AncientFarmBuild e) -> new Bar(Core.bundle.format("farmprogress"), Pal.lightOrange, () -> e.progress));
    }

    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        if (isMultiblock()){
            for (Tile other: tile.getLinkedTilesAs(this, tempTiles)){
                if (waterBlocks.contains(other.block())) return true;
            }
        }

        return false;
    }

    public class AncientFarmBuild extends GenericCrafterBuild implements UnitHolder {
        public int inUnits;
        public int phase = 0;

        @Override
        public int getUnits(){
            return inUnits;
        }

        @Override
        public boolean acceptUnit(Unit u){
            return inUnits < unitLimit;
        }

        @Override
        public boolean shouldConsume(){
            return super.shouldConsume() && getUnits() > 0;
        }

        @Override
        public void updateTile(){
            super.updateTile();

            Log.info(rotation);

            phase = Mathf.round(progress / 4);
        }

        @Override
        public void draw(){
            super.draw();

            TextureRegion toDraw = phases.get(phase);

            if (region != toDraw){
                Draw.rect(toDraw, x, y);
            }
        }
    }
}
