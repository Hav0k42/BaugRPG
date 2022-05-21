package org.baugindustries.baugrpg;

public enum Profession {
	STABLE_MASTER (
			1,
			new Recipes[] {
				Recipes.CORDOVAN_LEATHER,
				Recipes.HORSEHAIR,
				Recipes.SHARPENED_HOOF,
				Recipes.HORSESHOE,
				Recipes.MORRAL
			},
			new Recipes[] {
				Recipes.ROPE,
				Recipes.TEMPERED_LEATHER,
				Recipes.SPEAR,
				Recipes.WHISTLE,
				Recipes.BRIDLE
			},
			new Recipes[] {
				Recipes.CORD,
				Recipes.ENRICHED_LEATHER,
				Recipes.RUGGED_SWORD,
				Recipes.REGENERATIVE_HORSE_ARMOR,
				Recipes.EMBLEM_OF_THE_STALLION
			},
			new Recipes[] { }
			),
	STEELED_ARMORER (
			1,
			new Recipes[] {
				Recipes.IRON_PLATE,
				Recipes.SCREWS,
				Recipes.MESH,
				Recipes.IRON_HAMMER,
				Recipes.FEATHERED_SHOES
			},
			new Recipes[] {
				Recipes.HARDENED_PLATE,
				Recipes.HARDENED_MESH,
				Recipes.KNOCKBACK_SHIELD,
				Recipes.IRON_PLATE_HELMET,
				Recipes.IRON_PLATE_CHESTPIECE,
				Recipes.IRON_PLATE_LEGGINGS,
				Recipes.IRON_PLATE_GREAVES
			},
			new Recipes[] {
				Recipes.STEEL_PLATE,
				Recipes.STEEL_MESH,
				Recipes.EMBLEM_OF_THE_SHIELD,
				Recipes.STEEL_PLATE_HELMET,
				Recipes.STEEL_PLATE_CHESTPIECE,
				Recipes.STEEL_PLATE_LEGGINGS,
				Recipes.STEEL_PLATE_GREAVES
			},
			new Recipes[] { }
			),
	VERDANT_SHEPHERD (
			1,
			new Recipes[] {
				Recipes.MERINO_WOOL,
				Recipes.LANOLIN,
				Recipes.DRY_GRASS,
				Recipes.VEAL,
				Recipes.HEMP
			},
			new Recipes[] {
				Recipes.MERINO_CLOTH,
				Recipes.WAX,
				Recipes.CROOK,
				Recipes.SHEPHERDS_COMPASS,
				Recipes.CORRUPTED_STAFF
			},
			new Recipes[] {
				Recipes.ESSENCE_OF_FAUNA,
				Recipes.STEEL_WOOL,
				Recipes.VERDANT_MEDALLION,
				Recipes.EMBLEM_OF_THE_PASTURE,
				Recipes.GAIAS_WRATH
			},
			new Recipes[] { }
			),
	ENCHANTED_BOTANIST (
			2,
			new Recipes[] {
				Recipes.CHAFF,
				Recipes.ASSORTED_PETALS,
				Recipes.FLORAL_ROOTS,
				Recipes.FLORAL_TRANSMUTER,
				Recipes.CACTUS_GREAVES
			},
			new Recipes[] {
				Recipes.FLORAL_POULTICE,
				Recipes.ENRICHED_SOIL,
				Recipes.ENRICHED_HOE,
				Recipes.DEMETERS_BENEVOLENCE,
				Recipes.POTENT_HONEY
			},
			new Recipes[] {
				Recipes.AQUEOUS_SOLUTION,
				Recipes.CORRUPTED_SOIL,
				Recipes.EMBLEM_OF_THE_BLOSSOM,
				Recipes.SCYTHE,
				Recipes.GOLDEN_FLOWER
			},
			new Recipes[] { }
			),
	WOODLAND_CRAFTSMAN (
			2,
			new Recipes[] {
				Recipes.ELVEN_THREAD,
				Recipes.YEW_BRANCHES,
				Recipes.ENRICHED_LOGS,
				Recipes.SAPLING_TRANSMUTER,
				Recipes.SAW
			},
			new Recipes[] {
				Recipes.NAIL,
				Recipes.ELVEN_WEAVE,
				Recipes.TOOLBELT,
				Recipes.RUSTED_STAFF,
				Recipes.ENRICHED_WOOD_HELMET,
				Recipes.ENRICHED_WOOD_CHESTPIECE,
				Recipes.ENRICHED_WOOD_LEGGINGS,
				Recipes.ENRICHED_WOOD_GREAVES
			},
			new Recipes[] {
				Recipes.ELVEN_CLOTH,
				Recipes.PARCHMENT,
				Recipes.EMBLEM_OF_THE_FOREST,
				Recipes.ELVEN_HOOD,
				Recipes.ELVEN_CLOAK,
				Recipes.ELVEN_LEGGINGS,
				Recipes.ELVEN_GREAVES
			},
			new Recipes[] { }
			),
	LUNAR_ARTIFICER (
			2,
			new Recipes[] {
				Recipes.LUNAR_DEBRIS,
				Recipes.STARDUST,
				Recipes.ILLUMINA_ORB,
				Recipes.NEBULOUS_AURA
			},
			new Recipes[] {
				Recipes.METEORITE,
				Recipes.DARK_MATTER,
				Recipes.LIGHT_SHIELD,
				Recipes.LIGHT_BOW,
				Recipes.VOID_STONE
			},
			new Recipes[] {
				Recipes.BLOOD_MOON_FRAGMENT,
				Recipes.NEW_MOON_FRAGMENT,
				Recipes.LUNAR_BOOMERANG,
				Recipes.EMBLEM_OF_THE_MOON,
				Recipes.STAFF_OF_BALANCE
			},
			new Recipes[] { }
			),
	RADIANT_METALLURGIST (
			3,
			new Recipes[] {
				Recipes.STEEL,
				Recipes.RHYOLITE,
				Recipes.PUMICE,
				Recipes.FORGERS_SCROLL,
				Recipes.FERROUS_HARVESTER
			},
			new Recipes[] {
				Recipes.BRASS,
				Recipes.BRONZE,
				Recipes.RADIANT_BORE,
				Recipes.FLAMELASH,
				Recipes.HAND_FORGE
			},
			new Recipes[] {
				Recipes.TITANIUM,
				Recipes.ESSENCE_OF_FIRE,
				Recipes.EMBLEM_OF_THE_FORGE,
				Recipes.QUAKE_STAFF,
				Recipes.DWARVEN_AXE
			},
			new Recipes[] { }
			),
	ARCANE_JEWELER (
			3,
			new Recipes[] {
				Recipes.RUBY,
				Recipes.SAPPHIRE,
				Recipes.ROYAL_AZEL,
				Recipes.OPAL,
				Recipes.GOLD_RING,
				Recipes.REINFORCED_RING
			},
			new Recipes[] {
				Recipes.TOPAZ,
				Recipes.TURQUOISE,
				Recipes.STUDDED_HELMET,
				Recipes.STUDDED_CHESTPIECE,
				Recipes.STUDDED_LEGGINGS,
				Recipes.STUDDED_GREAVES,
				Recipes.BEJEWELED_COMPASS
			},
			new Recipes[] {
				Recipes.GOLD_DUST,
				Recipes.GEM_CLUSTER,
				Recipes.EMBLEM_OF_THE_WHIRLWIND,
				Recipes.DWARVEN_HELMET,
				Recipes.DWARVEN_CHESTPIECE,
				Recipes.DWARVEN_LEGGINGS,
				Recipes.DWARVEN_GREAVES
			},
			new Recipes[] { }
			),
	GILDED_MINER (
			3,
			new Recipes[] {
				Recipes.HARDENED_STONE,
				Recipes.ALUMINUM,
				Recipes.ZINC,
				Recipes.ENDURANCE_RUNE,
				Recipes.ROCKY_TRANSMUTER
			},
			new Recipes[] {
				Recipes.BLANK_EMBLEM,
				Recipes.LEAD,
				Recipes.DRILL,
				Recipes.PROTECTOR_STONE,
				Recipes.ROBUST_RUNE
			},
			new Recipes[] {
				Recipes.ESSENCE_OF_EARTH,
				Recipes.PLATINUM,
				Recipes.MAGNETIZED_IDOL,
				Recipes.EMBLEM_OF_THE_EARTH,
				Recipes.GRAPPLING_HOOK
			},
			new Recipes[] { }
			),
	DARK_ALCHEMIST (
			4,
			new Recipes[] {
				Recipes.FUNGAL_ROOTS,
				Recipes.WARPED_POWDER,
				Recipes.CRIMSON_POWDER,
				Recipes.HYPNOTIC_RING,
				Recipes.FLAMING_BUCKET
			},
			new Recipes[] {
				Recipes.ETHEREAL_POWDER,
				Recipes.HOGLIN_EYE,
				Recipes.ROD_OF_SHADOWS,
				Recipes.WAND_OF_DISPLACEMENT,
				Recipes.MAGIC_MIRROR
			},
			new Recipes[] {
				Recipes.ESSENCE_OF_VENGEANCE,
				Recipes.WITHER_POWDER,
				Recipes.EMBLEM_OF_THE_ELIXIR,
				Recipes.BREWING_WAND,
				Recipes.ANOMALOUS_PICKAXE
			},
			new Recipes[] { }
			),
	ENRAGED_BERSERKER (
			4,
			new Recipes[] {
				Recipes.HELLSTONE,
				Recipes.ETHEREAL_WOOD,
				Recipes.HOGLIN_TUSK,
				Recipes.RAGE_STONE,
				Recipes.SPECTRAL_WARD
				
			},
			new Recipes[] {
				Recipes.BONE_MARROW,
				Recipes.PYROCLASTIC_INGOT,
				Recipes.BLAZING_FURY,
				Recipes.SPECTRAL_HARNESS,
				Recipes.THE_SHREDDER
			},
			new Recipes[] {
				Recipes.EMPTY_SOUL,
				Recipes.BLOOD_OF_THE_FORSAKEN,
				Recipes.EMBLEM_OF_THE_BLADE,
				Recipes.FLESH_CANDLE,
				Recipes.CALIBURN
			},
			new Recipes[] { }
			),
	GREEDY_SCRAPPER (
			4,
			new Recipes[] {
				Recipes.SCRAP,
				Recipes.WASTELAND_GOO,
				Recipes.PEBBLES,
				Recipes.MAGMA_STONE,
				Recipes.DEMONIC_WRENCH
			},
			new Recipes[] {
				Recipes.BOLTS,
				Recipes.STEEL_FEATHER,
				Recipes.FRAGMENTED_HELMET,
				Recipes.FRAGMENTED_CHESTPIECE,
				Recipes.FRAGMENTED_LEGGINGS,
				Recipes.FRAGMENTED_GREAVES,
				Recipes.FLAMEDASH_BOOTS
			},
			new Recipes[] {
				Recipes.MAGMA_CRYSTAL,
				Recipes.GALVINIZED_ALLOY,
				Recipes.SCRAPPY_HELMET,
				Recipes.SCRAPPY_CHESTPIECE,
				Recipes.SCRAPPY_LEGGINGS,
				Recipes.SCRAPPY_GREAVES
			},
			new Recipes[] { }
			);

