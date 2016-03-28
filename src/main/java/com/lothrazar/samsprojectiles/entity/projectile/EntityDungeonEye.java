package com.lothrazar.samsprojectiles.entity.projectile;

import com.lothrazar.samsprojectiles.ModProj;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityDungeonEye extends EntityThrowable{

	private double targetX;
	private double targetY;
	private double targetZ;

	private BlockPos startPos;
	private BlockPos targetFound = null;
	private long timeLastSearched = 0;
	private boolean targetMovedTowards = false;
	private final static long timeSpan = 20;
	
	public EntityDungeonEye(World worldIn){

		super(worldIn);
	}

	public EntityDungeonEye(World worldIn, EntityLivingBase ent){

		super(worldIn, ent);
		startPos = ent.getPosition();
	}

	public EntityDungeonEye(World worldIn, double x, double y, double z){

		super(worldIn, x, y, z);
		startPos = new BlockPos(x,y,z);
	}
    public void moveTowards()
    {
    	//from EntityEnderEye
        double d0 = (double)targetFound.getX();
        int i = targetFound.getY();
        double d1 = (double)targetFound.getZ();
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

		this.targetMovedTowards = true;
    }
/*
	private void moveTowards(BlockPos pos){

		this.targetX = (double) pos.getX();
		this.targetY = pos.getY();
		this.targetZ = (double) pos.getZ();
		// double dx = this.targetX - this.posX;
		// // double dz = this.targetZ - this.posZ;
		// float dist = MathHelper.sqrt_double(dx * dx + dz * dz);

		EntityEnderEye x;
		this.setThrowableHeading(this.targetX, this.targetZ, this.targetY, (float) (this.getGravityVelocity()), 0.01F);
		
		this.targetMovedTowards = true;
	}
	*/

	public static int H_RADIUS = 32;// TODO: config file for these? yes no?
	public static int V_RADIUS = 32;// TODO: config file for these? yes no?
	//if no config then add information?
	@Override
	public void onUpdate(){

		super.onUpdate();
		
		if(this.startPos == null){
			startPos = this.getPosition();
		}
		
		if(targetMovedTowards == false){
			
		
			if(targetFound == null && this.worldObj.isRemote == false){
				trySearch();
			}
			//else target is already aquired, keep going
	
			if(targetFound != null){
				System.out.println("!!moveTowards "+targetFound.toString());
				this.moveTowards();
			}
		}
		

		if(!this.worldObj.isRemote){
			float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);

			double d0 = this.targetX - this.posX;
			double d1 = this.targetZ - this.posZ;
			float f1 = (float) Math.sqrt(d0 * d0 + d1 * d1);
			float f2 = (float) Math.atan2(d1, d0);
			double d2 = (double) f + (double) (f1 - f) * 0.0025D;

			if(f1 < 1.0F){
				d2 *= 0.8D;
				this.motionY *= 0.8D;
			}

			this.motionX = Math.cos((double) f2) * d2;
			this.motionZ = Math.sin((double) f2) * d2;

			if(this.posY < this.targetY){
				this.motionY += (1.0D - this.motionY) * 0.014999999664723873D;
			}
			else{
				this.motionY += (-1.0D - this.motionY) * 0.014999999664723873D;
			}
		}
		
		float r = 0.25F;
		//change color if/when found
		EnumParticleTypes spawn = (targetFound == null) ? EnumParticleTypes.SMOKE_NORMAL : EnumParticleTypes.PORTAL;
		for(int i = 0; i < particleCount; ++i){
			this.worldObj.spawnParticle(spawn, this.posX - this.motionX * (double) r + this.rand.nextDouble() * 0.6D - 0.3D, this.posY - this.motionY * (double) r - 0.5D, this.posZ - this.motionZ * (double) r + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX, this.motionY, this.motionZ, new int[0]);
		}
		

		//super.onUpdate();
	}

	private void trySearch(){
		//possibly run a search (which is expensive) 

		boolean doSearch = false;
		if(timeLastSearched == 0){
			doSearch = true;
		}
		else{
			doSearch = (this.worldObj.getTotalWorldTime() - timeLastSearched > timeSpan);
		}
		
		if(doSearch){
			timeLastSearched = this.worldObj.getTotalWorldTime();
			
			System.out.println("DoSearch  " +timeLastSearched);

			//TODO: ignore stuff behind the player..?? somehow?
			targetFound = ModProj.findClosestBlock(this.worldObj,this.startPos, Blocks.mob_spawner, H_RADIUS,V_RADIUS);

		}
	}
	
	private final static int particleCount = 50;
	
	@Override
	protected void onImpact(RayTraceResult mop){

		this.setDead();// does not pass through walls or entities
	}

}
