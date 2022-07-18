package org.baugindustries.baugrpg.protection;

import java.io.File;
import java.io.IOException;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockReceiveGameEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.block.SpongeAbsorbEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.ChatColor;

public class SpawnProtection implements Listener {

	
	private Main plugin;
	public SpawnProtection(Main plugin) {
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
			if (event.getClickedBlock().getType().toString().contains("DOOR") && !event.getClickedBlock().getType().toString().contains("TRAPDOOR")) return;
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
	public void FallingBlockLand(EntityChangeBlockEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation()));
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
	public void BlockBurn(BlockBurnEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation()));
	}
	
	@EventHandler
	public void BlockExplode(BlockExplodeEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation()));
	}
	
	@EventHandler
	public void BlockExplode(EntityExplodeEvent event) {
		if (event.isCancelled()) return;
		boolean check = false;
		for (Block block : event.blockList()) {
			if (isPositionIllegal(block.getLocation())) {
				check = true;
				break;
			}
		}
		
		event.setCancelled(check);
	}
	
//	@EventHandler
//	public void BlockFade(BlockFadeEvent event) {
//		if (event.isCancelled()) return;
//		event.setCancelled(isPositionIllegal(event.getBlock().getLocation()));
//	}
	
	@EventHandler
	public void BlockFlow(BlockFromToEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getToBlock().getLocation()));
	}
	
	@EventHandler
	public void BlockIgnite(BlockIgniteEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation()));
	}
	
	@EventHandler
	public void PlayerHit(EntityDamageByEntityEvent event) {
		if (event.isCancelled()) return;
		Player attacker;
		Player damaged;
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			attacker = (Player) event.getDamager();
			damaged = (Player) event.getEntity();
			event.setCancelled(isPositionIllegal(attacker.getLocation(), attacker) || isPositionIllegal(damaged.getLocation(), attacker));
		}
		
		if (event.getEntity() instanceof ArmorStand || event.getEntity() instanceof ItemFrame) {
			if (event.getDamager() instanceof Player) {
				event.setCancelled(isPositionIllegal(event.getEntity().getLocation(), (Player)event.getDamager()));
			} else {
				event.setCancelled(isPositionIllegal(event.getEntity().getLocation()));
			}
		}
		
	}
	
	@EventHandler
	public void PistonExtend(BlockPistonExtendEvent event) {
		if (event.isCancelled()) return;
		boolean check = false;
		for (Block block : event.getBlocks()) {
			if (isPositionIllegal(block.getLocation())) {
				check = true;
				break;
			}
		}
		
		event.setCancelled(check);
	}
	
	@EventHandler
	public void PistonRetract(BlockPistonRetractEvent event) {
		if (event.isCancelled()) return;
		boolean check = false;
		for (Block block : event.getBlocks()) {
			if (isPositionIllegal(block.getLocation())) {
				check = true;
				break;
			}
		}
		
		event.setCancelled(check);
	}
	
	@EventHandler
	public void SkulkActivate(BlockReceiveGameEvent event) {
		if (event.isCancelled()) return;
		event.setCancelled(isPositionIllegal(event.getBlock().getLocation()));
	}
	
	@EventHandler
	public void BlockFertilize(SpongeAbsorbEvent event) {
		if (event.isCancelled()) return;
		boolean check = false;
		for (BlockState blockstate : event.getBlocks()) {
			Block block = blockstate.getBlock();
			if (isPositionIllegal(block.getLocation())) {
				check = true;
				break;
			}
		}
		
		event.setCancelled(check);
	}
	
	
	
	
	private Boolean isPositionIllegal(Location blockLoc, Player player) {
		String[] races = {"men", "elf", "dwarf", "orc"};
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		

		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		for (String race : races) {
			int yMin = 0;
			if (!config.contains(race + "SpawnYMin")) {
				yMin = claimsConfig.getLocation(race + "Spawn").getBlockY() - 10;
				config.set(race + "SpawnYMin", yMin);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				yMin = config.getInt(race + "SpawnYMin");
			}
			
			int radius = 0;
			if (!config.contains("spawnRadius")) {
				radius = 16;
				config.set("spawnRadius", radius);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				radius = config.getInt("spawnRadius");
			}
			
			if (blockLoc.getBlockY() >= yMin && plugin.getDistanceFlat(blockLoc, claimsConfig.getLocation(race + "Spawn")) < radius) {
				if (!player.hasPermission("minecraft.command.op")) {
					player.sendMessage(ChatColor.RED + "You do not have permission to do that here.");
					return true;
				}
				
			}
		}
		return false;
	}
	
	private Boolean isPositionIllegal(Location blockLoc) {
		String[] races = {"men", "elf", "dwarf", "orc"};
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		

		File file = new File(plugin.getDataFolder() + File.separator + "config.yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		for (String race : races) {
			int yMin = 0;
			if (!config.contains(race + "SpawnYMin")) {
				yMin = claimsConfig.getLocation(race + "Spawn").getBlockY() - 10;
				config.set(race + "SpawnYMin", yMin);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				yMin = config.getInt(race + "SpawnYMin");
			}
			
			int radius = 0;
			if (!config.contains("spawnRadius")) {
				radius = 16;
				config.set("spawnRadius", radius);
				try {
					config.save(file);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				radius = config.getInt("spawnRadius");
			}
			
			if (blockLoc.getBlockY() >= yMin && plugin.getDistanceFlat(blockLoc, claimsConfig.getLocation(race + "Spawn")) < radius) {
				return true;
			}
		}
		return false;
	}
}
