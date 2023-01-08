package ages.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.type.*;

import static mindustry.content.TechTree.*;
import static mindustry.game.Objectives.*;
import static ages.util.AgesObjectives.*;
import static mindustry.content.SectorPresets.*;
import static ages.content.AgesFocus.*;


public class AgesTechTree {
    static TechTree.TechNode context = null;

    public static void load() {
        nodeRoot("Pre-mindustry research Focus", ages, () -> {
            node(resI, Seq.with(new customResearch(AgesItems.wood), new sectorsCompleted(1)), () -> {
                node(resII, Seq.with(new customResearch(AgesItems.stone), new sectorsCompleted(3)), () -> {
                    node(resIII, Seq.with(new customResearch(Items.coal), new sectorsCompleted(5)), () -> {
                        node(resIV, Seq.with(new customResearch(AgesItems.ironOre), new customResearch(AgesItems.iron), new sectorsCompleted(10)), () -> {});
                    });
                });
            });
            node(logging, Seq.with(new sectorsCompleted(1), new notUnlocked(mining)), () -> {});
            node(mining, Seq.with(new sectorsCompleted(1), new notUnlocked(logging)), () -> {});
            node(charcoal, Seq.with(new sectorsCompleted(0)), () -> {});
        });
    }
}


