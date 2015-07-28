package com.lothrazar.samsprojectiles.entity.projectile;
 
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityHomeBolt  extends EntityThrowable
{
    public EntityHomeBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityHomeBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityHomeBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

	@Override
	protected void onImpact(MovingObjectPosition mop) 
	{
		 if(this.getThrower() != null && this.getThrower() instanceof EntityPlayer && this.getThrower().dimension == 0)
    	 {
			 EntityPlayer player = (EntityPlayer)this.getThrower();
			 
			 BlockPos realBedPos = getBedLocationSafe(worldObj, player);
			 
			 if(realBedPos == null)
			 {
				 realBedPos =  worldObj.getSpawnPoint();
			 }

			 teleportWallSafe(player, worldObj, realBedPos); 
			 worldObj.playSoundAtEntity(player, "mob.endermen.portal", 1.0F, 1.0F);
			
			 this.setDead();
    	 }
	}
	
	public static BlockPos getBedLocationSafe(World world, EntityPlayer player) 
	{
		 BlockPos realBedPos = null;
		
		 BlockPos coords = player.getBedLocation(0);
		  
		 if(coords != null)
		 { 
			 Block block = world.getBlockState(coords).getBlock();
			 
			 if (block.equals(Blocks.bed) || block.isBed(world, coords, player))
			 {
				 //then move over according to how/where the bed wants me to spawn
				 realBedPos = block.getBedSpawnPosition(world, coords, player); 
			 }
		 }
		 
		 return realBedPos;
	}
	public static void teleportWallSafe(EntityLivingBase player, World world, BlockPos coords)
	{
		player.setPositionAndUpdate(coords.getX(), coords.getY(), coords.getZ()); 

		moveEntityWallSafe(player, world);
	}
	public static void moveEntityWallSafe(EntityLivingBase entity, World world) 
	{
		while (entity.getEntityBoundingBox() != null && //gm 3 must have a null box because of the ghost
				!world.getCollidingBoundingBoxes(entity, entity.getEntityBoundingBox()).isEmpty())
		{
			entity.setPositionAndUpdate(entity.posX, entity.posY + 1.0D, entity.posZ);
		}
	}
	
}
