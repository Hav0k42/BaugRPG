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
					ItemStack backgroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
					ItemMeta backgroundItemMeta = backgroundItem.getItemMeta();
					backgroundItemMeta.setDisplayName(" ");
					backgroundItem.setItemMeta(backgroundItemMeta);
					inventory.setItem(i, backgroundItem);
				}
				
				
				ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
				ItemMeta infoItemMeta = infoItem.getItemMeta();
				infoItemMeta.setDisplayName(ChatColor.YELLOW + "Choose Your Race");
				List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "This race will determine the way you build", "the way you play, and the way you", "interact with players on this server.", "", "It cannot be changed later.");
				infoItemMeta.setLore(infoItemLore);
				infoItem.setItemMeta(infoItemMeta);
				inventory.setItem(13, infoItem);

				ItemStack chooseMenItem = new ItemStack(Material.NETHERITE_SWORD);
				ItemMeta chooseMenItemMeta = chooseMenItem.getItemMeta();
				chooseMenItemMeta.setDisplayName(ChatColor.DARK_AQUA + "Men");
				List<String> chooseMenItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the sword and horse combat,", "Men prefer to live in flat open areas", "with lots of room to roam about.");
				chooseMenItemMeta.setLore(chooseMenItemLore);
				chooseMenItem.setItemMeta(chooseMenItemMeta);
				inventory.setItem(28, chooseMenItem);
				
				ItemStack chooseElvesItem = new ItemStack(Material.BOW);
				ItemMeta chooseElvesItemMeta = chooseElvesItem.getItemMeta();
				chooseElvesItemMeta.setDisplayName(ChatColor.DARK_GREEN + "Elves");
				List<String> chooseElvesItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the bow and ranged combat", "Elves reside in wooded areas", "due to their love of nature.");
				chooseElvesItemMeta.setLore(chooseElvesItemLore);
				chooseElvesItem.setItemMeta(chooseElvesItemMeta);
				inventory.setItem(30, chooseElvesItem);
				
				ItemStack chooseDwarvesItem = new ItemStack(Material.NETHERITE_AXE);
				ItemMeta chooseDwarvesItemMeta = chooseDwarvesItem.getItemMeta();
				chooseDwarvesItemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Dwarves");
				List<String> chooseDwarvesItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the riches beneath the surface.");
				chooseDwarvesItemMeta.setLore(chooseDwarvesItemLore);
				chooseDwarvesItem.setItemMeta(chooseDwarvesItemMeta);
				inventory.setItem(32, chooseDwarvesItem);
				
				ItemStack chooseOrcsItem = new ItemStack(Material.NETHERITE_HELMET);
				ItemMeta chooseOrcsItemMeta = chooseOrcsItem.getItemMeta();
				chooseOrcsItemMeta.setDisplayName(ChatColor.DARK_RED + "Orcs");
				List<String> chooseOrcsItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of iron forged weaponry and brutal combat", "Orcs smoulder in the fires of hell", "for they cannot go to the surface.");
				chooseOrcsItemMeta.setLore(chooseOrcsItemLore);
				chooseOrcsItem.setItemMeta(chooseOrcsItemMeta);
				inventory.setItem(34, chooseOrcsItem);

				
				
				
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
