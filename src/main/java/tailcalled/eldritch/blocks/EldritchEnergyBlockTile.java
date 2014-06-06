package tailcalled.eldritch.blocks;

import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import tailcalled.eldritch.blocks.EldritchBlock.EldritchBlockTile;
import tailcalled.eldritch.blocks.GenericBlock.GenericBlockTile;
import tailcalled.eldritch.items.Calibratable;
import tailcalled.eldritch.items.EldritchMetal;

public abstract class EldritchEnergyBlockTile extends GenericBlockTile implements Calibratable {

	public static enum LeakConfig {
		Enabled, Redstone, Disabled;
		public LeakConfig getNext() {
			return values()[(ordinal()+1)%values().length];
		}
	}
	public LeakConfig leakConfig = LeakConfig.Enabled;
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
		tag.setInteger("leak", leakConfig.ordinal());
	}
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
		leakConfig = LeakConfig.values()[tag.getInteger("leak")];
	}
	@Override
	public void calibrate(World world, int x, int y, int z, int side, EntityPlayer player) {
		player.addChatMessage(new ChatComponentText(String.format(LanguageRegistry.instance().getStringLocalization("calibrator.energyBlock.magnitude"), strength())));
	}
	
	@Override
	public void updateEntity() {
		super.updateEntity();
		if (getEnergySource() != null && getEnergySource() != worldObj.getTileEntity(getEnergySource().xCoord, getEnergySource().yCoord, getEnergySource().zCoord)) {
			resetEnergySource();
		}
		if (!worldObj.isRemote && (leakConfig == LeakConfig.Enabled || leakConfig == LeakConfig.Redstone && worldObj.getBlockPowerInput(xCoord, yCoord, zCoord) > 0)) {
			leak();
		}
	}
	protected void leak() {
		double strength = strength();
		double search = (double) Math.sqrt(strength*2);
		if (strength >= 3) {
			Random rand = worldObj.rand;
			double dX, dY, dZ;
			boolean neg;
			dX = rand.nextDouble() * search; dX -= search/2; neg = dX < 0; dX *= dX; if (neg) dX = -dX;
			dY = rand.nextDouble() * search; dY -= search/2; neg = dY < 0; dY *= dY; if (neg) dY = -dY;
			dZ = rand.nextDouble() * search; dZ -= search/2; neg = dZ < 0; dZ *= dZ; if (neg) dZ = -dZ;
			if (dX * dX + dY * dY + dZ * dZ <= strength/2 * strength/2) {
				dY /= 3;
				int bX = (int) (xCoord + Math.round(dX)), bY = (int) (yCoord + Math.round(dY)), bZ = (int) (zCoord + Math.round(dZ));
				affect(bX, bY, bZ);
			}
		}
	}

	protected void affect(int bX, int bY, int bZ) {
		Block b = worldObj.getBlock(bX, bY, bZ);
		if (b instanceof EldritchBlock) {
			//EldritchBlock other = (EldritchBlock) b;
			EldritchBlockTile otherTile = (EldritchBlockTile) worldObj.getTileEntity(bX, bY, bZ);
			if (otherTile.source == null && getRoot() != otherTile) {
				otherTile.source = this;
				otherTile.markDirty();
				worldObj.markBlockForUpdate(bX, bY, bZ);
			}
		}
		else if (b == Blocks.netherrack && worldObj.getBlock(bX, bY+1, bZ) == Blocks.air) {
			if (drawEnergy(2)) {
				worldObj.setBlock(bX, bY+1, bZ, Blocks.fire);
			}
		}
		else if (b == Blocks.furnace) {
			TileEntityFurnace furnace = (TileEntityFurnace) worldObj.getTileEntity(bX, bY, bZ);
			if (furnace.furnaceBurnTime < 20 && drawEnergy(5)) {
				furnace.furnaceBurnTime += 220;
				furnace.currentItemBurnTime = furnace.furnaceBurnTime;
				BlockFurnace.updateFurnaceBlockState(true, worldObj, bX, bY, bZ);
				furnace.markDirty();
				worldObj.markBlockForUpdate(bX, bY, bZ);
			}
		}
		else if (b == Blocks.grass) {
			if (drawEnergy(1)) {
				worldObj.setBlock(bX, bY, bZ, Blocks.dirt);
			}
		}
		else if (b == Blocks.leaves || b == Blocks.leaves2) {
			if (drawEnergy(1)) {
				worldObj.setBlock(bX, bY, bZ, Blocks.air);
			}
		}
		else if (b == Blocks.iron_block && worldObj.getBlock(bX, bY+1, bZ) == Blocks.iron_block && worldObj.getBlock(bX, bY-1, bZ) == Blocks.iron_block) {
			if (drawEnergy(50)) {
				worldObj.setBlock(bX, bY+1, bZ, Blocks.air);
				worldObj.setBlock(bX, bY, bZ, EldritchMetal.EldritchMetalBlock);
				worldObj.setBlock(bX, bY-1, bZ, Blocks.air);
			}
		}
	}


	public EldritchEnergyBlockTile getRoot() {
		if (getEnergySource() == null || getEnergySource() == this) {
			return this;
		}
		else {
			return getEnergySource().getRoot();
		}
	}
	
	protected void resetEnergySource() {}
	public abstract EldritchEnergyBlockTile getEnergySource();
	public abstract boolean drawEnergy(int amount);
	public abstract double strength();
	public abstract boolean isSource();
	
}
