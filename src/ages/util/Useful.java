package ages.util;

import ages.ui.dialogs.FocusDialog;
import arc.scene.ui.layout.Table;
import mindustry.content.TechTree;
import mindustry.gen.Tex;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.ui.Styles;
import mindustry.ui.dialogs.BaseDialog;

import static mindustry.Vars.*;

public class Useful {
    public static FocusDialog focusDialog;
    public static boolean showTechSelect;
    public static void focusBtn(Table t){
        t.button(b -> {
            b.imageDraw(() -> ui.research.root.node.icon()).padRight(8).size(iconMed);
            b.add().growX();
            b.label(() -> ui.research.root.node.localizedName()).color(Pal.accent);
            b.add().growX();
            b.add().size(iconMed);
        }, () -> {
            new BaseDialog("@techtree.select"){{
                cont.pane(t -> {
                    t.table(Tex.button, in -> {
                        in.defaults().width(300f).height(60f);
                        for(TechTree.TechNode node : TechTree.roots){
                            if(node.requiresUnlock && !node.content.unlocked() && node != ui.research.getPrefRoot()) continue;
                            if (node.localizedName().equals("Pre-mindustry Research Focus")) continue;
                            //TODO toggle
                            in.button(node.localizedName(), node.icon(), Styles.flatTogglet, iconMed, () -> {
                                if(node == ui.research.lastNode){
                                    return;
                                }

                                ui.research.rebuildTree(node);
                                hide();
                            }).marginLeft(12f).checked(node == ui.research.lastNode).row();
                        }
                    });
                });

                addCloseButton();
            }}.show();
        }).visible(() -> showTechSelect = TechTree.roots.count(node -> !(node.requiresUnlock && !node.content.unlocked())) > 1).minWidth(300f);
    }

    public static int completed(){
        int a = 0;
        for(Planet planet: content.planets()){
            for (Sector sector: planet.sectors){
                if (sector.isCaptured()){
                    a += 1;
                }
            }
        }
        return a;
    }
}
