package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class ScrollsOfBaugDwarvesInventoryListener implements Listener{

	
	private Main plugin;
	public ScrollsOfBaugDwarvesInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (!(event.getWhoClicked() instanceof Player)) return;
		if (!(event.getClickedInventory() != null && event.getCurrentItem() != null)) return;
		if (!event.getView().getTitle().equals(plugin.inventoryManager.getRaceColor(3) + "Scrolls of Baug")) return;
		Player player = (Player)event.getWhoClicked();
		PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
		int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		if (race != 3) return;
		
		ItemStack depositItem = plugin.itemManager.getDwarvenBankConversionItem();
		
		ItemStack skillTreeItem = plugin.itemManager.getSkillTreeMenuItem();
		
		if (event.getCurrentItem().equals(skillTreeItem)) {
			
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
			
			if (skillsconfig.getInt("totalSkillPoints") > 19) {
				player.openInventory(plugin.inventoryManager.getSkillTreeMenuInventory(player));
			} else {
				player.openInventory(plugin.inventoryManager.getGeneralSkillTreeMenuInventory(player));
			}
			
			
		} else if (event.getCurrentItem().equals(depositItem)) {
			player.openInventory(plugin.inventoryManager.getGoldConversionMenuInventory());
		} else if (event.getCurrentItem().equals(plugin.itemManager.getGovernmentMenuItem(3))) {
			player.openInventory(plugin.inventoryManager.getGovernmentMenuInventory(player));
		}
		event.setCancelled(true);
	}
}
