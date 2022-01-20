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
import mindustry.content.UnitTypes;
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
    public Seq<UnitType> t1 = new Seq<>();
    public Seq<UnitType> t2 = new Seq<>();
    public Seq<UnitType> t3 = new Seq<>();
    public Seq<UnitType> t4 = new Seq<>();
    public Seq<UnitType> t5 = new Seq<>();


    public DivisonPlanner(String name) {
        super(name);

        update = true;
        configurable = true;
        solid = true;
        logicConfigurable = true;
        sync = true;

        config(UnitType.class, (DivisionPlannerBuild tile, UnitType unit) -> {
            if (tile.remain()){
                tile.addCompose(tile.lastSelected, tile.selected);
            }
        });
    }

    public void setTier() {
        t1.addAll(UnitTypes.dagger, UnitTypes.nova, UnitTypes.crawler, UnitTypes.flare, UnitTypes.mono,
                UnitTypes.risso, UnitTypes.retusa);
        t2.addAll(UnitTypes.dagger, UnitTypes.nova, UnitTypes.crawler, UnitTypes.flare, UnitTypes.mono,
                UnitTypes.risso, UnitTypes.retusa);
        t3.addAll(UnitTypes.dagger, UnitTypes.nova, UnitTypes.crawler, UnitTypes.flare, UnitTypes.mono,
                UnitTypes.risso, UnitTypes.retusa);
        t4.addAll(UnitTypes.dagger, UnitTypes.nova, UnitTypes.crawler, UnitTypes.flare, UnitTypes.mono,
                UnitTypes.risso, UnitTypes.retusa);
        t5.addAll(UnitTypes.dagger, UnitTypes.nova, UnitTypes.crawler, UnitTypes.flare, UnitTypes.mono,
                UnitTypes.risso, UnitTypes.retusa);
    }

    /* public int unitWidth(UnitType unit){
    }

    */


    public class DivisionPlannerBuild extends Building{
        public UnitType selected;
        public Seq<UnitType> available = Vars.content.units().select(u -> !u.isBanned() && !u.isHidden() & u.node() != null);
        public boolean pending = false;
        public Seq<UnitType> compose = new Seq<>(3);
        public Seq<Integer> unitNums = new Seq<>(3);
        public int lastSelected;
        public int remaining = 5;

        @Override
        public void updateTile(){
            super.updateTile();
        }

        public void addCompose(int i, UnitType unit){
            compose.insert(i, unit);
            if (compose.size > i) compose.remove(i+1);
        }

        @Override
        public void buildConfiguration(Table table){
            if (planDialog == null){
                planDialog = new BaseDialog("Division Planner");
                planDialog.addCloseButton();
            }

            if (compose.size == 0) compose.add(null, null, null);

            planDialog.cont.clearChildren();

            planDialog.cont.center().top();

            /* combat width and available types */
            planDialog.cont.table(frame -> {
                frame.table(w -> {
                    w.add(Core.bundle.get("division.width") + ": " + combatWidth).size(70f);
                }).center().padBottom(30);

                frame.row();

                btnTable(frame);

                frame.row();

                frame.table(s -> {
                    s.defaults().padTop(50);
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
            }).center();

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
                btn.button(btnImg(0, compose), () -> {
                    boolean aa = selected == compose.get(0);
                    if (pending && selected != null){
                        addCompose(0, selected);
                        pending = false;
                    }
                    if (!aa){
                        btn.clearChildren();
                        btnTable(btn);
                    }
                }).growX().size(70).padRight(70f);

                btn.button(btnImg(1, compose), () -> {
                    boolean aa = selected == compose.get(1);
                    if (pending && selected != null){
                        addCompose(1, selected);
                        pending = false;
                    }
                    if (!aa){
                        btn.clearChildren();
                        btnTable(btn);
                    }
                }).growX().size(70);

                btn.button(btnImg(2, compose), () -> {
                    boolean aa = selected == compose.get(2);
                    if (pending && selected != null){
                        addCompose(2, selected);
                        pending = false;
                    }
                    if (!aa){
                        btn.clearChildren();
                        btnTable(btn);
                    }
                }).growX().size(70).padLeft(70f);

                btn.row();

                btn.add(unitName(0)).padRight(70f);
                btn.add(unitName(1)).size(40);
                btn.add(unitName(2)).padLeft(70f);

            }).growX().center();
        }
    }
}
