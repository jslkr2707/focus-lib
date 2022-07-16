package ages.world.blocks.ancient;

import ages.type.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.Table;
import arc.struct.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.Stat;

import static mindustry.Vars.world;
import static mindustry.content.Blocks.*;
import static ages.content.AgesItems.*;

public class AncientFarm extends GenericCrafter{
    protected Seq<Block> waterBlocks = new Seq<>(new Block[]{water, taintedWater, deepwater});
    protected Block soil = Blocks.dirt;
    public Seq<TextureRegion> phases = new Seq<>();
    public TextureRegion toDraw;
    public int totalPhase;
    public Seq<Item> availableCrops = new Seq<>(new Item[]{erwat});
    public Item selectedCrop;

    public AncientFarm(String name) {
        super(name);

        hasItems = false;
        hasLiquids = false;
        hasPower = false;

        configurable = true;
        craftTime = 1800f;
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.reload, t -> {
            t.row();
            t.add("[lightgray]" + Core.bundle.format("ages.block.ancientfarm.phase") + ": [white]" + totalPhase + Core.bundle.format("ages.values.phasevalue"));
        });
    }

    @Override
    public void load(){
        super.load();

        totalPhase = ((OrganicItems) outputItem.item).phases;

        for (int i = 0;i<totalPhase;i++){
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
        for (Tile other: getNearbyTiles(tile, this)){
            if (waterBlocks.contains(other.floor()) && !waterBlocks.contains(tile.floor()) && other.block() == air) return true;
        }

        return false;
    }

    public Seq<Tile> getNearbyTiles(Tile tile, Block block){
        Seq<Tile> tiles = new Seq<>();

        if (block.isMultiblock()){
            int size = block.size, o = block.sizeOffset;
            for (int i = 0;i<4;i++){
                for (int j = 0;j<size+o;j++){
                    tiles.add(world.tile(tile.x + size + o, tile.y+j));
                    tiles.add(world.tile(tile.x - size - o+1, tile.y+j));
                    tiles.add(world.tile(tile.x + j, tile.y+size+o));
                    tiles.add(world.tile(tile.x + j, tile.y-size-o+1));
                }
            }
        }else{
            for (int i = 0;i<4;i++){
                tiles.add(tile.nearby(i));
            }
        }

        return tiles;
    }

    public class AncientFarmBuild extends GenericCrafterBuild{
        public int phase = 0;

        @Override
        public void updateTile(){
            super.updateTile();

            phase = Mathf.round(progress / totalPhase);
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);

            table.table(t -> {
                for (Item i: availableCrops){
                    t.button(Core.bundle.format(i.name), () -> {
                        selectedCrop = i;
                        totalPhase = ((OrganicItems)selectedCrop).phases;
                    }).center().size(50f).growX();
                }
            }).center().growX();
        }

        @Override
        public void draw(){
            super.draw();

            if (phases.size > 0){
                toDraw = phases.get(phase);
            }

            if (region != toDraw){
                Draw.rect(toDraw, x, y);
            }
        }
    }
}
