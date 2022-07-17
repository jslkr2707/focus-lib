package ages.type;

import arc.*;
import arc.graphics.*;
import mindustry.type.*;
import mindustry.world.meta.*;

public class OrganicItems extends Item {
    public int calories, phases;
    public float growTime;

    public OrganicItems(String name) {
        super(name);
    }

    public OrganicItems(String name, Color color){
        super(name, color);
    }

    @Override
    public void setStats(){
        super.setStats();

        stats.remove(Stat.charge);
        stats.add(Stat.reload, t -> {
            t.row();
            t.add("[lightgray]" + Core.bundle.format("ages.item.calories") + ": [white]" + calories);
            t.row();
            t.add("[lightgray]" + Core.bundle.format("ages.item.phase") + ": [white]" + phases);
            t.row();
            t.add("[lightgray]" + Core.bundle.format("ages.item.growtime") + ": [white" + growTime / 60 + StatUnit.seconds);
        });
    }
}
