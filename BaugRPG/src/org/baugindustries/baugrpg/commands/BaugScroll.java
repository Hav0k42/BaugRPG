package org.baugindustries.baugrpg.commands;

import org.bukkit.command.CommandExecutor;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

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
				File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
			 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
				int level = skillsconfig.getInt("totalSkillPoints");
				
				if (race == 1) {//Men
					
					
					inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
					
					if (level > 5) {
						inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
					}
					
					player.openInventory(inventory);
					
				} else if (race == 2) {//Elves
					
					inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
					
					if (level > 5) {
						inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
					}
					
					inventory.setItem(12, plugin.itemManager.getCommunistHubItem());
					
					
					player.openInventory(inventory);
					
				} else if (race == 3) {//Dwarves
					
					inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
					
					if (level > 5) {
						inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
					}
					
					inventory.setItem(12, plugin.itemManager.getDwarvenBankConversionItem());
					
					player.openInventory(inventory);
					
				} else if (race == 4) {//Orcs
					
					inventory.setItem(0, plugin.itemManager.getScrollsOfBaugInfoItem());
					
					if (level > 5) {
						inventory.setItem(11, plugin.itemManager.getSkillTreeMenuItem());
					}

					player.openInventory(inventory);
					
				} else if (race == 5) {//Wizards
					
					
					inventory.setItem(0, plugin.itemManager.getWizardScrollsOfBaugInfoItem());
					
					
					inventory.setItem(11, plugin.itemManager.getFeatureManagementItem());
					
					
					inventory.setItem(12, plugin.itemManager.getInventorySnoopingItem());
					
					player.openInventory(inventory);
					
				}
				
				
				
			}
			return true;
		} else {
			player.sendMessage("You do not have permission to execute this command");
			
		}
		return true;
	}
}
