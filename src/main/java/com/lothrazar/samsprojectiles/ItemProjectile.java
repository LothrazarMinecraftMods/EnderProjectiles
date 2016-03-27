package com.lothrazar.samsprojectiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemProjectile extends Item{

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand){

		ModEvents.onItemThrow(itemStackIn, worldIn, playerIn, hand);

		return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
	}
}
