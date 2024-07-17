package focus.ui;

import arc.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import focus.type.*;
import mindustry.gen.*;

public class FocusDisplay extends Table {

    public FocusDisplay(){
        rebuild(null);
    }

    public void rebuild(Focus f){
        clear();
        bottom().left();
        margin(0);

        table(Tex.button, t -> {
            t.margin(10f);
            t.center();
            t.button((f == null ? Icon.none : new TextureRegionDrawable(f.fullIcon)), () -> {});
            t.row();
            t.add(Core.bundle.get(f == null ? "focus.none" : f.name));
            t.marginBottom(5f);
        });
    }

}
