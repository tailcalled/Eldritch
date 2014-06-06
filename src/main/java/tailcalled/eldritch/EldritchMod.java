package tailcalled.eldritch;

import tailcalled.eldritch.blocks.EldritchOre;
import tailcalled.eldritch.items.EldritchMetal;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = EldritchMod.MODID, version = EldritchMod.VERSION, name = "Eldritch Abominations")
public class EldritchMod {
	
	@Instance(value = EldritchMod.MODID)
	public static EldritchMod instance;
	
	@SidedProxy(clientSide="tailcalled.eldritch.client.ClientProxy", serverSide="tailcalled.eldritch.Proxy")
	public static Proxy proxy;
	
	public static final String MODID = "eldritch";
	public static final String VERSION = "0.0.0";
	
	public static DamageSource eldritchDamage = new DamageSource("eldritch").setMagicDamage();
	public static DamageSource eldritchDamageBy(EntityLivingBase dealer) {
		return new EntityDamageSource("byEldritch", dealer);
	}
	
	@EventHandler
	public void preinit(FMLPreInitializationEvent event) {
		
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		EldritchOre.init(event);
		EldritchMetal.init(event);
		proxy.registerRenderers();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}

}
