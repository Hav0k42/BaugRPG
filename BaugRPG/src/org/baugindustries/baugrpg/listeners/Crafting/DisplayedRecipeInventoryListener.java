package org.baugindustries.baugrpg.listeners.Crafting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.RecipeTypes;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class DisplayedRecipeInventoryListener implements Listener {
	private Main plugin;
	public DisplayedRecipeInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().equals(ChatColor.GOLD + "Recipe")) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getLearnedRecipesMenu(player.getUniqueId()));
			return;
		}
		
		if (event.getSlot() == 25) {
			Recipes recipe = null;
			for (Recipes currentRecipe : Recipes.values()) {
				if (currentRecipe.matches(plugin, event.getCurrentItem())) {
					recipe = currentRecipe;
					break;
				}
			}
			if (recipe == null) return;
			
			HashMap<Material, Integer> ingredients = new HashMap<Material, Integer>();
			ingredients = getIngredients(ingredients, recipe);
			
			player.closeInventory();
			player.sendMessage(ChatColor.GOLD + "Total raw ingredients for: " + recipe.toString());
			for (Material material : ingredients.keySet()) {
				player.sendMessage(ChatColor.YELLOW + materialString(material.toString()) + " x " + ingredients.get(material));
			}
		} else {

			Recipes recipe = null;
			for (Recipes currentRecipe : Recipes.values()) {
				if (currentRecipe.matches(plugin, event.getCurrentItem())) {
					recipe = currentRecipe;
					break;
				}
			}
			if (recipe == null) return;
			
			if (event.getClick().equals(ClickType.LEFT)) {
				File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
				FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				List<Recipes> learnedRecipes = new ArrayList<Recipes>();
				ConfigurationSection learnedRecipesSection = skillsconfig.getConfigurationSection("learnedRecipes");
				
				for (RecipeTypes recipeType : RecipeTypes.values()) {
					if (learnedRecipesSection.contains(recipeType.name())) {
						learnedRecipesSection.getStringList(recipeType.name()).forEach(recipeName -> {
							learnedRecipes.add(Recipes.valueOf(recipeName));
						});
					}
				}
				if (learnedRecipes.contains(recipe)) {
					String inventoryName = ChatColor.GOLD + "Recipe";
					Inventory inventory = Bukkit.createInventory(null, 45, inventoryName);
					
					ItemStack[] pattern = recipe.getPattern(plugin);
					
					inventory.setItem(10, pattern[0]);
					inventory.setItem(11, pattern[1]);
					inventory.setItem(12, pattern[2]);
					inventory.setItem(19, pattern[3]);
					inventory.setItem(20, pattern[4]);
					inventory.setItem(21, pattern[5]);
					inventory.setItem(28, pattern[6]);
					inventory.setItem(29, pattern[7]);
					inventory.setItem(30, pattern[8]);
					
					ItemStack resultItem = recipe.getResult(plugin);
					ItemMeta resultMeta = resultItem.getItemMeta();
					List<String> lore = resultMeta.getLore();
					lore.add("");
					lore.add("Click for total vanilla ingredients.");
					resultMeta.setLore(lore);
					resultItem.setItemMeta(resultMeta);
					
					inventory.setItem(25, resultItem);
					
					for (int i = 5; i < 45; i += 9) {
						inventory.setItem(i, plugin.itemManager.getBlankItem());
					}
					
					inventory.setItem(36, plugin.itemManager.getBackItem());
					player.openInventory(inventory);
				}
			} else if (event.getClick().equals(ClickType.RIGHT)) {
				File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
				FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				List<Recipes> learnedRecipes = new ArrayList<Recipes>();
				ConfigurationSection learnedRecipesSection = skillsconfig.getConfigurationSection("learnedRecipes");
				
				for (RecipeTypes recipeType : RecipeTypes.values()) {
					if (learnedRecipesSection.contains(recipeType.name())) {
						learnedRecipesSection.getStringList(recipeType.name()).forEach(recipeName -> {
							learnedRecipes.add(Recipes.valueOf(recipeName));
						});
					}
				}
				if (learnedRecipes.contains(recipe)) {
					List<Recipes> uses = recipe.getUses(plugin);
					
					String inventoryName = ChatColor.GOLD + recipe.toString() + " Uses";
					Inventory inventory = Bukkit.createInventory(null, ((uses.size() / 9) + 2) * 9, inventoryName);
	
					
					
					
					for (int i = 0; i < uses.size(); i++) {
						if (learnedRecipes.contains(uses.get(i))) { 
							ItemStack item = uses.get(i).getResult(plugin);
							item.setAmount(1);
							inventory.setItem(i, item);
						}
					}
					
					inventory.setItem(((uses.size() / 9) + 1) * 9, plugin.itemManager.getBackItem());
					player.openInventory(inventory);
				}
			}
			
		}
	}
	
	private HashMap<Material, Integer> getIngredients(HashMap<Material, Integer> ingredients, Recipes recipe) {
		HashMap<Recipes, Integer> subRecipes = new HashMap<Recipes, Integer>();
		for (Object obj : recipe.getUnresolvedPattern()) {
			if (obj instanceof ItemStack) {
				ItemStack item = (ItemStack) obj;
				if (ingredients.containsKey(item.getType())) {
					ingredients.put(item.getType(), 1 + ingredients.get(item.getType()));
				} else {
					ingredients.put(item.getType(), 1);
				}
			} else if (obj instanceof Recipes) {
				Recipes currentRecipe = (Recipes) obj;
				if (subRecipes.containsKey(currentRecipe)) {
					subRecipes.put(currentRecipe, 1 + subRecipes.get(currentRecipe));
				} else {
					subRecipes.put(currentRecipe, 1);
				}
			}
		}
		
		for (Recipes currentRecipe : subRecipes.keySet()) {
			double neededCrafts = (subRecipes.get(currentRecipe) * 1.0) / (currentRecipe.getResult(plugin).getAmount() * 1.0);
			
			for (int i = 0; i < Math.ceil(neededCrafts); i++) {
				ingredients = getIngredients(ingredients, currentRecipe);
			}
		}
		
		return ingredients;
	}
	
	private String materialString(String matName) {
		String[] words = matName.split("_");
		String name = "";
		for (String word : words) {
			name = name + word.substring(0, 1) + word.substring(1).toLowerCase() + " ";
		}
		return name.substring(0, name.length() - 1);
	}

}
