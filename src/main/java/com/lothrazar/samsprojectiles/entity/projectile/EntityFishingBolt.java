package com.lothrazar.samsprojectiles.entity.projectile;
 
import com.lothrazar.samsprojectiles.ModProj;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityFishingBolt extends EntityThrowable
{
    public EntityFishingBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityFishingBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityFishingBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

	static final double plainChance = 60;
	static final double salmonChance = 25 + plainChance;//so it is between 60 and 85
	static final double clownfishChance = 2 + salmonChance;//so between 85 and 87
	
	@Override
	protected void onImpact(MovingObjectPosition mop) 
	{
		BlockPos pos = mop.getBlockPos();
        if(pos == null){return;}//hasn't happened yet, but..
       
        if(this.isInWater())
        {
        	ModProj.spawnParticle(this.worldObj, EnumParticleTypes.WATER_BUBBLE, pos);

			EntityItem ei = new EntityItem(worldObj, pos.getX(),pos.getY(),pos.getZ(), getRandomFish());
			
	 		if(worldObj.isRemote == false)//do not spawn a second 'ghost' one on client side
	 		{
	 			worldObj.spawnEntityInWorld(ei);
	 		} 
	 		
			worldObj.playSoundAtEntity(ei, "game.neutral.swim.splash", 1.0F, 1.0F);
        }
	}

	private ItemStack getRandomFish() 
	{
		ItemStack fishSpawned = null;
		//below is from MY BlockFishing.java in the unreleased ./FarmingBlocks/
		ItemStack plain =  new ItemStack(Items.fish,1,0);

		ItemStack salmon =  new ItemStack(Items.fish,1,1);

		ItemStack clownfish =  new ItemStack(Items.fish,1,2);
		 
		ItemStack pufferfish =  new ItemStack(Items.fish,1,3);
 
		double diceRoll = rand.nextDouble() * 100; 
		
		if(diceRoll < plainChance)
		{
		 fishSpawned = plain;
		}
		else if(diceRoll < salmonChance )
		{
		 fishSpawned = salmon;
		}
		else if(diceRoll < clownfishChance )
		{
		 fishSpawned = clownfish;
		}
		else
		{
		 fishSpawned = pufferfish;
		}
		return fishSpawned;
	}
}
