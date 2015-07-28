package com.lothrazar.samsprojectiles.entity.projectile;

import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.world.World;

public class EntityDungeonEye  extends EntityEnderEye//EntityThrowable
{
    public EntityDungeonEye(World worldIn)
    {
        super(worldIn);
    }

	public static final int MAXRADIUS = 128;// TODO: config file for these? yes no?
	public static final int DEFAULTRADIUS = 64;
 

    public EntityDungeonEye(World worldIn, double x, double y, double z)
    {
        super(worldIn, x, y, z);
    }
    
    //TODO 1: overrode onUpdate, and others, so that we can make it not drop ender eyes, customize despawn timer, shatter chance
    //TODO 2: make render with my own texture 
 
}
