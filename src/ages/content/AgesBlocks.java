package ages.content;

import ages.world.blocks.ancient.*;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.gen.Sounds;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.turrets.ItemTurret;

import static mindustry.type.ItemStack.*;

public class AgesBlocks implements ContentList {
    public static Block
    /* region crafting */
    firepit,
    /* region turret */
    mortar, grenadeLauncher,

    woodenFence, advancedFence, bulwark;
    @Override
    public void load(){
        mortar = new ItemTurret("mortar"){{
            requirements(Category.turret, with(AgesItems.iron, 500, Items.copper, 500, Items.titanium, 300));
            ammo(
                    AgesItems.iron, Bullets.artilleryDense,
                    AgesItems.steel, AgesBullets.artilleryBig,
                    Items.copper, AgesBullets.missileSmall
            );
            targetAir = false;
            size = 3;
            shots = 1;
            inaccuracy = 0f;
            reloadTime = 120f;
            ammoEjectBack = 10f;
            shootEffect = Fx.explosion;
            ammoPerShot = 3;
            cooldown = 0.1f;
            velocityInaccuracy = 0;
            restitution = 0.02f;
            recoilAmount = 12f;
            range = 800f;
            health = 200 * size * size;
            shootSound = Sounds.missile;
        }};

        grenadeLauncher = new ItemTurret("grenade-launcher"){{
            requirements(Category.turret, with());
            size = 2;
            ammo(
                    Items.copper, AgesBullets.grenadeSmall
            );
            reloadTime = 120f;
            range = 300f;
            health = 200 * size * size;
            inaccuracy = 0;
            shootSound = Sounds.explosion;
            shots = 1;
            shootShake = 1f;
        }};

        woodenFence = new AncientFence("wooden-fence"){{
            requirements(Category.defense, with(AgesItems.wood, 6));

            health = 100;
            size = 1;
        }};

        advancedFence = new AncientFence("complex-wooden-fence"){{
            requirements(Category.defense, with(AgesItems.wood, 24));

            health = 400;
            size = 2;
        }};

        bulwark = new AncientTower("bulwark"){{
            requirements(Category.turret, with());

            health = 200000;
            size = 2;

            reloadTime = 120f;
            range = 150f;

            ammo(
                    AgesItems.stone, Bullets.standardCopper
            );
        }};

        firepit = new AncientCrafter("firepit"){{
            requirements(Category.crafting, with());

            fuel = Items.coal;
            size = 1;
            craftTime = 120f;
            consumes.items(with(AgesItems.wood, 1));
            outputItem = new ItemStack(Items.copper, 1);
        }};
    }
}
