package ru.d78boga.mahabre.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import ru.d78boga.mahabre.tileentities.TileEntityRecycler;

public class MGuiHandler implements IGuiHandler {
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		if (tileEntity instanceof TileEntityRecycler) {
			return ((TileEntityRecycler)tileEntity).createContainer(player.inventory, player);
		}
		
		return null;
	}

	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));

		if (tileEntity instanceof TileEntityRecycler) {
			return new GuiRecycler((TileEntityRecycler) tileEntity, player);	
		}
		
		return null;
	}
}
