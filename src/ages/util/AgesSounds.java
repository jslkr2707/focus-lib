package ages.util;

import arc.*;
import arc.audio.*;
import arc.struct.*;
import mindustry.gen.*;

public class AgesSounds extends Sounds{
    private static IntMap<Sound> idToSound = new IntMap<Sound>();
    private static ObjectIntMap<Sound> soundToId = new ObjectIntMap<>();

    public static Sound startResearch = new Sound();

    public AgesSounds(){
    }

    public static void load(){
        Core.assets.load("sounds/research.ogg", Sound.class).loaded = (a) -> {
            startResearch = a;
            soundToId.put(a, 0);
            idToSound.put(0, a);
        };
    }

    static {
        soundToId.put(startResearch, 1000);
    }
}
