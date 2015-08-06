package com.lothrazar.samsprojectiles.entity.projectile; 

import net.minecraft.entity.EntityLivingBase; 
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityDynamite extends EntityThrowable
{ 
	private float explosionLevel=1.0F;//creeper is 1=regular, 2=charged. 4=tnt, 6=endercrystal
    public EntityDynamite(World worldIn)
    {
        super(worldIn); 
    }
    public EntityDynamite(World worldIn, int explos)
    {
        super(worldIn); 
        this.explosionLevel = explos;
    }

    public EntityDynamite(World worldIn, EntityLivingBase ent, int explos)
    {
        super(worldIn, ent);
        this.explosionLevel = explos;
    }

    public EntityDynamite(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
 
    @Override
    protected void onImpact(MovingObjectPosition mop)
    { 
        this.worldObj.createExplosion(this, this.posX, this.posY + (double)(this.height / 2.0F), this.posZ, explosionLevel, true);
        this.setDead(); 
    }
}