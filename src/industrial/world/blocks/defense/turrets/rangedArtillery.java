package industrial.world.blocks.defense.turrets;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.util.Log;
import arc.util.Strings;
import industrial.world.blocks.heatMulti;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.blocks.defense.turrets.ItemTurret;

import static arc.graphics.g2d.Draw.color;

public class rangedArtillery extends ItemTurret {
    public float artilleryMinRange = 1f;
    public Color heatColor = Color.valueOf("e25822");
    public TextureRegion topRegion;
    public rangedArtillery(String name){
        super(name);
        hasItems = true;
    }

    @Override
    public void setBars() {
        super.setBars ();
        bars.add ("overheat", (rangedArtillery.rangedArtilleryBuild e) -> new Bar (() -> Core.bundle.format ("bar.turretoverheat", Strings.fixed (e.chargeHeat * 400, 1)), () -> Pal.lightishOrange, () -> e.heat));
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
            if (target != null){
                chargeHeat += 0.0015f;
            }else if (chargeHeat>=0){
                chargeHeat -= 0.0025f;
            }

            if (chargeHeat > 1f){
                chargeHeat = 1f;
            }

            if (chargeHeat < 0){
                chargeHeat = 0;
            }
            Log.info(chargeHeat);
            Log.info(target != null);

        }


        @Override
        public void draw(){
            Draw.z(Layer.effect);
            if (chargeHeat >= heatCap) {
                Draw.color(heatColor);
                Draw.alpha(heatCap / chargeHeat);
                Draw.rect(topRegion, x, y);
            }else{
                Draw.reset();
            }
            super.draw();
        }
    }
}
