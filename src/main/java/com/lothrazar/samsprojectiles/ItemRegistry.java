package com.lothrazar.samsprojectiles;

import java.util.ArrayList;  
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();
    
	public static Item soulstone;
	public static Item spell_water_dummy;
	public static Item spell_frostbolt_dummy;
	public static Item spell_harvest_dummy;
	public static Item spell_lightning_dummy;
	public static Item ender_torch;
  
	public static void registerItems()
	{   
		soulstone = new Item();
		ItemRegistry.registerItem(soulstone, "soulstone");
 
		ender_torch = new Item();
		ItemRegistry.registerItem(ender_torch, "ender_torch");
		ender_torch.setCreativeTab(ModProj.tabSamsContent);
		spell_water_dummy = new Item();
		ItemRegistry.registerItem(spell_water_dummy, "spell_water_dummy"); 
 
		spell_frostbolt_dummy = new Item();
		ItemRegistry.registerItem(spell_frostbolt_dummy, "spell_frostbolt_dummy");
 
		spell_harvest_dummy = new Item();
		ItemRegistry.registerItem(spell_harvest_dummy, "spell_harvest_dummy");
		spell_lightning_dummy = new Item();
		ItemRegistry.registerItem(spell_lightning_dummy, "spell_lightning_dummy");  
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 
		 GameRegistry.registerItem(item, name);
		 
		 items.add(item);
	}
}
