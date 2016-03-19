package com.lothrazar.samsprojectiles;

import com.lothrazar.samsprojectiles.entity.projectile.*;
import net.minecraft.item.Item;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
//import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
//import net.minecraft.client.resources.model.ModelResourceLocation;

public class ClientProxy extends CommonProxy 
{   
 
    @Override
    public void registerRenderers() 
    {   
        registerModels(); 
        
        registerEntities();
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes", "deprecation" })
	private void registerEntities()
    {
    	RenderManager rm = Minecraft.getMinecraft().getRenderManager();
    	RenderItem ri = Minecraft.getMinecraft().getRenderItem();
    	
    	//works similar to vanilla which is like
    	//Minecraft.getMinecraft().getRenderManager().entityRenderMap.put(EntitySoulstoneBolt.class, new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ItemRegistry.soulstone, Minecraft.getMinecraft().getRenderItem()));

    	RenderingRegistry.registerEntityRenderingHandler(EntityLightningballBolt.class, new RenderSnowball(rm, ItemRegistry.ender_lightning, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityHarvestBolt.class, new RenderSnowball(rm, ItemRegistry.ender_harvest, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWaterBolt.class, new RenderSnowball(rm, ItemRegistry.ender_water, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntitySnowballBolt.class, new RenderSnowball(rm, ItemRegistry.ender_snow, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityTorchBolt.class, new RenderSnowball(rm, ItemRegistry.ender_torch, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityFishingBolt.class, new RenderSnowball(rm, ItemRegistry.ender_fishing, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityShearingBolt.class, new RenderSnowball(rm, ItemRegistry.ender_wool, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityHomeBolt.class, new RenderSnowball(rm, ItemRegistry.ender_bed, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityDungeonEye.class, new RenderSnowball(rm, ItemRegistry.ender_dungeon, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityDynamite.class, new RenderSnowball(rm, ItemRegistry.ender_tnt_1, ri));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBlazeBolt.class, new RenderSnowball(rm, ItemRegistry.ender_blaze, ri));

    	
    }

	private void registerModels() 
	{
		//More info on proxy rendering
        //http://www.minecraftforge.net/forum/index.php?topic=27684.0
       //http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/2272349-lessons-from-my-first-mc-1-8-mod
   
        ItemModelMesher mesher = Minecraft.getMinecraft().getRenderItem().getItemModelMesher();

        String name;
 
        for(Item i : ItemRegistry.items)
        {  
        	name = ModProj.TEXTURE_LOCATION + i.getUnlocalizedName().replaceAll("item.", "");

   			mesher.register(i, 0, new ModelResourceLocation( name , "inventory"));	 
        }
        
      /*if(ModMain.cfg.respawn_egg)
       {
        	for(Object key : EntityList.entityEggs.keySet())
            {
            	mesher.register(ItemRegistry.respawn_egg, (Integer)key, new ModelResourceLocation(ModSpells.TEXTURE_LOCATION + "respawn_egg" , "inventory"));	 
            }
        }*/
	}
 
 
}
