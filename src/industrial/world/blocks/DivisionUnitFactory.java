package industrial.world.blocks;

import arc.Core;
import arc.func.Cons;
import arc.graphics.Color;
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
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.blocks.payloads.UnitPayload;
import mindustry.world.blocks.units.UnitFactory;

import java.util.HashMap;

public class DivisionUnitFactory extends UnitFactory {
    /* test */
    int aa = 5;
    public BaseDialog planDialog;
    public int combatWidth = 20;
    public Seq<UnitType> compose = new Seq<>();

    public DivisionUnitFactory(String name) {
        super(name);
    }

    public class DivisionUnitFactoryBuild extends UnitFactoryBuild{
        public Seq<UnitType> available = Vars.content.units().select(c -> c.unlocked() && c.node() != null
        && !c.isHidden());

        @Override
        public void updateTile(){
            super.updateTile();

            if(currentPlan != -1 && payload == null) {
                UnitPlan plan = plans.get(currentPlan);

                if(progress >= plan.time && consValid()){
                    for (UnitType units: compose){
                        payload = new UnitPayload(units.create(team));
                    }
                }
            }
        }

        @Override
        public void buildConfiguration(Table table){
            int col = 4;

            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clear();
            planDialog.cont.center().top();

            /* combat width and available types */
            planDialog.cont.table(t -> {
                t.center();

                t.add(Core.bundle.get("ui.combatwidth") + ": " + combatWidth);
                t.row();
                t.add(Core.bundle.get("ui.unittypes") + ": "+ "3");
            });

            planDialog.cont.row();

            planDialog.cont.table(tt -> {
                tt.center();

                tt.button(Core.bundle.get("ui.selectunit"), () -> {

                });
            });

            planDialog.show();
        }

        public BaseDialog select(){
            BaseDialog dialog = new BaseDialog("");
            int col = 4;
            dialog.addCloseButton();
            dialog.clear();

            dialog.cont.table(t -> {
                t.center().top();

                t.add(Core.bundle.get("ui.select1"));

                t.row();
            });

            dialog.cont.table(tt -> {
                for (int i=0;i <= available.size;i++){
                    Image image = available.get(i).unlocked() ? new Image(available.get(i).uiIcon) : new Image(Icon.none);

                    tt.add(image).size(32f);

                    if (i % 4 == 3){
                        tt.row();
                    }
                }
            });

            return dialog;

        }

    }

}
