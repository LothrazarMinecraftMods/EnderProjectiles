package com.lothrazar.samsprojectiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import com.lothrazar.samsprojectiles.entity.projectile.EntityBlazeBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityDungeonEye;
import com.lothrazar.samsprojectiles.entity.projectile.EntityDynamite;
import com.lothrazar.samsprojectiles.entity.projectile.EntityFishingBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityHarvestBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityHomeBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityLightningballBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityShearingBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntitySnowballBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityTorchBolt;
import com.lothrazar.samsprojectiles.entity.projectile.EntityWaterBolt;


public class ModEvents{

	@SubscribeEvent
	public void onPlayerInteract(PlayerInteractEvent event){

		System.out.println("interact");
		World world = event.world;
		EntityPlayer player = event.entityPlayer;
		BlockPos pos = event.pos;
		if(pos == null){
			pos = player.getPosition();
		}
	
		ItemStack held = player.getHeldItemMainhand();// player.getCurrentEquippedItem();
		
		System.out.println("interact");

		if(held != null && Action.RIGHT_CLICK_AIR == event.action){

			System.out.println("RIGHT_CLICK_AIR");
			boolean wasThrown = false;

			if(held.getItem() == ItemRegistry.ender_dungeon){

				EntityDungeonEye entityendereye = new EntityDungeonEye(world, player);

				BlockPos blockpos = ModProj.findClosestBlock(player, Blocks.mob_spawner, EntityDungeonEye.RADIUS);

				if(blockpos != null){
					entityendereye.moveTowards(blockpos);

					world.spawnEntityInWorld(entityendereye);

					wasThrown = true;
				}
				else // not found, so play sounds to alert player
				{
					// could spawn particle here if we either A) senta custom packet or B) spawned
					// the entity and have it die right away with a custom flag

					world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.item_firecharge_use, SoundCategory.PLAYERS, 1.0F, 1.0F, false);

				}
			}
			if(held.getItem() == ItemRegistry.ender_tnt_1){
				System.out.println("ender_tnt_1");
				world.spawnEntityInWorld(new EntityDynamite(world, player, 1));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_tnt_2){
				world.spawnEntityInWorld(new EntityDynamite(world, player, 2));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_tnt_4){
				world.spawnEntityInWorld(new EntityDynamite(world, player, 4));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_tnt_6){
				world.spawnEntityInWorld(new EntityDynamite(world, player, 6));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_blaze){
				world.spawnEntityInWorld(new EntityBlazeBolt(world, player));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_bed){
				world.spawnEntityInWorld(new EntityHomeBolt(world, player));

				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_torch){
				world.spawnEntityInWorld(new EntityTorchBolt(world, player));
				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_wool){
				world.spawnEntityInWorld(new EntityShearingBolt(world, player));
				wasThrown = true;
			}
			if(held.getItem() == ItemRegistry.ender_fishing){
				world.spawnEntityInWorld(new EntityFishingBolt(world, player));
				wasThrown = true;
			}
			/*
			 * else if(held.getItem() == ItemRegistry.soulstone) { world.spawnEntityInWorld(new
			 * EntitySoulstoneBolt(world,player)); wasThrown = true; }
			 */
			else if(held.getItem() == ItemRegistry.ender_snow){
				world.spawnEntityInWorld(new EntitySnowballBolt(world, player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_water){
				world.spawnEntityInWorld(new EntityWaterBolt(world, player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_harvest){
				world.spawnEntityInWorld(new EntityHarvestBolt(world, player));
				wasThrown = true;
			}
			else if(held.getItem() == ItemRegistry.ender_lightning){
				world.spawnEntityInWorld(new EntityLightningballBolt(world, player));
				wasThrown = true;
			}

			if(wasThrown){
				player.swingArm(EnumHand.MAIN_HAND);

				world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.entity_egg_throw, SoundCategory.PLAYERS, 1.0F, 1.0F, false);

				// world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F);
				if(player.capabilities.isCreativeMode == false){
					player.inventory.decrStackSize(player.inventory.currentItem, 1);
				}
			}
		}
	}
}
