package org.baugindustries.baugrpg.customitems.men.steeled_armorer.advanced;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;


public class BackpackListener implements Listener {
	private Main plugin;
	
	final int backpackSize = 18;
	
	public BackpackListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEvent event) {
		if (!Recipes.BACKPACK.matches(plugin, event.getItem())) return;
		
		if (event.getItem().getAmount() > 1) {
			event.getPlayer().sendMessage(ChatColor.RED + "Cannot open stacked backpacks.");
			return;
		}

		if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			event.getPlayer().sendMessage(ChatColor.RED + "Aight dog, so theres a duplication glitch that happens with the backpack, but only when you open it without clicking on a block. I have no idea why this happens, so the backpack is only gonna work if you click on a block to open it because I can't be bothered to actually figure out how to fix this it makes no goddamn sense.");
			return;
		}
		
		
		
		ItemStack backpack = event.getItem();

		
		ItemMeta backpackMeta = backpack.getItemMeta();
		
		File backpackfile = new File(plugin.getDataFolder() + File.separator + "backpackData.yml");
		FileConfiguration backpackConfig = YamlConfiguration.loadConfiguration(backpackfile);
		
		
		if (!backpackMeta.getPersistentDataContainer().has(new NamespacedKey(plugin, "backpackUUID"), PersistentDataType.INTEGER)) {
			int totalBackpacks = backpackConfig.getKeys(false).size();
			backpackMeta.getPersistentDataContainer().set(new NamespacedKey(plugin, "backpackUUID"), PersistentDataType.INTEGER, totalBackpacks + 1);
			backpack.setItemMeta(backpackMeta);
		}
		
		event.setCancelled(true);
		
		int backpackUUID = backpack.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(plugin, "backpackUUID"), PersistentDataType.INTEGER);
		
		Inventory backpackInv = Bukkit.createInventory(null, backpackSize, ChatColor.GOLD + "Backpack: " + backpackUUID);
		
		
		if (backpackConfig.contains(backpackUUID + "")) {
			ConfigurationSection backpackSection = backpackConfig.getConfigurationSection(backpackUUID + "");
			for (int i = 0; i < backpackSize; i++) {
				backpackInv.setItem(i, backpackSection.getItemStack(i + ""));
			}
		}
		
		
		event.getPlayer().openInventory(backpackInv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getCurrentItem() != null && (Recipes.BACKPACK.matches(plugin, event.getCurrentItem()) || event.getCurrentItem().getType().equals(Material.SHULKER_BOX)) && event.getView().getTitle().contains(ChatColor.GOLD + "Backpack: ")) {
			event.setCancelled(true);
			return;
		}
		
		if (event.getHotbarButton() != -1 && event.getWhoClicked().getInventory().getItem(event.getHotbarButton()) != null && (Recipes.BACKPACK.matches(plugin, event.getWhoClicked().getInventory().getItem(event.getHotbarButton())) || event.getWhoClicked().getInventory().getItem(event.getHotbarButton()).getType().equals(Material.SHULKER_BOX))) {
			event.setCancelled(true);
			return;
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryCloseEvent event) {
		if (!event.getView().getTitle().contains(ChatColor.GOLD + "Backpack: ")) return;
		
		String backpackUUIDStr = event.getView().getTitle().substring(12);
		
		File backpackfile = new File(plugin.getDataFolder() + File.separator + "backpackData.yml");
		FileConfiguration backpackConfig = YamlConfiguration.loadConfiguration(backpackfile);
		
		ConfigurationSection backpackSection;
		if (backpackConfig.contains(backpackUUIDStr)) {
			backpackSection = backpackConfig.getConfigurationSection(backpackUUIDStr);
		} else {
			backpackSection = backpackConfig.createSection(backpackUUIDStr);
		}
		
		for (int i = 0; i < backpackSize; i++) {
			backpackSection.set(i + "", event.getView().getTopInventory().getItem(i)); 
		}
		
		try {
			backpackConfig.save(backpackfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
