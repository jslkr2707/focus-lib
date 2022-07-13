package ages.type;

import arc.struct.Seq;
import mindustry.game.SectorInfo;
import mindustry.type.*;

import static mindustry.Vars.content;
import static mindustry.Vars.state;

public class ResourceFocus extends Focus{
    public Seq<ItemStack> toAdd = new Seq<>();
    public SectorInfo infos;

    public ResourceFocus(String name){
        super(name);
    }

    @Override
    public void onUnlock(){
        for (ItemStack stack: this.toAdd){
            for (Planet planet: content.planets()){
                for (Sector sector: planet.sectors){
                    infos = sector.info;
                    if (sector.hasBase()){
                            if (infos.items.get(stack.item) <= infos.storageCapacity){
                                if(sector.isBeingPlayed()){
                                    state.rules.defaultTeam.items().add(stack.item, stack.amount);
                                }else{
                                    infos.items.add(stack.item, stack.amount);
                                    infos.items.checkNegative();
                                    sector.saveInfo();
                                }
                            }
                    }
                }
            }
        }
    }

    public void setToAdd(ItemStack[] stack) {
        for (ItemStack item: stack){
            this.toAdd.add(item);
        }
    }
}
