package com.tigres810.adventurermod.blocks.machines.storages.tileentity;

import org.lwjgl.opengl.GL11;

import com.ibm.icu.impl.duration.impl.Utils;
import com.tigres810.adventurermod.init.ModFluids;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.terraingen.BiomeEvent.GetWaterColor;
import net.minecraftforge.fml.common.ClassNameUtils;

public class RenderFluxStorage extends TileEntitySpecialRenderer<TileEntityFluxStorage> {

	private static final ResourceLocation CIRCLE_INNER = new ResourceLocation(Reference.MOD_ID + ":textures/blocks/flux_fluid.png");
	
	
	@Override
	public void render(TileEntityFluxStorage te, double x, double y, double z, float partialTicks, int destroyStage, float alpha){
		
        renderCircle(te, x, y, z, partialTicks);
	}
	
	public void renderCircle(TileEntityFluxStorage te, double x, double y, double z, float partialTicks){
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.alphaFunc(519, 0.1F);
		GlStateManager.pushMatrix();
		GlStateManager.disableRescaleNormal();
		
		GlStateManager.translate(x+0.5, y+1/16, z+0.5);
        GlStateManager.scale(0.25, 14.1/16, 0.25);
        //GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks), 0.0F, 1.0F, 0.0F);

		bindTexture(CIRCLE_INNER);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder wrInner = tessellator.getBuffer();
		wrInner.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		double height = te.getEnergyStored()/te.getMaxEnergyStored();
		wrInner.pos(-1.0D, height, 1.0D).tex(0, 0).endVertex();
		wrInner.pos(1.0D, height, 1.0D).tex(1, 0).endVertex();
		wrInner.pos(1.0D, height, -1.0D).tex(1, 1).endVertex();
		wrInner.pos(-1.0D, height, -1.D).tex(0, 1).endVertex();
		tessellator.draw();
        GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		//GlStateManager.translate(x+0.5, y+0.03, z+0.5);
        //GlStateManager.scale(0.7, 0.7, 0.7);
		//GlStateManager.rotate((te.getWorld().getTotalWorldTime() + partialTicks)*-1, 0.0F, 1.0F, 0.0F);
		//GlStateManager.color(255F, 255F, 255F, 0.2F);
		
		GlStateManager.enableLighting();
        GlStateManager.popMatrix();
	}
}
