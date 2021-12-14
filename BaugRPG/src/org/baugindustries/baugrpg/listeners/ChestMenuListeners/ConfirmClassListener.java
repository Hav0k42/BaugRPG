package org.baugindustries.baugrpg.listeners.ChestMenuListeners;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ConfirmClassListener implements Listener {

	
	private Main plugin;
	public ConfirmClassListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (event.getClickedInventory() == null) return;
		if (!event.getView().getTitle().equals("Confirm Class Selection")) return;
		
		Player player = (Player)event.getWhoClicked();
		if (event.getSlot() == 15 && event.getCurrentItem().equals(plugin.createItem(Material.RED_STAINED_GLASS_PANE, 1, ChatColor.RED + "No", null))) {//No
			PersistentDataContainer data = player.getPersistentDataContainer();
			int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			int inventorySize = 27;
			String inventoryName = "Choose your Class";
			Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
			switch (race) {
				case 1:
					inventory.setItem(11, plugin.createItem(
							Material.LEATHER_HORSE_ARMOR, 
							1, 
							ChatColor.DARK_AQUA + "Stable Master"));
					
					inventory.setItem(13, plugin.createItem(
							Material.IRON_CHESTPLATE, 
							1, 
							ChatColor.DARK_AQUA + "Steeled Armorer"));
					
					inventory.setItem(15, plugin.createItem(
							Material.STICK, 
							1, 
							ChatColor.DARK_AQUA + "Verdant Shepherd"));
					break;
					
				case 2:
					inventory.setItem(11, plugin.createItem(
							Material.LILY_OF_THE_VALLEY, 
							1, 
							ChatColor.DARK_GREEN + "Enchanted Botanist"));
					
					inventory.setItem(13, plugin.createItem(
							Material.NETHER_STAR, 
							1,
							ChatColor.DARK_GREEN + "Lunar Artificer"));
					
					inventory.setItem(15, plugin.createItem(
							Material.ANVIL, 
							1, 
							ChatColor.DARK_GREEN + "Woodland Craftsman"));
					break;
					
				case 3:
					inventory.setItem(11, plugin.createItem(
							Material.BLAST_FURNACE, 
							1, 
							ChatColor.DARK_PURPLE + "Radiant Metallurgist"));
					
					inventory.setItem(13, plugin.createItem(
							Material.DIAMOND, 
							1, 
							ChatColor.DARK_PURPLE + "Arcane Jeweler"));
					
					inventory.setItem(15, plugin.createItem(
							Material.GOLDEN_PICKAXE, 
							1, 
							ChatColor.DARK_PURPLE + "Gilded Miner"));
					break;
					
				case 4:
					inventory.setItem(11, plugin.createItem(
							Material.POTION, 
							1, 
							ChatColor.DARK_RED + "Dark Alchemist"));
					
					inventory.setItem(13, plugin.createItem(
							Material.NETHERITE_AXE, 
							1, 
							ChatColor.DARK_RED + "Enraged Berserker"));
					
					inventory.setItem(15, plugin.createItem(
							Material.NETHERITE_SCRAP, 
							1, 
							ChatColor.DARK_RED + "Greedy Scrapper"));
					break;
			}
			
			player.openInventory(inventory);
		} else if (event.getSlot() == 11 && event.getCurrentItem().equals(plugin.createItem(Material.LIME_STAINED_GLASS_PANE, 1, ChatColor.GREEN + "Yes", null))) {//Yes
			ItemMeta raceInfoMeta = event.getInventory().getItem(4).getItemMeta();
			String selectedClass = raceInfoMeta.getLore().get(0);
			selectedClass = selectedClass.substring(36, selectedClass.length() - 1);
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		 	
		 	player.sendMessage(ChatColor.YELLOW + "You are now an apprentice " + selectedClass + ".\nYou have unlocked new skills and custom crafting items.");
		 	
		 	skillsconfig.set("class", selectedClass);
		 	try {
				skillsconfig.save(skillsfile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			player.closeInventory();
		}
		event.setCancelled(true);
		
	}
}