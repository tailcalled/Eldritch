package tailcalled.eldritch.items;

import tailcalled.eldritch.EldritchMod;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.AxisAlignedBB;

public class EldritchSword extends ItemSword {

	public EldritchSword() {
		super(EldritchMetal.EldritchTool);
		setUnlocalizedName("swordEldritch");
		setTextureName("eldritch:eldritch_sword");
	}
	@Override
	public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
		super.onUsingTick(stack, player, count);
		double ix = player.posX - 5, iy = player.posY - 5, iz = player.posZ - 5, ax = player.posX + 5, ay = player.posY + 5, az = player.posZ + 5;
		for (Object obj: player.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(ix, iy, iz, ax, ay, az))) {
			EntityLivingBase b = (EntityLivingBase) obj;
			if (b != player) {
				b.attackEntityFrom(EldritchMod.eldritchDamageBy(player), 5);
				stack.damageItem(1, player);
			}
		}
	}

}
