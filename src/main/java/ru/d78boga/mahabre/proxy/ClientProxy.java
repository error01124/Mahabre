package ru.d78boga.mahabre.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.d78boga.mahabre.inits.MEntities;
import ru.d78boga.mahabre.inits.MItems;

public class ClientProxy extends CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        
        MEntities.REGISTRY.registerAllRenders();
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        
        MItems.REGISTRY.registerAllRenders();
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
