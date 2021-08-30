package ic2.world.blocks.defense.turrets;

import arc.math.Mathf;
import arc.util.Log;
import mindustry.entities.bullet.BulletType;
import mindustry.world.blocks.defense.turrets.ItemTurret;

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
            if (charging){
                chargeHeat += 0.003f;
            }else{
                chargeHeat -= 0.002f;
            }
            if (Mathf.dst(this.x, this.y, targetPos.x, targetPos.y) < artilleryMinRange || chargeHeat >= heatCap){
                reload = 0;
            }else{
                reload += delta() * peekAmmo().reloadMultiplier * baseReloadSpeed();
                if(reload >= reloadTime && !charging){
                    BulletType type = peekAmmo();

                    shoot(type);

                    reload %= reloadTime;
                }
            }
        }
    }
}
