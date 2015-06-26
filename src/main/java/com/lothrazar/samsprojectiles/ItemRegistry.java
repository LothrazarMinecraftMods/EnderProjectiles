package com.lothrazar.samsprojectiles;

import java.util.ArrayList;  
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();
    
	public static Item soulstone;
	public static Item ender_water;
	public static Item ender_snow;
	public static Item ender_harvest;
	public static Item ender_lightning;
	public static Item ender_torch;
  
	public static void registerItems()
	{   
		soulstone = new Item();
		ItemRegistry.registerItem(soulstone, "soulstone");
 
		ender_torch = new Item();
		ItemRegistry.registerItem(ender_torch, "ender_torch");
		
		ender_water = new Item();
		ItemRegistry.registerItem(ender_water, "ender_water"); 
 
		ender_snow = new Item();
		ItemRegistry.registerItem(ender_snow, "ender_snow");
 
		ender_harvest = new Item();
		ItemRegistry.registerItem(ender_harvest, "ender_harvest");
		
		ender_lightning = new Item();
		ItemRegistry.registerItem(ender_lightning, "ender_lightning");  
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 item.setCreativeTab(ModProj.tabSamsContent);
		 
		 GameRegistry.registerItem(item, name);

		 items.add(item);
	}
}
