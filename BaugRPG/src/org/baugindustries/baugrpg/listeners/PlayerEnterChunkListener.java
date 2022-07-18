package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import org.bukkit.ChatColor;

public class PlayerEnterChunkListener implements Listener {
	private Main plugin;
	private HashMap<UUID, int[]> playerLocations = new HashMap<UUID, int[]>();
	private HashMap<UUID, Integer> currentFaction = new HashMap<UUID, Integer>();
	
	public PlayerEnterChunkListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onEnterChunk(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Chunk chunk = player.getLocation().getChunk();
		int[] toChunkLoc = {chunk.getX(), chunk.getZ()};
		int[] fromChunkLoc = playerLocations.get(player.getUniqueId());
		
		if (toChunkLoc[0] == fromChunkLoc[0] && toChunkLoc[1] == fromChunkLoc[1]) return;
		
		int owner = getChunkOwner(chunk.getX(), chunk.getZ(), chunk.getWorld());
		if (currentFaction.get(player.getUniqueId()) == owner) return;
		
		updateLocation(player.getUniqueId());
		switch (owner) {
			case 0:
				player.sendTitle(" ", ChatColor.YELLOW + "Entering Wilderness", 10, 70, 20);
				break;
			case 1:
				player.sendTitle(" ", ChatColor.DARK_AQUA + "Entering Human Territory", 10, 70, 20);
				break;
			case 2:
				player.sendTitle(" ", ChatColor.DARK_GREEN + "Entering Elven Territory", 10, 70, 20);
				break;
			case 3:
				player.sendTitle(" ", ChatColor.DARK_PURPLE + "Entering Dwarven Territory", 10, 70, 20);
				break;
			case 4:
				player.sendTitle(" ", ChatColor.DARK_RED + "Entering Orc Territory", 10, 70, 20);
				break;
		}
		
	}
	
	public void updateLocation(UUID uuid) {
		Chunk chunk = Bukkit.getPlayer(uuid).getLocation().getChunk();
		int[] blockLoc = {chunk.getX(), chunk.getZ()};
		playerLocations.put(uuid, blockLoc);
		currentFaction.put(uuid, getChunkOwner(chunk.getX(), chunk.getZ(), chunk.getWorld()));
	}
	
	private int getChunkOwner(int chunkX, int chunkZ, World world) {
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
}
