package com.lothrazar.samsprojectiles;

import java.util.ArrayList;  

import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry 
{ 
	public static ArrayList<Item> items = new ArrayList<Item>();
    
	//public static Item soulstone;
	public static Item ender_bed;
	public static Item ender_water;
	public static Item ender_snow;
	public static Item ender_harvest;
	public static Item ender_lightning;
	public static Item ender_torch;
	public static Item ender_wool;
	public static Item ender_fishing;
	public static Item ender_dungeon;
  
	public static void registerItems()
	{    
		ender_dungeon = new Item();
		ItemRegistry.registerItem(ender_dungeon, "ender_dungeon");
		
		if(ModProj.dungeon_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_dungeon, ModProj.dungeon_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Blocks.mossy_cobblestone)
				,new ItemStack(Items.nether_wart));//Blocks.iron_bars
		}
		
		ender_bed = new Item();
		ItemRegistry.registerItem(ender_bed, "ender_bed");
		
		if(ModProj.bed_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_bed, ModProj.bed_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Items.redstone)
				,new ItemStack(Items.bed));
		}
		
		ender_fishing = new Item();
		ItemRegistry.registerItem(ender_fishing, "ender_fishing");
		
		if(ModProj.fishing_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_fishing, ModProj.fishing_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Items.gunpowder)
				,new ItemStack(Items.string));
		}
		
		ender_wool = new Item();
		ItemRegistry.registerItem(ender_wool, "ender_wool");
		
		if(ModProj.wool_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_wool, ModProj.wool_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Blocks.wool)
				,new ItemStack(Items.shears));
		}
		/*
		soulstone = new Item();
		ItemRegistry.registerItem(soulstone, "soulstone");
		GameRegistry.addShapelessRecipe(new ItemStack(soulstone,2)
			,new ItemStack(Items.ender_pearl)
			,new ItemStack(Items.nether_wart)
			,new ItemStack(Items.ghast_tear));
 */
		ender_torch = new Item();
		ItemRegistry.registerItem(ender_torch, "ender_torch");
		if(ModProj.torch_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_torch, ModProj.torch_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Items.stick)
				,new ItemStack(Items.coal));
			GameRegistry.addShapelessRecipe(new ItemStack(ender_torch, ModProj.torch_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Items.stick)
				,new ItemStack(Items.coal,1,1));//charcoal
		}
		
		ender_water = new Item();
		ItemRegistry.registerItem(ender_water, "ender_water"); 
		if(ModProj.water_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_water, ModProj.water_recipe)
				,new ItemStack(Items.blaze_rod)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Blocks.ice));
		}
 
		ender_snow = new Item();
		ItemRegistry.registerItem(ender_snow, "ender_snow");
		if(ModProj.snow_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_snow, ModProj.snow_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Blocks.ice)
				,new ItemStack(Items.snowball));
			
		}
 
		ender_harvest = new Item();
		ItemRegistry.registerItem(ender_harvest, "ender_harvest");
		if(ModProj.harvest_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_harvest, ModProj.harvest_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Items.wheat)
				,new ItemStack(Items.wheat_seeds));
		}
		
		ender_lightning = new Item();
		ItemRegistry.registerItem(ender_lightning, "ender_lightning"); 
		if(ModProj.lightning_recipe > 0)
		{
			GameRegistry.addShapelessRecipe(new ItemStack(ender_lightning, ModProj.lightning_recipe)
				,new ItemStack(Items.ender_pearl)
				,new ItemStack(Items.quartz)
				,new ItemStack(Items.ghast_tear)); 
		}
	}
	
	public static void registerItem(Item item, String name)
	{ 
		 item.setUnlocalizedName(name);
		 item.setCreativeTab(ModProj.tabSamsContent);
		 
		 GameRegistry.registerItem(item, name);

		 items.add(item);
	}
}
