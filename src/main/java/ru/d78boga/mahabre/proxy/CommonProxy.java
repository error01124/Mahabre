package ru.d78boga.mahabre.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import ru.d78boga.mahabre.Mahabre;
import ru.d78boga.mahabre.inits.mBiomes;
import ru.d78boga.mahabre.inits.mBlocks;
import ru.d78boga.mahabre.inits.mCrafts;
import ru.d78boga.mahabre.inits.mCreativeTabs;
import ru.d78boga.mahabre.inits.mDimensions;
import ru.d78boga.mahabre.inits.mEntities;
import ru.d78boga.mahabre.inits.mItems;
import ru.d78boga.mahabre.inits.mTileEntities;
import ru.d78boga.mahabre.inventory.mGuiHandler;

public class CommonProxy {
    public void preInit(FMLPreInitializationEvent event) {
    	mCreativeTabs.REGISTRY.registerAll();
    	mBlocks.REGISTRY.registerAll();
    	mItems.REGISTRY.registerAll();
    	mCrafts.REGISTRY.registerAll();
    	mEntities.REGISTRY.registerAll();
    	mTileEntities.REGISTRY.registerAll();
    	mBiomes.REGISTRY.registerAll();
    	mDimensions.REGISTRY.registerAll();
    }

    public void init(FMLInitializationEvent event) {
    	MinecraftForge.EVENT_BUS.register(this);
    	NetworkRegistry.INSTANCE.registerGuiHandler(Mahabre.INSTANCE, new mGuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {

    }
}
