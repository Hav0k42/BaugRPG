package org.baugindustries.baugrpg.listeners.Crafting;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.RecipeTypes;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDropRecipeScroll implements Listener {
	private Main plugin;
	public EntityDropRecipeScroll(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onMonsterDeath(EntityDeathEvent event) {
		Entity mob = event.getEntity();
		if (!(mob instanceof Monster)) return;
		Player player = ((Monster)mob).getKiller();
		if (player == null) return;
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
		String professionName = skillsconfig.getString("class");
		
		if (professionName == null || professionName.equals("")) return;
		Profession profession = Profession.valueOf(professionName.toUpperCase().replace(' ', '_'));
		
		double rand = Math.random();
		if (rand < 0.001) {//Expert drop rate is 1/1000
			if (hasUnlearnedRecipes(player, RecipeTypes.EXPERT, profession)) {
				mob.getWorld().dropItemNaturally(mob.getLocation(), plugin.itemManager.getExpertRecipeScrollItem());
			}
		} else if (rand < 0.003) {//Advanced drop rate is 1/500
			if (hasUnlearnedRecipes(player, RecipeTypes.ADVANCED, profession)) {
				mob.getWorld().dropItemNaturally(mob.getLocation(), plugin.itemManager.getAdvancedRecipeScrollItem());
			}
		} else if (rand < 0.007) {//Intermediate drop rate is 1/250
			if (hasUnlearnedRecipes(player, RecipeTypes.ADVANCED, profession)) {
				mob.getWorld().dropItemNaturally(mob.getLocation(), plugin.itemManager.getIntermediateRecipeScrollItem());
			}
		} else if (rand < 0.017) {//Basic drop rate is 1/100
			if (hasUnlearnedRecipes(player, RecipeTypes.ADVANCED, profession)) {
				mob.getWorld().dropItemNaturally(mob.getLocation(), plugin.itemManager.getBasicRecipeScrollItem());
			}
		}
	}
	
	private boolean hasUnlearnedRecipes(Player player, RecipeTypes type, Profession profession) {
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		
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
		
		
		for (int i = 0; i < profession.getRecipes(type).length; i++) {
			if (!learnedRecipesTYPE.contains(profession.getRecipes(type)[i].toString())) {
				return true;
			}
		}
		return false;
	}
}
