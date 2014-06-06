package tailcalled.eldritch.blocks;

import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public abstract class EldritchBlock extends GenericBlock {
	
	public static abstract class EldritchBlockTile extends EldritchEnergyBlockTile {
		public EldritchEnergyBlockTile source; int _sX, _sY, _sZ; boolean _hasS = false;
		@Override
		public EldritchEnergyBlockTile getEnergySource() {
			return source;
		}
		@Override
		public void writeToNBT(NBTTagCompound tag) {
			super.writeToNBT(tag);
			if (source != null) {
				tag.setIntArray("source", new int[] {source.xCoord, source.yCoord, source.zCoord});
			}
		}
		@Override
		public void readFromNBT(NBTTagCompound tag) {
			super.readFromNBT(tag);
			int[] sourcePos = tag.getIntArray("source");
			if (sourcePos.length > 0) {
				int X = 0, Y = 1, Z = 2;
				_sX = sourcePos[X]; _sY = sourcePos[Y]; _sZ = sourcePos[Z]; _hasS = true;
			}
		}
		@Override
		protected void resetEnergySource() {
			source = null;
		}
		@Override
		public boolean drawEnergy(int amount) {
			if (source != null) {
				return source.drawEnergy(amount);
			}
			return false;
		}
		@Override
		public void updateEntity() {
			super.updateEntity();
			if (source == null && _hasS) {
				source = (EldritchEnergyBlockTile) worldObj.getTileEntity(_sX, _sY, _sZ);
				_hasS = false;
			}
		}
		@Override
		public boolean isSource() {
			return false;
		}
		@Override
		public void calibrate(World world, int x, int y, int z, int side, EntityPlayer player) {
			super.calibrate(world, x, y, z, side, player);
			if (player.isSneaking()) {
					leakConfig = leakConfig.getNext();
					markDirty();
					worldObj.markBlockForUpdate(x, y, z);
			}
			player.addChatMessage(new ChatComponentText(LanguageRegistry.instance().getStringLocalization("calibrator.energyBlock.leak" + leakConfig.name())));
		}
	}

	protected EldritchBlock(Material material) {
		super(material);
	}
	
	@Override
	public abstract EldritchBlockTile createNewTileEntity(World world, int metadata);

}
