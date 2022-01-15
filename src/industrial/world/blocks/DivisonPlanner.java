package industrial.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.layout.Table;
import arc.struct.Seq;
import arc.util.Scaling;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.gen.Icon;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;
import mindustry.world.Block;

public class DivisonPlanner extends Block {
    BaseDialog planDialog;
    public int combatWidth = 5;
    public Seq<UnitType> compose = new Seq<>(3);

    public DivisonPlanner(String name) {
        super(name);

        update = true;
        configurable = true;
        solid = true;
        logicConfigurable = true;

        compose.add(null, null, null);
    }

    public void addCompose(int i, UnitType unit){
        compose.insert(i, unit);
        if (compose.size > i) compose.remove(i+1);
    }

    public class DivisionPlannerBuild extends Building{
        public UnitType selected;
        public Seq<UnitType> available = Vars.content.units().select(u -> !u.isBanned() && !u.isHidden() & u.node() != null);

        @Override
        public void buildConfiguration(Table table){
            int col = 4;

            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clear();
            planDialog.cont.center().top();

            planDialog.cont.table(t -> {
                t.center();

                t.add(Core.bundle.get("ui.combatwidth") + ": " + combatWidth);
            });

            planDialog.cont.row();

            planDialog.cont.table(tt -> {
                tt.center();

                tt.button(new TextureRegionDrawable(btnImg(0)), () -> {
                }).size(50).pad(5);

                tt.button(new TextureRegionDrawable(btnImg(1)), () -> {
                }).size(50).pad(5);

                tt.button(new TextureRegionDrawable(btnImg(2)), () -> {
                }).size(50).pad(5);

            });

            planDialog.show();
        }

        public TextureRegion btnImg(int i){
            return compose.get(i) != null ? compose.get(i).uiIcon : Icon.none.getRegion();
        }

        public void select(Table t){
            t.row();

            for (int i = 0;i < available.size;i++){
                UnitType unit = available.get(i);
                t.button(b -> {
                    b.left();

                    b.image(unit.unlocked() ? unit.uiIcon : Icon.lock.getRegion()).size(40);

                }, () -> {}).left();

                if (i % 8 == 7){
                    t.row();
                }
            }

        }
    }
}
