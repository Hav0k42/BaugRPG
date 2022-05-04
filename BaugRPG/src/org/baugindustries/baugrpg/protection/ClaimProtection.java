package org.baugindustries.baugrpg.protection;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
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

public class ClaimProtection implements Listener {

	
	private Main plugin;
	public ClaimProtection(Main plugin) {
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
		if (event.useInteractedBlock().equals(Event.Result.DENY) || event.useItemInHand().equals(Event.Result.DENY)) return;
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
		
		String fromChunkOwner = getBlockOwner(event.getBlock());
		String toChunkOwner = getBlockOwner(event.getToBlock());
		
		if (fromChunkOwner == null && toChunkOwner == null) return;
		
		if (toChunkOwner != null) {
			if (fromChunkOwner != null && toChunkOwner.equals(fromChunkOwner)) return;
			event.setCancelled(true);
		}
	}
	
	
	
	private Boolean isPositionIllegal(Location blockLoc, Player player) {
		
		if (player.hasPermission("minecraft.command.op")) {
			return false;
		}
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		if (!claimsConfig.contains("personalClaims")) {
			claimsConfig.createSection("personalClaims");
		}
		
		String claimID = getBlockOwner(blockLoc.getBlock());
		if (claimID == null) return false;
		ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
		UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
		
		if (ownerUUID.equals(player.getUniqueId())) {
			return false;
		}
		if (!(currentClaim.getConfigurationSection("trustedPlayers").contains(player.getUniqueId().toString()))) {
			player.sendMessage(ChatColor.RED + "You do not have permission to build in " + Bukkit.getOfflinePlayer(ownerUUID).getName() + "'s claim. Run /claim for more information.");
			return true;
		}
		List<Integer> range = currentClaim.getConfigurationSection("trustedPlayers").getIntegerList(player.getUniqueId().toString());
		if (blockLoc.getBlockY() < range.get(0) || blockLoc.getBlockY() > range.get(1)) {
			player.sendMessage(ChatColor.RED + "You only have permission to build between y levels " + range.get(0) + " and " + range.get(1) + " in this claim.");
			return true;
		}
		return false;
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
}
