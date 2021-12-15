package industrial.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

import static mindustry.content.Blocks.*;
import static mindustry.content.SectorPresets.*;
import static industrial.content.ExBlocks.*;
import static industrial.content.IndFocuses.*;

public class IndTechTree implements ContentList {
    static TechTree.TechNode context = null;

    @Override
    public void load(){
        mergeNode(coreShard, () -> {
            node(ExItems.wood, () -> {
                node(ExItems.stone, () -> {
                    node(ExItems.ironOre, () -> {
                        node(ExItems.iron);
                    });
                });
            });

            node(defFirst, Seq.with(
                    new Objectives.SectorComplete(groundZero),
                    new Objectives.Research(copperWall),
                    new Objectives.Research(copperWallLarge)
            ), () -> {
            });
            node(atkFirst, Seq.with(
                    new Objectives.SectorComplete(groundZero),
                    new Objectives.Research(scatter),
                    new Objectives.Research(copperWall)
            ), () -> {
            });
        });

        mergeNode(graphitePress, () -> {
            node(furnace);
        });

    }

    private static void mergeNode(UnlockableContent parent, Runnable children){
        context = TechTree.all.find(t -> t.content == parent);
        children.run();
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Seq<Objectives.Objective> objectives, Runnable children){
        TechTree.TechNode node = new TechTree.TechNode(context, content, requirements);
        if(objectives != null) node.objectives = objectives;

        TechTree.TechNode prev = context;
        context = node;
        children.run();
        context = prev;
    }

    private static void node(UnlockableContent content, ItemStack[] requirements, Runnable children){
        node(content, requirements, null, children);
    }

    private static void node(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives, children);
    }

    private static void node(UnlockableContent content, Runnable children){
        node(content, content.researchRequirements(), children);
    }

    private static void node(UnlockableContent block){
        node(block, () -> {});
    }

    private static void nodeProduce(UnlockableContent content, Seq<Objectives.Objective> objectives, Runnable children){
        node(content, content.researchRequirements(), objectives.and(new Objectives.Produce(content)), children);
    }

    private static void nodeProduce(UnlockableContent content, Runnable children){
        nodeProduce(content, Seq.with(), children);
    }

    private static void nodeProduce(UnlockableContent content){
        nodeProduce(content, Seq.with(), () -> {});
    }
}

