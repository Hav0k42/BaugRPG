package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class PlayerDeathListener implements Listener{

	
	private Main plugin;
	public PlayerDeathListener(Main plugin) {
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
			
			if (plugin.orcVictim.equals(player.getUniqueId())) {
				plugin.orcVictim = null;
			}
		} else if (plugin.orcVictim.equals(player.getUniqueId())) {
			event.setDeathMessage(ChatColor.DARK_RED + player.getDisplayName() + ChatColor.WHITE + " tried to escape the inevitable.");
		}
		
		
		int race = player.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
	    
	    switch (race) {
	    	case 0://normie
	    		break;
	    	case 1://man
	    		break;
	    	case 2://elf
	    		if (player.getLastDamageCause().getCause().equals(DamageCause.CUSTOM)) {
    				event.setDeathMessage(ChatColor.DARK_GREEN + player.getDisplayName() + ChatColor.WHITE + " attempted to enter the Nether unprotected.");
    			}
	    		break;
	    	case 3://dwarf
	    		break;
	    	case 4://orc
	    		if (player.getLocation().getBlock().getLightFromSky() != 0) {
	    			if (player.getLastDamageCause().getCause().equals(DamageCause.FIRE_TICK)) {
	    				event.setDeathMessage(ChatColor.DARK_RED + player.getDisplayName() + ChatColor.WHITE + " attempted to venture into the sun");
	    			}
	    		}
	    		
	    		break;
	    	case 5://wizard
	    		break;
	    }
	}
	
	
}
