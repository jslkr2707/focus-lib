package ages.content;

import ages.world.blocks.ancient.*;
import mindustry.content.*;
import mindustry.entities.bullet.*;
import mindustry.graphics.Pal;
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

            addFuel(AgesItems.wood);
            size = 1;
            craftTime = 120f;
            consumeItems(with(AgesItems.wood, 2));
            outputItem = new ItemStack(Items.coal, 1);
        }};

        ancientFarm = new AncientFarm("ancient-farm"){{
            requirements(Category.production, with());

            size = 2;
            health = 100 * size * size;
            itemCapacity = 20;
            addCrops(with(AgesItems.erwat, 10, AgesItems.zibel, 10));
            outputItem = new ItemStack(AgesItems.erwat, 1);
            hasShadow = false;
        }};

        altar = new AncientAltar("ancient-altar"){{
            requirements(Category.effect, with());

            size = 2;
            health = 400;
            effCapacity = 600f;
            chance = 0.5f;
            range = 200f;
            reload = 180f;

            addEffect(
                    Items.copper, new StatusEffect("heal"){{
                        healthMultiplier = 1.5f;
                        color = Pal.health;
                    }},

                    AgesItems.wood, new StatusEffect("build"){{
                        buildCostMultiplier = 0.7f;
                        color = Pal.darkMetal;
                    }},

                    AgesItems.stone, new StatusEffect("damage"){{
                        damageMultiplier = 1.5f;
                        color = Pal.ammo;
                    }},

                    Items.lead, new StatusEffect("speed"){{
                        speedMultiplier = 2f;
                        color = Pal.stoneGray;
                    }}
            );
        }};
    }
}
