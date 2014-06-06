package tailcalled.eldritch.items;

import tailcalled.eldritch.blocks.EldritchDischarger;
import tailcalled.eldritch.blocks.EldritchRelay;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCompressed;
import net.minecraft.block.material.MapColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.common.util.EnumHelper;

public class EldritchMetal extends Item {
	
	public static final EldritchMetal EldritchMetal = new EldritchMetal();
	public static Block EldritchMetalBlock =
			new BlockCompressed(MapColor.cyanColor)
				.setHardness(50.0F)
				.setResistance(20)
				.setStepSound(Block.soundTypeMetal)
				.setBlockName("blockEldritch")
				.setBlockTextureName("eldritch:eldritch_block");
	public static final ToolMaterial EldritchTool = EnumHelper.addToolMaterial("eldritch", 5, 5000, 32.0F, 20, 1);
	public static final ArmorMaterial EldritchArmor = EnumHelper.addArmorMaterial("eldritch", 5000, new int[]{6, 16, 12, 6}, 1);
	
	private static final Item EldritchSword = new EldritchSword();
	private static final Item EldritchShovel = new ItemSpade(EldritchTool).setUnlocalizedName("shovelEldritch").setTextureName("eldritch:eldritch_shovel");
	private static final Item EldritchPickaxe = new ItemPickaxe(EldritchTool){}.setUnlocalizedName("pickaxeEldritch").setTextureName("eldritch:eldritch_pickaxe");
	private static final Item EldritchAxe = new ItemAxe(EldritchTool){}.setUnlocalizedName("axeEldritch").setTextureName("eldritch:eldritch_axe");
	private static final Item EldritchHoe = new ItemHoe(EldritchTool).setUnlocalizedName("hoeEldritch").setTextureName("eldritch:eldritch_hoe");

	private static final Item EldritchHelmet = new ItemArmor(EldritchArmor, 0, 0).setUnlocalizedName("helmetEldritch").setTextureName("eldritch:eldritch_helmet");
	private static final Item EldritchChestplate = new ItemArmor(EldritchArmor, 0, 1).setUnlocalizedName("chestplateEldritch").setTextureName("eldritch:eldritch_chestplate");
	private static final Item EldritchLeggings = new ItemArmor(EldritchArmor, 0, 2).setUnlocalizedName("leggingsEldritch").setTextureName("eldritch:eldritch_leggings");
	private static final Item EldritchBoots = new ItemArmor(EldritchArmor, 0, 3).setUnlocalizedName("bootsEldritch").setTextureName("eldritch:eldritch_boots");
	
	private EldritchMetal() {
		setUnlocalizedName("ingotEldritch").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("eldritch:eldritch_ingot");
	}

	public static void init(FMLInitializationEvent event) {
		GameRegistry.registerItem(EldritchMetal, EldritchMetal.getUnlocalizedName());
		GameRegistry.registerBlock(EldritchMetalBlock, EldritchMetalBlock.getUnlocalizedName());
		GameRegistry.registerItem(EldritchSword, EldritchSword.getUnlocalizedName());
		GameRegistry.registerItem(EldritchShovel, EldritchShovel.getUnlocalizedName());
		GameRegistry.registerItem(EldritchPickaxe, EldritchPickaxe.getUnlocalizedName());
		GameRegistry.registerItem(EldritchAxe, EldritchAxe.getUnlocalizedName());
		GameRegistry.registerItem(EldritchHoe, EldritchHoe.getUnlocalizedName());
		GameRegistry.registerItem(EldritchHelmet, EldritchHelmet.getUnlocalizedName());
		GameRegistry.registerItem(EldritchChestplate, EldritchChestplate.getUnlocalizedName());
		GameRegistry.registerItem(EldritchLeggings, EldritchLeggings.getUnlocalizedName());
		GameRegistry.registerItem(EldritchBoots, EldritchBoots.getUnlocalizedName());
		GameRegistry.addShapelessRecipe(new ItemStack(EldritchMetal, 9), EldritchMetalBlock);
		GameRegistry.addRecipe(new ItemStack(EldritchMetalBlock),
				"XXX",
				"XXX",
				"XXX", 'X', EldritchMetal);
		GameRegistry.addRecipe(new ItemStack(EldritchSword),
				"X",
				"X",
				"|", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchShovel),
				"X",
				"|",
				"|", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchPickaxe),
				"XXX",
				" | ",
				" | ", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchAxe),
				"XX",
				"X|",
				" |", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchHoe),
				"XX",
				" |",
				" |", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchHelmet),
				"XXX",
				"X X", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchChestplate),
				"X X",
				"XXX",
				"XXX", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchLeggings),
				"XXX",
				"X X",
				"X X", 'X', EldritchMetal, '|', Items.stick);
		GameRegistry.addRecipe(new ItemStack(EldritchBoots),
				"X X",
				"X X", 'X', EldritchMetal, '|', Items.stick);
		EldritchCalibrator.init(event);
		EldritchRelay.init(event);
		EldritchDischarger.init(event);
	}
	
}
