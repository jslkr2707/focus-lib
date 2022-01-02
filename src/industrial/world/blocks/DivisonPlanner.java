package industrial.world.blocks;

import arc.scene.style.Style;
import arc.scene.ui.layout.Table;
import industrial.ui.dialogs.DivisionTemplatePlannerDialog;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.ui.Styles;
import mindustry.world.Block;

public class DivisonPlanner extends Block {
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
            table.button(Icon.pencil, () -> {
                new DivisionTemplatePlannerDialog().show();
            });
        }
    }
}
