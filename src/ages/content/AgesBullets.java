package ages.content;

import mindustry.entities.bullet.*;

public class AgesBullets{
    public static BulletType

    stones;

    public static void load(){
        stones = new BasicBulletType(1.5f, 5){{
            width = height = 4f;
            lifetime = 90f;
        }};
    }
}


