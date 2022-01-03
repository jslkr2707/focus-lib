package industrial.type;

import arc.math.Mathf;
import arc.math.geom.Vec2;
import arc.util.Log;
import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.entities.bullet.BulletType;
import mindustry.gen.Bullet;
import mindustry.gen.Entityc;
import mindustry.world.blocks.defense.turrets.Turret;

public class IndMissile extends BasicBulletType{
    public float explodeDmg = 100f;
    public float expRad = 16f;

    public IndMissile(float speed, float damage){
        this.speed = speed;
        this.damage = damage;

        hitEffect = Fx.hitBulletBig;
        despawnEffect = Fx.blockExplosionSmoke;
    }

    @Override
    public void update(Bullet b){
        super.update(b);

        Turret.TurretBuild owner = (Turret.TurretBuild) b.owner;
        Vec2 targetpos = owner.targetPos;

        if (Mathf.dst(owner.x, owner.y, targetpos.x, targetpos.y) * 0.5 <= Mathf.dst(owner.x, owner.y, b.x, b.y)){
            b.vel().scl(1.1f * Time.delta);
        }

        Log.info(Mathf.dst(owner.x, owner.y, targetpos.x, targetpos.y)+" "+Mathf.dst(owner.x, owner.y, b.x, b.y));
    }

    @Override
    public void hit(Bullet b, float x, float y){
        Damage.damage(b.team, x, y, expRad, explodeDmg);
        super.hit(b, x, y);
    }
}
