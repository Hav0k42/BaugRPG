package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class OrcEatMeat implements Listener {
	private Main plugin;
	public OrcEatMeat(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerUse(PlayerItemConsumeEvent event) {
	    Player p = event.getPlayer();
	    
	    if (p.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 4) {//Check if the player is of the race of Elf
            
		    
		    Material[] meats = {Material.ROTTEN_FLESH, Material.BEEF, Material.CHICKEN, Material.MUTTON, Material.PORKCHOP, Material.RABBIT};
		    
		    Boolean acceptable = false;
		    for (int i = 0; i < meats.length; i++) {
			    if(event.getItem().getType().equals(meats[i])) {
			    	acceptable = true;
			    }
		    }
		    
		    if (!acceptable) {
		    	event.setCancelled(true);
		    } else {
		    	if (event.getItem().getType().equals(Material.ROTTEN_FLESH)) {
		    		p.setFoodLevel(p.getFoodLevel() + 5);
				    p.setSaturation(p.getSaturation() + 2.5f);
		    	}
		    	p.setFoodLevel(p.getFoodLevel() + 5);
			    p.setSaturation(p.getSaturation() + 2.5f);
			    ItemStack newItem = p.getItemInHand();
			    newItem.setAmount(newItem.getAmount() - 1);
			    p.setItemInHand(newItem);
		    	event.setCancelled(true);
		    }
		}
	}
}
