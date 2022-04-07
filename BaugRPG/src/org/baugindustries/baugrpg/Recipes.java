package org.baugindustries.baugrpg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;

public enum Recipes {
	
	
	CORDOVAN_LEATHER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
				new ItemStack(Material.STRING),
				new ItemStack(Material.RABBIT_HIDE),
				new ItemStack(Material.STRING),
				new ItemStack(Material.LEATHER),
				new ItemStack(Material.STRING),
				new ItemStack(Material.LEATHER),
				new ItemStack(Material.STRING),
				new ItemStack(Material.RABBIT_HIDE),
				new ItemStack(Material.STRING)
					)),
			false,
			"getCordovanLeatherItem"
			),
	
	IRON_PLATE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE),
					null,
					new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
					)), 
			false, 
			"getIronPlateItem"
			),
	
	MERINO_WOOL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.STRING),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.STRING),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.BONE_MEAL),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.STRING),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.STRING)
					)), 
			false, 
			"getMerinoWoolItem"
			),
			
	CHAFF (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.WHEAT),
					new ItemStack(Material.WHEAT_SEEDS),
					new ItemStack(Material.WHEAT_SEEDS)
			)), 
			true, 
			"getChaffItem"
			),
	
	ELVEN_WEAVE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.STRING),
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.STRING),
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.STRING),
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.STRING),
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.STRING)
					)), 
			false, 
			"getElvenWeaveItem"
			),
	
	YEW_BRANCHES (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.STICK),
					new ItemStack(Material.GOLD_NUGGET),
					null,
					new ItemStack(Material.GOLD_NUGGET),
					new ItemStack(Material.OAK_LOG),
					new ItemStack(Material.GOLD_NUGGET),
					null,
					new ItemStack(Material.GOLD_NUGGET),
					new ItemStack(Material.STICK)
					)), 
			false, 
			"getYewBranchesItem"
			),
	
	DWARVEN_STEEL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.COAL),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.COAL),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.COPPER_INGOT),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.COAL),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.COAL)
					)), 
			false, 
			"getDwarvenSteelItem"
			),
	
	RUBY (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.DIAMOND)
					)), 
			false, 
			"getRubyItem"
			),
	
	SAPPHIRE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.BLUE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.BLUE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.BLUE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.BLUE_DYE),
					new ItemStack(Material.DIAMOND)
					)), 
			false, 
			"getSapphireItem"
			),
	
	ROYAL_AZEL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.PURPLE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.PURPLE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.PURPLE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.PURPLE_DYE),
					new ItemStack(Material.DIAMOND)
			)), 
			false, 
			"getRoyalAzelItem"
			),
	
	OPAL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.WHITE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.WHITE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.WHITE_DYE),
					new ItemStack(Material.DIAMOND),
					new ItemStack(Material.WHITE_DYE),
					new ItemStack(Material.DIAMOND)
					)), 
			false, 
			"getOpalItem"
			),
	
	HARDENED_STONE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.STONE),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.STONE),
					new ItemStack(Material.SMOOTH_STONE),
					new ItemStack(Material.STONE),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.STONE),
					new ItemStack(Material.COBBLESTONE)
					)), 
			false, 
			"getHardenedStoneItem"
			),
	
	ESSENCE_OF_VENGEANCE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.FERMENTED_SPIDER_EYE),
					new ItemStack(Material.BLAZE_POWDER),
					new ItemStack(Material.FERMENTED_SPIDER_EYE),
					new ItemStack(Material.BLAZE_POWDER),
					new ItemStack(Material.GHAST_TEAR),
					new ItemStack(Material.BLAZE_POWDER),
					new ItemStack(Material.POTION),
					new ItemStack(Material.POTION),
					new ItemStack(Material.POTION)
			)), 
			false, 
			"getEssenceOfVengeanceItem"
			),
	
	BLOOD_OF_THE_FORSAKEN (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.NETHER_WART),
					null,
					new ItemStack(Material.NETHER_WART),
					new ItemStack(Material.BEETROOT_SOUP),
					new ItemStack(Material.NETHER_WART),
					null,
					new ItemStack(Material.SWEET_BERRIES),
					null
			)), 
			false, 
			"getBloodOfTheForsakenItem"
			),
	
	SCRAP (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.NETHER_QUARTZ_ORE),
					null,
					new ItemStack(Material.NETHER_QUARTZ_ORE),
					new ItemStack(Material.RAW_COPPER),
					new ItemStack(Material.NETHER_QUARTZ_ORE),
					null,
					new ItemStack(Material.CHAIN),
					null
			)), 
			false, 
			"getScrapItem"
			),
	
	;

	RecipeTypes recipeLevel;
	ArrayList<Object> pattern;
	boolean shapeless;
	String resultMethod;
	Recipes(RecipeTypes recipeLevel, ArrayList<Object> pattern, boolean shapeless, String resultMethod) {
		this.recipeLevel = recipeLevel;
		this.pattern = pattern;
		this.shapeless = shapeless;
		this.resultMethod = resultMethod;
		for (int i = 0; i < pattern.size(); i++) {
			if (pattern.get(i) != null) {
				if (pattern.get(i) instanceof ItemStack) {
					ItemStack tempItem = (ItemStack) pattern.get(i);
					tempItem.setAmount(1);
					pattern.set(i, tempItem);
				}
			}
		}
	}
	
	public String getResultMethod() {
		return resultMethod;
	}
	
	public boolean isShapeless() {
		return shapeless;
	}
	
	public ItemStack[] getPattern(Main plugin) {
		ItemStack[] resolvedPattern = new ItemStack[pattern.size()];
		for (int i = 0; i < resolvedPattern.length; i++) {
			if (pattern.get(i) instanceof ItemStack) {
				resolvedPattern[i] = (ItemStack) pattern.get(i);
			} else if (pattern.get(i) instanceof Recipes) {
				try {
					Recipes currentRecipe = (Recipes) pattern.get(i);
					ItemStack tempItem = currentRecipe.getResult(plugin);
					tempItem.setAmount(1);
					resolvedPattern[i] = tempItem;
				} catch (SecurityException | IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		return resolvedPattern;
	}
	
	public RecipeTypes getRecipeLevel() {
		return recipeLevel;
	}
	
	
	public ItemStack getResult(Main plugin) {
		Method getResultItem;
		try {
			getResultItem = plugin.itemManager.getClass().getDeclaredMethod(getResultMethod(), null);
			ItemStack tempItem = (ItemStack)getResultItem.invoke(plugin.itemManager, null);
			return tempItem;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
//	public boolean matchesPattern(ItemStack[] craftingTable, Main plugin) {
//		
//		if (shapeless) {
//			
//		}
//		
//		ItemStack[] resolvedPattern = getPattern(plugin);
//		
//		for (int i = 0; i < 9; i++) {
//			ItemStack item = craftingTable[i];
//			if (item != null) {
//				resolvedPattern[i].setAmount(item.getAmount());
//			}
//			
//			if (item == null && resolvedPattern[i] != null) {
//				resolvedPattern[i].setAmount(1);
//				return false;
//			}
//			
//			if (item != null && resolvedPattern[i] == null) {
//				resolvedPattern[i].setAmount(1);
//				return false;
//			}
//			
//			if (!item.equals(resolvedPattern[i])) {
//				resolvedPattern[i].setAmount(1);
//				return false;
//			}
//			resolvedPattern[i].setAmount(1);
//		}
//		return true;
//	}

	public Recipe getBukkitRecipe(Main plugin) {
		if (shapeless) {
			ShapelessRecipe recipe = new ShapelessRecipe(new NamespacedKey(plugin, this.name().toLowerCase()), getResult(plugin));
			ItemStack[] resolvedPattern = getPattern(plugin);
			for (ItemStack item : resolvedPattern) {
				recipe.addIngredient(item.getData());
			}
			return recipe;
		} else {
			ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(plugin, this.name().toLowerCase()), getResult(plugin));
			recipe.shape("ABC", "DEF", "GHI");
			ItemStack[] resolvedPattern = getPattern(plugin);
			for (int i = 0; i < resolvedPattern.length; i++) {
				if (resolvedPattern[i] != null) {
					recipe.setIngredient((char)(i + 65), resolvedPattern[i].getData());
				}
			}
			return recipe;
		}
	}
	
}


