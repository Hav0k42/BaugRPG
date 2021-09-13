package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.ChooseRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ScrollsOfBaugDwarvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ScrollsOfBaugElvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ScrollsOfBaugMenInventoryListener;
import org.baugindustries.baugrpg.listeners.ScrollsOfBaugOrcsInventoryListener;
import org.baugindustries.baugrpg.listeners.ScrollsOfBaugWizardsInventoryListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class BaugScroll implements CommandExecutor {
	
	private Main plugin;
	
	public BaugScroll(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("baugscroll").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		int race = 0;
		
		if (player.hasPermission("minecraft.command.help")) {//everyone has this command hopefully
			PersistentDataContainer data = player.getPersistentDataContainer();
			if (!data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {//Player is a normie, and therefore cannot open the Scrolls of Baug.
				player.sendMessage("You are not part of a race, join one using /setrace");
			} else {//Player is a part of a race and can read the baug scroll.

				int inventorySize = 54;
				String inventoryName = "Scrolls of Baug";
				Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
				
				
				race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
				
				if (race == 1) {//Men
					
					ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
					ItemMeta infoItemMeta = infoItem.getItemMeta();
					infoItemMeta.setDisplayName(ChatColor.GOLD + "Scrolls of Baug");
					List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "and all information you need to know to play on this server");
					infoItemMeta.setLore(infoItemLore);
					infoItem.setItemMeta(infoItemMeta);
					inventory.setItem(0, infoItem);
					
					
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugMenInventoryListener(plugin), plugin);
					
				} else if (race == 2) {//Elves
					
					ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
					ItemMeta infoItemMeta = infoItem.getItemMeta();
					infoItemMeta.setDisplayName(ChatColor.GOLD + "Scrolls of Baug");
					List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "and all information you need to know to play on this server");
					infoItemMeta.setLore(infoItemLore);
					infoItem.setItemMeta(infoItemMeta);
					inventory.setItem(0, infoItem);
					
					
					ItemStack sharedInventoriesItem = new ItemStack(Material.CHEST);
					ItemMeta sharedInventoriesItemMeta = infoItem.getItemMeta();
					sharedInventoriesItemMeta.setDisplayName(ChatColor.GOLD + "Shared Inventories");
					List<String> sharedInventoriesItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Access fellow Elves' inventories and ender chests");
					sharedInventoriesItemMeta.setLore(sharedInventoriesItemLore);
					sharedInventoriesItem.setItemMeta(sharedInventoriesItemMeta);
					inventory.setItem(11, sharedInventoriesItem);
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugElvesInventoryListener(plugin), plugin);
					
				} else if (race == 3) {//Dwarves
					
					ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
					ItemMeta infoItemMeta = infoItem.getItemMeta();
					infoItemMeta.setDisplayName(ChatColor.GOLD + "Scrolls of Baug");
					List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "and all information you need to know to play on this server");
					infoItemMeta.setLore(infoItemLore);
					infoItem.setItemMeta(infoItemMeta);
					inventory.setItem(0, infoItem);
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugDwarvesInventoryListener(plugin), plugin);
					
				} else if (race == 4) {//Orcs
					
					ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
					ItemMeta infoItemMeta = infoItem.getItemMeta();
					infoItemMeta.setDisplayName(ChatColor.GOLD + "Scrolls of Baug");
					List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "and all information you need to know to play on this server");
					infoItemMeta.setLore(infoItemLore);
					infoItem.setItemMeta(infoItemMeta);
					inventory.setItem(0, infoItem);
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugOrcsInventoryListener(plugin), plugin);
					
				} else if (race == 5) {//Wizards
					
					ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
					ItemMeta infoItemMeta = infoItem.getItemMeta();
					infoItemMeta.setDisplayName(ChatColor.GOLD + "Scrolls of Baug");
					List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Welcome to the power menu.", "", "This menu gives you access to every feature", "given to the other races on this server", "You can use these to spy on your subjects", "and play god as you please", "", "Your job is to control the populous without them knowing.", "You are the final boss of the BaugRPG");
					infoItemMeta.setLore(infoItemLore);
					infoItem.setItemMeta(infoItemMeta);
					inventory.setItem(0, infoItem);
					
					ItemStack featureManagementItem = new ItemStack(Material.WRITABLE_BOOK);
					ItemMeta featureManagementItemMeta = infoItem.getItemMeta();
					featureManagementItemMeta.setDisplayName(ChatColor.AQUA + "Feature Management");
					List<String> featureManagementItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Turn certain features on and off, according to how you wish to run your server.");
					featureManagementItemMeta.setLore(featureManagementItemLore);
					featureManagementItem.setItemMeta(featureManagementItemMeta);
					inventory.setItem(11, featureManagementItem);
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugWizardsInventoryListener(plugin), plugin);
					
				}
				
				
				
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return false;
	}
}
