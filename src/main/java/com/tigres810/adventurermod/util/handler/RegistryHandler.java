package com.tigres810.adventurermod.util.handler;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.init.ModBlocks;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.init.ModItems;
import com.tigres810.adventurermod.util.IHasModel;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@EventBusSubscriber
public class RegistryHandler {

	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event) {
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
		TileEntityHandler.registerTileEntities();
		Main.proxy.registerTESR();
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event) {
		for(Item item : ModItems.ITEMS) {
			if(item instanceof IHasModel) {
				((IHasModel)item).registerModels();
			}
		}
		
		for(Block block : ModBlocks.BLOCKS) {
			if(block instanceof IHasModel) {
				((IHasModel)block).registerModels();
			}
		}
		
		for(Fluid fluid : ModFluids.FLUIDS) {
			if(fluid instanceof IHasModel) {
				((IHasModel)fluid).registerModels();
			}
		}
	}
	
	public static void preInitRegistries(FMLPreInitializationEvent event)
	{
		ModFluids.registerFluids();
		
		Main.proxy.registerCustomMeshesAndStates();
	}
	
	public static void initRegistries(FMLInitializationEvent event) 
	{
		NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
	}
}
