package ages.type;

import mindustry.content.*;
import mindustry.entities.*;
import mindustry.type.*;

public class Fuel {
    public Item item;
    public int fuelUse;
    public float lifetime;
    public float heatCapacity;
    public float heatRate = 1f;
    public Effect burnEffect = Fx.smoke;

    public Fuel(Item item){
        this.item = item;
    }
}
