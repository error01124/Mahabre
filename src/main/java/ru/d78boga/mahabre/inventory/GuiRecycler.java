package ru.d78boga.mahabre.inventory;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import ru.d78boga.mahabre.tileentities.TileEntityRecycler;
import ru.d78boga.mahabre.util.Util;

public class GuiRecycler extends GuiContainer {
	private ResourceLocation texture = Util.locate("textures/gui/container/recycler.png");
	private InventoryPlayer playerInventory;
	private TileEntityRecycler tileRecycler;

	public GuiRecycler(TileEntityRecycler tileRecycler, EntityPlayer player) {
		super(tileRecycler.createContainer(player.inventory, player));
		playerInventory = player.inventory;
		this.tileRecycler = tileRecycler;
		ySize = 133;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		String name = tileRecycler.getDisplayName().getUnformattedText();
		fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 4210752);
		fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		drawTexturedModalRect(i, j, 0, 0, xSize, ySize);

		if (tileRecycler.isWorking()) {
			int l = getProgressScaled(24);
			drawTexturedModalRect(i + 32, j + 20, 176, 0, l + 1, 16);
		}
	}

	private int getProgressScaled(int pixels) {
		int i = tileRecycler.getField(0);
		int j = tileRecycler.getField(1);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
