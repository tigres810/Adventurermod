package com.tigres810.adventurermod.client.gui;

import com.tigres810.adventurermod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiInformationTabletButton extends GuiButton {

	final ResourceLocation information_tabletgui = new ResourceLocation(Reference.MOD_ID, "textures/gui/book1right.png");
	
	int buttonwidth = 16;
	int buttonheight = 14;
	int u = 175;
	int v = 1;
	int button;
	
	public GuiInformationTabletButton(int buttonId, int x, int y) {
		super(buttonId, x, y, 16, 14, "");
		if (buttonId == 2) {
			button = buttonId;
		}
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			mc.renderEngine.bindTexture(information_tabletgui);
			if(mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
				hovered = true;
			} else {
				hovered = false;
			}
			if (hovered) {
				v = 18;
			} else {
				v = 1;
			}
			if (button == 2) {
				drawTexturedModalRect(x, y, 192, v, buttonwidth, buttonheight);
			} else {
				drawTexturedModalRect(x, y, u, v, buttonwidth, buttonheight);
				}
		}
	}

}
