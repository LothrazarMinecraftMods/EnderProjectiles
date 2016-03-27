package com.lothrazar.samsprojectiles;

import com.lothrazar.samsprojectiles.entity.projectile.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

@Mod(modid = ModProj.MODID, useMetadata = true, updateJSON = "https://raw.githubusercontent.com/LothrazarMinecraftMods/EnderProjectiles/master/update.json")
public class ModProj{

	public static final String MODID = "samsprojectiles";
	public static final String TEXTURE_LOCATION = MODID + ":";
	@SidedProxy(clientSide = "com.lothrazar.samsprojectiles.ClientProxy", serverSide = "com.lothrazar.samsprojectiles.CommonProxy")
	public static CommonProxy proxy;
	@Instance(value = ModProj.MODID)
	public static ModProj instance;
	public static CreativeTabs tabSamsContent = new CreativeTabs("tabSamsProj") {

		@Override
		public Item getTabIconItem(){

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
	Configuration config;

	@EventHandler
	public void onPreInit(FMLPreInitializationEvent event){

		config = new Configuration(event.getSuggestedConfigurationFile());

		loadConfig();

		ItemRegistry.registerItems();

		// MinecraftForge.EVENT_BUS.register(new ModEvents());
	}

	private void loadConfig(){

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
		tnt_recipe = config.getInt("tnt_recipe", MODID, 6, 0, 64, "");
		blaze_recipe = config.getInt("blaze_recipe", MODID, 3, 0, 64, "");

		EntityShearingBolt.doesKnockback = config.getBoolean("wool.does_knockback", MODID, true, "Does appear to damage sheep on contact");
		EntityShearingBolt.doesShearChild = config.getBoolean("wool.does_child", MODID, true, "Does shear child sheep as well.");

		EntityBlazeBolt.fireSeconds = config.getInt("blaze.fire_seconds", MODID, 3, 0, 64, "Seconds of fire to put on entity when hit");
		EntityBlazeBolt.damageEntityOnHit = config.getBoolean("blaze.does_knockback", MODID, true, "Does it damage entity or not on hit (0 damage to blaze, 1 to others)");
		EntitySnowballBolt.damageEntityOnHit = config.getBoolean("snow.does_knockback", MODID, true, "Does it damage entity or not on hit (1 damage to blaze, 0 to others)");
		EntityTorchBolt.damageEntityOnHit = config.getBoolean("torch.does_knockback", MODID, true, "Does it damage entity or not on hit (0 dmg like a snowball)");

		EntityHarvestBolt.range_main = config.getInt("harvest.range_main", MODID, 6, 1, 32, "Horizontal range on level of hit to harvest");
		EntityHarvestBolt.range_offset = config.getInt("harvest.range_offset", MODID, 4, 1, 32, "Horizontal range on further heights to harvest");
		EntityHarvestBolt.doesHarvestStem = config.getBoolean("harvest.does_harvest_stem", MODID, false, "Does it harvest stems (pumkin/melon)");
		EntityHarvestBolt.doesHarvestSapling = config.getBoolean("harvest.does_harvest_sapling", MODID, false, "Does it harvest sapling");
		EntityHarvestBolt.doesHarvestTallgrass = config.getBoolean("harvest.does_harvest_tallgrass", MODID, false, "Does it harvest tallgrass/doubleplants");
		EntityHarvestBolt.doesHarvestMushroom = config.getBoolean("harvest.does_harvest_mushroom", MODID, true, "Does it harvest mushrooms");
		EntityHarvestBolt.doesMelonBlocks = config.getBoolean("harvest.does_harvest_melonblock", MODID, true, "Does it harvest pumpkin block");
		EntityHarvestBolt.doesPumpkinBlocks = config.getBoolean("harvest.does_harvest_pumpkinblock", MODID, true, "Does it harvest melon block");

		if(config.hasChanged()){
			config.save();
		}
	}

	@EventHandler
	public void onInit(FMLInitializationEvent event){

		int entityID = 999;

		EntityRegistry.registerModEntity(EntityLightningballBolt.class, "lightningbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityHarvestBolt.class, "harvestbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityWaterBolt.class, "waterbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntitySnowballBolt.class, "frostbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityTorchBolt.class, "torchbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityShearingBolt.class, "woolbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityFishingBolt.class, "fishingbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityHomeBolt.class, "bedbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityDungeonEye.class, "dungeonbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityDynamite.class, "tntbolt", entityID++, instance, 64, 1, true);
		EntityRegistry.registerModEntity(EntityBlazeBolt.class, "tntbolt", entityID++, instance, 64, 1, true);

		proxy.registerRenderers();
	}

	public static BlockPos findClosestBlock(EntityPlayer player, Block blockHunt, int RADIUS){

		// imported from MY CommandSearchSpawner in ./Commands/
		BlockPos found = null;
		int xMin = (int) player.posX - RADIUS;
		int xMax = (int) player.posX + RADIUS;

		int yMin = (int) player.posY - RADIUS;
		int yMax = (int) player.posY + RADIUS;

		int zMin = (int) player.posZ - RADIUS;
		int zMax = (int) player.posZ + RADIUS;

		int distance = 0, distanceClosest = RADIUS * RADIUS;

		BlockPos posCurrent = null;
		for(int xLoop = xMin; xLoop <= xMax; xLoop++){
			for(int yLoop = yMin; yLoop <= yMax; yLoop++){
				for(int zLoop = zMin; zLoop <= zMax; zLoop++){
					posCurrent = new BlockPos(xLoop, yLoop, zLoop);

					if(player.worldObj.getBlockState(posCurrent).getBlock().equals(blockHunt)){
						// find closest?

						if(found == null){
							found = posCurrent;
						}
						else{
							distance = (int) distanceBetweenHorizontal(player.getPosition(), posCurrent);

							if(distance < distanceClosest){
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

	public static double distanceBetweenHorizontal(BlockPos start, BlockPos end){

		int xDistance = Math.abs(start.getX() - end.getX());
		int zDistance = Math.abs(start.getZ() - end.getZ());
		// ye olde pythagoras
		return Math.sqrt(xDistance * xDistance + zDistance * zDistance);
	}

	public static void addChatMessage(EntityPlayer player, String string){

		player.addChatMessage(new TextComponentTranslation(string));
	}

	public static void spawnParticle(World world, EnumParticleTypes type, BlockPos pos){

		spawnParticle(world, type, pos.getX(), pos.getY(), pos.getZ());
	}

	public static void spawnParticle(World world, EnumParticleTypes type, double x, double y, double z){

		// http://www.minecraftforge.net/forum/index.php?topic=9744.0
		for(int countparticles = 0; countparticles <= 10; ++countparticles){
			world.spawnParticle(type, x + (world.rand.nextDouble() - 0.5D) * (double) 0.8, y + world.rand.nextDouble() * (double) 1.5 - (double) 0.1, z + (world.rand.nextDouble() - 0.5D) * (double) 0.8, 0.0D, 0.0D, 0.0D);
		}
	}

	public static String lang(String name){

		return I18n.translateToLocal(name);
	}
}
