package focus;

import arc.scene.ui.layout.*;
import arc.util.*;
import focus.type.*;
import focus.ui.dialogs.FocusDialog;
import arc.*;
import arc.scene.*;
import arc.scene.style.*;
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
                in.defaults().fillX();
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
                            b.imageDraw(() -> new TextureRegionDrawable(Core.atlas.find("research-focus"))).marginRight(8f).size(iconMed);
                            b.add().size(8f);
                            b.label(() -> Core.bundle.format(name)).color(Pal.accent);
                        }, dialog::show)
                        .width(Core.bundle.format(name).length() * 12f + 36f)
                        .name(Core.bundle.get(name))
                );
            }
        });
    }

    public static float nodeSize(TechTree.TechNode node){
        return Math.max(node.content.localizedName.length() * 12f + 36f, Scl.scl(128f));
    }
}
