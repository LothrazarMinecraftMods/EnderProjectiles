package com.lothrazar.samsprojectiles.entity.projectile; 

import com.lothrazar.samsprojectiles.ModProj;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World; 
import net.minecraftforge.event.entity.living.LivingHurtEvent;

public class EntitySoulstoneBolt extends EntityThrowable//EntitySnowball
{ 
	public static int secondsFrozenOnHit;
	public static int damageToNormal = 0;//TODO CONFIG
 
    public EntitySoulstoneBolt(World worldIn)
    {
        super(worldIn);
    }

    public EntitySoulstoneBolt(World worldIn, EntityLivingBase ent)
    {
        super(worldIn, ent);
    }

    public EntitySoulstoneBolt(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
    private static final String KEY_STONED = "soulstone";
	private static final int VALUE_SINGLEUSE = -1;
	private static final int VALUE_PERSIST = 1;
	private static final int VALUE_EMPTY = 0;
	public static void addEntitySoulstone(EntityLivingBase e) 
	{  
		//getInteger by default returns zero if no value exists
		if(e.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{ 
			return;//for single use, only apply if existing is empty (do not overwrite persist)
		}
 
		//(item == ItemRegistry.soulstone_persist) ? 
		int newValue = VALUE_PERSIST;// : VALUE_SINGLEUSE;
		 
		e.getEntityData().setInteger(KEY_STONED, newValue);
	} 
    @Override
    protected void onImpact(MovingObjectPosition mop)
    {
        if (mop.entityHit != null)
        {
            float damage = damageToNormal;
/*
            if (mop.entityHit instanceof EntityBlaze)
            {
               // damage = damageToBlaze;//TODO: config file blaze damage
            }
            */
            //do the snowball damage, which should be none. put out the fire
            mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), damage);
            
            if(mop.entityHit instanceof EntityLivingBase)
            {
            	EntityLivingBase e = (EntityLivingBase)mop.entityHit;

            	addEntitySoulstone(e);
            	
            	 if(this.getThrower() instanceof EntityPlayer)
            	 {
            		 ModProj.addChatMessage((EntityPlayer)this.getThrower(), ModProj.lang("spell.soulstone.complete") + e.getDisplayName().getFormattedText());
            	 }
            } 
        }
         
        this.setDead();
 
    }  
    

	public static void onLivingHurt(LivingHurtEvent event) 
	{
		//called from ModMain event handler
		//thanks for the help:
		//http://www.minecraftforge.net/forum/index.php?topic=7475.0
		
		if(event.entityLiving.getEntityData().getInteger(KEY_STONED) != VALUE_EMPTY)
		{  
			float amount = event.ammount;//yes there is a typo in the word 'amount' but it is not in my code  
			
			if(event.entityLiving.getHealth() - amount <= 0)
			{ 
				event.entityLiving.heal(40);
				
				//event.setCanceled(true);//this is possible but not needed
				
				ModProj.teleportWallSafe(event.entityLiving, event.entity.worldObj,  event.entity.worldObj.getSpawnPoint());

				//boolean isPersist = event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_PERSIST;
				 
				if(event.entityLiving.getEntityData().getInteger(KEY_STONED) == VALUE_SINGLEUSE)
				{
					//if it does not persist, then consume this use
					event.entityLiving.getEntityData().setInteger(KEY_STONED, VALUE_EMPTY);
				} 
			}
		} 
	} 
	
}