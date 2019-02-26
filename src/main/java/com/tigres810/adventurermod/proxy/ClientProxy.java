package com.tigres810.adventurermod.proxy;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.blocks.machines.storages.tileentity.RenderFluxStorage;
import com.tigres810.adventurermod.blocks.machines.storages.tileentity.TileEntityFluxStorage;
import com.tigres810.adventurermod.blocks.machines.tileentity.TileEntityFluxGenerator;
import com.tigres810.adventurermod.blocks.pipes.tileentity.TileEntityFluxPipe;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class ClientProxy extends CommonProxy {
	
	public void registerItemRenderer(Item item, int meta, String id) {
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), id));
		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFluxStorage.class, new RenderFluxStorage());
	}
}
