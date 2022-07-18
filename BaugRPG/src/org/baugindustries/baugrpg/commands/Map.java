package org.baugindustries.baugrpg.commands;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import org.bukkit.ChatColor;

public class Map implements CommandExecutor {
	
	private Main plugin;
	
	public Map(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("map").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		player.sendMessage(ChatColor.YELLOW + "Map of nearby chunks and which race owns them.");
		
		int radius = 7;
		
		String[] ownerStrings = {ChatColor.WHITE + "X", ChatColor.DARK_AQUA + "M", ChatColor.DARK_GREEN + "E", ChatColor.DARK_PURPLE + "D", ChatColor.DARK_RED + "O"};

		player.sendMessage(ChatColor.YELLOW + "Key: " + ownerStrings[0] + ": unowned, " + ownerStrings[1] + ": Men, " + ownerStrings[2] + ": Elves, " + ownerStrings[3] + ": Dwarves, " + ownerStrings[4] + ": Orcs");
		
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "                 N");
		player.sendMessage("");
		
		Chunk currentChunk = player.getLocation().getChunk();
		for (int z = currentChunk.getZ() - radius + 1; z < currentChunk.getZ() + radius; z++) {
			String line;
			if (z == currentChunk.getZ()) {
				line = ChatColor.GOLD + "  W  ";
			} else {
				line = "     ";
			}
			
			for (int x = currentChunk.getX() - radius + 1; x < currentChunk.getX() + radius; x++) {
				if (x == currentChunk.getX() && z == currentChunk.getZ()) {
					line = line + ownerStrings[chunkIsAvailable(x, z, currentChunk.getWorld())] + " ";
				} else {
					line = line + ownerStrings[chunkIsAvailable(x, z, currentChunk.getWorld())] + " ";
				}
			}
			
			if (z == currentChunk.getZ()) {
				line = line + ChatColor.GOLD + "  E";
			}
			player.sendMessage(line);
		}
		player.sendMessage("");
		player.sendMessage(ChatColor.GOLD + "                 S");
		
		return true;
	}

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
	
}
