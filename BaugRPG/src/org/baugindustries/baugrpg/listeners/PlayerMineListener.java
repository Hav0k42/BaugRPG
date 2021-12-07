package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;

public class PlayerMineListener implements Listener {
	private Main plugin;
	public PlayerMineListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerMineEvent(BlockDamageEvent event) {
		Player player = event.getPlayer();

		Float breakSpeed = event.getBlock().getBreakSpeed(player);
		plugin.miningSpeed.put(player, breakSpeed);
	}
}
