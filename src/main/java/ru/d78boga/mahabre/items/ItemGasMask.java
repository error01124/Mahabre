package ru.d78boga.mahabre.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import ru.d78boga.mahabre.inits.MDimensions;
import ru.d78boga.mahabre.world.MTeleporter;

public class ItemGasMask extends MItem {
	public ItemGasMask() {
		super("gas_mask");
		maxStackSize = 1;
		setMaxDamage(64);
	}

	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!player.isRiding() && !player.isBeingRidden() && !worldIn.isRemote) {
			int mahabre = MDimensions.MAHABRE.getId();
			int overworld = 0;
			int transferDimension = player.dimension == mahabre ? overworld : mahabre;

			if (!player.isCreative()) {
				ItemStack stack = player.inventory.getCurrentItem();
				stack.shrink(1);
			}

			changeDimension((EntityPlayerMP) player, transferDimension);
			return EnumActionResult.SUCCESS;
		}

		return EnumActionResult.FAIL;
	}

	public void changeDimension(EntityPlayerMP player, int dimensionIn) {
		MinecraftServer mcServer = player.mcServer;
		PlayerList playerList = mcServer.getPlayerList();
		WorldServer world = mcServer.getWorld(dimensionIn);
		playerList.transferPlayerToDimension(player, dimensionIn, new MTeleporter(world));
	}
}
