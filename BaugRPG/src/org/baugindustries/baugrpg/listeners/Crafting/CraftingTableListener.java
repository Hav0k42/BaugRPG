package org.baugindustries.baugrpg.listeners.Crafting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.Recipe;

public class CraftingTableListener implements Listener {
	private Main plugin;
	public CraftingTableListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PrepareItemCraftEvent event) {
		if (!(event.getViewers().get(0) instanceof Player)) return;
		if (event.getRecipe() == null) return;

		Player player = (Player) event.getViewers().get(0);
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
	 	if (!skillsconfig.contains("learnedRecipes")) return;

	 	
		List<Recipes> learnedRecipes = new ArrayList<Recipes>();
		ConfigurationSection learnedRecipesSection = skillsconfig.getConfigurationSection("learnedRecipes");
		
		if (learnedRecipesSection.contains("BASIC")) {
			learnedRecipesSection.getStringList("BASIC").forEach(recipeName -> {
				learnedRecipes.add(Recipes.valueOf(recipeName));
			});
		}
		
		if (learnedRecipesSection.contains("INTERMEDIATE")) {
			learnedRecipesSection.getStringList("INTERMEDIATE").forEach(recipeName -> {
				learnedRecipes.add(Recipes.valueOf(recipeName));
			});
		}
		
		if (learnedRecipesSection.contains("ADVANCED")) {
			learnedRecipesSection.getStringList("ADVANCED").forEach(recipeName -> {
				learnedRecipes.add(Recipes.valueOf(recipeName));
			});
		}
		
		if (learnedRecipesSection.contains("EXPERT")) {
			learnedRecipesSection.getStringList("EXPERT").forEach(recipeName -> {
				learnedRecipes.add(Recipes.valueOf(recipeName));
			});
		}
		

		Recipe bukkitRecipe = event.getRecipe();
		player.sendMessage(bukkitRecipe.toString());
		
		
	}

}
