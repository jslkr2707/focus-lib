package ages.content;

import ages.type.*;
import mindustry.content.*;
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

        charcoal = new Fuel(Items.coal){{
            heatCapacity = 400f;
            fuelUse = 1;
            lifetime = 900f;
            heatRate = 1.2f;
        }};
    }
}
