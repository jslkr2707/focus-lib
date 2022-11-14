package ages.world.blocks.units;

import arc.*;
import arc.graphics.g2d.*;
import arc.scene.ui.layout.*;
import arc.struct.*;
import mindustry.*;
import mindustry.type.*;
import mindustry.ui.*;
import mindustry.ui.dialogs.*;
import mindustry.world.blocks.units.*;

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
        public Seq<UnitType> available = Vars.content.units().select(c -> c.unlocked() && c.techNode != null
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
