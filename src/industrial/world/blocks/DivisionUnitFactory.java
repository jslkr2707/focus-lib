package industrial.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import industrial.type.DivisionUnitType;
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
    public int combatWidth = 5;
    public int unitTypes = 3;
    public HashMap<UnitType, Integer> compose = new HashMap<>();

    public DivisionUnitFactory(String name) {
        super(name);
    }

    public class DivisionUnitFactoryBuild extends UnitFactoryBuild{
        @Override
        public void updateTile(){
            super.updateTile();

            if(currentPlan != -1 && payload == null) {
                UnitPlan plan = plans.get(currentPlan);

                if(progress >= plan.time && consValid()){
                    for (UnitType units: compose.keySet()){
                        payload = new UnitPayload(units.create(team));
                    }
                }
            }
        }

        @Override
        public void buildConfiguration(Table table){
            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clear();
            planDialog.cont.center();

            planDialog.cont.table(t -> {
                t.center();
                t.top();

                t.add(Core.bundle.get("ui.combatwidth") + ": " + combatWidth);
                t.row();
                t.add(Core.bundle.get("ui.unittypes") + ": "+ unitTypes);
            });

            planDialog.show();
        }
    }

}
