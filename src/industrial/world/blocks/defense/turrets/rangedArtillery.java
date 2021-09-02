package industrial.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.defense.turrets.ItemTurret;

import static arc.graphics.g2d.Draw.color;

public class rangedArtillery extends ItemTurret {
    public float artilleryMinRange = 1f;

    public rangedArtillery(String name){
        super(name);
        hasItems = true;
    }

    public class rangedArtilleryBuild extends ItemTurretBuild{
        public float chargeHeat = 0;
        public float heatCap = 1f;
        @Override
        public void updateShooting(){
            super.updateShooting();
            if (Mathf.dst(this.x, this.y, targetPos.x, targetPos.y) < artilleryMinRange){
                reload = 0;
            }
        }

        @Override
        public void updateTile(){
            super.updateTile();
            if (isActive()){
                chargeHeat += 0.0015f;
            }else if (chargeHeat>=0){
                chargeHeat -= 0.0025f;
            }
            Log.info(chargeHeat);
            Log.info(isActive());

        }


        @Override
        public void draw(){
            Draw.z(Layer.turret);
            if (chargeHeat > 0) {
                Draw.color (Pal.lightOrange);
                Draw.alpha (0.6f);
                Fill.square (x, y, 4 * size);
            }
            super.draw();
        }
    }
}
