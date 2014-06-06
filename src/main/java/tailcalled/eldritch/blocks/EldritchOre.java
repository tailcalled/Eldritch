package tailcalled.eldritch.blocks;

import tailcalled.eldritch.EldritchMod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

public class EldritchOre extends GenericBlock {
	
	public static class EldritchOreTile extends EldritchEnergyBlockTile {

		public Entity sacrifice; private int _sacrifice = 0;
		public int sacrificeProgress;
		public int sacrifices;

		@Override
		public void writeToNBT(NBTTagCompound tag) {
			super.writeToNBT(tag);
			tag.setInteger("sacrifices", sacrifices);
			tag.setInteger("progress", sacrificeProgress);
			tag.setInteger("currentSacrifice", sacrifice == null? 0 : sacrifice.getEntityId());
		}
		@Override
		public void readFromNBT(NBTTagCompound tag) {
			super.readFromNBT(tag);
			sacrifices = tag.getInteger("sacrifices");
			sacrificeProgress = tag.getInteger("progress");
			_sacrifice = tag.getInteger("currentSacrifice");
		}
		@Override
		public void updateEntity() {
			super.updateEntity();
			if (sacrifice == null && _sacrifice != 0) {
				sacrifice = (Entity) worldObj.getEntityByID(_sacrifice);
				_sacrifice = 0;
			}
			if (sacrifice != null) {
				if (isValidSacrifice(sacrifice)) {
					sacrificeProgress++;
					if (sacrifice.attackEntityFrom(EldritchMod.eldritchDamage, sacrificeProgress * sacrificeProgress * 0.01F)) {
						markDirty();
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
						sacrifices += Math.log(sacrificeProgress);
					}
					sacrifice.setPosition(xCoord + 0.5, yCoord + sacrifice.yOffset + Math.log(sacrificeProgress+1) * 0.3 + 1, zCoord + 0.5);
					sacrifice.setVelocity(0, 0, 0);
				}
				else {
					sacrifice = null;
					markDirty();
					worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				}
			}
		}
		public boolean isValidSacrifice(Entity entity) {
			return entity instanceof EntityLivingBase && !entity.isDead;
		}
		public void sacrifice(Entity entity) {
			if (sacrifice == null && isValidSacrifice(entity)) {
				sacrifice = entity;
				sacrificeProgress = 0;
				markDirty();
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		@Override
		public EldritchEnergyBlockTile getEnergySource() {
			return this;
		}
		@Override
		public boolean drawEnergy(int amount) {
			if (sacrifices > amount) {
				sacrifices -= amount;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return true;
			}
			return false;
		}
		@Override
		public double strength() {
			return Math.log(sacrifices + 1) * 1.5;
		}
		@Override
		public boolean isSource() {
			return true;
		}
		@Override
		public void calibrate(World world, int x, int y, int z, int side, EntityPlayer player) {
			super.calibrate(world, x, y, z, side, player);
			player.addChatMessage(new ChatComponentText(String.format(LanguageRegistry.instance().getStringLocalization("calibrator.energyBlock.sacrifices"), sacrifices)));
		}
	}
	
	public static final String Name = "eldritchOre";
	public static final EldritchOre EldritchOre = new EldritchOre();
	
	private EldritchOre() {
		super(Material.rock);
		setHardness(50.0F);
		setStepSound(Block.soundTypeStone);
		setBlockName(Name);
		setCreativeTab(CreativeTabs.tabBlock);
		setHarvestLevel("pickaxe", 3);
		setBlockTextureName("eldritch:eldritch_ore");
	}

	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerBlock(EldritchOre, Name);
		GameRegistry.registerTileEntity(EldritchOreTile.class, Name + "Entity");
	}
	
	@Override
	public GenericBlockTile createNewTileEntity(World world, int metadata) {
		return new EldritchOreTile();
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity e) {
		super.onEntityWalking(world, x, y, z, e);
		if (validStructure(world, x, y, z)) {
			sacrifice(world, x, y, z, e);
		}
	}
	@Override
	public void onFallenUpon(World world, int x, int y, int z, Entity e, float speed) {
		super.onFallenUpon(world, x, y, z, e, speed);
		if (validStructure(world, x, y, z)) {
			sacrifice(world, x, y, z, e);
		}
	}

	private boolean validStructure(World world, int x, int y, int z) {
		for (int xD = -2; xD < 3; xD++) {
			for (int yD = 1; yD < 4; yD++) {
				for (int zD = -2; zD < 3; zD++) {
					Block b = world.getBlock(x+xD, y+yD, z+zD);
					if (xD * xD == 4 && zD * zD == 4) {
						if (yD == 3) {
							if (b != Blocks.air && b != Blocks.fire) {
								System.out.println("AUnexpected " + b + " at " + xD + " " + yD + " " + zD);
								return false;
							}
						}
						else if (b != Blocks.netherrack) {
							System.out.println("BUnexpected " + b + " at " + xD + " " + yD + " " + zD);
							return false;
						}
					}
					else if (b != Blocks.air && b != Blocks.fire) {
						System.out.println("CUnexpected " + b + " at " + xD + " " + yD + " " + zD);
						return false;
					}
				}
			}
		}
		return true;
	}
	private void sacrifice(World world, int x, int y, int z, Entity e) {
		EldritchOreTile ore = (EldritchOreTile) world.getTileEntity(x, y, z);
		ore.sacrifice(e);
	}

}
