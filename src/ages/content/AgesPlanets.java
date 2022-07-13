package ages.content;

import arc.graphics.*;
import arc.math.Mathf;
import arc.math.geom.Vec3;
import arc.struct.ObjectMap;
import arc.util.Tmp;
import arc.util.noise.Noise;
import arc.util.noise.Simplex;
import mindustry.content.*;
import mindustry.graphics.g3d.*;
import mindustry.maps.generators.BaseGenerator;
import mindustry.maps.generators.PlanetGenerator;
import mindustry.maps.planet.*;
import mindustry.type.*;
import mindustry.world.*;

public class AgesPlanets{
    public static Planet
    nuri;

    public static void load(){
        nuri = new Planet("nuri", Planets.sun, 0.8f){{
            generator = new AgesPlanetGenerator();
            meshLoader = () -> new HexMesh(this, 6);
            cloudMeshLoader = () ->  new HexSkyMesh(this, 11, 0.2f, 0.1f, 5, new Color().set(Color.sky).mul(0.8f).a(0.8f), 2, 0.45f, 0.9f, 0.38f);
        }};
    }

    public static class AgesPlanetGenerator extends PlanetGenerator {
        static final int seed = 879234;

        BaseGenerator basegen = new BaseGenerator();
        float scl = 5f;
        float seaOffset = 0.1f;
        boolean lakes = false;
        Block[][] arr =
                {
                        {Blocks.water, Blocks.darksandWater, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.darksandTaintedWater, Blocks.stone, Blocks.stone},
                        {Blocks.water, Blocks.darksandWater, Blocks.darksand, Blocks.darksand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.darksandTaintedWater, Blocks.stone, Blocks.stone, Blocks.stone},
                        {Blocks.water, Blocks.darksandWater, Blocks.darksand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.darksandTaintedWater, Blocks.stone, Blocks.stone, Blocks.stone},
                        {Blocks.water, Blocks.sandWater, Blocks.sand, Blocks.sand, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.snow, Blocks.iceSnow, Blocks.ice},
                        {Blocks.deepwater, Blocks.water, Blocks.sandWater, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.basalt, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice},
                        {Blocks.deepwater, Blocks.water, Blocks.sandWater, Blocks.sand, Blocks.sand, Blocks.sand, Blocks.iceSnow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.snow, Blocks.ice},
                        {Blocks.deepwater, Blocks.sandWater, Blocks.sand, Blocks.sand, Blocks.snow, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice},
                        {Blocks.deepTaintedWater, Blocks.darksandTaintedWater, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.basalt, Blocks.hotrock, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.ice},
                        {Blocks.darksandWater, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.snow, Blocks.basalt, Blocks.basalt, Blocks.ice, Blocks.snow, Blocks.ice, Blocks.ice},
                        {Blocks.darksandWater, Blocks.darksand, Blocks.darksand, Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.ice},
                        {Blocks.deepTaintedWater, Blocks.darksandTaintedWater, Blocks.darksand, Blocks.ice, Blocks.ice, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice},
                        {Blocks.taintedWater, Blocks.darksandTaintedWater, Blocks.darksand, Blocks.iceSnow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice},
                        {Blocks.darksandWater, Blocks.darksand, Blocks.snow, Blocks.ice, Blocks.iceSnow, Blocks.snow, Blocks.snow, Blocks.snow, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, Blocks.ice, AgesBlocks.soil}
                };

        ObjectMap<Block, Block> dec = ObjectMap.of(
                Blocks.water, Blocks.ice,
                AgesBlocks.soil, Blocks.dirt, Blocks.grass,
                Blocks.stone, Blocks.boulder
        );

        ObjectMap<Block, Block> tars = ObjectMap.of(
                Blocks.ice, Blocks.iceSnow,
                Blocks.snow, Blocks.iceSnow
        );

        float sea = 3f / arr[0].length;

        float rawHeight(Vec3 position){
            position = Tmp.v33.set(position).scl(scl);
            return (Mathf.pow(Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + seaOffset) / (1f + seaOffset);
        }

        @Override
        public void generateSector(Sector sector){

            //these always have bases
            if(sector.id == 1 || sector.id == 0){
                sector.generateEnemyBase = true;
                return;
            }

            PlanetGrid.Ptile tile = sector.tile;

            boolean any = false;
            float poles = Math.abs(tile.v.y);
            float noise = Noise.snoise3(tile.v.x, tile.v.y, tile.v.z, 0.001f, 0.58f);

            if(noise + poles/7.1 > 0.12 && poles > 0.23){
                any = true;
            }

            if(noise < 0.16){
                for(PlanetGrid.Ptile other : tile.tiles){
                    var osec = sector.planet.getSector(other);

                    //no sectors near start sector!
                    if(
                            osec.id == sector.planet.startSector || //near starting sector
                                    osec.generateEnemyBase && poles < 0.85 || //near other base
                                    (sector.preset != null && noise < 0.11) //near preset
                    ){
                        return;
                    }
                }
            }

            sector.generateEnemyBase = any;
        }

        @Override
        public float getHeight(Vec3 position){
            float height = rawHeight(position);
            return Math.max(height, sea);
        }

        @Override
        public Color getColor(Vec3 position){
            Block block = getBlock(position);
            //replace salt with sand color
            if(block == Blocks.salt) return Blocks.sand.mapColor;
            return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
        }

        Block getBlock(Vec3 position){
            float height = rawHeight(position);
            Tmp.v31.set(position);
            position = Tmp.v33.set(position).scl(scl);
            float rad = scl;
            float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
            float tnoise = Simplex.noise3d(seed, 7, 0.56, 1f/3f, position.x, position.y + 999f, position.z);
            temp = Mathf.lerp(temp, tnoise, 0.5f);
            height *= 1.2f;
            height = Mathf.clamp(height);

            float tar = Simplex.noise3d(seed, 4, 0.55f, 1f/2f, position.x, position.y + 999f, position.z) * 0.3f + Tmp.v31.dst(0, 0, 1f) * 0.2f;

            Block res = arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
            if(tar > 0.5f){
                return tars.get(res, res);
            }else{
                return res;
            }
        }
    }
}
