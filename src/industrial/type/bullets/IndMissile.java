package industrial.type.bullets;

import arc.math.Mathf;
import arc.math.Rand;
import arc.math.geom.Circle;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.ArtilleryBulletType;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Sounds;
import mindustry.graphics.Pal;
import mindustry.graphics.Trail;
import mindustry.world.blocks.defense.turrets.Turret;

public class IndMissile extends BasicBulletType{
    public float accel = 1f;
    public Effect accelEffect, explodeEffect, bulletFireEffect;
    public float expDmg, velLimit;

    public IndMissile(float speed, float damage){
        this.speed = speed;
        this.damage = damage;

        buildingDamageMultiplier = 1.5f;
        hitEffect = Fx.hitBulletBig;
        despawnEffect = Fx.blockExplosionSmoke;
        hitShake = 5;
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        Turret.TurretBuild owner = (Turret.TurretBuild) b.owner;
        Vec2 targetpos = owner.targetPos;

        float ownerDst = Mathf.dst(owner.x, owner.y, targetpos.x, targetpos.y);
        float bDst = Mathf.dst(owner.x, owner.y, b.x, b.y);
        float lastDst = Mathf.dst(owner.x, owner.y, b.x - b.vel().x, b.y - b.vel().y);

        if (lastDst <= 0.2 * ownerDst && bDst >= 0.2 * ownerDst){
            accelEffect.at(b.x, b.y);
        }

        /* why does this work btw */
        if (ownerDst * 0.2 <= bDst){
            b.vel().scl(accel * Time.delta);
            bulletFireEffect.at(b.x,b.y, b.fslope() * 2, Pal.gray);
        }

        b.vel().limit(velLimit);
    }

    @Override
    public void hit(Bullet b, float x, float y){
        b.damage += expDmg;
        explodeEffect.at(x, y);
        Sounds.bang.at(x, y);
        super.hit(b, x, y);
    }
}
