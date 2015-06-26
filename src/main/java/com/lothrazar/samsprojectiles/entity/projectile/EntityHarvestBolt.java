package com.lothrazar.samsprojectiles.entity.projectile; 
 
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World; 

public class EntityHarvestBolt extends EntityThrowable//EntitySnowball
{ 
    public EntityHarvestBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityHarvestBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityHarvestBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
    	if(this.getThrower() instanceof EntityPlayer && mop.sideHit != null)
    	{
        	BlockPos offset = mop.getBlockPos().offset(mop.sideHit);
        	
    		//it harvests a horizontal slice each time
        	harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), mop.getBlockPos(),4);
        	harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), offset,6);
        	harvestArea(this.worldObj, (EntityPlayer)this.getThrower(), offset.up(),4);
    	}
		 
        this.setDead();
 
    }  
    public static int harvestArea(World world, EntityPlayer player, BlockPos pos, int radius)
	{
		int x = (int)player.posX;
		//int y = (int)player.posY;
		int z = (int)player.posZ;
		
		//search in a cube
		int xMin = x - radius;
		int xMax = x + radius; 
		int zMin = z - radius;
		int zMax = z + radius;
		
		int eventy = pos.getY();
		
		BlockPos posCurrent;
		
		int countHarvested = 0;
		
		for (int xLoop = xMin; xLoop <= xMax; xLoop++)
		{ 
			for (int zLoop = zMin; zLoop <= zMax; zLoop++)
			{
				posCurrent = new BlockPos(xLoop, eventy, zLoop);
				IBlockState bs = world.getBlockState(posCurrent);
				Block blockCheck = bs.getBlock(); 

				if(blockCheck instanceof IGrowable)
				{ 
					IGrowable plant = (IGrowable) blockCheck;

					if(plant.canGrow(world, posCurrent, bs, world.isRemote) == false)
					{  
						if(world.isRemote == false)  //only drop items in serverside
							world.destroyBlock(posCurrent, true);
						//break fully grown, plant new seed
						world.setBlockState(posCurrent, blockCheck.getDefaultState());//this plants a seed. it is not 'hay_block'
					
						countHarvested++;
					} 
				} 
			}  
		} //end of the outer loop
		return countHarvested;
	}

}