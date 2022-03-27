package org.baugindustries.baugrpg.commands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class ChunkClaim implements CommandExecutor {
	
	private Main plugin;
	
	public ChunkClaim(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("chunkclaim").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		Player player = (Player)sender;
		int race = plugin.getRace(player);
		String raceString = plugin.getRaceString(race);
		if (!(leaderConfig.contains(raceString + "LeaderUUID") && player.getUniqueId().toString().equals(leaderConfig.getString(raceString + "LeaderUUID")))) {
			player.sendMessage(ChatColor.RED + "You must be the leader of your race to use this command.");
		}
		

		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);

		int radius = 1;
		Chunk currentChunk = player.getLocation().getChunk();

		if (args.length > 0) {
			if (args[0].equals("seed")) {
				
				int availableChunks;
				if (claimsConfig.contains(raceString + "TotalChunks")) {
					availableChunks = claimsConfig.getInt(raceString + "TotalChunks");
				} else {
					availableChunks = plugin.startingClaimChunks;
					claimsConfig.set(raceString + "TotalChunks", availableChunks);
					try {
						claimsConfig.save(claimsFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				if (claimsConfig.contains(raceString + "ChunkClaims") && 1 > availableChunks - claimsConfig.getStringList(raceString + "ChunkClaims").size()) {
					player.sendMessage(ChatColor.RED + "You do not have enough available chunk claims to use this command. Available chunks remaining: " + ChatColor.YELLOW + (availableChunks - claimsConfig.getStringList(raceString + "ChunkClaims").size()));
					return true;
				}
				
				
				int seedChunks = 0;
				if (claimsConfig.contains(raceString + "SeedChunks")) {
					seedChunks = claimsConfig.getInt(raceString + "SeedChunks");
				} else {
					seedChunks = plugin.startingSeedChunks;
					claimsConfig.set(raceString + "SeedChunks", seedChunks);
				}
				if (seedChunks == 0) {
					player.sendMessage(ChatColor.RED + "You do not have any more available seed chunks.");
					return true;
				}
				
				if (chunkIsAvailable(currentChunk.getX(), currentChunk.getZ(), currentChunk.getWorld()) == 0) {
					claimsConfig.set(raceString + "SeedChunks", seedChunks - 1);
					
					List<String> claimedChunks;
					if (!claimsConfig.contains(raceString + "ChunkClaims")) {
						claimedChunks = new ArrayList<String>();
					} else {
						claimedChunks = claimsConfig.getStringList(raceString + "ChunkClaims");
					}
					claimedChunks.add(currentChunk.getX() + "," + currentChunk.getZ() + ":" + currentChunk.getWorld().getUID());
					claimsConfig.set(raceString + "ChunkClaims", claimedChunks);
					
					try {
						claimsConfig.save(claimsFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					player.sendMessage(ChatColor.GOLD + "Chunk claimed. " + (seedChunks - 1) + " seed chunks remaining.");
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "This chunk is already claimed.");
					return true;
				}
				
			} else if (!plugin.isInteger(args[0])) {
				player.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage is: /chunkClaim <Radius>");
				return true;
			} else {
				radius = Integer.parseInt(args[0]);
			}
		}
		
		int totalChunks = ((radius * 2) - 1) * ((radius * 2) - 1);
		
		
		
		int availableChunks;
		if (claimsConfig.contains(raceString + "TotalChunks")) {
			availableChunks = claimsConfig.getInt(raceString + "TotalChunks");
		} else {
			availableChunks = plugin.startingClaimChunks;
			claimsConfig.set(raceString + "TotalChunks", availableChunks);
			try {
				claimsConfig.save(claimsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		if (claimsConfig.contains(raceString + "ChunkClaims") && totalChunks > availableChunks - claimsConfig.getStringList(raceString + "ChunkClaims").size()) {
			player.sendMessage(ChatColor.RED + "You do not have enough available chunk claims to use this command. Available chunks remaining: " + ChatColor.YELLOW + (availableChunks - claimsConfig.getStringList(raceString + "ChunkClaims").size()));
			return true;
		}
		
		
		
		List<String> toClaimChunks = new ArrayList<String>();
		HashMap<String, Integer> toOverwriteChunks = new HashMap<String, Integer>();
		List<String> claimedChunks;
		if (!claimsConfig.contains(raceString + "ChunkClaims")) {
			claimedChunks = new ArrayList<String>();
		} else {
			claimedChunks = claimsConfig.getStringList(raceString + "ChunkClaims");
		}
		
		boolean adjacent = false;
		boolean ownedCheck = false;
		boolean elseOwnedCheck = false;

		int[] overdrawnChunks = {getOverdrawnChunks(1), getOverdrawnChunks(2), getOverdrawnChunks(3), getOverdrawnChunks(4)};
		
		for (int x = currentChunk.getX() - radius + 1; x < currentChunk.getX() + radius; x++) {
			for (int z = currentChunk.getZ() - radius + 1; z < currentChunk.getZ() + radius; z++) {
				int chunkOwner = chunkIsAvailable(x, z, currentChunk.getWorld());
				if (chunkOwner == 0) {
					toClaimChunks.add(x + "," + z + ":" + currentChunk.getWorld().getUID());
					if (!adjacent) {
						adjacent = chunkIsAdjacent(x, z, currentChunk.getWorld(), race);
					}
				} else if (chunkOwner != race) {
					Bukkit.broadcastMessage(overdrawnChunks[chunkOwner - 1] + "");
					if (overdrawnChunks[chunkOwner - 1] > 0) {
						toClaimChunks.add(x + "," + z + ":" + currentChunk.getWorld().getUID());
						toOverwriteChunks.put(x + "," + z + ":" + currentChunk.getWorld().getUID(), chunkOwner);
						if (!adjacent) {
							adjacent = chunkIsAdjacent(x, z, currentChunk.getWorld(), race);
						}
						overdrawnChunks[chunkOwner - 1]--;
					} else {
						elseOwnedCheck = true;
					}
				} else {
					ownedCheck = true;
				}
			}
		}
		
		if (adjacent) {
			toOverwriteChunks.forEach((chunk, owner) -> {
				String ownerString = plugin.getRaceString(owner);
				List<String> tempList = claimsConfig.getStringList(ownerString + "ChunkClaims");
				tempList.remove(chunk);
				claimsConfig.set(ownerString + "ChunkClaims", tempList);
			});
			for (String toClaimChunk : toClaimChunks) {
				claimedChunks.add(toClaimChunk);
			}
			claimsConfig.set(raceString + "ChunkClaims", claimedChunks);
			try {
				claimsConfig.save(claimsFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			player.sendMessage(ChatColor.GOLD + "Claimed " + toClaimChunks.size() + " chunks.  Available chunks remaining: " + ChatColor.YELLOW + (availableChunks - claimsConfig.getStringList(raceString + "ChunkClaims").size()));
			return true;
		} else if (ownedCheck) {
			player.sendMessage(ChatColor.RED + "You already own these chunks. See what chunks you do and don't own using" + ChatColor.YELLOW + " /map");
			
			return true;
		} else if (elseOwnedCheck){
			player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by another faction. See what chunks you do and don't own using" + ChatColor.YELLOW + " /map");
			return true;
		} else {
			player.sendMessage(ChatColor.RED + "You cannot claim here because it is not adjacent to your race's current claim. You can claim this chunk using: " + ChatColor.YELLOW + "/chunkclaim seed");
			return true;
		}
	}
	
	
	//Things to do:
	//	chunks cannot be claimed unless next to an existing claimed chunks
	//  initial chunks are claimed using a limited amount of "seed chunks"
	//  available chunks should be counted using a number in the config "total available chunks" and when claiming it should check the size of the list of claimed chunks.
	//  claiming by radius needs to check each chunk to see if its adjacent to an existing one.
	//  isAvailable method needs to check to see if any faction has claimed more chunks than they have capacity. This will let other factions claim over chunks. Find a way to let this work with batch claim jobs.
	
	
	private int chunkIsAvailable(int chunkX, int chunkZ, World world) {
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		String[] races = {"men", "elf", "dwarf", "orc"};
		for (String race : races) {
			for (String chunkTitle : claimsConfig.getStringList(race + "ChunkClaims")) {
				if (chunkTitle.equals(chunkX + "," + chunkZ + ":" + world.getUID().toString())) {
					return plugin.getRace(race);
				}
			}
		}
		return 0;
	}
	
	private boolean chunkIsAdjacent(int chunkX, int chunkZ, World world, int race) {
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		List<String> chunks = claimsConfig.getStringList(plugin.getRaceString(race) + "ChunkClaims");
		for (int x = chunkX - 1; x < chunkX + 2; x++) {
			for (int z = chunkZ - 1; z < chunkZ + 2; z++) {
				if (chunks.contains(x + "," + z + ":" + world.getUID().toString())) {
					return true;
				}
				
			}
		}
		return false;
	}
	
	private int getOverdrawnChunks(int race) {
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		String raceString = plugin.getRaceString(race);
		
		if (!claimsConfig.contains(raceString + "ChunkClaims")) {
			return 0;
		}
		
		int availableChunks;
		if (claimsConfig.contains(raceString + "TotalChunks")) {
			availableChunks = claimsConfig.getInt(raceString + "TotalChunks");
		} else {
			availableChunks = plugin.startingClaimChunks;
			claimsConfig.set(raceString + "TotalChunks", availableChunks);
		}
		
		
		
		if (availableChunks - claimsConfig.getStringList(raceString + "ChunkClaims").size() < 0) {
			Bukkit.broadcastMessage(claimsConfig.getStringList(raceString + "ChunkClaims").size() - availableChunks + "");
			return claimsConfig.getStringList(raceString + "ChunkClaims").size() - availableChunks;
		}
		
		return 0;
	}
}