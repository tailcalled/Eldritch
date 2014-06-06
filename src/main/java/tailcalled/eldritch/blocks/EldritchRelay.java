package tailcalled.eldritch.blocks;

import tailcalled.eldritch.items.EldritchMetal;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EldritchRelay extends EldritchBlock {

	public static class EldritchRelayTile extends EldritchBlockTile {
		
		@Override
		public double strength() {
			return !getRoot().isSource()? 0 : (source.strength() * 0.4 + 7);
		}

	}

	public static final String Name = "eldritchRelay";
	public static final EldritchRelay EldritchRelay = new EldritchRelay();
	
	private EldritchRelay() {
		super(Material.iron);
		setHardness(50.0F);
		setStepSound(Block.soundTypeMetal);
		setBlockName(Name);
		setCreativeTab(CreativeTabs.tabBlock);
		setHarvestLevel("pickaxe", 3);
		setBlockTextureName("eldritch:eldritch_relay");
	}

	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerBlock(EldritchRelay, Name);
		GameRegistry.registerTileEntity(EldritchRelayTile.class, Name + "Entity");
		GameRegistry.addRecipe(new ItemStack(EldritchRelay),
				"IRI",
				"RXR",
				"IRI", 'X', EldritchMetal.EldritchMetalBlock, 'R', Items.redstone, 'I', EldritchMetal.EldritchMetal);
	}
	
	@Override
	public EldritchBlockTile createNewTileEntity(World world, int metadata) {
		return new EldritchRelayTile();
	}

}
