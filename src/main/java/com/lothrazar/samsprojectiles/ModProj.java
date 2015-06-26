package com.lothrazar.samsprojectiles;
 
import com.lothrazar.samsprojectiles.entity.projectile.*;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = ModProj.MODID,  useMetadata = true )
public class ModProj
{
    public static final String MODID = "samsprojectiles"; 
	public static final String TEXTURE_LOCATION = MODID + ":"; 
	@SidedProxy(clientSide="com.lothrazar.samsprojectiles.ClientProxy", serverSide="com.lothrazar.samsprojectiles.CommonProxy")
	public static CommonProxy proxy;   
	@Instance(value = ModProj.MODID)
	public static ModProj instance;
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsProj") 
	{ 
		@Override
		public Item getTabIconItem() 
		{ 
			return ItemRegistry.spell_harvest_dummy;
		}
	};    
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
//		cfg = new ConfigSpells(new Configuration(event.getSuggestedConfigurationFile()));
		
		ItemRegistry.registerItems();
		
		FMLCommonHandler.instance().bus().register(instance); 
		MinecraftForge.EVENT_BUS.register(instance);
		
		
	}
	public static void teleportWallSafe(EntityLivingBase player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		moveEntityWallSafe(player, world);
	}
	
	public static void moveEntityWallSafe(EntityLivingBase entity, World world) 
	{
		while (!world.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox()).isEmpty())
		{
			entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D, entity.posZ);
		}
	}
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) 
	{ 
		EntitySoulstoneBolt.onLivingHurt(event);
	}
	
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		// some example code
        System.out.println("DIRT BLOCK >> "+Blocks.dirt.getUnlocalizedName());
    }
    
    @EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
		 
  		//TODO: we could make our own custom projectileRegistry, that acts as our other ones above do.
  		
  		//TODO: Entity ids are the 999,1000,1001 -> config file
        EntityRegistry.registerModEntity(EntitySoulstoneBolt.class, "soulstonebolt",999, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt",1000, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHarvestbolt.class, "harvestbolt",1001, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt",1002, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt",1003, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt",1004, instance, 64, 1, true);
		
		proxy.registerRenderers();
	}
    
    @SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		if(event.pos == null){return;}
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		ItemStack held = player.getCurrentEquippedItem();
		//Block blockClicked = event.world.getBlockState(event.pos).getBlock(); 
		//TileEntity container = event.world.getTileEntity(event.pos);

		if(held != null && held.getItem() == ItemRegistry.spell_torch_dummy && 
				event.action.RIGHT_CLICK_AIR == event.action 
			)
		{ 
			world.spawnEntityInWorld(new EntityTorchBolt(world,player));
			player.inventory.decrStackSize(player.inventory.currentItem, 1);
			player.swingItem();
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F);
		}
  	}	
    
	public static void addChatMessage(EntityPlayer player,String string) 
	{ 
		player.addChatMessage(new ChatComponentTranslation(string));
	}
	
	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos)
	{
		spawnParticle(world,type,pos.getX(),pos.getY(),pos.getZ());
    }

	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z)
	{ 
		//http://www.minecraftforge.net/forum/index.php?topic=9744.0
		for(int countparticles = 0; countparticles <= 10; ++countparticles)
		{
			world.spawnParticle(type, x + (world.rand.nextDouble() - 0.5D) * (double)0.8, y + world.rand.nextDouble() * (double)1.5 - (double)0.1, z + (world.rand.nextDouble() - 0.5D) * (double)0.8, 0.0D, 0.0D, 0.0D);
		} 
    }
	
	public static String lang(String name)
	{
		return StatCollector.translateToLocal(name);
	}
}
