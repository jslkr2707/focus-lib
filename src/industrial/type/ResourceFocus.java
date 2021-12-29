package industrial.type;

import arc.struct.Seq;
import arc.util.*;
import mindustry.ClientLauncher;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.core.UI;
import mindustry.ctype.ContentType;
import mindustry.game.SectorInfo;
import mindustry.type.*;
import mindustry.ui.dialogs.ResearchDialog;
import mindustry.world.modules.ItemModule;

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
        while (this.toAdd.size > 0){
            for (Planet planet: content.planets()){
                for (Sector sector: planet.sectors){
                    infos = sector.info;
                    if (sector.hasBase()){
                        for (ItemStack stack: this.toAdd){
                            if (infos.items.get(stack.item) <= infos.storageCapacity){
                                if(sector.isBeingPlayed()){
                                    state.rules.defaultTeam.items().add(stack.item, stack.amount);
                                }else{
                                    infos.items.add(stack.item, stack.amount);
                                    infos.items.checkNegative();
                                    sector.saveInfo();
                                }
                                this.toAdd.remove(stack);
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
