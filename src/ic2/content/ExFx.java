package ic2.content;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import mindustry.ctype.ContentList;
import mindustry.entities.Effect;
import mindustry.graphics.Pal;

import static arc.graphics.g2d.Draw.color;
import static arc.math.Angles.randLenVectors;

public class ExFx {
    public static final Effect

    carbondust = new Effect(420, e -> {
        randLenVectors(e.id, 7, 6f + e.fin() * 5f, (x, y) -> {
            color(Color.black);
            Fill.circle(e.x + x, e.y + y, 1f);
        });
    });

}
