package ages.type;

import arc.*;
import arc.graphics.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.world.meta.*;

public class OrganicItems extends Item {
    public int calories, phases;
    public float growTime;

    public OrganicItems(String name) {
        super(name);
    }

    public OrganicItems(String name, Color color){
        super(name, color);
        buildable = false;
        radioactivity = 0;
        charge = 0;
    }

    @Override
    public void setStats(){
        stats.addPercent(Stat.flammability, flammability);
        stats.add(Stat.abilities, t -> {
            t.row();
            t.add("[lightgray]" + Core.bundle.format("stat.calories") + ": [white]" + calories).left();
            t.row();
            t.add("[lightgray]" + Core.bundle.format("stat.phase") + ": [white]" + phases).left();
            t.row();
            t.add("[lightgray]" + Core.bundle.format("stat.growtime") + ": [white]" + (int)(growTime / 60)).left();
            t.add(StatUnit.seconds.localized()).padLeft(2).padRight(5).color(Color.lightGray).style(Styles.outlineLabel);
        });
    }
}
