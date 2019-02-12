package com.tigres810.adventurermod.client.gui;

import java.io.IOException;

import com.tigres810.adventurermod.Main;
import com.tigres810.adventurermod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiInformationTablet extends GuiScreen {

	final ResourceLocation information_tabletgui = new ResourceLocation(Reference.MOD_ID, "textures/gui/book1right.png");
	final ResourceLocation information_tabletgui1 = new ResourceLocation(Reference.MOD_ID, "textures/gui/book1left.png");
	int guiWidth = 175;
	int guiHeight = 228;
	GuiButton button1;
	GuiInformationTabletButton arrow;
	GuiInformationTabletButton arrow1;
	
	final int BUTTON1 = 0, ARROW = 1, ARROW1 = 2;
	
	int currentpage = 0;
	int maxpages = 2;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		Minecraft.getMinecraft().renderEngine.bindTexture(information_tabletgui);
		float centerX = (width / 1.7f) - guiWidth / 2;
		int centerY = (height / 2) - guiHeight / 2;
		drawTexturedModalRect(centerX, centerY, 0, 0, guiWidth, guiHeight);
		Minecraft.getMinecraft().renderEngine.bindTexture(information_tabletgui1);
		float centerX1 = (width / 2.44f) - guiWidth / 2;
		drawTexturedModalRect(centerX1, centerY, 0, 0, guiWidth, guiHeight);
		//arrow.drawButton(mc, mouseX, mouseY, partialTicks);
		fontRenderer.drawString(I18n.format("gui.title"), (int) ((width / 2.4f) - fontRenderer.getStringWidth("Prueba") / 2), centerY + 15, 0x000000);
		fontRenderer.drawString(currentpage + "/" + maxpages, (int) ((width / 1.96f) - fontRenderer.getStringWidth("Prueba") / 2), centerY + 195, 0x000000);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	@Override
	public void initGui() {
		buttonList.clear();
		int centerY = (height / 2) - guiHeight / 2;
		buttonList.add(button1 = new GuiButton(BUTTON1, (int) (width / 1.465f) - fontRenderer.getStringWidth("Prueba") / 2, centerY + 12, 15, 20, "X"));
		buttonList.add(arrow = new GuiInformationTabletButton(ARROW, (int) (width / 1.51f) - fontRenderer.getStringWidth("Prueba") / 2, centerY + 183));
		super.initGui();
	}
	
	private void NextDrawButton() {
		int centerY = (height / 2) - guiHeight / 2;
		if (!buttonList.contains(arrow1)) {
			buttonList.add(arrow1 = new GuiInformationTabletButton(ARROW1, (int) (width / 2.79f) - fontRenderer.getStringWidth("Prueba") / 2, centerY + 183));
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
		case BUTTON1:
			mc.displayGuiScreen(null);
			break;
		case ARROW:
			if (currentpage < maxpages - 1) {
				currentpage++;
			} else {
				currentpage++;
				buttonList.remove(arrow);
			}
			NextDrawButton();
			break;
		case ARROW1:
			if (currentpage > 1) {
				currentpage--;
				if (!buttonList.contains(arrow)) {
					buttonList.add(arrow);
				}
			} else {
				currentpage = 0;
				buttonList.remove(arrow1);
			}
			break;
		}
		super.actionPerformed(button);
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		// TODO Auto-generated method stub
		super.keyTyped(typedChar, keyCode);
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public void onGuiClosed() {
		// TODO Auto-generated method stub
		super.onGuiClosed();
	}
	
}