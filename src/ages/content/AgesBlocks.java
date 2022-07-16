package ages.content;

import ages.world.blocks.ancient.*;
import mindustry.content.*;
import mindustry.entities.bullet.BasicBulletType;
import mindustry.type.*;
import mindustry.world.*;

import static mindustry.type.ItemStack.*;

public class AgesBlocks{
    public static Block
    /* region environment */
    soil,
    /* region production */
    ancientFarm,
    /* region crafting */
    firepit,

    woodenFence, advancedFence, bulwark;
    public static void load(){
        woodenFence = new AncientFence("wooden-fence"){{
            requirements(Category.defense, with(AgesItems.wood, 6));

            health = 150;
            size = 1;
        }};

        advancedFence = new AncientFence("complex-wooden-fence"){{
            requirements(Category.defense, with(AgesItems.wood, 24));

            health = 600;
            size = 2;
            speedMultiplier = 0.15f;
            fenceDamage = 10f;
        }};

        bulwark = new AncientTower("bulwark"){{
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

        firepit = new AncientCrafter("firepit"){{
            requirements(Category.crafting, with());

            fuel = Items.coal;
            size = 1;
            craftTime = 120f;
            consumeItems(with(AgesItems.wood, 1));
            outputItem = new ItemStack(Items.copper, 1);
        }};

        ancientFarm = new AncientFarm("ancient-farm"){{
            requirements(Category.production, with());

            size = 2;
            health = 100 * size * size;
            outputItem = new ItemStack(AgesItems.erwat, 60);
        }};
    }
}
