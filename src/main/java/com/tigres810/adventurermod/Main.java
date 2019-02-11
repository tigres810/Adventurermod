package com.tigres810.adventurermod;

import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.init.ModItems;
import com.tigres810.adventurermod.proxy.CommonProxy;
import com.tigres810.adventurermod.tabs.AdventurerModBlocksTab;
import com.tigres810.adventurermod.tabs.AdventurerModItemsTab;
import com.tigres810.adventurermod.util.Reference;
import com.tigres810.adventurermod.util.handler.RegistryHandler;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class Main {

	public static final CreativeTabs adventurermoditemstab = new AdventurerModItemsTab("AdventurerModItemsTab");
	public static final CreativeTabs adventurermodblockstab = new AdventurerModBlocksTab("AdventurerModBlocksTab");
	
	@Instance
	public static Main instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	static { FluidRegistry.enableUniversalBucket(); }
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		RegistryHandler.preInitRegistries(event);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		RegistryHandler.initRegistries(event);
		proxy.init();
	}
	
	@EventHandler
	public static void Postinit(FMLPostInitializationEvent event)
	{
		
	}
	
}
