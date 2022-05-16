package org.baugindustries.baugrpg.customitems.orcs.dark_alchemist.intermediate;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.RayTrace;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;

public class WandOfDisplacementListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Long> cooldown = new HashMap<UUID, Long>();
	int cooldownTime = 75000;//75 seconds

	public WandOfDisplacementListener(Main plugin) {
		this.plugin = plugin;
		
	}
	
	
	@EventHandler
	public void onClickCopper(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.WAND_OF_DISPLACEMENT.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.WAND_OF_DISPLACEMENT.matches(plugin, player.getInventory().getItemInOffHand()))) return;
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
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) event.setCancelled(true);
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) event.setCancelled(true);
		
		
		RayTrace raytrace = new RayTrace(player.getLocation().toVector(), player.getLocation().getDirection());
		Vector[] positions = new Vector[50];
		positions = raytrace.traverse(50, 1).toArray(positions);
		
		
		for (int i = 0; i < positions.length; i++) {
			double distMult = 1;
			
			Location loc = positions[i].toLocation(player.getWorld());
//			player.sendMessage(loc.toString());
			for (Player bukkitPlayer : loc.getWorld().getPlayers()) {
				if (player.getWorld().getNearbyEntities(loc, distMult, distMult, distMult).contains(bukkitPlayer)) {
					
					if (plugin.getRace(player) != plugin.getRace(bukkitPlayer)) {
						Location newLocBukkit = player.getLocation();
						Location newLocPlayer = bukkitPlayer.getLocation();
						
						player.teleport(newLocPlayer);
						bukkitPlayer.teleport(newLocBukkit);
						
						cooldown.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime);
						return;
					}
					
				}
			}
		}
		
		player.sendMessage(ChatColor.RED + "No player found.");
		
	}

}
