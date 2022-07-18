package org.baugindustries.baugrpg;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
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
					null,
					new ItemStack(Material.WHEAT_SEEDS),
					null,
					null,
					null,
					null,
					null
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
	
	CACTUS_GREAVES (
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
	
	BOTTLED_STARLIGHT (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null,
					null
					)), 
			false
			),
	
	LUNAR_DEBRIS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.CLAY_BALL),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.CLAY_BALL),
					new ItemStack(Material.STONE),
					new ItemStack(Material.CLAY_BALL),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.CLAY_BALL),
					new ItemStack(Material.COBBLESTONE)
					)), 
			false
			),
	
	STARDUST (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.SUGAR),
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.SUGAR),
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.SUGAR),
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.SUGAR)
					)), 
			false
			),
	
	ILLUMINA_ORB (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.TORCH),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.TORCH),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.LANTERN),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.TORCH),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.TORCH)
					)), 
			false
			),
	
	NEBULOUS_AURA (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.LAPIS_LAZULI),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.SOUL_TORCH),
					new ItemStack(Material.LANTERN),
					new ItemStack(Material.SOUL_TORCH),
					new ItemStack(Material.GLOW_INK_SAC),
					new ItemStack(Material.LAPIS_LAZULI),
					new ItemStack(Material.GLOW_INK_SAC)
					)), 
			false
			),
	
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
	
	RHYOLITE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.RED_SAND),
					new ItemStack(Material.SANDSTONE),
					new ItemStack(Material.RED_SAND),
					new ItemStack(Material.SANDSTONE),
					new ItemStack(Material.ANDESITE),
					new ItemStack(Material.SANDSTONE),
					new ItemStack(Material.RED_SAND),
					new ItemStack(Material.SANDSTONE),
					new ItemStack(Material.RED_SAND)
					)), 
			false
			),
	
	PUMICE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.CALCITE),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.CALCITE),
					new ItemStack(Material.TUFF),
					new ItemStack(Material.CALCITE),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.CALCITE),
					new ItemStack(Material.COBBLESTONE)
					)), 
			false
			),
	
	FORGERS_SCROLL (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.ANVIL),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.PAPER)
					)), 
			false
			),
	
	FERROUS_HARVESTER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.SPIDER_EYE),
					null,
					new ItemStack(Material.STONE_AXE),
					new ItemStack(Material.IRON_NUGGET),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	//Arcane Jeweler
	
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
	
	GOLD_RING (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLDEN_PICKAXE),
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					null
					)), 
			false
			),
	
	REINFORCED_RING (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.IRON_CHESTPLATE),
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.IRON_INGOT),
					null
					)), 
			false
			),
	
	//Gilded Miner
	
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
	
	ALUMINUM (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.LIGHT_GRAY_DYE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.LIGHT_GRAY_DYE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.LIGHT_GRAY_DYE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.LIGHT_GRAY_DYE),
					new ItemStack(Material.IRON_INGOT)
					)), 
			false
			),
	
	ZINC (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.GRAY_DYE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.LIGHT_GRAY_DYE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.LIGHT_GRAY_DYE),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.GRAY_DYE),
					new ItemStack(Material.IRON_INGOT)
					)), 
			false
			),
	
	ENDURANCE_RUNE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.COOKED_BEEF),
					new ItemStack(Material.GOLDEN_CARROT),
					new ItemStack(Material.COOKED_BEEF),
					null,
					new ItemStack(Material.PAPER),
					null,
					null,
					new ItemStack(Material.STONE_SLAB),
					null
					)), 
			false
			),
	
	ROCKY_TRANSMUTER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GRANITE),
					new ItemStack(Material.REDSTONE),
					null,
					new ItemStack(Material.STICK),
					new ItemStack(Material.ANDESITE),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	//Dark Alchemist
	
	FUNGAL_ROOTS (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.WARPED_ROOTS),
					null,
					new ItemStack(Material.CRIMSON_ROOTS),
					new ItemStack(Material.NETHER_WART),
					new ItemStack(Material.CRIMSON_ROOTS),
					null,
					new ItemStack(Material.WARPED_ROOTS),
					null
					)), 
			false
			),
	
	WARPED_POWDER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.ENDER_PEARL),
					null,
					new ItemStack(Material.WARPED_ROOTS),
					new ItemStack(Material.WARPED_FUNGUS),
					new ItemStack(Material.WARPED_ROOTS),
					null,
					new ItemStack(Material.WARPED_ROOTS),
					null
					)), 
			false
			),
	
	CRIMSON_POWDER (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.BLAZE_POWDER),
					null,
					new ItemStack(Material.CRIMSON_ROOTS),
					new ItemStack(Material.CRIMSON_FUNGUS),
					new ItemStack(Material.CRIMSON_ROOTS),
					null,
					new ItemStack(Material.CRIMSON_ROOTS),
					null
					)), 
			false
			),
	
	HYPNOTIC_RING (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.ENDER_EYE),
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					null
					)), 
			false
			),
	
	FLAMING_BUCKET (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.MAGMA_BLOCK),
					null,
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.LAVA_BUCKET),
					new ItemStack(Material.MAGMA_BLOCK),
					null,
					new ItemStack(Material.MAGMA_BLOCK),
					null
					)), 
			false
			),
	
	//Enraged Berserker
	
	HELLSTONE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.RAW_IRON),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.RAW_IRON),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.MAGMA_BLOCK)
					)), 
			false
			),
	
	ETHEREAL_WOOD (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.WARPED_STEM),
					null,
					new ItemStack(Material.CRIMSON_STEM),
					new ItemStack(Material.OAK_SAPLING),
					new ItemStack(Material.CRIMSON_STEM),
					null,
					new ItemStack(Material.WARPED_STEM),
					null
					)), 
			false
			),
	
	HOGLIN_TUSK (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					new ItemStack(Material.BONE),
					new ItemStack(Material.BONE),
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.BONE),
					null,
					new ItemStack(Material.BONE),
					null
					)), 
			false
			),
	
	RAGE_STONE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.REDSTONE),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.REDSTONE),
					new ItemStack(Material.STONE),
					new ItemStack(Material.REDSTONE),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.IRON_SWORD),
					new ItemStack(Material.MAGMA_BLOCK)
					)), 
			false
			),
	
	SPECTRAL_WARD (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.LEAD),
					null,
					new ItemStack(Material.GHAST_TEAR),
					new ItemStack(Material.SHIELD),
					new ItemStack(Material.GHAST_TEAR),
					null,
					new ItemStack(Material.GHAST_TEAR),
					null
					)), 
			false
			),
	
	//Greedy Scrapper
	
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
	
	WASTELAND_GOO (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.BLAZE_POWDER),
					null,
					new ItemStack(Material.NETHER_WART),
					new ItemStack(Material.RAW_COPPER),
					new ItemStack(Material.NETHER_WART),
					null,
					new ItemStack(Material.NETHER_WART),
					null
			)), 
			false
			),
	
	PEBBLES (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.STONE_BUTTON),
					null,
					new ItemStack(Material.STONE_BUTTON),
					new ItemStack(Material.COBBLESTONE),
					new ItemStack(Material.STONE_BUTTON),
					null,
					new ItemStack(Material.STONE_BUTTON),
					null
			)), 
			false
			),
	
	MAGMA_STONE (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.FLINT),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.FLINT),
					new ItemStack(Material.STONE),
					new ItemStack(Material.FLINT),
					new ItemStack(Material.MAGMA_BLOCK),
					new ItemStack(Material.LAVA_BUCKET),
					new ItemStack(Material.MAGMA_BLOCK)
			)), 
			false
			),
	
	DEMONIC_WRENCH (
			RecipeTypes.BASIC, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.COBWEB),
					new ItemStack(Material.IRON_BARS),
					null,
					new ItemStack(Material.STICK),
					new ItemStack(Material.COBWEB),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Intermediate
	
	//Stable Master
	
	ROPE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.STRING),
					new ItemStack(Material.STRING),
					new ItemStack(Material.STRING),
					HORSEHAIR,
					new ItemStack(Material.STRING),
					new ItemStack(Material.STRING),
					new ItemStack(Material.STRING),
					null
					)), 
			false
			),
	
	TEMPERED_LEATHER (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.LEATHER),
					null,
					new ItemStack(Material.LEATHER),
					CORDOVAN_LEATHER,
					new ItemStack(Material.LEATHER),
					null,
					new ItemStack(Material.FLINT_AND_STEEL),
					null
					)), 
			false
			),
	
	SPEAR (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HORSEHAIR,
					SHARPENED_HOOF,
					null,
					new ItemStack(Material.STICK),
					HORSEHAIR,
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	WHISTLE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					SCREWS,
					null,
					new ItemStack(Material.IRON_INGOT),
					DRY_GRASS,
					SCREWS,
					null,
					new ItemStack(Material.IRON_INGOT),
					null
					)), 
			false
			),
	
	BRIDLE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.STRING),
					null,
					CORDOVAN_LEATHER,
					new ItemStack(Material.SADDLE),
					new ItemStack(Material.STRING),
					CORDOVAN_LEATHER,
					null,
					null
					)), 
			false
			),
	
	//Steeled Armorer
	
	HARDENED_PLATE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_NUGGET),
					null,
					new ItemStack(Material.IRON_INGOT),
					IRON_PLATE,
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.IRON_NUGGET),
					null
					)), 
			false
			),
	
	HARDENED_MESH (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_NUGGET),
					null,
					new ItemStack(Material.IRON_INGOT),
					MESH,
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.IRON_NUGGET),
					null
					)), 
			false
			),
	
	KNOCKBACK_SHIELD (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.IRON_NUGGET),
					SHARPENED_HOOF,
					new ItemStack(Material.IRON_NUGGET),
					IRON_PLATE,
					new ItemStack(Material.SHIELD),
					IRON_PLATE,
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.LEATHER)
					)), 
			false
			),
	
	IRON_PLATE_HELMET (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					IRON_PLATE,
					HORSEHAIR,
					IRON_PLATE,
					IRON_PLATE,
					null,
					IRON_PLATE,
					null,
					null,
					null
					)), 
			false
			),
	
	IRON_PLATE_CHESTPIECE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					IRON_PLATE,
					null,
					IRON_PLATE,
					IRON_PLATE,
					HORSEHAIR,
					IRON_PLATE,
					IRON_PLATE,
					IRON_PLATE,
					IRON_PLATE
					)), 
			false
			),
	
	IRON_PLATE_LEGGINGS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					IRON_PLATE,
					IRON_PLATE,
					IRON_PLATE,
					HORSEHAIR,
					null,
					HORSEHAIR,
					IRON_PLATE,
					null,
					IRON_PLATE
					)), 
			false
			),
	
	IRON_PLATE_GREAVES (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					IRON_PLATE,
					null,
					IRON_PLATE,
					SHARPENED_HOOF,
					null,
					SHARPENED_HOOF,
					null,
					null,
					null
					)), 
			false
			),
	
	//Verdant Shepherd
	
	MERINO_CLOTH (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.WHITE_WOOL),
					HORSEHAIR,
					MERINO_WOOL,
					HORSEHAIR,
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.WHITE_WOOL),
					new ItemStack(Material.WHITE_WOOL)
					)), 
			false
			),
	
	WAX (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.YELLOW_DYE),
					new ItemStack(Material.HONEYCOMB),
					new ItemStack(Material.YELLOW_DYE),
					new ItemStack(Material.HONEYCOMB),
					LANOLIN,
					new ItemStack(Material.HONEYCOMB),
					new ItemStack(Material.YELLOW_DYE),
					new ItemStack(Material.HONEYCOMB),
					new ItemStack(Material.YELLOW_DYE)
					)), 
			false
			),
	
	CROOK (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					SCREWS,
					SHARPENED_HOOF,
					null,
					new ItemStack(Material.STICK),
					null,
					null,
					new ItemStack(Material.STICK),
					null
					)), 
			false
			),
	
	SHEPHERDS_COMPASS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.WHITE_WOOL),
					null,
					MERINO_WOOL,
					new ItemStack(Material.COMPASS),
					MERINO_WOOL,
					null,
					new ItemStack(Material.WHITE_WOOL),
					null
					)), 
			false
			),
	
	CORRUPTED_STAFF (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.RED_MUSHROOM),
					LANOLIN,
					null,
					new ItemStack(Material.STICK),
					new ItemStack(Material.BROWN_MUSHROOM),
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	//Enchanted Botanist
	
	FLORAL_POULTICE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.YELLOW_DYE),
					null,
					new ItemStack(Material.WHITE_DYE),
					ASSORTED_PETALS,
					new ItemStack(Material.RED_DYE),
					null,
					new ItemStack(Material.BLUE_DYE),
					null
					)), 
			false
			),
	
	ENRICHED_SOIL (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					CHAFF,
					null,
					new ItemStack(Material.DIRT),
					new ItemStack(Material.DIRT),
					new ItemStack(Material.DIRT),
					null,
					FLORAL_ROOTS,
					null
					)), 
			false
			),
	
	ENRICHED_HOE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					null,
					new ItemStack(Material.STICK),
					null,
					null,
					new ItemStack(Material.STICK),
					null
					)), 
			false
			),
	
	DEMETERS_BENEVOLENCE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.BONE_MEAL),
					null,
					new ItemStack(Material.IRON_INGOT),
					ASSORTED_PETALS,
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.IRON_INGOT)
					)), 
			false
			),
	
	POTENT_HONEY (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					ASSORTED_PETALS,
					new ItemStack(Material.HONEY_BOTTLE),
					ASSORTED_PETALS,
					ASSORTED_PETALS,
					null,
					ASSORTED_PETALS
					)), 
			false
			),
	
	//Woodland Craftsman
	
	NAIL (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_NUGGET),
					null,
					new ItemStack(Material.IRON_NUGGET),
					YEW_BRANCHES,
					new ItemStack(Material.IRON_NUGGET),
					null,
					new ItemStack(Material.IRON_NUGGET),
					null
					)), 
			false
			),
	
	ELVEN_WEAVE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.LIME_DYE),
					null,
					new ItemStack(Material.STRING),
					ELVEN_THREAD,
					new ItemStack(Material.STRING),
					null,
					new ItemStack(Material.LIME_DYE),
					null
					)), 
			false
			),
	
	TOOLBELT (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.STICK),
					new ItemStack(Material.STICK),
					new ItemStack(Material.STICK),
					ENRICHED_LOGS,
					new ItemStack(Material.IRON_LEGGINGS),
					ENRICHED_LOGS,
					null,
					ELVEN_THREAD,
					null
					)), 
			false
			),
	
	RUSTED_STAFF (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STARDUST,
					new ItemStack(Material.COPPER_INGOT),
					null,
					YEW_BRANCHES,
					null,
					null,
					new ItemStack(Material.STICK),
					null
					)), 
			false
			),
	
	ENRICHED_WOOD_HELMET (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					null,
					ENRICHED_LOGS,
					null,
					null,
					null
					)), 
			false
			),
	
	ENRICHED_WOOD_CHESTPIECE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LOGS,
					null,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS
					)), 
			false
			),
	
	ENRICHED_WOOD_LEGGINGS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					null,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					null,
					ENRICHED_LOGS
					)), 
			false
			),
	
	ENRICHED_WOOD_GREAVES (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LOGS,
					null,
					ENRICHED_LOGS,
					ENRICHED_LOGS,
					null,
					ENRICHED_LOGS,
					null,
					null,
					null
					)), 
			false
			),
	
	//Lunar Artificier
	
	METEORITE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.OBSIDIAN),
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.OBSIDIAN),
					STARDUST,
					LUNAR_DEBRIS,
					STARDUST,
					new ItemStack(Material.OBSIDIAN),
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.OBSIDIAN)
					)), 
			false
			),
	
	DARK_MATTER (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					LUNAR_DEBRIS,
					new ItemStack(Material.BLACK_DYE),
					LUNAR_DEBRIS,
					null,
					STARDUST,
					null,
					LUNAR_DEBRIS,
					new ItemStack(Material.BLACK_DYE),
					LUNAR_DEBRIS
					)), 
			false
			),
	
	LIGHT_SHIELD (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GLOWSTONE_DUST),
					null,
					STARDUST,
					new ItemStack(Material.SHIELD),
					LUNAR_DEBRIS,
					null,
					new ItemStack(Material.GLOWSTONE_DUST),
					null
					)), 
			false
			),
	
	LIGHT_BOW (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STARDUST,
					new ItemStack(Material.STRING),
					BOTTLED_STARLIGHT,
					null,
					new ItemStack(Material.STRING),
					null,
					STARDUST,
					new ItemStack(Material.STRING)
					)), 
			false
			),
	
	VOID_STONE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.BLACK_DYE),
					LUNAR_DEBRIS,
					new ItemStack(Material.BLACK_DYE),
					LUNAR_DEBRIS,
					BOTTLED_STARLIGHT,
					LUNAR_DEBRIS,
					new ItemStack(Material.BLACK_DYE),
					LUNAR_DEBRIS,
					new ItemStack(Material.BLACK_DYE)
					)), 
			false
			),
	
	//Radiant Metallurgist
	
	BRASS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.COPPER_INGOT),
					ZINC,
					new ItemStack(Material.COPPER_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					null
					)), 
			false
			),
	
	BRONZE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.COPPER_INGOT),
					null,
					new ItemStack(Material.COPPER_INGOT),
					ALUMINUM,
					new ItemStack(Material.COPPER_INGOT),
					null,
					new ItemStack(Material.COPPER_INGOT),
					null
					)), 
			false
			),
	
	RADIANT_BORE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STEEL,
					null,
					STEEL,
					new ItemStack(Material.STICK),
					STEEL,
					null,
					new ItemStack(Material.STICK),
					null
					)), 
			false
			),
	
	FLAMELASH (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					PUMICE,
					null,
					null,
					STEEL,
					RHYOLITE,
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	HAND_FORGE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					HARDENED_STONE,
					HARDENED_STONE,
					HARDENED_STONE,
					RHYOLITE,
					null,
					RHYOLITE,
					PUMICE,
					PUMICE,
					PUMICE
					)), 
			false
			),
	
	//Arcane Jeweler
	
	TOPAZ (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					RUBY,
					null,
					new ItemStack(Material.EMERALD),
					new ItemStack(Material.YELLOW_DYE),
					new ItemStack(Material.EMERALD),
					null,
					RUBY,
					null
					)), 
			false
			),
	
	TURQUOISE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					OPAL,
					null,
					SAPPHIRE,
					new ItemStack(Material.CYAN_DYE),
					SAPPHIRE,
					null,
					OPAL,
					null
					)), 
			false
			),
	
	STUDDED_HELMET (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					OPAL,
					STEEL,
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					null,
					null,
					null
					)), 
			false
			),
	
	STUDDED_CHESTPIECE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					null,
					STEEL,
					new ItemStack(Material.GOLD_INGOT),
					RUBY,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT)
					)), 
			false
			),
	
	STUDDED_LEGGINGS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					SAPPHIRE,
					STEEL,
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					null,
					new ItemStack(Material.GOLD_INGOT)
					)), 
			false
			),
	
	STUDDED_GREAVES (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					STEEL,
					null,
					STEEL,
					ROYAL_AZEL,
					null,
					ROYAL_AZEL
					)), 
			false
			),
	
	BEJEWELED_COMPASS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					RUBY,
					null,
					SAPPHIRE,
					new ItemStack(Material.COMPASS),
					OPAL,
					null,
					ROYAL_AZEL,
					null
					)), 
			false
			),
	
	//Gilded Miner
	
	BLANK_EMBLEM (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					HARDENED_STONE,
					new ItemStack(Material.IRON_INGOT),
					HARDENED_STONE,
					null,
					HARDENED_STONE,
					new ItemStack(Material.IRON_INGOT),
					HARDENED_STONE,
					STEEL
					)), 
			false
			),
	
	LEAD (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GRAY_DYE),
					null,
					new ItemStack(Material.GRAY_DYE),
					ALUMINUM,
					new ItemStack(Material.GRAY_DYE),
					null,
					new ItemStack(Material.GRAY_DYE),
					null
					)), 
			false
			),
	
	DRILL (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STEEL,
					new ItemStack(Material.DIAMOND),
					null,
					new ItemStack(Material.STICK),
					STEEL,
					HARDENED_STONE,
					null,
					null
					)), 
			false
			),
	
	PROTECTOR_STONE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					PUMICE,
					new ItemStack(Material.IRON_INGOT),
					PUMICE,
					new ItemStack(Material.IRON_INGOT),
					HARDENED_STONE,
					new ItemStack(Material.IRON_INGOT),
					PUMICE,
					new ItemStack(Material.IRON_INGOT),
					PUMICE
					)), 
			false
			),
	
	ROBUST_RUNE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					STEEL,
					STEEL,
					new ItemStack(Material.EMERALD),
					HARDENED_STONE,
					new ItemStack(Material.EMERALD),
					null,
					new ItemStack(Material.EMERALD),
					null
					)), 
			false
			),
	
	//Dark Alchemist
	
	ETHEREAL_POWDER (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.CRIMSON_ROOTS),
					null,
					WARPED_POWDER,
					FUNGAL_ROOTS,
					CRIMSON_POWDER,
					null,
					new ItemStack(Material.WARPED_ROOTS),
					null
					)), 
			false
			),
	
	HOGLIN_EYE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HOGLIN_TUSK,
					null,
					new ItemStack(Material.LEATHER),
					new ItemStack(Material.ENDER_PEARL),
					CRIMSON_POWDER,
					null,
					CRIMSON_POWDER,
					null
					)), 
			false
			),
	
	ROD_OF_SHADOWS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HELLSTONE,
					WARPED_POWDER,
					null,
					new ItemStack(Material.STICK),
					HELLSTONE,
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	WAND_OF_DISPLACEMENT (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					HELLSTONE,
					new ItemStack(Material.REDSTONE),
					HELLSTONE,
					null,
					ETHEREAL_WOOD,
					null,
					null,
					new ItemStack(Material.STICK),
					null
					)), 
			false
			),
	
	MAGIC_MIRROR (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.SAND),
					new ItemStack(Material.GLOWSTONE_DUST),
					SCRAP,
					FUNGAL_ROOTS,
					SCRAP,
					new ItemStack(Material.GLOWSTONE_DUST),
					new ItemStack(Material.SAND),
					new ItemStack(Material.GLOWSTONE_DUST)
					)), 
			false
			),
	
	//Enraged Berserker
	
	BONE_MARROW (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.BONE_MEAL),
					null,
					new ItemStack(Material.BONE_MEAL),
					HOGLIN_TUSK,
					new ItemStack(Material.BONE_MEAL),
					null,
					new ItemStack(Material.BONE_MEAL),
					null
					)), 
			false
			),
	
	PYROCLASTIC_INGOT (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_INGOT),
					null,
					new ItemStack(Material.BLAZE_POWDER),
					HOGLIN_TUSK,
					new ItemStack(Material.BLAZE_POWDER),
					null,
					new ItemStack(Material.IRON_INGOT),
					null
					)), 
			false
			),
	
	BLAZING_FURY (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.BLAZE_POWDER),
					SCRAP,
					null,
					HOGLIN_TUSK,
					new ItemStack(Material.BLAZE_POWDER),
					null,
					HOGLIN_TUSK,
					null
					)), 
			false
			),
	
	SPECTRAL_HARNESS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					SCRAP,
					new ItemStack(Material.LEAD),
					SCRAP,
					new ItemStack(Material.GHAST_TEAR),
					new ItemStack(Material.SADDLE),
					new ItemStack(Material.GHAST_TEAR),
					SCRAP,
					new ItemStack(Material.GHAST_TEAR),
					SCRAP
					)), 
			false
			),
	
	THE_SHREDDER (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HELLSTONE,
					SCRAP,
					null,
					new ItemStack(Material.STICK),
					HELLSTONE,
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	//Greedy Scrapper
	
	BOLTS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.IRON_NUGGET),
					null,
					WASTELAND_GOO,
					PEBBLES,
					WASTELAND_GOO,
					null,
					new ItemStack(Material.IRON_NUGGET),
					null
					)), 
			false
			),
	
	STEEL_FEATHER (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					CRIMSON_POWDER,
					SCRAP,
					new ItemStack(Material.IRON_INGOT),
					new ItemStack(Material.FEATHER),
					CRIMSON_POWDER,
					null,
					new ItemStack(Material.IRON_INGOT),
					null
					)), 
			false
			),
	
	FRAGMENTED_HELMET (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					WARPED_POWDER,
					new ItemStack(Material.MAGMA_BLOCK),
					WARPED_POWDER,
					SCRAP,
					null,
					SCRAP,
					null,
					null,
					null
					)), 
			false
			),
	
	FRAGMENTED_CHESTPIECE (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.RAW_COPPER),
					null,
					new ItemStack(Material.RAW_COPPER),
					SCRAP,
					new ItemStack(Material.QUARTZ),
					SCRAP,
					new ItemStack(Material.QUARTZ),
					ETHEREAL_WOOD,
					new ItemStack(Material.QUARTZ)
					)), 
			false
			),
	
	FRAGMENTED_LEGGINGS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					CRIMSON_POWDER,
					WARPED_POWDER,
					CRIMSON_POWDER,
					new ItemStack(Material.MAGMA_BLOCK),
					null,
					new ItemStack(Material.MAGMA_BLOCK),
					SCRAP,
					null,
					SCRAP
					)), 
			false
			),
	
	FRAGMENTED_GREAVES (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					new ItemStack(Material.MAGMA_BLOCK),
					null,
					new ItemStack(Material.MAGMA_BLOCK),
					SCRAP,
					null,
					SCRAP
					)), 
			false
			),
	
	FLAMEDASH_BOOTS (
			RecipeTypes.INTERMEDIATE, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					new ItemStack(Material.BLAZE_POWDER),
					new ItemStack(Material.GOLDEN_BOOTS),
					new ItemStack(Material.BLAZE_POWDER),
					HELLSTONE,
					null,
					HELLSTONE
					)), 
			false
			),
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//Advanced
	
	//Stable Master
	
	CORD (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HORSEHAIR,
					ROPE,
					HORSEHAIR,
					BONE_MARROW,
					HORSEHAIR,
					ROPE,
					HORSEHAIR,
					null
					)), 
			false
			),
	
	ENRICHED_LEATHER (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					CORDOVAN_LEATHER,
					null,
					CORDOVAN_LEATHER,
					null,
					TEMPERED_LEATHER,
					null,
					new ItemStack(Material.LEATHER),
					ENRICHED_SOIL,
					new ItemStack(Material.LEATHER)
					)), 
			false
			),
	
	RUGGED_SWORD (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					HARDENED_PLATE,
					null,
					HARDENED_PLATE,
					null,
					BOLTS,
					null,
					null
					)), 
			false
			),
	
	REGENERATIVE_HORSE_ARMOR (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					MERINO_CLOTH,
					ROPE,
					MERINO_CLOTH,
					new ItemStack(Material.DIAMOND_HORSE_ARMOR),
					null,
					null,
					TURQUOISE,
					null
					)), 
			false
			),
	
	EMBLEM_OF_THE_STALLION (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.SADDLE),
					null,
					TEMPERED_LEATHER,
					BLANK_EMBLEM,
					ROPE,
					null,
					SHARPENED_HOOF,
					null
					)), 
			false
			),
	
	//Steeled Armorer
	
	STEEL_PLATE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					IRON_PLATE,
					null,
					IRON_PLATE,
					HARDENED_PLATE,
					IRON_PLATE,
					null,
					IRON_PLATE,
					STEEL
					)), 
			false
			),
	
	STEEL_MESH (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					MESH,
					null,
					MESH,
					HARDENED_MESH,
					MESH,
					null,
					MESH,
					STEEL
					)), 
			false
			),
	
	EMBLEM_OF_THE_SHIELD (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HARDENED_PLATE,
					null,
					SCREWS,
					BLANK_EMBLEM,
					new ItemStack(Material.SHIELD),
					null,
					HARDENED_MESH,
					null
					)), 
			false
			),
	
	STEEL_PLATE_HELMET (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					HARDENED_MESH,
					STEEL,
					HARDENED_MESH,
					HARDENED_PLATE,
					null,
					HARDENED_PLATE,
					null,
					null,
					null
					)), 
			false
			),
	
	STEEL_PLATE_CHESTPIECE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					HARDENED_PLATE,
					null,
					HARDENED_PLATE,
					HARDENED_PLATE,
					STEEL,
					HARDENED_PLATE,
					HARDENED_MESH,
					HARDENED_PLATE,
					HARDENED_MESH
					)), 
			false
			),
	
	STEEL_PLATE_LEGGINGS (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					HARDENED_PLATE,
					STEEL,
					HARDENED_PLATE,
					HARDENED_PLATE,
					null,
					HARDENED_PLATE,
					HARDENED_MESH,
					null,
					HARDENED_MESH
					)), 
			false
			),
	
	STEEL_PLATE_GREAVES (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					STEEL,
					null,
					STEEL,
					HARDENED_MESH,
					null,
					HARDENED_MESH
					)), 
			false
			),
	
	//Verdant Shepherd
	
	ESSENCE_OF_FAUNA (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					LANOLIN,
					WAX,
					MERINO_WOOL,
					ETHEREAL_POWDER,
					LANOLIN,
					WAX,
					MERINO_WOOL,
					null
					)), 
			false
			),
	
	STEEL_WOOL (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					MERINO_WOOL,
					MERINO_CLOTH,
					MERINO_WOOL,
					STEEL_FEATHER,
					MERINO_WOOL,
					MERINO_CLOTH,
					MERINO_WOOL,
					null
					)), 
			false
			),
	
	VERDANT_MEDALLION (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.GREEN_DYE),
					null,
					ROPE,
					new ItemStack(Material.GOLD_NUGGET),
					ROPE,
					null,
					DARK_MATTER,
					null
					)), 
			false
			),
	
	EMBLEM_OF_THE_PASTURE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					CROOK,
					null,
					MERINO_CLOTH,
					BLANK_EMBLEM,
					DRY_GRASS,
					null,
					WAX,
					null
					)), 
			false
			),
	
	GAIAS_WRATH (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					DRY_GRASS,
					WAX,
					null,
					YEW_BRANCHES,
					DRY_GRASS,
					YEW_BRANCHES,
					null,
					null
					)), 
			false
			),
	
	//Enchanted Botanist
	
	AQUEOUS_SOLUTION (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					WAX,
					ASSORTED_PETALS,
					new ItemStack(Material.WATER_BUCKET),
					FLORAL_POULTICE,
					WAX,
					ASSORTED_PETALS,
					new ItemStack(Material.WATER_BUCKET),
					null
					)), 
			false
			),
	
	CORRUPTED_SOIL (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					FLORAL_ROOTS,
					ENRICHED_SOIL,
					FLORAL_ROOTS,
					HOGLIN_EYE,
					FLORAL_ROOTS,
					ENRICHED_SOIL,
					FLORAL_ROOTS,
					null
					)), 
			false
			),
	
	EMBLEM_OF_THE_BLOSSOM (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.DIAMOND_HOE),
					null,
					FLORAL_POULTICE,
					BLANK_EMBLEM,
					ASSORTED_PETALS,
					null,
					ENRICHED_SOIL,
					null
					)), 
			false
			),
	
	SCYTHE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					BRONZE,
					BRONZE,
					ELVEN_THREAD,
					null,
					YEW_BRANCHES,
					null,
					YEW_BRANCHES,
					null,
					null
					)), 
			false
			),
	
	GOLDEN_FLOWER (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					TOPAZ,
					null,
					ASSORTED_PETALS,
					new ItemStack(Material.SUNFLOWER),
					ASSORTED_PETALS,
					null,
					FLORAL_POULTICE,
					null
					)), 
			false
			),
	
	//Woodland Craftsman
	
	ELVEN_CLOTH (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ELVEN_THREAD,
					ELVEN_WEAVE,
					ELVEN_THREAD,
					MERINO_CLOTH,
					ELVEN_THREAD,
					ELVEN_WEAVE,
					ELVEN_THREAD,
					null
					)), 
			false
			),
	
	PARCHMENT (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.FEATHER),
					new ItemStack(Material.INK_SAC),
					null,
					ASSORTED_PETALS,
					TEMPERED_LEATHER,
					null,
					new ItemStack(Material.PAPER),
					new ItemStack(Material.PAPER),
					new ItemStack(Material.PAPER)
					)), 
			false
			),
	
	EMBLEM_OF_THE_FOREST (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.DIAMOND_AXE),
					null,
					ELVEN_WEAVE,
					BLANK_EMBLEM,
					NAIL,
					null,
					ENRICHED_LOGS,
					null
					)), 
			false
			),
	
	ELVEN_HOOD (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ELVEN_WEAVE,
					HARDENED_MESH,
					ELVEN_WEAVE,
					ELVEN_WEAVE,
					null,
					ELVEN_WEAVE,
					null,
					null,
					null
					)), 
			false
			),
	
	ELVEN_CLOAK (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ELVEN_WEAVE,
					null,
					ELVEN_WEAVE,
					ELVEN_WEAVE,
					HARDENED_MESH,
					ELVEN_WEAVE,
					ELVEN_WEAVE,
					ELVEN_WEAVE,
					ELVEN_WEAVE
					)), 
			false
			),
	
	ELVEN_LEGGINGS (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ELVEN_WEAVE,
					HARDENED_MESH,
					ELVEN_WEAVE,
					ELVEN_WEAVE,
					null,
					ELVEN_WEAVE,
					ELVEN_WEAVE,
					null,
					ELVEN_WEAVE
					)), 
			false
			),
	
	ELVEN_GREAVES (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					HARDENED_MESH,
					null,
					HARDENED_MESH,
					ELVEN_WEAVE,
					null,
					ELVEN_WEAVE
					)), 
			false
			),
	
	//Lunar Artificer
	
	BLOOD_MOON_FRAGMENT (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					DARK_MATTER,
					METEORITE,
					STARDUST,
					PYROCLASTIC_INGOT,
					DARK_MATTER,
					METEORITE,
					STARDUST,
					null
					)), 
			false
			),
	
	NEW_MOON_FRAGMENT (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STARDUST,
					LUNAR_DEBRIS,
					DARK_MATTER,
					ETHEREAL_POWDER,
					STARDUST,
					LUNAR_DEBRIS,
					DARK_MATTER,
					null
					)), 
			false
			),
	
	LUNAR_BOOMERANG (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					LUNAR_DEBRIS,
					LUNAR_DEBRIS,
					METEORITE,
					null,
					BRASS,
					null,
					LUNAR_DEBRIS,
					LUNAR_DEBRIS
					)), 
			false
			),
	
	EMBLEM_OF_THE_MOON (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					LIGHT_BOW,
					null,
					DARK_MATTER,
					BLANK_EMBLEM,
					BOTTLED_STARLIGHT,
					null,
					METEORITE,
					null
					)), 
			false
			),
	
	STAFF_OF_BALANCE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					STARDUST,
					STARDUST,
					OPAL,
					STARDUST,
					YEW_BRANCHES,
					DARK_MATTER,
					YEW_BRANCHES,
					DARK_MATTER,
					DARK_MATTER
					)), 
			false
			),
	
	//Radiant Metallurgist
	
	TITANIUM (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STEEL,
					OPAL,
					STEEL,
					HARDENED_PLATE,
					STEEL,
					OPAL,
					STEEL,
					null
					)), 
			false
			),
	
	ESSENCE_OF_FIRE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					RHYOLITE,
					BRONZE,
					new ItemStack(Material.LAVA_BUCKET),
					METEORITE,
					PUMICE,
					ALUMINUM,
					BRONZE,
					new ItemStack(Material.LAVA_BUCKET)
					)), 
			false
			),
	
	EMBLEM_OF_THE_FORGE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					HARDENED_PLATE,
					null,
					BRASS,
					BLANK_EMBLEM,
					STEEL,
					null,
					HAND_FORGE,
					null
					)), 
			false
			),
	
	QUAKE_STAFF (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					PUMICE,
					ROPE,
					null,
					ALUMINUM,
					RHYOLITE,
					ALUMINUM,
					null,
					null
					)), 
			false
			),
	
	DWARVEN_AXE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					BRASS,
					STEEL,
					null,
					YEW_BRANCHES,
					BRONZE,
					null,
					YEW_BRANCHES,
					null
					)), 
			false
			),
	
	//Arcane Jeweler
	
	GOLD_DUST (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					BRASS,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					FLORAL_POULTICE,
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					new ItemStack(Material.GOLD_INGOT),
					BRASS
					)), 
			false
			),
	
	GEM_CLUSTER (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ROYAL_AZEL,
					TOPAZ,
					SAPPHIRE,
					null,
					WAX,
					null,
					OPAL,
					TURQUOISE,
					RUBY
					)), 
			false
			),
	
	EMBLEM_OF_THE_WHIRLWIND (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					GOLD_RING,
					null,
					TOPAZ,
					BLANK_EMBLEM,
					TURQUOISE,
					null,
					ELVEN_WEAVE,
					null
					)), 
			false
			),
	
	DWARVEN_HELMET (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ZINC,
					ELVEN_WEAVE,
					ZINC,
					STEEL,
					null,
					STEEL,
					null,
					null,
					null
					)), 
			false
			),
	
	DWARVEN_CHESTPIECE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					null,
					STEEL,
					STEEL,
					ELVEN_WEAVE,
					STEEL,
					STEEL,
					ALUMINUM,
					STEEL
					)), 
			false
			),
	
	DWARVEN_LEGGINGS (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					STEEL,
					ELVEN_WEAVE,
					STEEL,
					STEEL,
					null,
					STEEL,
					STEEL,
					null,
					STEEL
					)), 
			false
			),
	
	DWARVEN_GREAVES (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					STEEL,
					null,
					STEEL,
					ELVEN_WEAVE,
					null,
					ELVEN_WEAVE
					)), 
			false
			),
	
	//Gilded Miner
	
	ESSENCE_OF_EARTH (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					PUMICE,
					HARDENED_STONE,
					RHYOLITE,
					HARDENED_STONE,
					ETHEREAL_POWDER,
					HARDENED_STONE,
					RHYOLITE,
					HARDENED_STONE,
					PUMICE
					)), 
			false
			),
	
	PLATINUM (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					LEAD,
					new ItemStack(Material.IRON_INGOT),
					LEAD,
					new ItemStack(Material.IRON_INGOT),
					STARDUST,
					new ItemStack(Material.IRON_INGOT),
					LEAD,
					new ItemStack(Material.IRON_INGOT),
					LEAD
					)), 
			false
			),
	
	MAGNETIZED_IDOL (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.BLUE_DYE),
					null,
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.IRON_INGOT),
					DARK_MATTER,
					new ItemStack(Material.IRON_INGOT),
					STEEL,
					new ItemStack(Material.IRON_BLOCK),
					STEEL
					)), 
			false
			),
	
	EMBLEM_OF_THE_EARTH (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ENRICHED_SOIL,
					null,
					ALUMINUM,
					BLANK_EMBLEM,
					ZINC,
					null,
					HARDENED_STONE,
					null
					)), 
			false
			),
	
	GRAPPLING_HOOK (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					new ItemStack(Material.TRIPWIRE_HOOK),
					ROPE,
					ROPE,
					ROPE,
					ROPE,
					null,
					null
					)), 
			false
			),
	
	//Dark Alchemist
	
	ESSENCE_OF_VENGEANCE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ETHEREAL_POWDER,
					null,
					HOGLIN_EYE,
					null,
					RUBY,
					null,
					HOGLIN_EYE,
					null,
					ETHEREAL_POWDER
					)), 
			false
			),
	
	WITHER_POWDER (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.COAL_BLOCK),
					DARK_MATTER,
					new ItemStack(Material.COAL_BLOCK),
					ETHEREAL_POWDER,
					new ItemStack(Material.COAL_BLOCK),
					DARK_MATTER,
					new ItemStack(Material.COAL_BLOCK),
					null
					)), 
			false
			),
	
	EMBLEM_OF_THE_ELIXIR (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ETHEREAL_POWDER,
					null,
					MAGIC_MIRROR,
					BLANK_EMBLEM,
					HOGLIN_EYE,
					null,
					FUNGAL_ROOTS,
					null
					)), 
			false
			),
	
	BREWING_WAND (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					WARPED_POWDER,
					FLORAL_POULTICE,
					null,
					new ItemStack(Material.BLAZE_ROD),
					CRIMSON_POWDER,
					ETHEREAL_POWDER,
					null,
					null
					)), 
			false
			),
	
	ANOMALOUS_PICKAXE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					LEAD,
					ETHEREAL_POWDER,
					LEAD,
					null,
					new ItemStack(Material.STICK),
					null,
					null,
					new ItemStack(Material.STICK),
					null
					)), 
			false
			),
	
	//Enraged Berserker
	
	EMPTY_SOUL (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					ETHEREAL_POWDER,
					DARK_MATTER,
					BONE_MARROW,
					HOGLIN_EYE,
					new ItemStack(Material.HEART_OF_THE_SEA),
					CRIMSON_POWDER,
					DARK_MATTER,
					WARPED_POWDER,
					HOGLIN_TUSK
					)), 
			false
			),
	
	BLOOD_OF_THE_FORSAKEN (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.RED_DYE),
					new ItemStack(Material.RED_DYE),
					HOGLIN_EYE,
					new ItemStack(Material.RED_DYE),
					WAX,
					BONE_MARROW,
					WAX
					)), 
			false
			),
	
	EMBLEM_OF_THE_BLADE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.DIAMOND_AXE),
					null,
					BONE_MARROW,
					BLANK_EMBLEM,
					PYROCLASTIC_INGOT,
					null,
					ETHEREAL_WOOD,
					null
					)), 
			false
			),
	
	FLESH_CANDLE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					new ItemStack(Material.CANDLE),
					null,
					new ItemStack(Material.BEEF),
					WAX,
					new ItemStack(Material.CHICKEN),
					new ItemStack(Material.PORKCHOP),
					WAX,
					new ItemStack(Material.MUTTON)
					)), 
			false
			),
	
	CALIBURN (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					NAIL,
					PYROCLASTIC_INGOT,
					null,
					PYROCLASTIC_INGOT,
					NAIL,
					new ItemStack(Material.STICK),
					null,
					null
					)), 
			false
			),
	
	//Greedy Scrapper
	
	MAGMA_CRYSTAL (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					RHYOLITE,
					SCRAP,
					RHYOLITE,
					SCRAP,
					HELLSTONE,
					SCRAP,
					RHYOLITE,
					SCRAP,
					RHYOLITE
					)), 
			false
			),
	
	GALVINIZED_ALLOY (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					SCRAP,
					PYROCLASTIC_INGOT,
					SCRAP,
					PYROCLASTIC_INGOT,
					HARDENED_PLATE,
					PYROCLASTIC_INGOT,
					SCRAP,
					PYROCLASTIC_INGOT,
					SCRAP
					)), 
			false
			),
	
	SCRAPPY_HELMET (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					PYROCLASTIC_INGOT,
					SCREWS,
					PYROCLASTIC_INGOT,
					SCRAP,
					null,
					SCRAP,
					null,
					null,
					null
					)), 
			false
			),
	
	SCRAPPY_CHESTPIECE (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					SCRAP,
					null,
					SCRAP,
					SCRAP,
					NAIL,
					SCRAP,
					PYROCLASTIC_INGOT,
					BOLTS,
					PYROCLASTIC_INGOT
					)), 
			false
			),
	
	SCRAPPY_LEGGINGS (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					SCRAP,
					PYROCLASTIC_INGOT,
					SCRAP,
					SCREWS,
					null,
					SCREWS,
					SCRAP,
					null,
					SCRAP
					)), 
			false
			),
	
	SCRAPPY_GREAVES (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					null,
					null,
					NAIL,
					null,
					NAIL,
					SCRAP,
					null,
					SCRAP
					)), 
			false
			),
	
	EMBLEM_OF_THE_RAVEN (
			RecipeTypes.ADVANCED, 
			new ArrayList<Object> (Arrays.asList(
					null,
					STEEL_FEATHER,
					null,
					SCRAP,
					BLANK_EMBLEM,
					BOLTS,
					null,
					FLAMEDASH_BOOTS,
					null
					)), 
			false
			),
	
	//EXPERT
	
	//Stable Master
	
	ENCHANTED_WHISTLE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DIAMOND),
					BOLTS,
					new ItemStack(Material.DIAMOND),
					PLATINUM,
					WHISTLE,
					NEW_MOON_FRAGMENT,
					new ItemStack(Material.DIAMOND),
					BOLTS,
					new ItemStack(Material.DIAMOND)
					)), 
			false
			),
	
	ENCHANTED_HORSE_ARMOR (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.DIAMOND),
					TITANIUM,
					new ItemStack(Material.DIAMOND),
					GALVINIZED_ALLOY,
					WHISTLE,
					BLOOD_MOON_FRAGMENT,
					new ItemStack(Material.DIAMOND),
					TITANIUM,
					new ItemStack(Material.DIAMOND)
					)), 
			false
			),
	
	SOULBOUND_SWORD (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_SWORD),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	SOUL_OF_THE_EQUESTRIAN (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					CORD,
					ENRICHED_LEATHER,
					ELVEN_CLOTH,
					ENRICHED_LEATHER,
					EMPTY_SOUL,
					ENRICHED_LEATHER,
					ESSENCE_OF_EARTH,
					ENRICHED_LEATHER,
					CORD
					)), 
			false
			),
	
	//Steeled Armorer
	
	SOUL_OF_THE_GUARDIAN (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					STEEL_MESH,
					STEEL_PLATE,
					NEW_MOON_FRAGMENT,
					STEEL_PLATE,
					EMPTY_SOUL,
					STEEL_PLATE,
					GOLD_DUST,
					STEEL_PLATE,
					STEEL_MESH
					)), 
			false
			),
	
	SOULBOUND_CHESTPIECE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_CHESTPLATE),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	VISAGE_OF_THE_GORGON (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					CORRUPTED_SOIL,
					ESSENCE_OF_FIRE,
					STEEL_WOOL,
					BLOOD_OF_THE_FORSAKEN,
					new ItemStack(Material.WITHER_SKELETON_SKULL),
					BLOOD_OF_THE_FORSAKEN,
					STEEL_WOOL,
					ESSENCE_OF_FIRE,
					CORRUPTED_SOIL
					)), 
			false
			),
	
	//Verdant Shepherd
	
	SOUL_OF_THE_CARETAKER (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ESSENCE_OF_FAUNA,
					STEEL_WOOL,
					CORRUPTED_SOIL,
					STEEL_WOOL,
					EMPTY_SOUL,
					STEEL_WOOL,
					GEM_CLUSTER,
					STEEL_WOOL,
					ESSENCE_OF_FAUNA
					)), 
			false
			),
	
	SOULBOUND_HOE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_HOE),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	VEILED_TOTEM (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LEATHER,
					ESSENCE_OF_FAUNA,
					BLOOD_MOON_FRAGMENT,
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.TOTEM_OF_UNDYING),
					ESSENCE_OF_FAUNA,
					WITHER_POWDER,
					ESSENCE_OF_FAUNA,
					ESSENCE_OF_EARTH
					)), 
			false
			),
	
	//Enchanted Botanist
	
	SOUL_OF_THE_FLORIST (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					AQUEOUS_SOLUTION,
					CORRUPTED_SOIL,
					GOLD_DUST,
					CORRUPTED_SOIL,
					EMPTY_SOUL,
					CORRUPTED_SOIL,
					STEEL_MESH,
					CORRUPTED_SOIL,
					AQUEOUS_SOLUTION
					)), 
			false
			),
	
	SOULBOUND_SHOVEL (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_SHOVEL),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	STAFF_OF_PERSEPHONE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ESSENCE_OF_FAUNA,
					GOLDEN_FLOWER,
					null,
					GALVINIZED_ALLOY,
					ESSENCE_OF_EARTH,
					GALVINIZED_ALLOY,
					null,
					null
					)), 
			false
			),
	
	//Woodland Craftsman
	
	SOUL_OF_THE_WOODSMAN (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LOGS,
					PARCHMENT,
					TITANIUM,
					ELVEN_CLOTH,
					EMPTY_SOUL,
					ELVEN_CLOTH,
					ENRICHED_LEATHER,
					PARCHMENT,
					ENRICHED_LOGS
					)), 
			false
			),
	
	SOULBOUND_LEGGINGS (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_LEGGINGS),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	WOODSLING (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					GOLD_DUST,
					LIGHT_BOW,
					MAGMA_CRYSTAL,
					CORD,
					ENRICHED_LOGS,
					CORD,
					null,
					CORD,
					null
					)), 
			false
			),
	
	//Woodland Craftsman
	
	SOUL_OF_THE_ASTROLOGER (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					BLOOD_MOON_FRAGMENT,
					NEW_MOON_FRAGMENT,
					GEM_CLUSTER,
					NEW_MOON_FRAGMENT,
					EMPTY_SOUL,
					BLOOD_MOON_FRAGMENT,
					STEEL_PLATE,
					BLOOD_MOON_FRAGMENT,
					NEW_MOON_FRAGMENT
					)), 
			false
			),
	
	ASTRAL_WRENCH (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					NEW_MOON_FRAGMENT,
					STEEL_MESH,
					WITHER_POWDER,
					null,
					STAFF_OF_BALANCE,
					STEEL_MESH,
					PLATINUM,
					null,
					BLOOD_MOON_FRAGMENT
					)), 
			false
			),
	
	SOULBOUND_ELYTRA (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.ELYTRA),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	//Radiant Metallurgist
	
	SOUL_OF_THE_BLACKSMITH (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ESSENCE_OF_FIRE,
					TITANIUM,
					ELVEN_CLOTH,
					TITANIUM,
					EMPTY_SOUL,
					TITANIUM,
					STEEL_WOOL,
					TITANIUM,
					ESSENCE_OF_FIRE
					)), 
			false
			),
	
	TOTEM_OF_THE_MOLE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ENRICHED_LEATHER,
					MAGMA_CRYSTAL,
					ENRICHED_LEATHER,
					MAGMA_CRYSTAL,
					RADIANT_BORE,
					MAGMA_CRYSTAL,
					CORRUPTED_SOIL,
					CORRUPTED_SOIL,
					CORRUPTED_SOIL
					)), 
			false
			),
	
	RADIANT_AXE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					WITHER_POWDER,
					STEEL_PLATE,
					STEEL_PLATE,
					ESSENCE_OF_FIRE,
					DWARVEN_AXE,
					STEEL_PLATE,
					BLOOD_MOON_FRAGMENT,
					ESSENCE_OF_FIRE,
					WITHER_POWDER
					)), 
			false
			),
	
	//Arcane Jeweler
	
	SOUL_OF_THE_ENGRAVER (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					GOLD_DUST,
					GEM_CLUSTER,
					STEEL_WOOL,
					GEM_CLUSTER,
					EMPTY_SOUL,
					GEM_CLUSTER,
					PARCHMENT,
					GEM_CLUSTER,
					GOLD_DUST
					)), 
			false
			),
	
	SOULBOUND_CROWN (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_HELMET),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	COMPRESSIVE_WAND (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					null,
					GEM_CLUSTER,
					new ItemStack(Material.NETHER_STAR),
					CORD,
					GALVINIZED_ALLOY,
					GEM_CLUSTER,
					PARCHMENT,
					CORD,
					null
					)), 
			false
			),
	
	//Arcane Jeweler
	
	SOUL_OF_THE_PROSPECTOR (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ESSENCE_OF_EARTH,
					PLATINUM,
					STEEL_MESH,
					PLATINUM,
					EMPTY_SOUL,
					PLATINUM,
					PARCHMENT,
					PLATINUM,
					ESSENCE_OF_EARTH
					)), 
			false
			),
	
	SOULBOUND_PICKAXE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_PICKAXE),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	MULTITOOL (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ANOMALOUS_PICKAXE,
					null,
					DWARVEN_AXE,
					new ItemStack(Material.NETHER_STAR),
					SCYTHE,
					null,
					GAIAS_WRATH,
					null
					)), 
			false
			),
	
	//Dark Alchemist
	
	SOUL_OF_THE_SCIENTIST (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					WITHER_POWDER,
					ENRICHED_LEATHER,
					WITHER_POWDER,
					PARCHMENT,
					EMPTY_SOUL,
					GOLD_DUST,
					ESSENCE_OF_VENGEANCE,
					ESSENCE_OF_VENGEANCE,
					ESSENCE_OF_VENGEANCE
					)), 
			false
			),
	
	WAND_OF_DISFIGURATION (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					null,
					ESSENCE_OF_FAUNA,
					null,
					BLOOD_MOON_FRAGMENT,
					BREWING_WAND,
					ESSENCE_OF_VENGEANCE,
					null,
					ESSENCE_OF_FIRE,
					null
					)), 
			false
			),
	
	VOODOO_DOLL (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					BLOOD_OF_THE_FORSAKEN,
					null,
					BLOOD_OF_THE_FORSAKEN,
					BLOOD_MOON_FRAGMENT,
					new ItemStack(Material.TOTEM_OF_UNDYING),
					CORD,
					BLOOD_OF_THE_FORSAKEN,
					ESSENCE_OF_FIRE,
					BLOOD_OF_THE_FORSAKEN
					)), 
			false
			),
	
	//Enraged Berserker
	
	SOUL_OF_THE_WARRIOR (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					BLOOD_OF_THE_FORSAKEN,
					NEW_MOON_FRAGMENT,
					BLOOD_OF_THE_FORSAKEN,
					STEEL_PLATE,
					EMPTY_SOUL,
					TITANIUM,
					BLOOD_OF_THE_FORSAKEN,
					BLOOD_OF_THE_FORSAKEN,
					BLOOD_OF_THE_FORSAKEN
					)), 
			false
			),
	
	SOULBOUND_AXE (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_AXE),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	PHOENIX_ASHES (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					ESSENCE_OF_FIRE,
					ESSENCE_OF_FAUNA,
					ESSENCE_OF_FIRE,
					BLOOD_MOON_FRAGMENT,
					WITHER_POWDER,
					BLOOD_MOON_FRAGMENT,
					ESSENCE_OF_FIRE,
					ESSENCE_OF_FAUNA,
					ESSENCE_OF_FIRE
					)), 
			false
			),
	
	//Greedy Scrapper
	
	SOUL_OF_THE_THIEF (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					MAGMA_CRYSTAL,
					CORD,
					MAGMA_CRYSTAL,
					ELVEN_CLOTH,
					EMPTY_SOUL,
					GOLD_DUST,
					GALVINIZED_ALLOY,
					GALVINIZED_ALLOY,
					GALVINIZED_ALLOY
					)), 
			false
			),
	
	SOULBOUND_GREAVES (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_FAUNA,
					new ItemStack(Material.AMETHYST_SHARD),
					AQUEOUS_SOLUTION,
					new ItemStack(Material.DIAMOND_BOOTS),
					ESSENCE_OF_VENGEANCE,
					new ItemStack(Material.AMETHYST_SHARD),
					ESSENCE_OF_EARTH,
					new ItemStack(Material.AMETHYST_SHARD)
					)), 
			false
			),
	
	ABERRANT_COMPASS (
			RecipeTypes.EXPERT, 
			new ArrayList<Object> (Arrays.asList(
					STEEL_PLATE,
					new ItemStack(Material.SHULKER_SHELL),
					WITHER_POWDER,
					new ItemStack(Material.SHULKER_SHELL),
					BEJEWELED_COMPASS,
					new ItemStack(Material.SHULKER_SHELL),
					NEW_MOON_FRAGMENT,
					new ItemStack(Material.SHULKER_SHELL),
					PLATINUM
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
	
	public ArrayList<Object> getUnresolvedPattern() {
		return pattern;
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
				if (item != null) {
					recipe.addIngredient(item.getData());
				}
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
		if (otherItem == null) {
//			Bukkit.getLogger().warning("Compared item is null.");
			return false;
		}
		if (!otherItem.hasItemMeta()) return false;
		
		if (thisItem.getItemMeta().getDisplayName().equals(otherItem.getItemMeta().getDisplayName()) && (thisItem.getType().equals(otherItem.getType()) || this.equals(Recipes.MULTITOOL))) {
			return true;
		}
		
		if (otherItem.getItemMeta().hasCustomModelData() && thisItem.getItemMeta().getCustomModelData() == otherItem.getItemMeta().getCustomModelData() && thisItem.getType().name().toLowerCase().contains("bucket") && otherItem.getType().name().toLowerCase().contains("bucket")) return true;
		
		return false;
	}
	
	public List<Recipes> getUses(Main plugin) {
		List<Recipes> uses = new ArrayList<Recipes>();
		for (Recipes recipe : Recipes.values()) {
			for (Object item : recipe.getUnresolvedPattern()) {
				if (item instanceof Recipes && this.equals((Recipes)item)) {
					uses.add(recipe);
					break;
				}
			}
		}
		return uses;
	}
	
	public boolean playerIsCarrying(Player player, Main plugin) {
		boolean matches = false;
		
		for (ItemStack item : player.getInventory()) {
			if (this.matches(plugin, item)) {
				matches = true;
				break;
			}
		}
		
		if (!matches) return false;
		return true;
	}
	
	public String toString() {
		String[] words = this.name().split("_");
		String name = "";
		for (String word : words) {
			name = name + word.substring(0, 1) + word.substring(1).toLowerCase() + " ";
		}
		return name.substring(0, name.length() - 1);
	}
	
}


