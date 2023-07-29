package ru.d78boga.mahabre.recipes;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.d78boga.mahabre.inits.MBlocks;

public class RecyclerRecipes {
	public static RecyclerRecipes INSTANCE = new RecyclerRecipes();
	public Map<ItemStack, ItemStack[][]> recyclingList = Maps.<ItemStack, ItemStack[][]>newHashMap();
	private Random rand;

	private RecyclerRecipes() {
		rand = new Random();
		addRecyclingRecipe(MBlocks.CONSTRUCTION_DEBRIS,
			collect(cast(Blocks.STONEBRICK, 3)),
			collect(cast(Blocks.STONE, 3)),
			collect(cast(Blocks.GRAVEL, 2)));
		addRecyclingRecipe(MBlocks.GARBAGE_LAYING,
		    collect(cast(Items.PAPER, 3)), 
			collect(cast(Items.LEATHER, 2)),
			collect(cast(Items.LEAD, 3)));
		addRecyclingRecipe(MBlocks.RESOURCE_LAYING,
			collect(cast(Items.DIAMOND, 1)), 
			collect(cast(Items.EMERALD, 1)),																																																																																																			
			collect(cast(Items.IRON_INGOT, 1)),		
			collect(cast(Items.IRON_INGOT, 2)), 
			collect(cast(Items.GOLD_INGOT, 1)),	
			collect(cast(Items.GOLD_INGOT, 2)),	
			collect(cast(Items.REDSTONE, 2)), 
			collect(cast(Items.REDSTONE, 3)),	
			collect(cast(Items.COAL, 4)), 
			collect(cast(Items.COAL, 5)), 
			collect(new ItemStack(Items.DYE, 2, EnumDyeColor.BLUE.getDyeDamage())), 
			collect(new ItemStack(Items.DYE, 3, EnumDyeColor.BLUE.getDyeDamage())));
	}

	private ItemStack[] collect(ItemStack... stacks) {
		return stacks;
		
	}

	public void addRecyclingRecipe(Block block, ItemStack[]... recipes) {
		addRecyclingRecipe(cast(block, 1), recipes);
	}

	public void addRecyclingRecipe(Item item, ItemStack[]... recipes) {
		recyclingList.put(cast(item, 1), recipes);
	}

	public void addRecyclingRecipe(ItemStack stack, ItemStack[]... recipes) {
		recyclingList.put(stack, recipes);
	}

	private ItemStack cast(Block block, int amount) {
		return cast(Item.getItemFromBlock(block), amount);
	}

	private ItemStack cast(Item item, int amount) {
		return new ItemStack(item, amount);
	}

	public ItemStack[] getRecyclingResultRandom(ItemStack stack) {
		int i = 0;
		for (Entry<ItemStack, ItemStack[][]> entrySet : recyclingList.entrySet()) {
			if (compareItemStacks(stack, entrySet.getKey())) {
				ItemStack[][] recipes = entrySet.getValue();
				i = rand.nextInt(recipes.length);
				return recipes[i];
			}	
		}

		return new ItemStack[] {ItemStack.EMPTY };
	}

	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem();
	}
}
