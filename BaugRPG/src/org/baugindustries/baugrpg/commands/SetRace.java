package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.listeners.ChooseRaceInventoryListener;
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

public class SetRace implements CommandExecutor {
	
	private Main plugin;
	
	public SetRace(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("setrace").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (player.hasPermission("minecraft.command.help")) {//everyone has this command hopefully
			PersistentDataContainer data = player.getPersistentDataContainer();
			if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				player.sendMessage("You already have a race assigned.");
			} else {

				int inventorySize = 54;
				String inventoryName = "Choose Your Race";
				Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
				
				for (int i = 0; i < inventorySize; i++) {
					inventory.setItem(i, plugin.createItem(Material.BLACK_STAINED_GLASS_PANE, 1, " ", null));
				}
				
				
				
				inventory.setItem(13, plugin.createItem(
						Material.NETHER_STAR, 
						1, 
						ChatColor.YELLOW + "Choose Your Race", 
						Arrays.asList(ChatColor.LIGHT_PURPLE + "This race will determine the way you build", "the way you play, and the way you", "interact with players on this server.", "", "It cannot be changed later.")));
				
				inventory.setItem(28, plugin.createItem(
						Material.NETHERITE_SWORD, 
						1, 
						ChatColor.DARK_AQUA + "Men", 
						Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the sword and horse combat,", "Men prefer to live in flat open areas", "with lots of room to roam about.")));
					
				inventory.setItem(30, plugin.createItem(
						Material.BOW, 
						1, 
						ChatColor.DARK_GREEN + "Elves", 
						Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the bow and ranged combat", "Elves reside in wooded areas", "due to their love of nature.")));
				
				inventory.setItem(32, plugin.createItem(
						Material.NETHERITE_AXE, 
						1, 
						ChatColor.DARK_PURPLE + "Dwarves", 
						Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the riches beneath the surface.")));
				
				inventory.setItem(34, plugin.createItem(
						Material.NETHERITE_HELMET, 
						1, 
						ChatColor.DARK_RED + "Orcs", 
						Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of iron forged weaponry and brutal combat", "Orcs smoulder in the fires of hell", "for they cannot go to the surface.")));
				
				
				
				player.openInventory(inventory);
				
				plugin.getServer().getPluginManager().registerEvents(new ChooseRaceInventoryListener(plugin), plugin);
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return false;
	}
}
