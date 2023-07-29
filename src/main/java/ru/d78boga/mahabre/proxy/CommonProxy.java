package ru.d78boga.mahabre.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ru.d78boga.mahabre.Mahabre;
import ru.d78boga.mahabre.inits.MBiomes;
import ru.d78boga.mahabre.inits.MBlocks;
import ru.d78boga.mahabre.inits.MCrafts;
import ru.d78boga.mahabre.inits.MCreativeTabs;
import ru.d78boga.mahabre.inits.MDimensions;
import ru.d78boga.mahabre.inits.MEntities;
import ru.d78boga.mahabre.inits.MItems;
import ru.d78boga.mahabre.inits.MTileEntities;
import ru.d78boga.mahabre.inventory.MGuiHandler;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
    	MCreativeTabs.REGISTRY.registerAll();
    	MBlocks.REGISTRY.registerAll();
    	MItems.REGISTRY.registerAll();
    	MCrafts.REGISTRY.registerAll();
    	MEntities.REGISTRY.registerAll();
    	MTileEntities.REGISTRY.registerAll();
    	MBiomes.REGISTRY.registerAll();
    	MDimensions.REGISTRY.registerAll();
    }

    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(this);
    	NetworkRegistry.INSTANCE.registerGuiHandler(Mahabre.INSTANCE, new MGuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
