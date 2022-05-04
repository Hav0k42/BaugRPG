package org.baugindustries.baugrpg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public enum Recipes {
	
	//BASIC
	
	//Stable Master
	
	CORDOVAN_LEATHER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
				null,
				new ItemStack(Material.RABBIT_HIDE),
				null,
				new ItemStack(Material.LEATHER),
				null,
				new ItemStack(Material.LEATHER),
				null,
				new ItemStack(Material.RABBIT_HIDE),
				null
					)),
			false
			),
	
	HORSEHAIR (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
				null,
				new ItemStack(Material.WHEAT),
				new ItemStack(Material.STRING),
				new ItemStack(Material.WHEAT),
				new ItemStack(Material.STRING),
				new ItemStack(Material.WHEAT),
				new ItemStack(Material.STRING),
				new ItemStack(Material.WHEAT),
				null
					)),
			false
			),
	
	SHARPENED_HOOF (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
				null,
				new ItemStack(Material.FLINT),
				null,
				new ItemStack(Material.FLINT),
				new ItemStack(Material.GRINDSTONE),
				new ItemStack(Material.FLINT),
				null,
				new ItemStack(Material.BONE),
				null
					)),
			false
			),
	
	HORSESHOE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
				new ItemStack(Material.RAW_GOLD),
				new ItemStack(Material.RAW_GOLD),
				new ItemStack(Material.RAW_GOLD),
				new ItemStack(Material.RAW_GOLD),
				null,
				new ItemStack(Material.RAW_GOLD),
				new ItemStack(Material.GOLD_NUGGET),
				null,
				new ItemStack(Material.GOLD_NUGGET)
					)),
			false
			),
	
	MORRAL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
				new ItemStack(Material.WHEAT),
				new ItemStack(Material.APPLE),
				new ItemStack(Material.WHEAT),
				new ItemStack(Material.LEAD),
				new ItemStack(Material.CARROT),
				new ItemStack(Material.LEAD),
				new ItemStack(Material.IRON_NUGGET),
				null,
				new ItemStack(Material.IRON_NUGGET)
					)),
			false
			),
	
	//Steeled Armorer
	
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
			false
			),
	
	SCREWS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_NUGGET),
					null,
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.RAW_IRON),
					new ItemStack(Material.IRON_NUGGET),
					null,
					new ItemStack(Material.IRON_NUGGET),
					null
					)), 
			false
			),
	
	MESH (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.STRING),
					null,
					new ItemStack(Material.STRING),
					null,
					new ItemStack(Material.STRING),
					null,
					new ItemStack(Material.STRING),
					null,
					new ItemStack(Material.STRING)
					)), 
			false
			),
	
	IRON_HAMMER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.STICK),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	FEATHERED_SHOES (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.FEATHER),
					null,
					new ItemStack(Material.FEATHER),
					new ItemStack(Material.FEATHER),
					new ItemStack(Material.LEATHER_BOOTS),
					new ItemStack(Material.FEATHER),
					null,
					null,
					null
					)), 
			false
			),
	
	//Verdant Shepherd
	
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
			false
			),
	
	LANOLIN (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.HONEYCOMB),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.LEATHER)
					)), 
			false
			),
	
	DRY_GRASS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GRASS),
					null,
					new ItemStack(Material.GRASS),
					new ItemStack(Material.DEAD_BUSH),
					new ItemStack(Material.GRASS),
					null,
					new ItemStack(Material.GRASS),
					null
					)), 
			false
			),
	
	VEAL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.BEEF),
					null,
					new ItemStack(Material.BEEF),
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.BEEF),
					null,
					new ItemStack(Material.BEEF),
					null
					)), 
			false
			),
	
	HEMP (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.PAPER),
					new ItemStack(Material.GRASS),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.GRASS),
					new ItemStack(Material.FERN),
					new ItemStack(Material.GRASS),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.GRASS),
					new ItemStack(Material.PAPER)
					)), 
			false
			),
	
	//Enchanted Botanist
			
	CHAFF (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.WHEAT),
					new ItemStack(Material.WHEAT_SEEDS),
					new ItemStack(Material.WHEAT_SEEDS)
			)), 
			true
			),
	
	ASSORTED_PETALS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DANDELION),
					new ItemStack(Material.CORNFLOWER),
					new ItemStack(Material.PEONY),
					new ItemStack(Material.POPPY),
					new ItemStack(Material.SUNFLOWER),
					new ItemStack(Material.POPPY),
					new ItemStack(Material.LILAC),
					new ItemStack(Material.CORNFLOWER),
					new ItemStack(Material.DANDELION)
					)), 
			false
			),
	
	FLORAL_ROOTS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.HANGING_ROOTS),
					new ItemStack(Material.VINE),
					new ItemStack(Material.HANGING_ROOTS),
					new ItemStack(Material.VINE),
					new ItemStack(Material.HANGING_ROOTS),
					new ItemStack(Material.VINE),
					null,
					new ItemStack(Material.POPPY),
					null
					)), 
			false
			),
	
	FLORAL_TRANSMUTER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.LILAC),
					new ItemStack(Material.REDSTONE),
					null,
					new ItemStack(Material.STICK),
					new ItemStack(Material.LILAC),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	CACTUS_BOOTS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.CACTUS),
					null,
					new ItemStack(Material.CACTUS),
					new ItemStack(Material.CACTUS),
					null,
					new ItemStack(Material.CACTUS),
					new ItemStack(Material.SAND),
					new ItemStack(Material.SAND),
					new ItemStack(Material.SAND)
					)), 
			false
			),
	
	//Woodland Craftsman
	
	ELVEN_THREAD (
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
			false
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
			false
			),
	
	ENRICHED_LOGS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.OAK_LOG),
					new ItemStack(Material.DARK_OAK_LOG),
					new ItemStack(Material.BIRCH_LOG),
					new ItemStack(Material.OAK_LOG),
					new ItemStack(Material.DARK_OAK_LOG),
					new ItemStack(Material.BIRCH_LOG),
					new ItemStack(Material.BONE_MEAL),
					new ItemStack(Material.BONE_MEAL),
					new ItemStack(Material.BONE_MEAL)
					)), 
			false
			),
	
	SAPLING_TRANSMUTER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.OAK_SAPLING),
					new ItemStack(Material.REDSTONE),
					null,
					new ItemStack(Material.STICK),
					new ItemStack(Material.BIRCH_SAPLING),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	SAW (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.IRON_AXE),
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	//Lunar Artificer
	
	
	
	//Radiant Metallurgist
	
	STEEL (
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
			false
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
			false
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
			false
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
			false
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
			false
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
			false
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
			false
			),
	
	;

	RecipeTypes recipeLevel;
	ArrayList<Object> pattern;
	boolean shapeless;
	String resultMethod;
	Recipes(RecipeTypes recipeLevel, ArrayList<Object> pattern, boolean shapeless) {
		this.recipeLevel = recipeLevel;
		this.pattern = pattern;
		this.shapeless = shapeless;
		String[] words = name().toLowerCase().split("_");
		resultMethod = "get";
		for (String word : words) {
			resultMethod = resultMethod + word.substring(0, 1).toUpperCase() + word.substring(1);
		}
		resultMethod = resultMethod + "Item";
		
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
			getResultItem = plugin.itemManager.getClass().getDeclaredMethod(getResultMethod(), (Class<?>[])null);
			ItemStack tempItem = (ItemStack)getResultItem.invoke(plugin.itemManager, (Object[])null);
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
					recipe.setIngredient((char)(i + 65), resolvedPattern[i].getType());
					
				}
			}
			return recipe;
		}
	}
	
	public boolean matches(Main plugin, ItemStack otherItem) {
		ItemStack thisItem = getResult(plugin);
		thisItem.setAmount(otherItem.getAmount());
		if (thisItem.getItemMeta().getDisplayName().equals(otherItem.getItemMeta().getDisplayName()) && thisItem.getType().equals(otherItem.getType())) {
			return true;
		}
		return false;
	}
	
}


