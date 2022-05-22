package ages.content;

import ages.type.WorkerUnitType;
import mindustry.annotations.Annotations;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.gen.Unitc;
import mindustry.type.UnitType;
import mindustry.type.Weapon;

public class AgesUnitTypes implements ContentList {
    public static @Annotations.EntityDef({Unitc.class})

    WorkerUnitType slinger;

    @Override
    public void load(){
        slinger = new WorkerUnitType("slinger"){{
            speed = 0.8f;
            charge = 30f;
            health = 100;
            hitSize = 6;
            weapons.add(new Weapon("sling"){{
                reload = 60f;
                x = 8f;
                y = 2f;
                bullet = new BasicBulletType(3f, 5){{
                    width = height = 4f;
                    lifetime = 60f;
                }};
            }});
        }};
    }
}
