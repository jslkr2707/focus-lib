package focus.ui;

import arc.scene.style.*;

import static arc.Core.atlas;

public class FocusUILoader {
    public static Drawable buttonGreen;

    public void init(){
        buttonGreen = atlas.getDrawable("focus-lib-button-green");
    }
}
