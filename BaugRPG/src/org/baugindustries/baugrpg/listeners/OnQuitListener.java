package org.baugindustries.baugrpg.listeners;



import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnQuitListener implements Listener{
	
	private Main plugin;
	public OnQuitListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		
		//Save player's inventory to YML file
		Player player = event.getPlayer();
		
		//create file with player's UUID
		File file = new File(plugin.getDataFolder() + File.separator + "inventoryData" + File.separator + player.getUniqueId() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		//Check to see if the file already exists. If not, create it.
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		config.set("Username", player.getDisplayName());
		config.set("UUID", player.getUniqueId().toString());
		config.set("Inventory", player.getInventory().getContents());
		
		PersistentDataContainer data = player.getPlayer().getPersistentDataContainer();
		if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
			config.set("Race Data", data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER));
		}
		
			
		
		
		try {
			config.save(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
