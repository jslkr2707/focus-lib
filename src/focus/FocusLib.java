package focus;

import focus.content.*;
import focus.ui.*;
import arc.*;
import arc.func.*;
import mindustry.game.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class FocusLib extends Mod{
    public static FocusTex ft = new FocusTex();

    public FocusLib(){
    }

    @Override
    public void init(){
        Mods.LoadedMod mod = mods.locateMod("focus-lib");

        if(!headless){
            //credits to BM and PU
            Func<String, String> stringf = value -> Core.bundle.get("mod." + value);

            mod.meta.displayName = stringf.get(mod.meta.name + ".name");
            mod.meta.description = Core.bundle.get("mod.ages.description");

            mod.meta.author = "[royal]" + mod.meta.author + "[]";
        }
        mod.meta.version = mod.meta.version + "\n";

        Events.run(EventType.ClientLoadEvent.class, () -> {
            FocusSetting.init("focus.title");
            ft.init();
        });
    }

    @Override
    public void loadContent(){
        ExFocus.load();
        ExTechTree.load();
    }
}
