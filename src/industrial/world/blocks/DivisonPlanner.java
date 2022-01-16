package industrial.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.TextureRegion;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.Image;
import arc.scene.ui.Label;
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
    public int remaining = 5;
    public Seq<UnitType> compose = new Seq<>(3);

    public DivisonPlanner(String name) {
        super(name);

        update = true;
        configurable = true;
        solid = true;
        logicConfigurable = true;
        sync = true;

        compose.add(null, null, null);

        config(UnitType.class, (DivisionPlannerBuild tile, UnitType unit) -> {
            if (tile.remain()){
                addCompose(tile.lastSelected, tile.selected);
            }
        });
    }

    public void addCompose(int i, UnitType unit){
        compose.insert(i, unit);
        if (compose.size > i) compose.remove(i+1);
    }

    public class DivisionPlannerBuild extends Building{
        public UnitType selected;
        public Seq<UnitType> available = Vars.content.units().select(u -> !u.isBanned() && !u.isHidden() & u.node() != null);
        public boolean pending = false;
        public int lastSelected;

        @Override
        public void updateTile(){
            super.updateTile();
        }

        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clear();
            planDialog.cont.center().top();

            planDialog.cont.table(t -> {
                t.center();

                t.add(Core.bundle.get("ui.combatwidth") + ": " + combatWidth).padBottom(20);
            });

            planDialog.cont.row();

            planDialog.cont.table(tt -> {
                tt.center();

                tt.button(new TextureRegionDrawable(btnImg(0, compose)), () -> {
                    if (pending && selected != null){
                        lastSelected = 0;
                        pending = false;
                        configure(selected);
                    }
                }).size(70).padRight(50f);

                tt.button(new TextureRegionDrawable(btnImg(1, compose)), () -> {
                    if (pending && selected != null){
                        lastSelected = 1;
                        pending = false;
                        configure(selected);
                    }
                }).size(70).pad(5);

                tt.button(new TextureRegionDrawable(btnImg(2, compose)), () -> {
                    if (pending && selected != null){
                        lastSelected = 2;
                        pending = false;
                        configure(selected);
                    }
                }).size(70).padLeft(50f);

                tt.row();

                tt.add(unitName(0)).padRight(50f);
                tt.add(unitName(1)).pad(5);
                tt.add(unitName(2)).padLeft(50f);
            });

            planDialog.cont.table(tbl -> {
                tbl.left();

                for (int i = 0;i < available.size;i++){
                    int j = i;
                    tbl.button(new TextureRegionDrawable(btnImg(i, available)), () -> {
                        selected = available.get(j);
                        pending = true;
                    }).size(60).pad(10);
                    if (i % 8 == 7) tbl.row();
                }
            });

            planDialog.show();
        }

        public TextureRegion btnImg(int i, Seq<UnitType> seq){
            return seq.get(i) != null ? seq.get(i).uiIcon : Icon.none.getRegion();
        }

        public Label unitName(int i){
            return compose.get(i) != null ? new Label(compose.get(i).localizedName) : new Label(Core.bundle.get("ui.none"));
        }

        public boolean remain(){
            return remaining > 0;
        }
    }
}
