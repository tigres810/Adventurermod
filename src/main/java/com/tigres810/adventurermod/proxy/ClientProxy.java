package com.tigres810.adventurermod.proxy;

import com.tigres810.adventurermod.blocks.machines.storages.tileentity.RenderFluxStorage;
import com.tigres810.adventurermod.blocks.machines.storages.tileentity.TileEntityFluxStorage;
import com.tigres810.adventurermod.util.handler.RenderHandler;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy extends CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluxStorage.class, new RenderFluxStorage());
	}
	
	public void registerTESR() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluxStorage.class, new RenderFluxStorage());
	}
	
	public void registerCustomMeshesAndStates() {
		RenderHandler.registerCustomMeshesAndStates();
	}
}
