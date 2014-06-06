package tailcalled.eldritch.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface Calibratable {
	
	public void calibrate(World world, int x, int y, int z, int side, EntityPlayer player);
	
}