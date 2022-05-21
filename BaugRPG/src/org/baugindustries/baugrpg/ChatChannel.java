package org.baugindustries.baugrpg;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
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
		int race = plugin.getRace(p);
		
		if (plugin.resetRaceEscape.contains(p.getUniqueId())) {
			event.setCancelled(true);
			if (event.getMessage().toLowerCase().equals("exit")) {
				plugin.resetRaceEscape.remove(p.getUniqueId());
				p.sendMessage(ChatColor.GOLD + "Returning to the chat.");
			} else if (event.getMessage().equals("CONFIRM " + p.getName())) {
				p.sendMessage(ChatColor.GOLD + "Race Reset.");
				
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					  public void run() {
							p.teleport(plugin.getServer().getWorlds().get(0).getSpawnLocation());
							p.openInventory(plugin.inventoryManager.getSetRaceMenuInventory());
					  }
				 }, 1L);
				
				
				
				
				PersistentDataContainer data = p.getPersistentDataContainer();
				if (data.has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER)) {
					data.remove(new NamespacedKey(plugin, "Race"));
					plugin.board.getTeam(plugin.board.getEntryTeam(p.getName()).getName()).removeEntry(p.getName());//removes player from team they're currently on
				}
				
				
				p.getInventory().clear();
				p.getEnderChest().clear();
				
				
				File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + p.getUniqueId() + ".yml");
			 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
			 	
			 	int newPoints = 0;
			 	if (skillsconfig.contains("skillPoints")) {
			 		newPoints = skillsconfig.getInt("skillPoints") / 2;
			 	}
			 	
			 	FileConfiguration newSkillsConfig = new YamlConfiguration();
			 	newSkillsConfig.set("skillPoints", newPoints);
			 	newSkillsConfig.set("totalSkillPoints", newPoints);
			 	newSkillsConfig.set("racereset", true);
			 	try {
					newSkillsConfig.save(skillsfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 	
				p.setSaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 10);
				p.setUnsaturatedRegenRate(plugin.onJoinListener.getSaturationSlownessMultiplier() * 80);
				p.setWalkSpeed(0.2f);
				p.setFlySpeed(0.2f);
				
				
				File bankfile = new File(plugin.getDataFolder() + File.separator + "bank.yml");
			 	FileConfiguration bankconfig = YamlConfiguration.loadConfiguration(bankfile);
			 	if (bankconfig.contains(p.getUniqueId().toString())) {
			 		bankconfig.set(p.getUniqueId().toString(), 0);
			 	}
			 	try {
					bankconfig.save(bankfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 	
			 	
			 	File claimsfile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
			 	FileConfiguration claimsconfig = YamlConfiguration.loadConfiguration(claimsfile);
			 	if (claimsconfig.contains("personalClaims")) {
			 		ConfigurationSection claims = claimsconfig.getConfigurationSection("personalClaims");
			 		for (String claimKey : claims.getKeys(false)) {
			 			ConfigurationSection claim = claims.getConfigurationSection(claimKey);
			 			if (claim.getString("owner").equals(p.getUniqueId().toString())) {
			 				claims.set(claimKey, null);
			 			}
			 			
			 		}
			 	}
			 	try {
					claimsconfig.save(claimsfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 	
			 	
			 	File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
			 	FileConfiguration econconfig = YamlConfiguration.loadConfiguration(econfile);
			 	if (econconfig.contains(p.getUniqueId().toString())) {
			 		econconfig.set(p.getUniqueId().toString(), 0);
			 	}
			 	try {
					econconfig.save(econfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 	
			 	File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
			 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
			 	for (String shopName : signconfig.getKeys(false)) {
			 		ConfigurationSection shop = signconfig.getConfigurationSection(shopName);
			 		if (shop.getString("owner").equals(p.getUniqueId().toString())) {
			 			signconfig.set(shopName, null);
			 		}
			 	}
			 	try {
					signconfig.save(signfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			 	
			 	File toolsfile = new File(plugin.getDataFolder() + File.separator + "tools.yml");
			 	FileConfiguration toolsconfig = YamlConfiguration.loadConfiguration(toolsfile);
			 	if (toolsconfig.contains(p.getUniqueId().toString())) {
			 		toolsconfig.set(p.getUniqueId().toString(), null);
			 	}
			 	try {
			 		toolsconfig.save(toolsfile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				
			}
		} else if (plugin.leaderPunishEscape.containsKey(p.getUniqueId())) {
			Integer index = plugin.leaderPunishEscape.get(p.getUniqueId());
			if (event.getMessage().toLowerCase().equals("exit")) {
				plugin.leaderPunishEscape.remove(p.getUniqueId());
				p.sendMessage(ChatColor.GOLD + "Returning to the chat.");
			} else if (event.getMessage().toLowerCase().equals("next")) {
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					unvotedCrimes.add(crimeString);
				});
				
				index++;
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
				String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
				String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
				
				p.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
						+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
				
				String infoString = "";
				switch (race) {
				case 1:
					infoString = ChatColor.GOLD + "\nHow many claim blocks do you want to revoke from " + defendant + "and award to" + plaintiff + "?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-200. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					break;
				case 3:
					infoString = ChatColor.GOLD + "\nWhat percentage of " + defendant + "'s wealth do you want to award " + plaintiff + "?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 0-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					break;
				case 4:
					infoString = ChatColor.GOLD + "\nHow many executions do you want to award " + plaintiff + "?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					break;
				}
				p.sendMessage(infoString);
				
				plugin.leaderPunishEscape.put(p.getUniqueId(), index);
			} else if (event.getMessage().toLowerCase().equals("back")) {
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					unvotedCrimes.add(crimeString);
				});
				
				index--;
				if (index < 0) {
					index = unvotedCrimes.size() - 1;
				}
				String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
				String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
				String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
				
				p.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
						+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
				
				String infoString = "";
				switch (race) {
				case 1:
					infoString = ChatColor.GOLD + "\nHow many claim blocks do you want to revoke from " + defendant + "and award to" + plaintiff + "?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-200. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					break;
				case 3:
					infoString = ChatColor.GOLD + "\nWhat percentage of " + defendant + "'s wealth do you want to award " + plaintiff + "?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 0-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					break;
				case 4:
					infoString = ChatColor.GOLD + "\nHow many executions do you want to award " + plaintiff + "?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					break;
				}
				p.sendMessage(infoString);
				
				plugin.leaderPunishEscape.put(p.getUniqueId(), index);
			} else if (plugin.isInteger(event.getMessage()) && ((race == 1 && Integer.parseInt(event.getMessage()) > 0 && Integer.parseInt(event.getMessage()) < 201) || (race == 4 && Integer.parseInt(event.getMessage()) > 0 && Integer.parseInt(event.getMessage()) < 6)) || (race == 3 && plugin.isDouble(event.getMessage()) && Double.parseDouble(event.getMessage()) > 0 && Double.parseDouble(event.getMessage()) <= 5)) {
				String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
				
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				
				ConfigurationSection currentCrime = crimes.getConfigurationSection(crimes.getKeys(false).toArray(new String[crimes.getKeys(false).size()])[index]);//This is so many fucking steps. omg

				String currentDefendantUUID = currentCrime.getString("defendant");
				String currentPlaintiffUUID = currentCrime.getString("plaintiff");
				switch (race) {
				case 1:
					ConfigurationSection claimBlocks;
					if (leaderConfig.contains("claimBlocks")) {
						claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
					} else {
						claimBlocks = leaderConfig.createSection("claimBlocks");
					}
					
					if (claimBlocks.contains(currentPlaintiffUUID)) {
						claimBlocks.set(currentPlaintiffUUID, claimBlocks.getInt(currentPlaintiffUUID) + Integer.parseInt(event.getMessage()));
					} else {
						claimBlocks.set(currentPlaintiffUUID, plugin.startingClaimBlocks + Integer.parseInt(event.getMessage()));
					}
					
					if (claimBlocks.contains(currentDefendantUUID)) {
						claimBlocks.set(currentDefendantUUID, claimBlocks.getInt(currentDefendantUUID) - Integer.parseInt(event.getMessage()));
					} else {
						claimBlocks.set(currentDefendantUUID, plugin.startingClaimBlocks - Integer.parseInt(event.getMessage()));
					}
					
					if (Bukkit.getOfflinePlayer(UUID.fromString(currentPlaintiffUUID)).isOnline()) {
				 		((Player)Bukkit.getOfflinePlayer(UUID.fromString(currentPlaintiffUUID))).sendMessage(ChatColor.GOLD + "The criminal you reported has been punished, and you have been awarded " + Integer.parseInt(event.getMessage()) + " claim blocks");
				 	}
				 	
				 	if (Bukkit.getOfflinePlayer(UUID.fromString(currentDefendantUUID)).isOnline()) {
				 		((Player)Bukkit.getOfflinePlayer(UUID.fromString(currentDefendantUUID))).sendMessage(ChatColor.RED + "You have been found guilty, and have been fined " + Integer.parseInt(event.getMessage()) + " claim blocks");
				 	}
					
					break;
				case 3:
					File file = new File(plugin.getDataFolder() + File.separator + "bank.yml");
				 	FileConfiguration config = YamlConfiguration.loadConfiguration(file);
				 	
				 	int transferAmount = (int) (config.getInt(currentDefendantUUID) * (Double.parseDouble(event.getMessage()) / 100));
				 	
				 	config.set(currentPlaintiffUUID, config.getInt(currentPlaintiffUUID) + transferAmount);
				 	config.set(currentDefendantUUID, config.getInt(currentDefendantUUID) - transferAmount);
				 	
				 	if (Bukkit.getOfflinePlayer(UUID.fromString(currentPlaintiffUUID)).isOnline()) {
				 		((Player)Bukkit.getOfflinePlayer(UUID.fromString(currentPlaintiffUUID))).sendMessage(ChatColor.GOLD + "The criminal you reported has been punished, and you have been awarded " + transferAmount + " Dwarven Gold");
				 	}
				 	
				 	if (Bukkit.getOfflinePlayer(UUID.fromString(currentDefendantUUID)).isOnline()) {
				 		((Player)Bukkit.getOfflinePlayer(UUID.fromString(currentDefendantUUID))).sendMessage(ChatColor.RED + "You have been found guilty, and have been fined " + transferAmount + " Dwarven Gold");
				 	}
				 	
				 	try {
						config.save(file);
					} catch (IOException e) {
						e.printStackTrace();
					}
					break;
				case 4:
					ConfigurationSection executions;
					if (leaderConfig.contains("executions")) {
						executions = leaderConfig.getConfigurationSection("executions");
					} else {
						executions = leaderConfig.createSection("executions");
					}
					
					ConfigurationSection plaintiffExecutions;
					if (executions.contains(currentPlaintiffUUID)) {
						plaintiffExecutions = executions.getConfigurationSection(currentPlaintiffUUID);
					} else {
						plaintiffExecutions = executions.createSection(currentPlaintiffUUID);
					}
					
					if (plaintiffExecutions.contains(currentDefendantUUID)) {
						plaintiffExecutions.set(currentDefendantUUID, plaintiffExecutions.getInt(currentDefendantUUID) + Integer.parseInt(event.getMessage()));
					} else {
						plaintiffExecutions.set(currentDefendantUUID, Integer.parseInt(event.getMessage()));
					}
					
					if (Bukkit.getOfflinePlayer(UUID.fromString(currentPlaintiffUUID)).isOnline()) {
				 		((Player)Bukkit.getOfflinePlayer(UUID.fromString(currentPlaintiffUUID))).sendMessage(ChatColor.GOLD + "You won the trial you reported, and have been awarded " + ChatColor.DARK_RED + Integer.parseInt(event.getMessage()) + " executions." + ChatColor.GOLD + " You can redeem them in the governemnt menu using /bs.");
				 	}
					
					break;
				}
				
				crimes.set(currentCrime.getName(), null);
				
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					unvotedCrimes.add(crimeString);
				});
				
				
				
				if (unvotedCrimes.size() != 0) {
					if (index >= unvotedCrimes.size()) {
						index = 0;
					}
					
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
							+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
					
					String infoString = "";
					switch (race) {
						case 1:
							infoString = ChatColor.GOLD + "\nHow many claim blocks do you want to revoke from " + defendant + "and award to" + plaintiff + "?";
							infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-200. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
							break;
						case 3:
							infoString = ChatColor.GOLD + "\nWhat percentage of " + defendant + "'s wealth do you want to award " + plaintiff + "?";
							infoString = infoString + ChatColor.GREEN + "\nEnter a number between 0-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
							break;
						case 4:
							infoString = ChatColor.GOLD + "\nHow many executions do you want to award " + plaintiff + "?";
							infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
							break;
					}
					p.sendMessage(infoString);
				} else {
					p.sendMessage(ChatColor.RED + "There are currently no criminals to punish. Returning to the chat.");
					plugin.leaderPunishEscape.remove(p.getUniqueId());
				}
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				switch (race) {
					case 1:
						p.sendMessage(ChatColor.RED + "Enter a number between 1-200. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.");
						break;
					case 3:
						p.sendMessage(ChatColor.RED + "Enter a number between 0-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.");
						break;
					case 4:
						p.sendMessage(ChatColor.RED + "Enter a number between 1-5. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.");
						break;
					}
			}
				
			event.setCancelled(true);
		} else if (plugin.elfPunishEscape.containsKey(p.getUniqueId())) {
			Integer index = plugin.elfPunishEscape.get(p.getUniqueId());
			if (event.getMessage().toLowerCase().equals("exit")) {
				plugin.elfPunishEscape.remove(p.getUniqueId());
				p.sendMessage(ChatColor.GOLD + "Returning to the chat.");
			} else if (event.getMessage().toLowerCase().equals("next")) {
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(p.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				index++;
				if (index >= unvotedCrimes.size()) {
					index = 0;
				}
				String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
				String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
				String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
				
				p.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
						+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
				
				String infoString = ChatColor.GOLD + "\nHow many hours do you want to restrict " + defendant + "'s access to communal resources?";
				infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-48. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
				
				p.sendMessage(infoString);
				plugin.elfPunishEscape.put(p.getUniqueId(), index);
			} else if (event.getMessage().toLowerCase().equals("back")) {
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(p.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				index--;
				if (index < 0) {
					index = unvotedCrimes.size() - 1;
				}
				String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
				String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
				String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
				
				p.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
						+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
				
				String infoString = ChatColor.GOLD + "\nHow many hours do you want to restrict " + defendant + "'s access to communal resources?";
				infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-48. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
				
				p.sendMessage(infoString);
				plugin.elfPunishEscape.put(p.getUniqueId(), index);
			} else if (plugin.isInteger(event.getMessage()) && Integer.parseInt(event.getMessage()) > 0  && Integer.parseInt(event.getMessage()) < 49) {
				File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
				FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
				String crimeConfigString = plugin.getRaceString(race) + "GuiltyCrimes";
				
				ConfigurationSection crimes = leaderConfig.getConfigurationSection(crimeConfigString);
				ConfigurationSection currentCrime = crimes.getConfigurationSection(crimes.getKeys(false).toArray(new String[crimes.getKeys(false).size()])[index]);//This is so many fucking steps. omg

				currentCrime.set(p.getUniqueId().toString(), Integer.parseInt(event.getMessage()));
				
				String currentDefendantUUID = currentCrime.getString("defendant");
				
				if (currentCrime.getKeys(false).size() - 2 > plugin.getAllEligibleElves().size()) {
					String[] keys = currentCrime.getKeys(false).toArray(new String[currentCrime.getKeys(false).size()]);
					int totalAmount = 0;
					int c = 0;
					for (String key : keys) {
						if (!(key.equals("title") || key.equals("defendant") || key.equals("plaintiff"))) {
							c++;
							totalAmount += currentCrime.getInt(key);
						}
					}
					
					double punishmentHours = totalAmount / c;
					ConfigurationSection guiltyElvesCooldown;
					if (leaderConfig.contains("guiltyElvesCooldown")) {
						guiltyElvesCooldown = leaderConfig.getConfigurationSection("guiltyElvesCooldown");
					} else {
						guiltyElvesCooldown = leaderConfig.createSection("guiltyElvesCooldown");
					}
					guiltyElvesCooldown.set(currentDefendantUUID, System.currentTimeMillis() + (3600000 * punishmentHours));
					
					if (Bukkit.getOfflinePlayer(UUID.fromString(currentDefendantUUID)).isOnline()) {
				 		((Player)Bukkit.getOfflinePlayer(UUID.fromString(currentDefendantUUID))).sendMessage(ChatColor.RED + "You have been found guilty of a crime, and are now restricted from accessing communal resources for " + ChatColor.DARK_PURPLE + (int)punishmentHours + " hours.");
				 	}
					
					crimes.set(currentCrime.getName(), null);
				}
				
				List<String> unvotedCrimes = new ArrayList<String>();
				crimes.getKeys(false).forEach(crimeString -> {
					if (!crimes.getConfigurationSection(crimeString).contains(p.getUniqueId().toString())) {
						unvotedCrimes.add(crimeString);
					}
				});
				
				if (unvotedCrimes.size() != 0) {
					if (index >= unvotedCrimes.size()) {
						index = 0;
					}
					String crimeString = crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("title");
					String defendant = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("defendant"))).getName();
					String plaintiff = Bukkit.getOfflinePlayer(UUID.fromString(crimes.getConfigurationSection(unvotedCrimes.get(index)).getString("plaintiff"))).getName();
					
					p.sendMessage("\n \n \n \n \n \n \n \n \n \n \n \n \n" + ChatColor.GOLD + "DEFENDANT: " + defendant + ",    PLAINTIFF: " + plaintiff
							+ ChatColor.YELLOW + "\n \nCrime: " + crimeString + "\n ");
					
					String infoString = ChatColor.GOLD + "\nHow long do you want to restrict " + defendant + "'s access to communal resources?";
					infoString = infoString + ChatColor.GREEN + "\nEnter a number between 1-48. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.";
					
					p.sendMessage(infoString);
					plugin.elfPunishEscape.put(p.getUniqueId(), index);
				} else {
					p.sendMessage(ChatColor.RED + "There are no more criminals to decide punishments for. Returning to the chat.");
					plugin.elfPunishEscape.remove(p.getUniqueId());
				}
				try {
					leaderConfig.save(leaderDataFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				p.sendMessage(ChatColor.RED + "Enter a number between 1-48. Enter NEXT to view next dispute, BACK to go back, or EXIT to return to the chat.");
			}
			event.setCancelled(true);
		} else if (plugin.taxDwarvesEscape == p.getUniqueId()) {
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
		} else if (plugin.draftLawEscape.contains(p.getUniqueId())) {
			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);

			
			String raceString = null;
			switch (race) {
				case 1:
					raceString = "men";
					break;
				case 2:
					raceString = "elfDraft";
					break;
				case 3:
					raceString = "dwarf";
					break;
				case 4:
					raceString = "orc";
					break;
			}
			
			if (race == 2) {
				if (!leaderConfig.contains(raceString + "Laws")) {
					ConfigurationSection elfDraftLaws = leaderConfig.createSection(raceString + "Laws");
					ConfigurationSection bill = elfDraftLaws.createSection(event.getMessage().replaceAll("\\p{Punct}", ""));
					bill.set("title", event.getMessage());
				} else {
					ConfigurationSection elfDraftLaws = leaderConfig.getConfigurationSection(raceString + "Laws");
					ConfigurationSection bill = elfDraftLaws.createSection(event.getMessage().replaceAll("\\p{Punct}", ""));
					bill.set("title", event.getMessage());
				}
			} else {
			
				if (!leaderConfig.contains(raceString + "Laws")) {
					List<String> suggestionList = new ArrayList<String>();
					suggestionList.add(event.getMessage());
					leaderConfig.set(raceString + "Laws", suggestionList);
				} else {
					List<String> suggestionList = leaderConfig.getStringList(raceString + "Laws");
					suggestionList.add(event.getMessage());
					leaderConfig.set(raceString + "Laws", suggestionList);
				}
			}
			
			String optStr = "";
			if (race == 2) {
				optStr = "\nOnce this law gets enough votes, it will be passed.";
			}
			
			p.sendMessage(ChatColor.GOLD + "You've drafted a new law: " + ChatColor.YELLOW + event.getMessage() + optStr);
			
			
			
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			plugin.draftLawEscape.remove(p.getUniqueId());
			
			event.setCancelled(true);
		} else if (plugin.lawSuggestionEscape.contains(p.getUniqueId())) {
			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);

			
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
		} else if (plugin.reportCrimeEscape.containsKey(p.getUniqueId())) {
			File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
			FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);

			
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
			
			
			if (plugin.reportCrimeEscape.get(p.getUniqueId()) == 0) {
				
				plugin.reportCrimeMap.put(p.getUniqueId(), event.getMessage());
					
				p.sendMessage(ChatColor.GOLD + "<Reported Crime>: " + ChatColor.YELLOW + event.getMessage());
				p.sendMessage(ChatColor.YELLOW + "Enter the username of the player that committed the alleged crime.");
				
				
				plugin.reportCrimeEscape.put(p.getUniqueId(), 1);
				
			} else if (plugin.reportCrimeEscape.get(p.getUniqueId()) == 1) {
				
				
				UUID reportedUUID = null;
				for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
					if (player.getName().equals(event.getMessage()) ) {
						reportedUUID = player.getUniqueId();
						break;
					}
				}
				
				if (reportedUUID == null) {
					p.sendMessage(ChatColor.RED + "The username you entered does not exist. Please try again >");
				} else {
					
					if (race != plugin.getRace(Bukkit.getOfflinePlayer(reportedUUID))) {
						p.sendMessage(ChatColor.RED + "Reported player must be of the same race. Exiting crime reporting");
						
						plugin.reportCrimeEscape.remove(p.getUniqueId());
						plugin.reportCrimeMap.remove(p.getUniqueId());
						event.setCancelled(true);
						return;
					}
					
					p.sendMessage(ChatColor.GOLD + "Player reported.");ConfigurationSection allCrimes;
					if (!leaderConfig.contains(raceString + "ReportedCrimes")) {
						allCrimes = leaderConfig.createSection(raceString + "ReportedCrimes");
					} else {
						allCrimes = leaderConfig.getConfigurationSection(raceString + "ReportedCrimes");
					}
					ConfigurationSection crime = allCrimes.createSection(plugin.reportCrimeMap.get(p.getUniqueId()).replaceAll("\\p{Punct}", ""));
					crime.set("title", plugin.reportCrimeMap.get(p.getUniqueId()));
					crime.set("plaintiff", p.getUniqueId().toString());
					crime.set("defendant", reportedUUID.toString());
					
					try {
						leaderConfig.save(leaderDataFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					plugin.reportCrimeEscape.remove(p.getUniqueId());
					plugin.reportCrimeMap.remove(p.getUniqueId());
					

					File reportCrimeCooldownFile = new File(plugin.getDataFolder() + File.separator + "reportCrimeCooldownData.yml");
					FileConfiguration reportCrimeCooldownConfig = YamlConfiguration.loadConfiguration(reportCrimeCooldownFile);
					
					reportCrimeCooldownConfig.set(p.getUniqueId().toString(), System.currentTimeMillis());
					try {
						reportCrimeCooldownConfig.save(reportCrimeCooldownFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
				
			event.setCancelled(true);
		} else {
			PersistentDataContainer data = p.getPersistentDataContainer();
			race = 0;
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
