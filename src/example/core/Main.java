package example.core;

import arc.Events;
import example.content.ModLoader;
import mindustry.game.EventType;
import mindustry.mod.Mod;

public class Main extends Mod{

    public Main(){
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
        new ModLoader().load();
    }
}
