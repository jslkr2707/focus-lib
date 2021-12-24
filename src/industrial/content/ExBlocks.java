package industrial.content;

import industrial.world.blocks.heatMulti;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.production.GenericCrafter;
import multilib.MultiCrafter;
import multilib.Recipe;

import static mindustry.type.ItemStack.*;

public class ExBlocks implements ContentList {
    public static Block
    /* region crafting */
    furnace, macerator,
    /* region defense */
    steelWall, steelWallLarge, bunkerWall,
    /* region turret */
    mortar;
    @Override
    public void load(){
        furnace = new heatMulti("furnace", 6){{
            requirements(Category.crafting, with(ExItems.iron, 200, ExItems.tin, 100));
            size = 2;
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 1, ExItems.ironOre, 1)),
                    new Recipe.OutputContents(with( ExItems.iron, 1)), 1000f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 1, ExItems.ironPowder, 1)),
                    new Recipe.OutputContents(with( ExItems.iron, 2)), 1500f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 1, ExItems.tinOre, 1)),
                    new Recipe.OutputContents(with(ExItems.tin, 1)), 1200f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 1, ExItems.ironPowder, 1)),
                    new Recipe.OutputContents(with( ExItems.iron, 2)), 1500f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 2, ExItems.bauxite, 1)),
                    new Recipe.OutputContents(with( ExItems.aluminum, 1)), 2500f
            );
            addRecipe(
                    new Recipe.InputContents(with(Items.coal, 1, Items.coal, 1, ExItems.iron, 3)),
                    new Recipe.OutputContents(with( ExItems.steel, 4)), 3000f
            );
        }};

        macerator = new MultiCrafter("macerator", 2){{
            requirements(Category.crafting, with(ExItems.iron, 200, Items.copper, 100, Items.graphite, 100));
            size = 2;
            addRecipe(
                    new Recipe.InputContents(with(ExItems.ironOre, 1)),
                    new Recipe.OutputContents(with(ExItems.ironPowder, 2)), 1000f
            );
            addRecipe(
                    new Recipe.InputContents(with(ExItems.tinOre, 1)),
                    new Recipe.OutputContents(with(ExItems.tinPowder, 2)), 1200f
            );
        }};

        mortar = new ItemTurret("mortar"){{
            requirements(Category.turret, with(ExItems.iron, 500, Items.copper, 500, Items.titanium, 300));
            ammo(
                    ExItems.steel, ExBullets.artilleryBig
            );
            targetAir = false;
            size = 3;
            shots = 1;
            inaccuracy = 20f;
            reloadTime = 300f;
            ammoEjectBack = 10f;
            shootEffect = Fx.shootSmallFlame;
            ammoPerShot = 3;
            cooldown = 0.1f;
            velocityInaccuracy = 0.3f;
            restitution = 0.02f;
            recoilAmount = 12f;
            shootShake = 4f;
            range = 800f;
            health = 200 * size * size;
            shootSound = Sounds.artillery;
        }};

        /* region wall */
        steelWall = new Wall("steel-wall"){{
            requirements(Category.defense, with(ExItems.steel, 10));
            size = 1;
            health = 150 * 4;
        }};

        steelWallLarge = new Wall("steel-wall-large"){{
            requirements(Category.defense, with(ExItems.steel, 50));
            size = 2;
            health = 150 * 4 * 4;
        }};

        bunkerWall = new Wall("bunker-wall"){{
            requirements(Category.defense, with(ExItems.steel, 500, Items.copper, 300));
            size = 3;
            health = 500 * 4 * 4;
            chanceDeflect = 30f;
            deflectSound = Sounds.rockBreak;
        }};

    }
}
