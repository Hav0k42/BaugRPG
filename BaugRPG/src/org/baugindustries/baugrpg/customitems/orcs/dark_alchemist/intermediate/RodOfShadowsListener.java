package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class RodOfShadowsListener implements Listener {
	
	Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 75000;//75 seconds
	
	public RodOfShadowsListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickGrass(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.ROD_OF_SHADOWS.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.ROD_OF_SHADOWS.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		if (cooldown.containsKey(player.getUniqueId())) {
			if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
				int secondsRemaining = (int) ((cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000) + 1;
				String timeString = " seconds remaining.";
				if (secondsRemaining == 1) {
					timeString = " second remaining.";
				}
				player.sendMessage(ChatColor.RED + "You can't use that yet. " + secondsRemaining + timeString);
				return;
			}
		}
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 600, 0, true, false));
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 0, true, false));

		cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
		
		
	}

}
