package quests;

import quests.content.*;
import quests.ui.*;
import arc.*;
import arc.func.*;
import mindustry.game.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class Quests extends Mod{
    public static FocusUILoader fui = new FocusUILoader();

    public Quests(){
    }

    @Override
    public void init(){
        Mods.LoadedMod mod = mods.locateMod("focus-lib");

        if(!headless){
            //credits to BM and PU
            Func<String, String> stringf = value -> Core.bundle.get("mod." + value);

            mod.meta.displayName = stringf.get(mod.meta.name + ".name");
            mod.meta.description = Core.bundle.get("mod.focus-lib.description");

            mod.meta.author = "[royal]" + mod.meta.author + "[]";
        }

        mod.meta.version = mod.meta.version + "\n";

        Events.run(EventType.ClientLoadEvent.class, () -> {
            QuestSetting.init("focus.title");
            fui.init();
        });
    }

    @Override
    public void loadContent(){
        ExFocus.load();
        ExTechTree.load();
    }
}
