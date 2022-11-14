package ages.entities.bullet;

import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.*;

public class GrenadeBulletType extends BasicBulletType {
    public float explodeDmg = 100;
    public float explodeRad = 32f;
    public Effect explodeEffect = Fx.blastExplosion;

    public GrenadeBulletType(float speed, float damage){
        super(speed, damage);
        collidesAir = false;
    }

    @Override
    public void hit(Bullet b, float x, float y){
        kaboom(x, y);
        Damage.damage(b.team, x, y, explodeRad, explodeDmg, false, true);
        super.hit(b,x,y);
    }

    @Override
    public void despawned(Bullet b){
        if (!b.hit && b.time >= b.lifetime){
            kaboom(b.x, b.y);
            Damage.damage(b.team, b.x, b.y, explodeRad, explodeDmg, false, true);
        }
    }

    public void kaboom(float x, float y){
        Sounds.explosionbig.at(x, y);
        Effect.shake(1f, 8f, x, y);
        explodeEffect.at(x, y);
    }
}
