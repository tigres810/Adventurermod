package com.tigres810.adventurermod.blocks.statements;

import org.lwjgl.opengl.GL11;

import com.tigres810.adventurermod.util.Reference;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("rawtypes")
public class TileEntityRenderCable extends TileEntitySpecialRenderer {

	ResourceLocation textureCable = new ResourceLocation(Reference.MOD_ID, "textures/blocks/cable.png");
	
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GL11.glTranslated(x, y, z);
		GL11.glDisable(GL11.GL_LIGHTING);
		this.bindTexture(textureCable);
		
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glTranslated(x, y, z);
	}
}
