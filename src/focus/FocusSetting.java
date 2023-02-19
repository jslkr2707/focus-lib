package focus;

import focus.type.*;
import focus.ui.dialogs.FocusDialog;
import arc.*;
import arc.scene.*;
import arc.scene.style.*;
import arc.scene.ui.layout.Table;
import mindustry.*;
import mindustry.content.TechTree;
import mindustry.gen.Tex;
import mindustry.graphics.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.*;

public class FocusSetting {
    public static boolean showTechSelect;
    public String dialogTitleKey;

    public static void focusBtn(Table t){
        t.button(b -> {
            b.imageDraw(() -> ui.research.root.node.icon()).padRight(8).size(iconMed);
            b.add().growX();
            b.label(() -> ui.research.root.node.localizedName()).color(Pal.accent);
            b.add().growX();
            b.add().size(iconMed);
        }, () -> new BaseDialog("@techtree.select"){{
            cont.pane(t -> t.table(Tex.button, in -> {
                in.defaults().width(300f).height(60f);
                for(TechTree.TechNode node : TechTree.roots){
                    if (node.children.get(0).content instanceof Focus) continue;

                    in.button(node.localizedName(), node.icon(), Styles.flatTogglet, iconMed, () -> {
                        if(node == ui.research.lastNode){
                            return;
                        }

                        ui.research.rebuildTree(node);
                        hide();
                    }).marginLeft(12f).checked(node == ui.research.lastNode).row();
                }
            }));
            addCloseButton();
        }}.show()).visible(showTechSelect = TechTree.roots.count(node -> !(node.requiresUnlock && !node.content.unlocked())) > 1).minWidth(300f);
    }

    public static void init(String name){
        FocusDialog dialog = new FocusDialog(Core.bundle.format(name));

        ui.research.shown(() -> {
            var group = (Group)ui.research.getChildren().first();

            if (mobile){

            } else {
                ui.research.titleTable.remove();
                group.fill(c -> focusBtn(c.center().top()));
                group.fill(c -> c.center().top().marginTop(70f)
                        .button(b -> {
                            b.imageDraw(() -> new TextureRegionDrawable(Core.atlas.find("research-focus"))).padRight(8).size(iconMed);
                            b.add().growX();
                            b.label(() -> Core.bundle.format(name)).color(Pal.accent);
                            b.add().growX();
                            b.add().size(iconMed);
                        }, dialog::show)
                        .size(240, 48)
                        .name(Core.bundle.get(name))
                );
            }
        });
    }
}
