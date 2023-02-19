package focus.content;

import arc.*;
import arc.struct.Seq;
import focus.util.*;
import mindustry.content.*;

import static mindustry.content.TechTree.*;
import static focus.util.FObjectives.*;
import static focus.content.ExFocus.*;


public class ExTechTree {
    static TechTree.TechNode context = null;

    public static void load() {
        nodeRoot("asdf", example, () -> {
                node(advanced, Seq.with(new sectorsCompleted(1)), () -> {});
        });
    }
}


