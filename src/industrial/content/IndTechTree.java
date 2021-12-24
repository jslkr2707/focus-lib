package industrial.content;

import arc.struct.Seq;
import industrial.util.IndObjectives;
import mindustry.content.Blocks;
import mindustry.content.TechTree;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives;
import mindustry.type.ItemStack;

import static mindustry.content.Blocks.*;
import static mindustry.content.Items.*;
import static mindustry.content.SectorPresets.*;
import static industrial.content.ExBlocks.*;
import static industrial.content.ExItems.*;
import static industrial.content.IndFocuses.*;

public class IndTechTree implements ContentList {
    static TechTree.TechNode context = null;

    @Override
    public void load(){
        mergeNode(copper, () -> {
            node(ironOre, Seq.with(
                    new Objectives.Produce(ExItems.ironOre)
            ), () -> {
                node(iron, Seq.with(
                        new Objectives.Produce(ExItems.iron)
                ),() -> {
                    node(steel, Seq.with(
                            new Objectives.Produce(ExItems.steel)
                    ), () -> {

                    });
                });
            });

            node(tinOre, Seq.with(
                    new Objectives.Produce(ExItems.tinOre)
            ), () -> {
                node(tin, Seq.with(
                        new Objectives.Produce(ExItems.tin)
                ), () -> {

                });
                node(tinPowder, Seq.with(
                        new Objectives.Produce(ExItems.tinPowder)
                ), () -> {

                });
            });

            node(bauxite, Seq.with(
                    new Objectives.Produce(ExItems.bauxite)
            ),() -> {
                node(aluminum, Seq.with(
                        new Objectives.Produce(ExItems.aluminum)
                ),() -> {

                });
            });
        });

        mergeNode(copperWall, () -> {
            node(steelWall, () -> {
                node(steelWallLarge, () -> {
                    node(bunkerWall);
                });
            });
        });

        mergeNode(coreShard, () -> {
            node(defEffort, Seq.with(
                    new Objectives.SectorComplete(groundZero)
            ), () -> {
                node(wallFirst, Seq.with(
                        new Objectives.Research(copperWall),
                        new Objectives.Research(copperWallLarge),
                        new Objectives.SectorComplete(groundZero),
                        new IndObjectives.sectorsCompleted(3),
                        new IndObjectives.notUnlocked(turretFirst)
                ), () -> {});

                node(turretFirst, Seq.with(
                        new Objectives.Research(duo),
                        new Objectives.Research(scatter),
                        new Objectives.SectorComplete(groundZero),
                        new IndObjectives.sectorsCompleted(3),
                        new IndObjectives.notUnlocked(wallFirst)
                ), () -> {});
            });

            node(resI, Seq.with(
                    new Objectives.SectorComplete(groundZero),
                    new Objectives.Research(copper),
                    new Objectives.Research(lead)
            ), () -> {
                node(resII, Seq.with(
                        new IndObjectives.sectorsCompleted(5),
                        new Objectives.Research(graphite),
                        new Objectives.Research(metaglass)
                ), () -> {
                    node(resIII, Seq.with(
                            new IndObjectives.sectorsCompleted(10),
                            new Objectives.Research(silicon),
                            new Objectives.Research(titanium),
                            new Objectives.Research(thorium),
                            new Objectives.Research(plastanium)
                    ), () -> {
                        node(resIV, Seq.with(
                                new IndObjectives.sectorsCompleted(15),
                                new Objectives.Research(phaseFabric)
                        ), () -> {
                            node(resV, Seq.with(
                                    new IndObjectives.sectorsCompleted(15),
                                    new Objectives.Research(surgeAlloy)
                            ), () -> {});
                        });
                    });
                });
            });

            mergeNode(graphitePress, () -> {
                node(furnace);
            });
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

