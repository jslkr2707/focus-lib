package industrial.world.blocks;

import arc.scene.ui.layout.Table;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;

public class DivisonPlanner extends Block {
    BaseDialog plannerDialog;

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

            if (plannerDialog == null){
                plannerDialog = new BaseDialog("Division Planner");
                plannerDialog.addCloseButton();
            }

            plannerDialog.cont.center().top();
            plannerDialog.cont.clear();

            table.button(Icon.pencil, () -> {
                plannerDialog.show();
            });
        }
    }
}
