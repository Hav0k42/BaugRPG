package org.baugindustries.baugrpg;

public enum Profession {
	STABLE_MASTER (
			1,
			new Recipes[] {
				Recipes.CORDOVAN_LEATHER
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	STEELED_ARMORER (
			1,
			new Recipes[] {
				Recipes.IRON_PLATE
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	VERDANT_SHEPHERD (
			1,
			new Recipes[] {
				Recipes.MERINO_WOOL
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	ENCHANTED_BOTANIST (
			2,
			new Recipes[] {
				Recipes.CHAFF
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	LUNAR_ARTIFICER (
			2,
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	WOODLAND_CRAFTSMAN (
			2,
			new Recipes[] {
				Recipes.ELVEN_THREAD,
				Recipes.YEW_BRANCHES
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	RADIANT_METALLURGIST (
			3,
			new Recipes[] {
				Recipes.STEEL
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	ARCANE_JEWELER (
			3,
			new Recipes[] {
				Recipes.RUBY,
				Recipes.SAPPHIRE,
				Recipes.ROYAL_AZEL,
				Recipes.OPAL
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	GILDED_MINER (
			3,
			new Recipes[] {
				Recipes.HARDENED_STONE
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	DARK_ALCHEMIST (
			4,
			new Recipes[] {
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	ENRAGED_BERSERKER (
			4,
			new Recipes[] {
			},
			new Recipes[] { },
			new Recipes[] { },
			new Recipes[] { }
			),
	GREEDY_SCRAPPER (
			4,
			new Recipes[] {
				Recipes.SCRAP
			},
			new Recipes[] { },
			new Recipes[] { },
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
	
}
