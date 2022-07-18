package org.baugindustries.baugrpg.listeners.Crafting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.RecipeTypes;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftingTableListener implements Listener {
	private Main plugin;
	
	List<UUID> eventViewers;
	
	private boolean classCraftingRestrictions = true;
	
	public CraftingTableListener(Main plugin) {
		this.plugin = plugin;
		eventViewers = new ArrayList<UUID>();
	}
	
	
	@EventHandler
	public void onInventoryClick(PrepareItemCraftEvent event) {
		if (!(event.getViewers().get(0) instanceof Player)) return;
		if (event.getRecipe() == null) return;
		
		//this event gets called once for the item placing, and once for each item in every slot multiplied by the size of the stack. NOT OKAY HUGE AMOUNTS OF LAG
		

		Player player = (Player) event.getViewers().get(0);
		if (eventViewers.contains(player.getUniqueId())) return;
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
				
				if (classCraftingRestrictions && !learnedRecipes.contains(recipe)) {//Check if you know the recipe
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
}
