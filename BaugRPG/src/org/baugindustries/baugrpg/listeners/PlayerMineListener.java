package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;

public class PlayerMineListener implements Listener {
	private Main plugin;
	public PlayerMineListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerMineEvent(BlockDamageEvent event) {
		Player player = event.getPlayer();

		Float breakSpeed = event.getBlock().getBreakSpeed(player);
		Block block = event.getBlock();
		plugin.miningSpeed.put(player, breakSpeed);
	}
}
