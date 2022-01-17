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
            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            planDialog.cont.clearChildren();

            planDialog.cont.center().top();

            /* combat width and available types */
            planDialog.cont.table(frame -> {
                frame.table(w -> {
                    w.defaults().pad(5);
                    w.add(Core.bundle.get("division.width") + ": " + combatWidth).size(50).center();
                }).growX().center();

                frame.row();
                btnTable(frame);

                frame.row();
                frame.table(s -> {
                    s.defaults().padTop(20);
                    s.center();

                    for (int i = 0;i < available.size;i++){
                        int j = i;

                        s.button(btnImg(i, available), () -> {
                            selected = available.get(j);
                            pending = true;
                        }).pad(5).center().size(50);

                        if (i % 8 == 7) s.row();
                    }
                }).growX();
            });

            planDialog.show();
        }

        public TextureRegionDrawable btnImg(int i, Seq<UnitType> seq){
            return seq.get(i) != null ? new TextureRegionDrawable(seq.get(i).uiIcon) : new TextureRegionDrawable(Icon.none.getRegion());
        }

        public Label unitName(int i){
            return compose.get(i) != null ? new Label(compose.get(i).localizedName) : new Label(Core.bundle.get("ui.none"));
        }

        public boolean remain(){
            return remaining > 0;
        }

        public void btnTable(Table tbl){
            tbl.table(btn -> {
                btn.defaults().padTop(50);
                btn.center();

                for (int i = 0;i < 3; i++){
                    int j = i;
                    btn.button(btnImg(j, compose), () -> {
                        boolean aa = selected == compose.get(j);
                        if (pending && selected != null){
                            addCompose(j, selected);
                            pending = false;
                        }
                        if (!aa){
                            btn.clearChildren();
                            btnTable(btn);
                        }
                    }).growX().center().size(70).padLeft((j - 1) * 70f);
                }
                btn.row();
                btn.add(unitName(0)).padRight(70f).center();
                btn.add(unitName(1)).center();
                btn.add(unitName(2)).padLeft(70f).center();

            }).growX();
        }
    }
}
