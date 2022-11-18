package ages.content;

import ages.world.blocks.defense.WiredFence;
import ages.world.blocks.defense.DefenseTower;
import ages.world.blocks.production.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.blocks.defense.Wall;
import mindustry.world.blocks.production.GenericCrafter;

import static mindustry.type.ItemStack.*;

public class AgesBlocks{
    public static Block
    /* region environment */
    soil,
    /* region production */
    ancientFarm, lumber, basicMine,
    /* region crafting */
    pitKiln, furnace,
    /* region religion */
    altar,
    /* region defense */
    woodenFence, barbedFence, bulwark;
    public static void load(){
        woodenFence = new WiredFence("wooden-fence"){{
            requirements(Category.defense, with(AgesItems.wood, 6));

            health = 200;
            size = 1;
            speedMultiplier = 0.25f;
            fenceDamage = 5f;
            maxDurability = 400f;
        }};

        barbedFence = new WiredFence("complex-wooden-fence"){{
            requirements(Category.defense, with(AgesItems.iron, 8));

            health = 800;
            size = 2;
            speedMultiplier = 0.15f;
            fenceDamage = 10f;
            maxDurability = 1000f;
        }};

        bulwark = new DefenseTower("bulwark"){{
            requirements(Category.turret, with());

            health = 500;
            size = 2;

            reload = 120f;
            range = 150f;

            ammo(
                    AgesItems.stone, new BasicBulletType(1.5f, 5){{
                        width = height = 4f;
                        lifetime = 120f;
                    }}
            );
        }};

        /*
        pitKiln = new ModernCrafter("pit-kiln"){{
            requirements(Category.crafting, with());

            size = 1;
            consumeItems(with(AgesItems.wood, 2));
            consumeFuel(
                    new Fuel(AgesItems.wood, 2){{
                        heatCapacity = 300f;
                        heatRate = 0.8f;
                        burnEffect = Fx.smoke;
                    }},

                    new Fuel(Items.coal, 1){{
                        heatCapacity = 800f;
                        heatRate = 1.3f;
                        burnEffect = Fx.smeltsmoke;
                        capacityMultiplier = 1.2f;
                    }}
            );
        }};


        furnace = new ModernCrafter("furnace"){{

            requirements(Category.crafting, with());
            size = 2;
            consumeFuel(
                    new Fuel(AgesItems.wood, 2){{
                        heatCapacity = 300f;
                        heatRate = 0.8f;
                        burnEffect = Fx.smoke;
                    }}
            );
        }};

         */

        lumber = new Wall("lumber"){{
            requirements(Category.defense, with(AgesItems.wood, 24));
            health = 400;
            size=1;
        }};

    }
}