	int race;
	//Max amount of recipes a single profession can have should be 28. This allows for 336 possible custom items
	//Basic items are items that only use vanilla items.
	//Intermediate items are items that use other custom items to make. At least one custom item from a profession other than your own, but of the same race.
	//Advanced items are items that use custom items from another race.
	//Expert items are items that use at least one custom item from every race.
	Recipes[] basicRecipes;
	Recipes[] intermediateRecipes;
	Recipes[] advancedRecipes;
	Recipes[] expertRecipes;
	Profession(int race, Recipes[] basicRecipes, Recipes[] intermediateRecipes, Recipes[] advancedRecipes, Recipes[] expertRecipes) {
		this.race = race;
		this.basicRecipes = basicRecipes;
		this.intermediateRecipes = intermediateRecipes;
		this.advancedRecipes = advancedRecipes;
		this.expertRecipes = expertRecipes;
	}
	
	public int getRace() {
		return race;
	}
	
	public Recipes[] getBasicRecipes() {
		return basicRecipes;
	}
	public Recipes[] getIntermediateRecipes() {
		return intermediateRecipes;
	}
	public Recipes[] getAdvancedRecipes() {
		return advancedRecipes;
	}
	public Recipes[] getExpertRecipes() {
		return expertRecipes;
	}
	
	public Recipes[] getRecipes(RecipeTypes type) {
		switch (type) {
			case ADVANCED:
				return advancedRecipes;
			case BASIC:
				return basicRecipes;
			case INTERMEDIATE:
				return intermediateRecipes;
			case EXPERT:
				return expertRecipes;
			default:
				return basicRecipes;
		}
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
