package tailcalled.eldritch.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public abstract class GenericBlock extends Block implements ITileEntityProvider {
	
	public static class GenericBlockTile extends TileEntity {
		@Override
		public Packet getDescriptionPacket() {
			NBTTagCompound tag = new NBTTagCompound();
			writeToNBT(tag);
			return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
		}
		@Override
		public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
			super.onDataPacket(net, pkt);
			readFromNBT(pkt.func_148857_g());
		}
	}
	
	protected GenericBlock(Material material) {
		super(material);
	}

	@Override
	public abstract GenericBlockTile createNewTileEntity(World world, int metadata);
	
}
