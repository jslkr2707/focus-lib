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
            node(logging, Seq.with(new sectorsCompleted(1), new notUnlocked(mining)), () -> {});
            node(mining, Seq.with(new sectorsCompleted(1), new notUnlocked(logging)), () -> {});
            node(charcoal, Seq.with(new sectorsCompleted(0)), () -> {});
        });
    }
}


