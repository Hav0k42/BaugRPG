package org.baugindustries.baugrpg;


import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ChatChannel implements Listener {

	Main plugin;
	
	public ChatChannel(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		plugin.channelManager.joinChannel(event.getPlayer(), "Global Chat");
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent event) {
		Player p = event.getPlayer();
		PersistentDataContainer data = p.getPersistentDataContainer();
		int race = 0;
		if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
			race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
		}
		switch (race) {
		case 0:
			event.setFormat("[Normie] " + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			break;
		case 1:
			event.setFormat(ChatColor.GOLD + "[Global Chat] " + ChatColor.DARK_AQUA + "[Man] " + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			break;
		case 2:
			event.setFormat(ChatColor.GOLD + "[Global Chat] " + ChatColor.DARK_GREEN + "[Elf] " + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			break;
		case 3:
			event.setFormat(ChatColor.GOLD + "[Global Chat] " + ChatColor.DARK_PURPLE + "[Dwarf] " + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			break;
		case 4:
			event.setFormat(ChatColor.GOLD + "[Global Chat] " + ChatColor.DARK_RED + "[Orc] " + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			break;
		case 5:
			event.setFormat(ChatColor.GOLD + "[Global Chat] " + ChatColor.AQUA + "[Wizard] " + ChatColor.YELLOW + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
			break;
		}
		
		
		if (!plugin.channelManager.getChannelName(p).equals("Global Chat")) {//player is in a race chat
			event.getRecipients().clear();
			plugin.channelManager.getChannel(p).stream().forEach(player -> event.getRecipients().add(player));
			for (Player player : plugin.getServer().getOnlinePlayers()) {
				PersistentDataContainer currentPlayerData = player.getPersistentDataContainer();
				int temprace = 0;
				
				if (currentPlayerData.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
					temprace = currentPlayerData.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
				}
				
				if (temprace == race || temprace == 5) {
					event.getRecipients().add(player);
					
					switch (race) {
					case 1:
						event.setFormat(ChatColor.GOLD + "[Man Chat] " + ChatColor.DARK_AQUA + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
						break;
					case 2:
						event.setFormat(ChatColor.GOLD + "[Elf Chat] " + ChatColor.DARK_GREEN + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
						break;
					case 3:
						event.setFormat(ChatColor.GOLD + "[Dwarf Chat] " + ChatColor.DARK_PURPLE + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
						break;
					case 4:
						event.setFormat(ChatColor.GOLD + "[Orc Chat] " + ChatColor.DARK_RED + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
						break;
					case 5:
						event.setFormat(ChatColor.GOLD + "[Wizard Chat] " + ChatColor.AQUA + p.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
						break;
					}
					
					
				}
			}
		}
		
	}
}
