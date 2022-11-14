package ages.core;

import ages.*;
import ages.content.*;
import ages.ui.dialogs.*;
import arc.*;
import arc.*;
import arc.func.*;
import arc.scene.*;
import arc.util.*;
import mindustry.game.*;
import mindustry.mod.*;
import mindustry.ui.*;

import static mindustry.Vars.*;
import static ages.AgesVars.*;

public class Ages extends Mod{
    public Ages(){
    }

    @Override
    public void init(){
        Mods.LoadedMod mod = mods.locateMod("ages");

        FocusDialog dialog = new FocusDialog();
        focusDialog = dialog;

        if(!headless){
            //Partial credits to BM and PU

            Func<String, String> stringf = value -> Core.bundle.get("mod." + value);

            mod.meta.displayName = stringf.get(mod.meta.name + ".name");
            mod.meta.description = Core.bundle.get("mod.ages.description");

            mod.meta.author = "[royal]" + mod.meta.author + "[]";
        }
        mod.meta.version = mod.meta.version + "\n";

        //i... don't know what im doing, but it works so ugh
        Events.on(EventType.ClientLoadEvent.class, e -> {
            try{
                var group = (Group)ui.research.getChildren().first();

                if (mobile){

                } else {
                    ui.research.titleTable.remove();
                    group.fill(c -> AgesVars.focusBtn(c.top()));
                    group.fill(c -> c.top().marginTop(70f)
                            .button("Focus", Styles.black, dialog::show)
                            .size(240, 48)
                            .name("Focus Tree")
                    );
                }
            } catch(Throwable t) {
                Log.info("Couldn't create button", Strings.getFinalCause(t));
            }
        });
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
