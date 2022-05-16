package ages.content;

import ages.entities.bullet.modern.GrenadeBulletType;
import ages.entities.bullet.contemporary.IndMissile;
import mindustry.content.Bullets;
import mindustry.content.Fx;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.graphics.Pal;

public class ExBullets implements ContentList{
    public static BulletType

    artilleryBig, missileSmall, grenadeSmall;

    @Override
    public void load(){
        artilleryBig = new ArtilleryBulletType(2f, 200){{
            hitEffect = Fx.massiveExplosion;
            knockback = 1.2f;
            lifetime = 120f;
            width = height = 22f;
            collidesAir = false;
            splashDamage = 50f;
            splashDamageRadius = 10f * 0.75f;
            backColor = Pal.gray;
            shrinkY = 0.5f;
            shrinkX = 0.5f;
            fragBullet = Bullets.fragExplosive;
            fragBullets = 8;
            despawnEffect = Fx.fireballsmoke;
            trailEffect = Fx.artilleryTrail;
        }};

        missileSmall = new IndMissile(1f, 150){{
            accel = 1.1f;
            lifetime = 1000f;
            width = 12f;
            height = 36f;
            collidesAir = false;
            accelEffect = ExFx.bulletAccel;
            explodeEffect = Fx.blastExplosion;
            frontColor = Pal.lightFlame;
            backColor = Pal.missileYellow;
            bulletFireEffect = ExFx.bulletFire;
            velLimit = 20f;
        }};

        grenadeSmall = new GrenadeBulletType(2f, 10){{
            lifetime = 60f;
            width = height = 4f;
            frontColor = Pal.plastaniumFront;
            backColor = Pal.lightishGray;
            shootEffect = Fx.shootSmallFlame;
        }};
    }
}


