package ic2.world.blocks;

import arc.Core;
import arc.graphics.Color;
import arc.scene.style.TextureRegionDrawable;
import arc.scene.ui.layout.Table;
import mindustry.world.blocks.production.GenericCrafter;

public class testTable extends GenericCrafter {
    public testTable(String name){ super(name); }

    public class testTableBuild extends GenericCrafterBuild {
        @Override
        public void buildConfiguration(Table table){
            super.buildConfiguration(table);
            table.button(new TextureRegionDrawable (Core.atlas.find("duo")), 40, () -> {
                configure("d");

                table.clear();
                buildConfiguration(table);
            }).size(40).color(Color.valueOf("313131"));

        }
    }
}
