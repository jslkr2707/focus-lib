package industrial.type.bullets;

import arc.util.Time;
import mindustry.content.Fx;
import mindustry.entities.Damage;
import mindustry.entities.Effect;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.game.Team;
import mindustry.gen.Bullet;
import mindustry.gen.Hitboxc;
import mindustry.gen.Sounds;
import mindustry.gen.Unit;

public class GrenadeBulletType extends BasicBulletType {
    public float explodeTimer = 0;
    public float explodeDmg = 100;
    public float explodeRad = 32f;
    public Effect explodeEffect = Fx.blastExplosion;

    public GrenadeBulletType(float speed, float damage){
        super(speed, damage);
        collidesAir = false;
    }

    @Override
    public void hitEntity(Bullet b, Hitboxc entity, float health){
        super.hitEntity(b, entity, health);
        if (entity instanceof Unit u) kaboom(u.x, u.y, b.team);
    }

    @Override
    public void hit(Bullet b, float x, float y){
        super.hit(b,x,y);
        kaboom(x, y, b.team);
    }

    public void kaboom(float x, float y, Team team){
        Time.run(120f, () -> {
            Sounds.explosionbig.at(x, y);
            Damage.damage(team, x, y, explodeRad, explodeDmg);
            Effect.shake(3f, 8f, x, y);
            explodeEffect.at(x, y);
        });
    }
}
