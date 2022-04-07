package org.baugindustries.baugrpg.listeners.Crafting;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.RecipeTypes;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class PlayerPickUpRecipeScroll implements Listener {
	private Main plugin;
	public PlayerPickUpRecipeScroll(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void pickUpRecipe(EntityPickupItemEvent event) {
		if (!(event.getEntity() instanceof Player)) return;
		Player player = (Player)event.getEntity();
		
		ItemStack tempItemChecker = event.getItem().getItemStack();
		tempItemChecker.setAmount(1);
		RecipeTypes type;
		if (tempItemChecker.equals(plugin.itemManager.getBasicRecipeScrollItem())) {
			type = RecipeTypes.BASIC;
		} else if (tempItemChecker.equals(plugin.itemManager.getIntermediateRecipeScrollItem())) {
			type = RecipeTypes.INTERMEDIATE;
		} else if (tempItemChecker.equals(plugin.itemManager.getAdvancedRecipeScrollItem())) {
			type = RecipeTypes.ADVANCED;
		} else if (tempItemChecker.equals(plugin.itemManager.getExpertRecipeScrollItem())) {
			type = RecipeTypes.EXPERT;
		} else {
			return;
		}
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
		String professionName = skillsconfig.getString("class");
		Profession profession = Profession.valueOf(professionName.toUpperCase().replace(' ', '_'));
		
		ConfigurationSection learnedRecipes;
		if (skillsconfig.contains("learnedRecipes")) {
			learnedRecipes = skillsconfig.getConfigurationSection("learnedRecipes");
		} else {
			learnedRecipes = skillsconfig.createSection("learnedRecipes");
		}
		
		List<String> learnedRecipesTYPE;
		if (learnedRecipes.contains(type.toString())) {
			learnedRecipesTYPE = learnedRecipes.getStringList(type.toString());
		} else {
			learnedRecipesTYPE = new ArrayList<String>();
		}
		
		List<Recipes> unlearnedRecipes = new ArrayList<Recipes>();
		for (int i = 0; i < profession.getRecipes(type).length; i++) {
			if (!learnedRecipesTYPE.contains(profession.getRecipes(type)[i].toString())) {
				unlearnedRecipes.add(profession.getRecipes(type)[i]);
			}
		}
		
		if (unlearnedRecipes.size() == 0) {
			event.setCancelled(true);
			return;
		}
		
		Recipes newRecipe = unlearnedRecipes.get((int)(Math.random() * unlearnedRecipes.size()));
		learnedRecipesTYPE.add(newRecipe.toString());
		learnedRecipes.set(type.toString(), learnedRecipesTYPE);
		try {
			skillsconfig.save(skillsfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] recipeWords = newRecipe.toString().toLowerCase().split("_");
		String recipeName = "";
		for (String word : recipeWords) {
			recipeName = recipeName + " " +  word.substring(0, 1).toUpperCase() + word.substring(1);
		}
		player.sendMessage(ChatColor.GOLD + "You've learned the recipe for" + recipeName + ". View your learned recipes using /bs.");
		event.getItem().remove();
		event.setCancelled(true);
		
	}
}
