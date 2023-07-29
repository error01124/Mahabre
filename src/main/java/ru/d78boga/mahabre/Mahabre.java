package ru.d78boga.mahabre;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import ru.d78boga.mahabre.proxy.CommonProxy;

@Mod(
		modid = Mahabre.MODID, 
		name = Mahabre.MODNAME,
		version = Mahabre.VERSION
	)
public class Mahabre 
{
    public static final String MODID = "mahabre";
    public static final String MODNAME = "Mahabre";
    public static final String VERSION = "1.0";

    @Mod.Instance(Mahabre.MODID)
    public static Mahabre INSTANCE;

    
    @SidedProxy(
    		clientSide = "ru.d78boga.mahabre.proxy.ClientProxy", 
    		serverSide = "ru.d78boga.mahabre.proxy.CommonProxy"
    )
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) 
    {
    	proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) 
    {
    	proxy.init(event);
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) 
    {
    	proxy.postInit(event);
    }
}
