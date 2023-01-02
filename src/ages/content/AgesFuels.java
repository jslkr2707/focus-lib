package ages.content;

import ages.type.*;
import mindustry.type.*;

public class AgesFuels{
    public static Fuel wood, charcoal, coal;

    public static void load(){
        wood = new Fuel(AgesItems.wood){{
            heatCapacity = 200f;
            fuelUse = 1;
            lifetime = 420f;
            heatRate = 1f;
        }};

        charcoal = new Fuel(AgesItems.charcoal){{
            heatCapacity = 400f;
            fuelUse = 1;
            lifetime = 900f;
            heatRate = 1.2f;
        }};
    }
}
