package org.baugindustries.baugrpg;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class ChatChannelManager {

	public HashMap<String, ArrayList<Player>> channels = new HashMap<String, ArrayList<Player>>();
	public HashMap<Player, String> playerChannel = new HashMap<Player, String>();
	
	//join channel
	public void joinChannel(Player player, String channelName) {
		if (playerChannel.get(player) != null) {
			String prevChannel = playerChannel.get(player);
			leaveChannel(player, prevChannel);
		}
		
		ArrayList<Player> players = channels.get(channelName);
		if (players == null) {
			players = new ArrayList<Player>();
		}
		players.add(player);
		channels.put(channelName, players);
		playerChannel.put(player, channelName);
		player.sendMessage(ChatColor.GREEN +  "You joined " + ChatColor.GOLD + channelName);
	}
	
	
	//leave channel
	public void leaveChannel(Player player, String channelName) {
		ArrayList<Player> players = channels.get(channelName);
		players.remove(player);
		channels.put(channelName, players);
		playerChannel.remove(player);
		
	}
	
	//return a list with players in a channel
	public ArrayList<Player> getChannel(Player player) {
		String channelName = playerChannel.get(player);
		return channels.get(channelName);
	}
	
	
	
	public String getChannelName(Player player) {
		String channelName = playerChannel.get(player);
		return channelName;
	}
	
}
