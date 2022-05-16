package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards;

import java.util.HashMap;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class ClassDisplayedRecipeInventoryListener implements Listener {
	private Main plugin;
	public ClassDisplayedRecipeInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		boolean check = false;
		Profession currentProfession = null;
		for (Profession profession : Profession.values()) {
			if (event.getView().getTitle().equals(ChatColor.GOLD + profession.toString() + " Recipe")) {
				check = true;
				currentProfession = profession;
				break;
			}
		}
		
		if (!check) return;
		if (!(event.getView().getTopInventory().equals(event.getClickedInventory())) && event.getCursor() == null) return;
		Player player = (Player)event.getWhoClicked();
		event.setCancelled(true);
		if (event.getCurrentItem().equals(plugin.itemManager.getBackItem())) {
			player.openInventory(plugin.inventoryManager.getAllRecipesMenu(currentProfession));
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
