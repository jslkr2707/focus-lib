package industrial.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;

public class DivisonPlanner extends Block {
    BaseDialog planDialog;
    public int combatWidth = 5;
    public int unitTypes = 3;

    public DivisonPlanner(String name) {
        super(name);

        update = true;
        configurable = true;
        solid = true;
        logicConfigurable = true;
    }

    public class DivisionPlannerBuild extends Building{
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);

            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clear();
            planDialog.cont.center();

            planDialog.cont.table(t -> {
                t.center();
                t.top();

                t.add(Core.bundle.get("ui.combatwidth") + ": " + combatWidth).color(Pal.accent);
                t.row();
                t.add(Core.bundle.get("ui.unittypes") + ": "+ unitTypes).color(Pal.accent);
            });

            planDialog.show();
        }
    }
}
