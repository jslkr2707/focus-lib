package ages.core;

import ages.content.*;
import arc.Events;
import mindustry.game.EventType;
import mindustry.mod.Mod;

public class Ages extends Mod{

    public Ages(){
        Events.on(EventType.ClientLoadEvent.class, e -> {
        });

        Events.on(EventType.WorldLoadEvent.class, e -> {
        });
    }

    @Override
    public void init(){
    }

    @Override
    public void loadContent(){
        AgesItems.load();
        AgesBullets.load();
        AgesBlocks.load();
        AgesFocus.load();
        AgesTechTree.load();
    }
}
