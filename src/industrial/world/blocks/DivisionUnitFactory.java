package industrial.world.blocks;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.Element;
import arc.scene.style.Drawable;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Button;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Scaling;
import industrial.type.DivisionUnitType;
import mindustry.Vars;
import mindustry.gen.Icon;
import mindustry.gen.UnitEntity;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.UnitFactory;

import java.util.HashMap;

public class DivisionUnitFactory extends UnitFactory {
    /* test */
    int aa = 5;
    public BaseDialog planDialog;
    public int combatWidth = 20;
    public Seq<UnitType> compose = new Seq<>(4);

    public DivisionUnitFactory(String name) {
        super(name);

        compose.add(null, null, null);
    }

    public void addCompose(int i, UnitType unit){
        compose.insert(i, unit);
        if (compose.size > i) compose.remove(i+1);
    }

    public class DivisionUnitFactoryBuild extends UnitFactoryBuild{
        public Seq<UnitType> available = Vars.content.units().select(c -> c.unlocked() && c.node() != null
        && !c.isHidden());

        @Override
        public void updateTile(){
            super.updateTile();
        }

        @Override
        public void buildConfiguration(Table table){
            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clear();
            planDialog.cont.center().top();

            /* combat width and available types */
            planDialog.cont.table(frame -> {
                frame.table(w -> {
                    w.defaults().pad(5);

                    w.add(Core.bundle.get("division.width") + ": " + combatWidth).size(50);
                }).growX();

                frame.row();


            });

            planDialog.show();
        }

        public TextureRegion btnImg(int i){
            return compose.get(i) != null ? compose.get(i).uiIcon : (TextureRegion) Styles.black;
        }



    }

}
