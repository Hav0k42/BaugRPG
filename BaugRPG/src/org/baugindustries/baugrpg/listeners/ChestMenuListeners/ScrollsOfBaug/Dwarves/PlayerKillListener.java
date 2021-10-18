package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerKillListener implements Listener{

	
	private Main plugin;
	public PlayerKillListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void PlayerDeathEvent(PlayerDeathEvent event) {
		Player player = event.getEntity();
		if (player.getKiller() != null) {
			Player killer = (Player) player.getKiller();
			
			PersistentDataContainer playerData = player.getPlayer().getPersistentDataContainer();
			PersistentDataContainer killerData = killer.getPlayer().getPersistentDataContainer();
			int playerRace = -1;
			int killerRace = -1;
			if (playerData.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				playerRace = playerData.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			}
			if (killerData.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
				killerRace = killerData.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			}
			
			if (killerRace == 3 && playerRace == 2) {//Dwarf killed an elf
				killer.getWorld().dropItemNaturally(player.getLocation(), plugin.createItem(Material.GOLD_INGOT, 1 + (int)(Math.random() * 3)));
			}
		}
	}
	
	
}
