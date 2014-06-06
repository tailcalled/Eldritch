package tailcalled.eldritch.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class EldritchFurnace extends EldritchBlock {

	public class EldritchFurnaceTile extends EldritchBlockTile {

		@Override
		public double strength() {
			return 0;
		}

	}

	protected EldritchFurnace() {
		super(Material.iron);
	}

	@Override
	public EldritchBlockTile createNewTileEntity(World world, int metadata) {
		return new EldritchFurnaceTile();
	}

}
