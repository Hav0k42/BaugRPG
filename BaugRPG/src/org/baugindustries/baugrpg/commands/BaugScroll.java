package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ChooseRaceInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves.ScrollsOfBaugDwarvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves.ScrollsOfBaugElvesInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Men.ScrollsOfBaugMenInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Orcs.ScrollsOfBaugOrcsInventoryListener;
import org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Wizards.ScrollsOfBaugWizardsInventoryListener;
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
					
					
					inventory.setItem(0, plugin.createItem(
							Material.NETHER_STAR,
							1,
							ChatColor.GOLD + "Scrolls of Baug", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "all information you need to know to play on this server")));
					
					inventory.setItem(11, plugin.createItem(
							Material.OAK_SAPLING,
							1,
							ChatColor.GOLD + "Skill Tree",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race.")));
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugMenInventoryListener(plugin), plugin);
					
				} else if (race == 2) {//Elves
					
					inventory.setItem(0, plugin.createItem(
							Material.NETHER_STAR,
							1,
							ChatColor.GOLD + "Scrolls of Baug", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "all information you need to know to play on this server")));
					
					inventory.setItem(11, plugin.createItem(
							Material.OAK_SAPLING,
							1,
							ChatColor.GOLD + "Skill Tree",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race.")));
					
					inventory.setItem(12, plugin.createItem(
							Material.CHEST,
							1,
							ChatColor.GOLD + "Shared Inventories",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Access fellow Elves' inventories and ender chests")));
					
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugElvesInventoryListener(plugin), plugin);
					
				} else if (race == 3) {//Dwarves
					
					inventory.setItem(0, plugin.createItem(
							Material.NETHER_STAR,
							1,
							ChatColor.GOLD + "Scrolls of Baug", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "all information you need to know to play on this server")));
					
					inventory.setItem(11, plugin.createItem(
							Material.OAK_SAPLING,
							1,
							ChatColor.GOLD + "Skill Tree",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race.")));
					
					inventory.setItem(12, plugin.createItem(
							Material.GOLD_INGOT,
							1,
							ChatColor.GOLD + "Bank Deposit",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Convert gold ingots to Dwarven Gold.")));
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugDwarvesInventoryListener(plugin), plugin);
					
				} else if (race == 4) {//Orcs
					
					inventory.setItem(0, plugin.createItem(
							Material.NETHER_STAR,
							1,
							ChatColor.GOLD + "Scrolls of Baug", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "This menu gives you access to any and", "all information you need to know to play on this server")));

					inventory.setItem(11, plugin.createItem(
							Material.OAK_SAPLING,
							1,
							ChatColor.GOLD + "Skill Tree",
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Upgrade active and passive skills", "based on your class and race.")));

					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugOrcsInventoryListener(plugin), plugin);
					
				} else if (race == 5) {//Wizards
					
					
					inventory.setItem(0, plugin.createItem(
							Material.NETHER_STAR,
							1,
							ChatColor.GOLD + "Scrolls of Baug", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Welcome to the power menu.", "", "This menu gives you access to every feature", "given to the other races on this server", "You can use these to spy on your subjects",
									"and play god as you please", "", "Your job is to control the populous without them knowing.", "You are the final boss of the BaugRPG")));
					
					
					inventory.setItem(11, plugin.createItem(
							Material.WRITABLE_BOOK,
							1,
							ChatColor.AQUA + "Feature Management", 
							Arrays.asList(ChatColor.LIGHT_PURPLE + "Turn certain features on and off,", "according to how you wish to run your server.")));
					
					
					inventory.setItem(12, plugin.createItem(
							Material.CHEST,
							1,
							ChatColor.AQUA + "Inventory Snooping", 
							Arrays.asList(ChatColor.LIGHT_PURPLE +  "Access every players' inventories and ender chests")));
					
					player.openInventory(inventory);
					
					plugin.getServer().getPluginManager().registerEvents(new ScrollsOfBaugWizardsInventoryListener(plugin), plugin);
					
				}
				
				
				
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return true;
	}
}
