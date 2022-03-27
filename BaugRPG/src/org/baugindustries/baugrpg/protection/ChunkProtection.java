package org.baugindustries.baugrpg.protection;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import net.md_5.bungee.api.ChatColor;

public class ChunkProtection implements Listener {

	
	private Main plugin;
	public ChunkProtection(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void BlockBuild(BlockPlaceEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation(), event.getPlayer()));
	}
	
	@EventHandler
	public void BlockDamage(BlockDamageEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation(), event.getPlayer()));
	}
	
	@EventHandler
	public void BlockDestroy(BlockBreakEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation(), event.getPlayer()));
	}
	
	@EventHandler
	public void BlockFertilize(BlockFertilizeEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation(), event.getPlayer()));
	}
	
	@EventHandler
	public void SignChange(SignChangeEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation(), event.getPlayer()));
	}
	
	@EventHandler
	public void PlayerInteract(PlayerInteractEvent event) {
		if (event.isCancelled()) return;
		if (event.getClickedBlock() != null) {
			event.setCancelled(isPositionIllegal(event.getClickedBlock().getLocation(), event.getPlayer()));
		}
	}
	
	@EventHandler
	public void PlayerInteract(PlayerInteractEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getRightClicked() instanceof ItemFrame) {
			event.setCancelled(isPositionIllegal(event.getRightClicked().getLocation(), event.getPlayer()));
		}
	}
	
	@EventHandler
	public void ArmorStandInteract(PlayerArmorStandManipulateEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getRightClicked().getLocation(), event.getPlayer()));
	}
	
	@EventHandler
	public void BreakPainting(HangingBreakByEntityEvent event) {
		if (event.isCancelled()) return;
		if (event.getRemover() instanceof Player) {
			event.setCancelled(isPositionIllegal(event.getEntity().getLocation(), (Player)event.getRemover()));
		} else {
			
		}
		
	}
	
	@EventHandler
	public void BlockFlow(BlockFromToEvent event) {
		if (event.isCancelled()) return;
		Chunk fromChunk = event.getBlock().getChunk();
		Chunk toChunk = event.getToBlock().getChunk();
		
		if (fromChunk.equals(toChunk)) return;
		
		int fromChunkOwner = getChunkOwner(fromChunk.getX(), fromChunk.getZ(), fromChunk.getWorld());
		int toChunkOwner = getChunkOwner(toChunk.getX(), toChunk.getZ(), toChunk.getWorld());
		
		if (toChunkOwner == fromChunkOwner) return;
		if (toChunkOwner != 0) {
			event.setCancelled(true);
		}
	}
	
	
	
	private Boolean isPositionIllegal(Location blockLoc, Player player) {
		int race = plugin.getRace(player);
		int owner = getChunkOwner(blockLoc.getChunk().getX(), blockLoc.getChunk().getZ(), blockLoc.getWorld());
		if (owner == 0) return false;
		if (race != owner) {
			if (!player.hasPermission("minecraft.command.op")) {
				player.sendMessage(ChatColor.RED + "You do not have permission to do that here.");
				return true;
			}
			
		}
		return false;
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
