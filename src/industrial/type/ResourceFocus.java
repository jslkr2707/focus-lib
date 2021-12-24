package industrial.type;

import arc.util.*;
import mindustry.ClientLauncher;
import mindustry.Vars;
import mindustry.content.Items;
import mindustry.core.UI;
import mindustry.ctype.ContentType;
import mindustry.game.SectorInfo;
import mindustry.type.*;
import mindustry.ui.dialogs.ResearchDialog;

import static mindustry.Vars.content;

public class ResourceFocus extends Focus{
    public ItemStack[] toAdd;
    public SectorInfo infos;

    public ResourceFocus(String name){
        super(name);
    }

    @Override
    public void onUnlock(){
        for (Planet planet: content.planets()){
            for (Sector sector: planet.sectors){
                infos = sector.info;
                if (sector.hasBase()){
                    if (this.toAdd != null){
                        for (ItemStack stack: this.toAdd){
                            if (infos.items.get(stack.item) >= infos.storageCapacity){

                            }
                        }
                    }
                }
            }
        }
    }

    public void setToAdd(ItemStack[] toAdd) {
        this.toAdd = toAdd;
    }

    @Override
    public ItemStack[] researchRequirements() {
        return ItemStack.empty;
    }
}
