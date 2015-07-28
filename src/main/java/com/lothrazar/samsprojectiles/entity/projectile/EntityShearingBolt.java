package com.lothrazar.samsprojectiles.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityShearingBolt extends EntityThrowable
{
    public EntityShearingBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntityShearingBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntityShearingBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }

	@Override
	protected void onImpact(MovingObjectPosition mop) 
	{
		if (mop.entityHit != null && mop.entityHit instanceof EntitySheep)
        {
			EntitySheep sheep = (EntitySheep)mop.entityHit;

			//imported from MY  unreleased/abandoned BlockShearWool.java in ./FarmingBlocks/
			if(sheep.getSheared() == false && sheep.worldObj.isRemote == false)
			{ 
				//this part is the same as how EntitySheep goes, use a random number
				sheep.setSheared(true);
                int i = 1 + sheep.worldObj.rand.nextInt(3);

                for (int j = 0; j < i; ++j)
                {
                    EntityItem entityitem = sheep.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, sheep.getFleeceColor().getMetadata()), 1.0F);
                    entityitem.motionY += (double)(sheep.worldObj.rand.nextFloat() * 0.05F);
                    entityitem.motionX += (double)((sheep.worldObj.rand.nextFloat() - sheep.worldObj.rand.nextFloat()) * 0.1F);
                    entityitem.motionZ += (double)((sheep.worldObj.rand.nextFloat() - sheep.worldObj.rand.nextFloat()) * 0.1F);
                }

                sheep.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 0);
                sheep.playSound("mob.sheep.shear", 1.0F, 1.0F);
			} 
        }
	}
}
