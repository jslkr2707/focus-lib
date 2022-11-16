package ages.core;

import ages.content.*;
import ages.ui.dialogs.*;
import arc.*;
import arc.func.*;
import arc.scene.*;
import arc.scene.style.TextureRegionDrawable;
import arc.util.*;
import mindustry.game.*;
import mindustry.graphics.Pal;
import mindustry.mod.*;
import mindustry.ui.*;

import static mindustry.Vars.*;
import static ages.util.Overwriter.*;

public class Ages extends Mod{
    public Ages(){
    }

    @Override
    public void init(){
        Mods.LoadedMod mod = mods.locateMod("ages");

        FocusDialog dialog = new FocusDialog();
        focusDialog = dialog;

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
            addSelect(dialog);
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

    public void addSelect(FocusDialog dialog){
        try{
            var group = (Group)ui.research.getChildren().first();

            if (mobile){

            } else {
                ui.research.titleTable.remove();
                group.fill(c -> focusBtn(c.center().top()));
                group.fill(c -> c.center().top().marginTop(70f)
                        .button(b -> {
                            b.imageDraw(() -> new TextureRegionDrawable(Core.atlas.find("research-focus"))).padRight(8).size(iconMed);
                            b.add().growX();
                            b.label(() -> Core.bundle.format("focus")).color(Pal.accent);
                            b.add().growX();
                            b.add().size(iconMed);
                        }, dialog::show)
                        .size(240, 48)
                        .name("Focus Tree")
                );
            }
        } catch(Throwable t) {
            Log.info("Couldn't create button", Strings.getFinalCause(t));
        }
    }
}
