package org.baugindustries.baugrpg;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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
		
		if (plugin.taxDwarvesEscape == p.getUniqueId()) {
			if (plugin.isDouble(event.getMessage())) {
				double taxAmount = Double.parseDouble(event.getMessage());
				if (taxAmount > 5 || taxAmount < 0) {
					p.sendMessage(ChatColor.RED + "Please enter a number between 0 and 5.");
				} else {
					p.sendMessage(ChatColor.GOLD + "You are now taxing your citizens " + taxAmount + "%.");
					plugin.taxDwarvesEscape = null;
					File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
					FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
					leaderConfig.set("taxAmount", taxAmount / 100);
					try {
						leaderConfig.save(leaderDataFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} else {
				p.sendMessage(ChatColor.RED + "Please enter a number");
			}
			event.setCancelled(true);
		} else if (plugin.signChatEscape.containsKey(p)) {
			int step = plugin.signChatEscape.get(p);
			switch (step) {
				case 1:
					String shopname = event.getMessage();
					p.sendMessage(ChatColor.GOLD + "<Shop Name Input>: " + ChatColor.YELLOW + shopname);
					List<String> signData = new ArrayList<String>();
					signData.add(shopname);
					plugin.signData.put(p, signData);
					p.sendMessage(ChatColor.GREEN + "How much do you want people to buy the item for? >");
					plugin.signChatEscape.put(p, 2);
					break;
				case 2:
					if (plugin.isInteger(event.getMessage())) {
						int buyAmount = Math.abs(Integer.parseInt(event.getMessage()));
						p.sendMessage(ChatColor.GOLD + "<Purchase Amount Input>: " + ChatColor.YELLOW + buyAmount);
						signData = plugin.signData.get(p);
						signData.add("Buy: " + buyAmount);
						plugin.signData.put(p, signData);
						p.sendMessage(ChatColor.GREEN + "How much do you want people to sell the item for? >");
						plugin.signChatEscape.put(p, 3);
					} else {
						p.sendMessage(ChatColor.GREEN + "Thats not a number... Try again >");
					}
					break;
				case 3:
					if (plugin.isInteger(event.getMessage())) {
						int sellAmount = Math.abs(Integer.parseInt(event.getMessage()));
						p.sendMessage(ChatColor.GOLD + "<Sell Amount Input>: " + ChatColor.YELLOW + sellAmount);
						signData = plugin.signData.get(p);
						signData.add("Sell: " + sellAmount);
						plugin.signData.put(p, signData);
						p.sendMessage(ChatColor.GREEN + "Right click the sign with the item you want to trade.");
						plugin.signChatEscape.remove(p);
					} else {
						p.sendMessage(ChatColor.GREEN + "Thats not a number... Try again >");
					}
					break;
					
			}
			event.setCancelled(true);
		} else if (plugin.lawSuggestionEscape.contains(p.getUniqueId())) {
			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);

			PersistentDataContainer data = p.getPersistentDataContainer();
			int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			
			String raceString = null;
			switch (race) {
				case 1:
					raceString = "men";
					break;
				case 2:
					raceString = "elf";
					break;
				case 3:
					raceString = "dwarf";
					break;
				case 4:
					raceString = "orc";
					break;
			}
			
			if (!leaderConfig.contains(raceString + "LawSuggestions")) {
				List<String> suggestionList = new ArrayList<String>();
				suggestionList.add(event.getMessage());
				leaderConfig.set(raceString + "LawSuggestions", suggestionList);
			} else {
				List<String> suggestionList = leaderConfig.getStringList(raceString + "LawSuggestions");
				suggestionList.add(event.getMessage());
				leaderConfig.set(raceString + "LawSuggestions", suggestionList);
			}
			
			p.sendMessage(ChatColor.GOLD + "<Suggestion Input>: " + ChatColor.YELLOW + event.getMessage());
			
			
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.lawSuggestionEscape.remove(p.getUniqueId());
			
			event.setCancelled(true);
		} else if (plugin.reportCrimeEscape.contains(p.getUniqueId())) {
			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);

			PersistentDataContainer data = p.getPersistentDataContainer();
			int race = data.get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER);
			
			String raceString = null;
			switch (race) {
				case 1:
					raceString = "men";
					break;
				case 2:
					raceString = "elf";
					break;
				case 3:
					raceString = "dwarf";
					break;
				case 4:
					raceString = "orc";
					break;
			}
			
			if (!leaderConfig.contains(raceString + "ReportedCrimes")) {
				List<String> suggestionList = new ArrayList<String>();
				suggestionList.add(event.getMessage());
				leaderConfig.set(raceString + "ReportedCrimes", suggestionList);
			} else {
				List<String> suggestionList = leaderConfig.getStringList(raceString + "ReportedCrimes");
				suggestionList.add(event.getMessage());
				leaderConfig.set(raceString + "ReportedCrimes", suggestionList);
			}
			
			p.sendMessage(ChatColor.GOLD + "<Reported Crime>: " + ChatColor.YELLOW + event.getMessage());
			
			
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.reportCrimeEscape.remove(p.getUniqueId());
			
			event.setCancelled(true);
		} else {
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
}
