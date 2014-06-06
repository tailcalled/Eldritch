package tailcalled.eldritch.items;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EldritchCalibrator extends Item {
	
	public static final EldritchCalibrator EldritchCalibrator = new EldritchCalibrator();
	
	private EldritchCalibrator() {
		setCreativeTab(CreativeTabs.tabTools);
		setUnlocalizedName("calibratorEldritch");
		setTextureName("eldritch:eldritch_calibrator");
	}
	@Override
	public boolean onItemUseFirst(ItemStack item, EntityPlayer player, World world, int x,
			int y, int z, int side, float hX, float hY, float hZ) {
		if (!world.isRemote) {
			if (world.getBlock(x, y, z) instanceof Calibratable) {
				((Calibratable) world.getBlock(x, y, z)).calibrate(world, x, y, z, side, player);
			}
			if (world.getTileEntity(x, y, z) instanceof Calibratable) {
				((Calibratable) world.getTileEntity(x, y, z)).calibrate(world, x, y, z, side, player);
			}
		}
		return false;
	}
	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(EldritchCalibrator, EldritchCalibrator.getUnlocalizedName());
		GameRegistry.addRecipe(new ItemStack(EldritchCalibrator),
				" X",
				"X ", 'X', EldritchMetal.EldritchMetalBlock);
	}
	
}
