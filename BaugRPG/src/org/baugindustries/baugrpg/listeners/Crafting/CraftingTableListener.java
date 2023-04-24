package org.baugindustries.baugrpg.listeners.Crafting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.RecipeTypes;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftingTableListener implements Listener {
	private Main plugin;
	
	List<UUID> eventViewers;
	HashMap<UUID, Recipes> shiftClickedRecipe;
	public CraftingTableListener(Main plugin) {
		this.plugin = plugin;
		eventViewers = new ArrayList<UUID>();
		shiftClickedRecipe = new HashMap<UUID, Recipes>();
	}
	
	
	@EventHandler
	public void onInventoryClick(PrepareItemCraftEvent event) {
		if (!(event.getViewers().get(0) instanceof Player)) return;
		if (event.getRecipe() == null) return;
		
		//this event gets called once for the item placing, and once for each item in every slot multiplied by the size of the stack. NOT OKAY HUGE AMOUNTS OF LAG
		
		Player player = (Player) event.getViewers().get(0);
		if (eventViewers.contains(player.getUniqueId())) {
			if (shiftClickedRecipe.containsKey(player.getUniqueId())) {
				addCraftingXP(shiftClickedRecipe.get(player.getUniqueId()), player, true);
			}
			return;
		}
		eventViewers.add(player.getUniqueId());
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				eventViewers.remove(player.getUniqueId());
			}
		}, 1L);
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		

	 	if (skillsconfig.contains("learnedRecipes") && skillsconfig.getConfigurationSection("learnedRecipes").getKeys(false).size() == 0) {
	 		skillsconfig.set("learnedRecipes", null);
	 		try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				e.printStackTrace();
			}
	 	}
		
		

		Recipes recipe = null;
		boolean vanillaRecipe = false;
		if (event.getRecipe() instanceof ShapedRecipe) {
			ShapedRecipe bukkitRecipe = (ShapedRecipe)event.getRecipe();
			try {
				recipe = Recipes.valueOf(bukkitRecipe.getKey().toString().toUpperCase().substring(8));
			} catch (IllegalArgumentException e) {
				vanillaRecipe = true;
			}
		} else if (event.getRecipe() instanceof ShapelessRecipe) {
			ShapelessRecipe bukkitRecipe = (ShapelessRecipe)event.getRecipe();
			try {
				recipe = Recipes.valueOf(bukkitRecipe.getKey().toString().toUpperCase().substring(8));
			} catch (IllegalArgumentException e) {
				vanillaRecipe = true;
			}
		}
		if (vanillaRecipe) {
			for (int i = 1; i < 10; i++) {
				ItemStack currentItem = event.getInventory().getItem(i);
				if (currentItem != null) {
					for (Recipes r : Recipes.values()) {
						if (r.matches(plugin, currentItem)) {
							plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									event.getInventory().setResult(new ItemStack(Material.AIR));
								}
							}, 1L);
							return;
						}
					}
				}
			}
		} else {
			
			shiftClickedRecipe.put(player.getUniqueId(), recipe);
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					shiftClickedRecipe.remove(player.getUniqueId());
				}
			}, 1L);
			if (skillsconfig.contains("learnedRecipes")) {
				List<Recipes> learnedRecipes = new ArrayList<Recipes>();
				ConfigurationSection learnedRecipesSection = skillsconfig.getConfigurationSection("learnedRecipes");
				
				for (RecipeTypes recipeType : RecipeTypes.values()) {
					if (learnedRecipesSection.contains(recipeType.name())) {
						learnedRecipesSection.getStringList(recipeType.name()).forEach(recipeName -> {
							learnedRecipes.add(Recipes.valueOf(recipeName));
						});
					}
				}
				
				
				File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
			 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				
				if (!config.contains("restrictCrafting")) {
			 		config.set("restrictCrafting", true);
			 	}
				
				if (!config.contains("restrictCraftingMaterials")) {  
			 		config.set("restrictCraftingMaterials", true);
			 	}

				boolean classCraftingRestrictions = config.getBoolean("restrictCrafting");
				boolean materialCraftingRestrictions = config.getBoolean("restrictCraftingMaterials");
				
				if ((classCraftingRestrictions && !learnedRecipes.contains(recipe)) || (materialCraftingRestrictions && !recipe.isMaterial())) {//Check if you know the recipe
					 plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						  public void run() {
								event.getInventory().setResult(new ItemStack(Material.AIR));
						  }
					 }, 1L);
					return;
				}
				
				
				for (int i = 1; i < 10; i++) {
					ItemStack currentItem = event.getInventory().getItem(i);
					if (currentItem != null) {
						if (recipe.getUnresolvedPattern().get(i - 1).getClass().equals(Recipes.class)) {//Current item needs to be a specific custom item.
							if (!((Recipes)recipe.getUnresolvedPattern().get(i - 1)).matches(plugin, currentItem)) {
								plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
									  public void run() {
											event.getInventory().setResult(new ItemStack(Material.AIR));
									  }
								 }, 1L);
								return;
							}
						} else {//Current item needs to be a vanilla item
							for (Recipes r : Recipes.values()) {
								if (r.matches(plugin, currentItem)) {
									plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
										  public void run() {
												event.getInventory().setResult(new ItemStack(Material.AIR));
										  }
									 }, 1L);
									return;
								}
							}
						}
					}
				}
			}
			
			
		}
		
		
		
	}
	

	@EventHandler
	public void onInventoryClick(CraftItemEvent event) {
		
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getCurrentItem() == null) return;
		
		addCraftingXP(event.getCurrentItem(), (Player) event.getWhoClicked(), false);
		
		
	}
	
	private void addCraftingXP(ItemStack currentItem, Player player, boolean updateFlag) {
		Recipes recipe = null;
		for (Recipes r : Recipes.values()) {
			if (r.matches(plugin, currentItem)) {
				recipe = r;
				break;
			}
		}
		if (recipe == null) return;
		
		addCraftingXP(recipe, player, updateFlag);
	}
	
	private void addCraftingXP(Recipes recipe, Player player, boolean updateFlag) {
		double pointsToAdd = 0;
		switch(recipe.getRecipeLevel()) {
			case ADVANCED:
				pointsToAdd = 100;
				break;
			case BASIC:
				pointsToAdd = 1;
				break;
			case EXPERT:
				pointsToAdd = 1000;
				break;
			case INTERMEDIATE:
				pointsToAdd = 10;
				break;
		}
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (updateFlag) {
	 		int itemCount = 0;
	 		for (Object obj : recipe.getUnresolvedPattern()) {
	 			if (obj != null) {
	 				itemCount++;
	 			}
	 		}
	 		
	 		pointsToAdd /= itemCount;
	 	}
	 	
	 	double newPoints = pointsToAdd;
	 	double oldPoints = 0;
	 	if (skillsconfig.contains("craftingExperience")) {
	 		oldPoints = skillsconfig.getDouble("craftingExperience");
	 		newPoints = pointsToAdd + oldPoints;
	 	}
	 	
	 	
	 	
	 	skillsconfig.set("craftingExperience", newPoints);
	 	try {
			skillsconfig.save(skillsfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	 	Profession profession = plugin.getProfession(player);
	 	
	 	if (newPoints >= 1000 && oldPoints < 1000) {
	 		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				 	int points = skillsconfig.getInt("craftingExperience");
					player.sendMessage(ChatColor.YELLOW + "You are now an expert " + profession.toString() + ".\nYou have unlocked expert crafting items. Your crafting xp is " + points + ". Reach 5000 crafting xp to gain race reset drops. Use /cxp to check your crafting xp.");
				}
			}, 1L);
	 	} else if (newPoints >= 100 && oldPoints < 100) {
	 		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				 	int points = skillsconfig.getInt("craftingExperience");
					player.sendMessage(ChatColor.YELLOW + "You are now an experienced " + profession.toString() + ".\nYou have unlocked advanced crafting items. Your crafting xp is " + points + ". Reach 1000 crafting xp to gain expert crafting items. Use /cxp to check your crafting xp.");
				}
			}, 1L);
	 	} else if (newPoints >= 10 && oldPoints < 10) {
	 		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				public void run() {
					FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				 	int points = skillsconfig.getInt("craftingExperience");
					player.sendMessage(ChatColor.YELLOW + "You are now a novice " + profession.toString() + ".\nYou have unlocked intermediate crafting items. Your crafting xp is " + points + ". Reach 100 crafting xp to gain advanced crafting items. Use /cxp to check your crafting xp.");
				}
			}, 1L);
	 	}
	}
}
