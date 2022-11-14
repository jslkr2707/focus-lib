package ages.content;

import ages.world.blocks.defense.WiredFence;
import ages.world.blocks.defense.DefenseTower;
import ages.world.blocks.production.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
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
    firepit, potteryKiln,
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

        firepit = new ModernCrafter("firepit"){{
            requirements(Category.crafting, with());

            addFuel(AgesItems.wood);
            size = 1;
            craftTime = 120f;
            consumeItems(with(AgesItems.wood, 2));
            outputItem = new ItemStack(Items.coal, 1);
        }};
    }
}
