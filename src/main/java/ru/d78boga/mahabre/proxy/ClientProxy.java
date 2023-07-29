package ru.d78boga.mahabre.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import ru.d78boga.mahabre.inits.mEntities;
import ru.d78boga.mahabre.inits.mItems;

public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        
        mEntities.REGISTRY.registerAllRenders();
    }

    public void init(FMLInitializationEvent event) {
        super.init(event);
        
        mItems.REGISTRY.registerAllRenders();
    }

    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }
}
