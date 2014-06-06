package tailcalled.eldritch.blocks;

import tailcalled.eldritch.items.EldritchMetal;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import static tailcalled.eldritch.Util.dx;
import static tailcalled.eldritch.Util.dy;
import static tailcalled.eldritch.Util.dz;

public class EldritchDischarger extends EldritchBlock {

	public static class EldritchDischargerTile extends EldritchBlockTile {

		@Override
		public void calibrate(World world, int x, int y, int z, int side, EntityPlayer player) {
			super.calibrate(world, x, y, z, side, player);
			worldObj.setBlockMetadataWithNotify(x, y, z, side + 1, 3);
		}
		@Override
		protected void leak() {
			int side = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) - 1;
			affect(xCoord + dx(side), yCoord + dy(side), zCoord + dz(side));
		}
		@Override
		public double strength() {
			return !getRoot().isSource()? 0 : 1;
		}

	}

	public static final String Name = "eldritchDischarger";
	public static final EldritchDischarger EldritchDischarger = new EldritchDischarger();
	
	private static IIcon Discharger;
	private static IIcon Side;
	
	private EldritchDischarger() {
		super(Material.iron);
		setHardness(50.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(Name);
		setCreativeTab(CreativeTabs.tabBlock);
		setHarvestLevel("pickaxe", 3);
	}
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side,
			float hX, float hY, float hZ, int meta) {
		return side + 1;
	}
	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return metadata == 0 && side == 2 ? Discharger : side == metadata-1? Discharger : Side;
	}
	
	@Override
	@SideOnly(cpw.mods.fml.relauncher.Side.CLIENT)
	public void registerBlockIcons(IIconRegister icons) {
		Discharger = icons.registerIcon("eldritch:eldritch_discharger");
		Side = icons.registerIcon("eldritch:eldritch_block");
	}
	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerBlock(EldritchDischarger, Name);
		GameRegistry.registerTileEntity(EldritchDischargerTile.class, Name + "Entity");
		GameRegistry.addRecipe(new ItemStack(EldritchDischarger),
				"XXX",
				"RRR",
				"XXX", 'X', EldritchMetal.EldritchMetal, 'R', Items.redstone);
	}
	
	@Override
	public EldritchBlockTile createNewTileEntity(World world, int metadata) {
		return new EldritchDischargerTile();
	}

}
