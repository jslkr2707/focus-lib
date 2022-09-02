package ages.world.blocks.ancient;

import ages.type.*;
import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import arc.util.*;
import arc.util.io.*;
import mindustry.content.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.*;
import mindustry.world.blocks.production.*;
import mindustry.world.meta.*;

import static mindustry.Vars.world;
import static mindustry.content.Blocks.*;

public class AncientFarm extends GenericCrafter{
    protected Seq<Block> waterBlocks = new Seq<>(new Block[]{water, taintedWater, deepwater});
    protected Block soil = Blocks.dirt;
    public TextureRegion[][] phases;
    public Seq<ItemStack> availableCrops = new Seq<>();

    public AncientFarm(String name) {
        super(name);

        hasItems = false;
        hasLiquids = false;
        hasPower = false;

        configurable = true;
    }

    public void addCrops(ItemStack... item){
        availableCrops.add(item);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.reload, t -> {
            t.row();
            t.add("[lightgray]" + Core.bundle.format("stat.phase") + ": [white]");
            for (ItemStack i: availableCrops){
                t.add(new ItemImage(i));
                t.add((int)(((OrganicItems)i.item).growTime) + StatUnit.seconds.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
            }
        });
    }

    @Override
    public void load(){
        super.load();

        int a = 0;
        phases = new TextureRegion[availableCrops.size][];

        for (ItemStack i: availableCrops){
            OrganicItems j = (OrganicItems)i.item;
            int n = j.phases;
            TextureRegion[] t = new TextureRegion[n];
            for (int l = 0;l<n;l++){
                TextureRegion p = Core.atlas.find(j.name+"-"+l);
                if (!p.found()) p = region;
                t[l] = p;
            }
            phases[a] = t;
            a++;
        }
    }

    @Override
    public void setBars(){
        super.setBars();

        removeBar("items");
        addBar("progress", (AncientFarmBuild e) -> new Bar(Core.bundle.format("bar.farmprogress"), Pal.lightOrange, () -> e.progress));
    }
    
    @Override
    public boolean canPlaceOn(Tile tile, Team team, int rotation) {
        return getNearbyTiles(tile, this).count(other -> waterBlocks.contains(other.floor()) && !waterBlocks.contains(tile.floor()) && other.block() == air && tile.floor() == soil) > 0
                && tile.getLinkedTilesAs(this, tempTiles).count(t -> t.drop() != null || waterBlocks.contains(t.floor())) == 0;
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

    public int index(Item i){
        int a = 0;
        for (ItemStack j: availableCrops){
            if (j.item == i){
                return a;
            }
            a++;
        }
        return a;
    }

    public class AncientFarmBuild extends GenericCrafterBuild{
        public int totalPhase;
        public ItemStack selectedCrop;
        public int phase = 0;
        public TextureRegion phaseRegion(){
            if (selectedCrop != null && phases != null) return phases[index(selectedCrop.item)][phase];
            return region;
        }

        public void manualOffload(){
            if (selectedCrop != null && progress >= 1f && shouldConsume()){
                for(int i = 0; i < selectedCrop.amount; i++){
                    handleItem(this, selectedCrop.item);
                }
                progress %= 1f;
            }
        }

        @Override
        public boolean shouldConsume(){
            return selectedCrop != null && this.items.get(selectedCrop.item) + selectedCrop.amount <= itemCapacity;
        }

        @Override
        public void updateTile(){
            if(efficiency > 0 && selectedCrop != null){
                progress += getProgressIncrease(((OrganicItems) selectedCrop.item).growTime);
                warmup = Mathf.approachDelta(warmup, warmupTarget(), warmupSpeed);

                if(wasVisible && Mathf.chanceDelta(updateEffectChance)){
                    updateEffect.at(x + Mathf.range(size * 4f), y + Mathf.range(size * 4));
                }
            }else{
                warmup = Mathf.approachDelta(warmup, 0f, warmupSpeed);
            }

            totalProgress += warmup * Time.delta;

            if(progress >= 1f){
                craft();
                progress = 1f;
            }

            phase = (int) (progress * totalPhase);

            if (phase > 3) phase = 3;

            if (timer(0, 60f) && selectedCrop != null){
                Log.info(selectedCrop.item.name);
            }
        }

        @Override
        public void craft(){
            consume();

            if(wasVisible){
                craftEffect.at(x, y);
            }
        }

        @Override
        public void dumpOutputs(){
            Log.info("dumpOutputs called");
            if(selectedCrop != null && timer(timerDump, dumpTime / timeScale)){
                while (this.items.get(selectedCrop.item) > 0){
                    dump(selectedCrop.item);
                }
            }
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);

            table.table(t -> {
                for (ItemStack i: availableCrops){
                    t.button(new TextureRegionDrawable(Core.atlas.find(i.item.name)), () -> {
                        selectedCrop = i;
                        totalPhase = ((OrganicItems) selectedCrop.item).phases;
                    }).center().size(50).growX().checked(i == selectedCrop);
                }
                t.row();
                t.button(new TextureRegionDrawable(Core.atlas.find("icon-harvest")), this::manualOffload).center().size(50);
            }).center().growX();
        }

        @Override
        public void draw(){
            TextureRegion toDraw = phaseRegion();
            Draw.z(Layer.block);
            Draw.rect(toDraw, x, y);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            selectedCrop = availableCrops.get(read.i());
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.i(availableCrops.indexOf(selectedCrop));
        }
    }
}
