package ages.world.blocks.ancient;

import arc.scene.ui.layout.Table;
import arc.struct.*;
import mindustry.content.StatusEffects;
import mindustry.gen.*;
import mindustry.type.*;
import mindustry.world.*;
import mindustry.world.consumers.ConsumeItemFilter;

public class AncientAltar extends Block {
    public ObjectMap<Item, StatusEffect> effectTypes = new ObjectMap<Item, StatusEffect>();
    public int offUse = 1;
    public float effProb = 0.5f;

    public AncientAltar(String name) {
        super(name);

        hasPower = false;
        hasLiquids = false;
    }

    public void effect(Object... obj){
        effectTypes = ObjectMap.of(obj);
    }

    @Override
    public void init(){
        consume(new ConsumeItemFilter(i -> effectTypes.containsKey(i)){
            @Override
            public float efficiency(Building build){
                return build instanceof AncientAltarBuild b && b.effecting ? 1f : 0f;
            }
        });
    }

    public class AncientAltarBuild extends Building {
        public float effTime = 0;
        public boolean effecting = false;

        public boolean canEffect(){
            return effTime <= 0;
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return super.acceptItem(source, item) && canEffect() && effectTypes.containsKey(item);
        }
    }
}
