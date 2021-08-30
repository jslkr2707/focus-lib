package ic2.content;

import ic2.world.blocks.defense.turrets.rangedArtillery;
import ic2.world.blocks.heatMulti;
import mindustry.content.Fx;
import mindustry.content.Items;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.production.GenericCrafter;
import multilib.Recipe;

import static mindustry.type.ItemStack.*;

public class ExBlocks implements ContentList {
    public static Block
    /* region crafting */
    furnace, macerator,
    /* region turret */
    mortar;
    @Override
    public void load(){
        furnace = new heatMulti("furnace", 3){{
            requirements(Category.crafting, with(ExItems.wood, 10, ExItems.stone, 10));
            size = 2;
            addRecipe(
                    new Recipe.InputContents(with(ExItems.wood, 1)),
                    new Recipe.OutputContents(with(Items.coal, 2)), 600f
            );
            addRecipe(
                    new Recipe.InputContents(with(ExItems.wood, 1, ExItems.ironOre, 1)),
                    new Recipe.OutputContents(with( ExItems.iron, 1)), 1000f
            );
            addRecipe(
                    new Recipe.InputContents (with(Items.coal, 1, ExItems.groundIron, 1)),
                    new Recipe.OutputContents (with(ExItems.iron, 1)), 1000f
            );
        }};

        macerator = new GenericCrafter ("macerator"){{
            size = 2;
            craftEffect = Fx.smeltsmoke;
            requirements(Category.crafting, with(Items.copper, 50));
            outputItem = new ItemStack(ExItems.ironOre, 1);
            consumes.items(with(ExItems.groundIron, 2));
            craftTime = 60f;

        }};

        mortar = new rangedArtillery ("mortar"){{
            requirements(Category.turret, with(ExItems.stone, 100, ExItems.iron, 200));
            ammo(
                    ExItems.iron, ExBullets.artilleryBig
            );
            targetAir = false;
            size = 3;
            shots = 1;
            inaccuracy = 20f;
            reloadTime = 300f;
            ammoEjectBack = 10f;
            shootEffect = ExFx.turretOverheat;
            ammoPerShot = 3;
            cooldown = 0.1f;
            velocityInaccuracy = 0.3f;
            restitution = 0.02f;
            recoilAmount = 12f;
            shootShake = 4f;
            range = 800f;
            artilleryMinRange = 50f;

            health = 200 * size * size;
            shootSound = Sounds.artillery;
        }};
    }
}
