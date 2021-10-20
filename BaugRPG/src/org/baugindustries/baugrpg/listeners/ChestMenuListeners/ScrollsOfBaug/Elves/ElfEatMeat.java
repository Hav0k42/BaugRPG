package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Elves;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.persistence.PersistentDataType;

public class ElfEatMeat implements Listener {
	
	private Main plugin;
	public ElfEatMeat(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerUse(PlayerItemConsumeEvent event) {
	    Player p = event.getPlayer();
	    
	    if (p.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 2) {//Check if the player is of the race of Elf
            
		    
		    Material[] meats = {Material.COOKED_BEEF, Material.COOKED_CHICKEN, Material.COOKED_MUTTON, Material.COOKED_PORKCHOP, Material.COOKED_RABBIT, Material.ROTTEN_FLESH, Material.BEEF, Material.CHICKEN, Material.MUTTON, Material.PORKCHOP, Material.RABBIT, Material.RABBIT_STEW};
		    
		    for (int i = 0; i < meats.length; i++) {
			    if(event.getItem().getType().equals(meats[i])) {
			    	
			    	event.setCancelled(true);
			    }
		    }
		}
	}
}
