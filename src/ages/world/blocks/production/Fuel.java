package ages.world.blocks.production;

import mindustry.entities.*;
import mindustry.type.*;

public class Fuel {
    public Item item;
    public int fuelUse;
    public float capacityMultiplier = 1f;
    public float heatCapacity;
    public float heatRate = 1f;
    public Effect burnEffect;

    public Fuel(Item item, int use){
        this.item = item;
        this.fuelUse = use;
    }
}
