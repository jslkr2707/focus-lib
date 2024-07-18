package focus.ui;

import arc.*;
import arc.scene.style.*;
import arc.scene.ui.layout.*;
import arc.util.*;
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

        add(Core.bundle.get("focus.researching"));
        row();
        table(Tex.button, t -> {
            t.margin(20f);
            t.center();
            t.image(f == null ? Icon.none.getRegion() : f.fullIcon).padBottom(10f);
            t.row();
            t.add(f == null ? Core.bundle.get("focus.none") : f.localizedName).scaling(Scaling.bounded);
        }).center().padTop(10f).size(200f, 150f);
    }

}
