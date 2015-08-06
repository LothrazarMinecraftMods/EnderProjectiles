package com.lothrazar.samsprojectiles;
 
import java.util.ArrayList;

import com.lothrazar.samsprojectiles.entity.projectile.*;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityEnderEye;
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
import net.minecraftforge.common.config.Configuration;
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
			return ItemRegistry.ender_harvest;
		}
	};    
	public static int fishing_recipe;
	public static int wool_recipe;
	public static int torch_recipe;
	public static int lightning_recipe;
	public static int snow_recipe;
	public static int water_recipe;
	public static int harvest_recipe;
	public static int bed_recipe;
	public static int dungeon_recipe;
	public static int tnt_recipe;
	public static int blaze_recipe;
	
	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event)
	{ 
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		config.addCustomCategoryComment(MODID, "For each item, you can decide how many the recipe produces.  Set to zero to disable the crafting recipe.");
		
		torch_recipe = config.getInt("torch_crafted", MODID, 6, 0, 64, "");
		lightning_recipe = config.getInt("lightning_crafted", MODID, 1, 0, 64, "");
		snow_recipe = config.getInt("snow_crafted", MODID, 4, 0, 64, "");
		water_recipe = config.getInt("water_crafted", MODID, 4, 0, 64, "");
		harvest_recipe = config.getInt("harvest_crafted", MODID, 4, 0, 64, "");
		wool_recipe = config.getInt("wool_crafted", MODID, 32, 0, 64, "");
		fishing_recipe = config.getInt("fishing_recipe", MODID, 10, 0, 64, "");
		bed_recipe = config.getInt("bed_recipe", MODID, 4, 0, 64, "");
		dungeon_recipe = config.getInt("dungeon_recipe", MODID, 4, 0, 64, "");
		tnt_recipe = config.getInt("dungeon_recipe", MODID, 6, 0, 64, "");
		blaze_recipe = config.getInt("dungeon_recipe", MODID, 5, 0, 64, "");
		
		if(config.hasChanged()){config.save();}
			//TODO:fix soulstone???
 
		
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
	/*
	@SubscribeEvent
	public void onLivingHurt(LivingHurtEvent event) 
	{ 
		EntitySoulstoneBolt.onLivingHurt(event);
	}
 */
    @EventHandler
	public void onInit(FMLInitializationEvent event)
	{       
    	int entityID = 999;
       
        EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHarvestBolt.class, "harvestbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityShearingBolt.class, "woolbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityFishingBolt.class, "fishingbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityHomeBolt.class, "bedbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityDungeonEye.class, "dungeonbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityDynamite.class, "tntbolt",entityID++, instance, 64, 1, true);
        EntityRegistry.registerModEntity(EntityBlazeBolt.class, "tntbolt",entityID++, instance, 64, 1, true);
		
		proxy.registerRenderers();
	}
    
    @SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event)
  	{        
		if(event.pos == null){return;}
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		ItemStack held = player.getCurrentEquippedItem();
	
		if(held != null && event.action.RIGHT_CLICK_AIR == event.action )
		{
			boolean wasThrown = false;
			if(held.getItem() == ItemRegistry.ender_dungeon)
			{ 
                EntityDungeonEye entityendereye = new EntityDungeonEye(world, player);
 
                BlockPos blockpos = findClosestBlock(player, Blocks.mob_spawner, EntityDungeonEye.RADIUS);
   
                if (blockpos != null)
                { 
                    entityendereye.moveTowards(blockpos);
                      
                    world.spawnEntityInWorld(entityendereye); 
                    
    				wasThrown = true;
                }
                else //not found, so play sounds to alert player
                {
                	//could spawn particle here if we either A) senta custom packet or B) spawned the entity and have it die right away with a custom flag
                	world.playSoundAtEntity(player, "item.fireCharge.use", 1,1);

                } 
			}
			if(held.getItem() == ItemRegistry.ender_tnt_1)
			{ 
				world.spawnEntityInWorld(new EntityDynamite(world,player,1));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_tnt_2)
			{ 
				world.spawnEntityInWorld(new EntityDynamite(world,player,2));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_tnt_4)
			{ 
				world.spawnEntityInWorld(new EntityDynamite(world,player,4));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_tnt_6)
			{ 
				world.spawnEntityInWorld(new EntityDynamite(world,player,6));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_blaze)
			{ 
				world.spawnEntityInWorld(new EntityBlazeBolt(world,player));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_bed)
			{ 
				world.spawnEntityInWorld(new EntityHomeBolt(world,player));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_torch)
			{ 
				world.spawnEntityInWorld(new EntityTorchBolt(world,player));
				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_wool)
			{ 
				world.spawnEntityInWorld(new EntityShearingBolt(world,player));
				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_fishing)
			{ 
				world.spawnEntityInWorld(new EntityFishingBolt(world,player));
				wasThrown = true;
			}
			/*
			else if(held.getItem() == ItemRegistry.soulstone)
			{
				world.spawnEntityInWorld(new EntitySoulstoneBolt(world,player));
				wasThrown = true;
			}*/
			else if(held.getItem() == ItemRegistry.ender_snow)
			{
				world.spawnEntityInWorld(new EntitySnowballBolt(world,player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_water)
			{
				world.spawnEntityInWorld(new EntityWaterBolt(world,player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_harvest)
			{
				world.spawnEntityInWorld(new EntityHarvestBolt(world,player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_lightning)
			{
				world.spawnEntityInWorld(new EntityLightningballBolt(world,player));
				wasThrown = true;
			}
			
			if(wasThrown)
			{
				player.swingItem();
				world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F);
				if(player.capabilities.isCreativeMode == false)
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
			}
		}
  	}	
    public static BlockPos findClosestBlock(EntityPlayer player, Block blockHunt, int RADIUS ) 
	{        
    	//imported from MY CommandSearchSpawner in ./Commands/
    	BlockPos found = null;
		int xMin = (int) player.posX - RADIUS;
		int xMax = (int) player.posX + RADIUS;

		int yMin = (int) player.posY - RADIUS;
		int yMax = (int) player.posY + RADIUS;

		int zMin = (int) player.posZ - RADIUS;
		int zMax = (int) player.posZ + RADIUS;
		 
		int distance = 0, distanceClosest = RADIUS * RADIUS;
		
		BlockPos posCurrent = null; 
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{
			for (int yLoop = yMin; yLoop <= yMax; yLoop++)
			{
				for (int zLoop = zMin; zLoop <= zMax; zLoop++)
				{  
					posCurrent = new BlockPos(xLoop, yLoop, zLoop);
					
					if(player.worldObj.getBlockState(posCurrent).getBlock().equals(blockHunt))
					{  
						//  find closest?
						
						if(found == null){ found = posCurrent;}
						else
						{
							distance = (int) distanceBetweenHorizontal(player.getPosition(), posCurrent);
							
							if(distance < distanceClosest)
							{
								found = posCurrent;
								
								distanceClosest = distance;
							}
						}
					} 
				}
			}
		}
		
		return found; 
	}
	public static double distanceBetweenHorizontal(BlockPos start, BlockPos end)
	{
		int xDistance =  Math.abs(start.getX() - end.getX() );
		int zDistance =  Math.abs(start.getZ() - end.getZ() );
		//ye olde pythagoras
		return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
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
