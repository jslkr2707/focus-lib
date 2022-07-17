package ages.world.blocks.ancient;

import ages.type.*;
import arc.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.Log;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

import static mindustry.Vars.multicastPort;
import static mindustry.Vars.world;
import static mindustry.content.Blocks.*;
import static ages.content.AgesItems.*;

public class AncientFarm extends GenericCrafter{
    protected Seq<Block> waterBlocks = new Seq<>(new Block[]{water, taintedWater, deepwater});
    protected Item[] allCrops = {erwat};
    protected Block soil = Blocks.dirt;
    public Seq<TextureRegion> phases = new Seq<>();
    public TextureRegion toDraw;
    public Seq<ItemStack> availableCrops = new Seq<>();

    public AncientFarm(String name) {
        super(name);

        hasItems = false;
        hasLiquids = false;
        hasPower = false;

        configurable = true;
        craftTime = 1800f;
    }

    public void addCrops(ItemStack... item){
        availableCrops.add(item);
    }

    /*
    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.reload, t -> {
            t.row();
            t.add("[lightgray]" + Core.bundle.format("ages.block.ancientfarm.phase") + ": [white]" + totalPhase + Core.bundle.format("ages.values.phasevalue"));
        });
    }

     */

    @Override
    public void load(){
        super.load();

        for (Item i: allCrops){
            for (int j = 0;j < ((OrganicItems) i).phases;j++){
                phases.add(Core.atlas.find(i.name + "-phases-" + j));
            }
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
        public int totalPhase;
        public ItemStack selectedCrop;
        public int phase = 0;

        @Override
        public boolean productionValid(){
            return super.productionValid();
        }

        @Override
        public void updateTile(){
            super.updateTile();

            phase = Mathf.round(progress / totalPhase);
        }

        @Override
        public void craft(){
            consume();

            if(selectedCrop != null){
                for(int i = 0; i < selectedCrop.amount; i++){
                    offload(selectedCrop.item);
                }
            }

            if(wasVisible){
                craftEffect.at(x, y);
            }

            progress %= 1f;
        }

        @Override
        public void dumpOutputs(){
            if(selectedCrop != null && timer(timerDump, dumpTime / timeScale)){
                dump(selectedCrop.item);
            }
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);

            table.table(t -> {
                for (ItemStack i: availableCrops){
                    t.button(Core.bundle.format("item."+i.item.name), () -> {
                        selectedCrop = i;
                        totalPhase = ((OrganicItems) selectedCrop.item).phases;
                    }).center().size(50f).growX().checked(i == selectedCrop);
                }
            }).center().growX();
        }


        /*
        @Override
        public void draw(){
            super.draw();

            if (phases.size > 0){
                toDraw = phases.get(phase);
            }

            if (region != toDraw && toDraw != null){
                Draw.rect(toDraw, x, y);
            }
        }
         */
    }
}
