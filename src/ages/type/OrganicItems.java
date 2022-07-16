package ages.type;

import arc.Core;
import arc.graphics.Color;
import mindustry.type.Item;
import mindustry.world.meta.Stat;

public class OrganicItems extends Item {
    public int calories;

    public OrganicItems(String name) {
        super(name);
    }

    public OrganicItems(String name, Color color){
        super(name, color);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.add(Stat.charge, t -> {
            t.row();
            t.add("[lightgray]" + Core.bundle.format("ages.item.calories") + ": [white]" + calories);
        });
    }
}
