package com.lothrazar.samsprojectiles.entity.projectile;

import com.lothrazar.samsprojectiles.ItemRegistry;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDungeonEye  extends EntityThrowable// EntityEnderEye  implements IProjectile//
{ 
    private double targetX;
    private double targetY;
    private double targetZ;
    
	public EntityDungeonEye(World worldIn)
    {
        super(worldIn);
    }
    public EntityDungeonEye(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }
    public EntityDungeonEye(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
 
    public void moveTowards(BlockPos pos)
    {
        System.out.println("moveTowards ? "+pos.getX()+" "+pos.getY()+" "+pos.getZ());
        double d0 = (double)pos.getX();
        int i = pos.getY();
        double d1 = (double)pos.getZ();
        double d2 = d0 - this.posX;
        double d3 = d1 - this.posZ;
        float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);

        if (f > 12.0F)
        {
            this.targetX = this.posX + d2 / (double)f * 12.0D;
            this.targetZ = this.posZ + d3 / (double)f * 12.0D;
            this.targetY = this.posY + 8.0D;
        }
        else
        {
            this.targetX = d0;
            this.targetY = (double)i;
            this.targetZ = d1;
        }

       // this.despawnTimer = 0;
       // this.shatterOrDrop = this.rand.nextInt(5) > 0;
        

        //this.setThrowableHeading(this.targetX, this.targetZ, this.targetY, (float)(this.getVelocity()+0.1F), 1.0F);
    } 
   
	public static final int RADIUS = 64;// TODO: config file for these? yes no?
 
    //TODO 1: overrode onUpdate, and others, so that we can make it not drop ender eyes, customize despawn timer, shatter chance
    //TODO 2: make render with my own texture 
 
	@Override
	protected void onImpact(MovingObjectPosition p_70184_1_) {
		// TODO Auto-generated method stub
		
		System.out.println("impact");
		this.setDead();
	}
 
}
