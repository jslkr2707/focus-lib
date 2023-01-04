package ages.content;

import arc.struct.Seq;
import mindustry.content.TechTree;
import mindustry.type.*;

import static mindustry.content.TechTree.*;
import static mindustry.game.Objectives.*;
import static ages.util.AgesObjectives.*;
import static mindustry.content.SectorPresets.*;
import static ages.content.AgesFocus.*;


public class AgesTechTree {
    static TechTree.TechNode context = null;

    public static void load() {
        nodeRoot("Pre-mindustry Research Focus", ages, () -> {
            node(resI, Seq.with(new Research(AgesItems.wood), new sectorsCompleted(1)), () -> {
                node(resII, Seq.with(new Research(AgesItems.stone), new sectorsCompleted(3)), () -> {
                    node(resIII, Seq.with(new Research(AgesItems.charcoal), new sectorsCompleted(5)), () -> {
                        node(resIV, Seq.with(new Research(AgesItems.ironOre), new Research(AgesItems.iron), new Research(AgesBlocks.furnace), new sectorsCompleted(10)), () -> {});
                    });
                });
            });
            node(logging, Seq.with(new sectorsCompleted(1), new notUnlocked(mining)), () -> {});
            node(mining, Seq.with(new sectorsCompleted(1), new notUnlocked(logging)), () -> {});
            node(charcoal, Seq.with(new sectorsCompleted(0)), () -> {});
        });
    }
}


