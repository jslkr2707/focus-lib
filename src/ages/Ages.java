package ages;

import ages.content.*;
import ages.core.*;
import ages.ui.*;
import ages.ui.dialogs.*;
import ages.util.*;
import arc.*;
import arc.func.*;
import arc.scene.*;
import arc.scene.style.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.graphics.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class Ages extends Mod{
    public Ages(){
    }

    @Override
    public void init(){
        Mods.LoadedMod mod = mods.locateMod("ages");

        if(!headless){
            //credits to BM and PU
            Func<String, String> stringf = value -> Core.bundle.get("mod." + value);

            mod.meta.displayName = stringf.get(mod.meta.name + ".name");
            mod.meta.description = Core.bundle.get("mod.ages.description");

            mod.meta.author = "[royal]" + mod.meta.author + "[]";
        }
        mod.meta.version = mod.meta.version + "\n";

        //i... don't know what im doing, but it works so ugh
        Events.on(EventType.ClientLoadEvent.class, e -> {
            FocusSetting.init();
            FocusTex.load();
        });
    }

    @Override
    public void loadContent(){
        AgesItems.load();
        AgesBullets.load();
        AgesBlocks.load();
        AgesFocus.load();
        AgesTechTree.load();
        AgesSounds.load();
    }
}
