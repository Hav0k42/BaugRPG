package org.baugindustries.baugrpg.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.protection.ClaimData;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Claim implements CommandExecutor {

	private Main plugin;
	
	public Claim(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("claim").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		Player player = (Player)sender;
		
		int race = plugin.getRace(player);
		if (!(race == 1 || race == 3)) {
			player.sendMessage(ChatColor.RED + "Only men and elves are allowed to use /claim.");
			return true;
		}
		
		if (args.length == 0) {
			player.sendMessage(ChatColor.GOLD + "Claiming info:");
			player.sendMessage(" ");
			player.sendMessage(ChatColor.GOLD + "Claiming land allows you to protect it from modification by other players of your race. You are only able to claim land within your race's territory which can be viewed using /map.");
			player.sendMessage(ChatColor.GOLD + "If your race has not claimed any territory contact your leader about using /cc to claim land.");
			player.sendMessage(" ");
			player.sendMessage(ChatColor.GOLD + "In order to claim land you will need a golden shovel and a stick. You can use the stick to see if a block is available to be claimed. When you know where you want to claim, right click the corner of the area with a golden shovel.");
			player.sendMessage(" ");
			player.sendMessage(ChatColor.GOLD + "Once you've claimed your region you can use /claim delete to delete it, and /claim trust <Player> to allow another player to build in your claim. Collect experience to gain more claim blocks.");
			player.sendMessage(ChatColor.GOLD + "Available claim blocks: " + getPlayerClaimBlocks(player));
			return true;
		}
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		if (!claimsConfig.contains("personalClaims")) {
			claimsConfig.createSection("personalClaims");
		}
		
		String claimID = getBlockOwner(player.getLocation().getBlock());
		ConfigurationSection currentClaim;
		if (claimID == null) {
			player.sendMessage(ChatColor.RED + "You are not currently standing in one of your claims.");
			return true;
		} else {
			currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
			UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
			if (!ownerUUID.equals(player.getUniqueId())) {
				player.sendMessage(ChatColor.RED + "You are not currently standing in one of your claims.");
				return true;
			}
		}
		
		
		if (args.length == 1) {
			if (args[0].toLowerCase().equals("delete")) {
				ClaimData tempData = new ClaimData(player.getUniqueId(), currentClaim.getLocation("location1"));
				tempData.setLocation1(currentClaim.getLocation("location1"));
				tempData.setLocation2(currentClaim.getLocation("location2"));
				
				int area = tempData.getArea();
				
				player.sendMessage(ChatColor.YELLOW + "Claim deleted. Available claim blocks: " + (area + getPlayerClaimBlocks(player)) + ".");
				setPlayerClaimBlocks(player, area + getPlayerClaimBlocks(player));
				ConfigurationSection playerClaims = claimsConfig.getConfigurationSection("personalClaims");
				playerClaims.set(claimID, null);
				try {
					claimsConfig.save(claimsFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				return true;
			} else if (args[0].toLowerCase().equals("trust")) {
				player.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage is /claim trust <Player>");
				return true;
			} else if (args[0].toLowerCase().equals("untrust")) {
				player.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage is /claim untrust <Player>");
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "Unknown argument. Correct usage is /claim, /claim delete, /claim trust <Player>, or /claim untrust <Player>");
				return true;
			}
		}
		
		OfflinePlayer[] allOnlinePlayers = plugin.getServer().getOfflinePlayers();
		
		if (args.length >= 2) {
			if (args[0].toLowerCase().equals("trust")) {
				for (int i = 0; i < allOnlinePlayers.length; i ++) {
					if (args[1].toLowerCase().equals(allOnlinePlayers[i].getName().toLowerCase())) {
						if (allOnlinePlayers[i].getName().equals(player.getName())) {
							player.sendMessage(ChatColor.YELLOW + "Do you not trust yourself already?");
							return true;
						}
						
						ConfigurationSection trustedUUIDs;
						if (currentClaim.contains("trustedPlayers")) {
							trustedUUIDs = currentClaim.getConfigurationSection("trustedPlayers");
						} else {
							trustedUUIDs = currentClaim.createSection("trustedPlayers");
						}
						List<Integer> range = new ArrayList<Integer>();
						if (args.length > 2) {
							
							if (args.length != 4) {
								player.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage is: /claim trust <Player> <yLevel> <yLevel>");
								return true;
							}
							
							if (!(plugin.isInteger(args[2]) && plugin.isInteger(args[3]))) {
								player.sendMessage(ChatColor.RED + "Incorrect usage. yLevel values should be integers.");
								return true;
							}
							
							int lower = Integer.parseInt(args[2]);
							int higher = Integer.parseInt(args[3]);
							if (lower > higher) {
								player.sendMessage(ChatColor.RED + "Incorrect usage. First number should be less than the second.");
								return true;
							}
							if (lower > 255 || lower < -64) {
								player.sendMessage(ChatColor.RED + "Incorrect usage. First number is outside buildable range.");
								return true;
							}
							if (higher > 255 || higher < -64) {
								player.sendMessage(ChatColor.RED + "Incorrect usage. Second number is outside buildable range.");
								return true;
							}
							range.add(lower);
							range.add(higher);
							
						} else {
							range.add(-64);
							range.add(255);
						}
						trustedUUIDs.set(allOnlinePlayers[i].getUniqueId().toString(), range);
						
						player.sendMessage(ChatColor.YELLOW + "You've trusted " + allOnlinePlayers[i].getName() + " to make changes in this claim.");
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						return true;
					}
				}
				player.sendMessage(ChatColor.RED + "Error: cannot find player.");
			} else if (args[0].toLowerCase().equals("untrust")) {
				for (int i = 0; i < allOnlinePlayers.length; i ++) {
					if (args[1].toLowerCase().equals(allOnlinePlayers[i].getName().toLowerCase())) {
						if (allOnlinePlayers[i].getName().equals(player.getName())) {
							player.sendMessage(ChatColor.YELLOW + "Are you untrustworthy?");
							return true;
						}
						
						player.sendMessage(ChatColor.YELLOW + "You've disallowed " + allOnlinePlayers[i].getName() + " from making changes in this claim.");
						
						ConfigurationSection trustedUUIDs;
						if (currentClaim.contains("trustedPlayers")) {
							trustedUUIDs = currentClaim.getConfigurationSection("trustedPlayers");
						} else {
							return true;
						}
						
						if (trustedUUIDs.contains(allOnlinePlayers[i].getUniqueId().toString())) {
							trustedUUIDs.set(allOnlinePlayers[i].getUniqueId().toString(), null);
						}
						
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
						
						return true;
					}
				}
				player.sendMessage(ChatColor.RED + "Error: cannot find player.");
			} else {
				player.sendMessage(ChatColor.RED + "Unknown argument. Correct usage is /claim, /claim delete, or /claim trust <Player>");
				return true;
			}
		}
		
		
		return true;
	}
	
	
	private int getPlayerClaimBlocks(Player player) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		ConfigurationSection claimBlocks;
		if (leaderConfig.contains("claimBlocks")) {
			claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
		} else {
			claimBlocks = leaderConfig.createSection("claimBlocks");
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String uuid = player.getUniqueId().toString();
		
		if (claimBlocks.contains(uuid)) {
			return claimBlocks.getInt(uuid);
		} else {
			claimBlocks.set(uuid, plugin.startingClaimBlocks);
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return plugin.startingClaimBlocks;
		}
	}
	
	private String getBlockOwner(Block block) {
		//returns the claimID that the current block belongs to. Not the owner UUID, the claimID.
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		ConfigurationSection playerClaims;
		if (claimsConfig.contains("personalClaims")) {
			playerClaims = claimsConfig.getConfigurationSection("personalClaims");
		} else {
			playerClaims = claimsConfig.createSection("personalClaims");
		}
		
		String[] keys = playerClaims.getKeys(false).toArray(new String[playerClaims.getKeys(false).size()]);
		
		for (String key : keys) {
			ConfigurationSection claim = playerClaims.getConfigurationSection(key);
			Location loc1 = claim.getLocation("location1");
			Location loc2 = claim.getLocation("location2");
			int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
			int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
			int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
			int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
			if (block.getWorld().equals(loc1.getWorld())) {
				if (block.getX() >= minX && block.getX() <= maxX && block.getZ() >= minZ && block.getZ() <= maxZ) {
					return key;
				}
			}
		}
		
		return null;
	}
	
	
	private void setPlayerClaimBlocks(Player player, int blocks) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		ConfigurationSection claimBlocks;
		if (leaderConfig.contains("claimBlocks")) {
			claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
		} else {
			claimBlocks = leaderConfig.createSection("claimBlocks");
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		claimBlocks.set(player.getUniqueId().toString(), blocks);
		try {
			leaderConfig.save(leaderDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
