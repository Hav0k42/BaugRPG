package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.RayTrace;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedDataWatcher.Serializer;

public class StarlightHealingListener implements Listener {
	private int starlightHealingCooldownTime = 30;
	
	private Main plugin;
	public StarlightHealingListener(Main plugin) {
		this.plugin = plugin;
	}
	
	public int getStarlightHealingCooldownTime() {
		return starlightHealingCooldownTime;
	}
	
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		Player player = event.getPlayer();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
        if (!(skillsconfig.contains("LunarArtificer2") && skillsconfig.getBoolean("LunarArtificer2"))) return;
        
        if (plugin.starlightHealingCooldown.containsKey(player.getUniqueId())) {
	 		int minutesToMillis = 60000;
	 		if (plugin.starlightHealingCooldown.get(player.getUniqueId()) + (starlightHealingCooldownTime * minutesToMillis) < System.currentTimeMillis()) {
	 			plugin.starlightHealingCooldown.remove(player.getUniqueId());
	 		}
	 	}
        
        if (plugin.starlightHealingCooldown.containsKey(player.getUniqueId())) return;
        
        if (!player.isSneaking()) {//player is not sneaking when the begin sneaking, so this value should be inverted
        	plugin.sneakingLunarArtificers.put(player.getUniqueId(), Bukkit.getWorlds().get(0).getFullTime());
        	runGlowNearbyElves(player.getUniqueId());
        } else if (plugin.sneakingLunarArtificers.containsKey(player.getUniqueId())) {
        	plugin.sneakingLunarArtificers.remove(player.getUniqueId());
        	if (plugin.glowingElvesPerArtificer.containsKey(player.getUniqueId())) {
        		
        		plugin.glowingElvesPerArtificer.get(player.getUniqueId()).forEach(uuid -> {
        			Player bukkitPlayer = Bukkit.getPlayer(uuid);
        			
        			//Was sending a packet to remove the glowing effect. This is way easier, has no visual effect, and would accurately display the correct player state instead of being just the base.
        			//Its a little hacky, but it works so much better and is so much easier to understand.
        			if (!bukkitPlayer.isSneaking()) {
        				bukkitPlayer.setSneaking(true);
            			bukkitPlayer.setSneaking(false);
        			} else {
            			bukkitPlayer.setSneaking(false);
        				bukkitPlayer.setSneaking(true);
        			}
        			
        			
        			
        			
        			//Raytrace to see if player is within a close enough range of the crosshair.
        			RayTrace raytrace = new RayTrace(player.getLocation().toVector(), player.getLocation().getDirection());
        			Vector[] positions = new Vector[50];
        			positions = raytrace.traverse(50, 1).toArray(positions);
        			
        			
        			for (int i = 0; i < positions.length; i++) {
        				double distMult = 1 ;
        				
        				Location loc = positions[i].toLocation(player.getWorld());
//        				player.sendMessage(loc.toString());
        				if (player.getWorld().getNearbyEntities(loc, distMult, distMult, distMult).contains(bukkitPlayer)) {
        					//for the next 10 seconds, heal player and create particle effect
        					
        					plugin.starlightHealingCooldown.put(player.getUniqueId(), System.currentTimeMillis());
        					plugin.starlightHealingTicks.put(uuid, 0L);
        					healPlayer(uuid);
        					break;
        				}
        			}
        				
        			
        			
        			
        		});
        		
        		plugin.glowingElvesPerArtificer.remove(player.getUniqueId());
        	}
        }
	}
	
	
	private void healPlayer(UUID uuid) {

		
		Runnable healPlayer = new Runnable() {
			public void run() {
				Long ticks = plugin.starlightHealingTicks.get(uuid);
				Particle particle = Particle.END_ROD;
				Player player = Bukkit.getPlayer(uuid);
				player.getWorld().spawnParticle(particle, player.getLocation().add(0, 1, 0), 2, 0, 0.5, 0, 0.03);
				if (player.getHealth() + 0.0375 < 20) {
					player.setHealth(player.getHealth() + 0.075);
				}
				
				plugin.starlightHealingTicks.put(uuid, ticks + 1);
				
				if (ticks <= 200) {
					healPlayer(uuid);
		 		}
			}
		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, healPlayer, 1L);
	}
	
	
	private void runGlowNearbyElves(UUID uuid) {
		Runnable glowElves = new Runnable() {
			public void run() {
				
				
				
				double radius = 50;
				Player player = Bukkit.getPlayer(uuid);
				
	        	
				List<UUID> nearbyPlayers = new ArrayList<UUID>();
				plugin.glowingElvesPerArtificer.put(uuid, nearbyPlayers);
				Bukkit.getOnlinePlayers().forEach(bukkitPlayer -> {
		 			if (player.isSneaking() && (!bukkitPlayer.getUniqueId().equals(uuid)) && bukkitPlayer.getPersistentDataContainer().has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) && bukkitPlayer.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 2) {
		 				
		 				Location bukkitLoc = bukkitPlayer.getLocation();
		 				double dist = Math.sqrt((Math.abs(bukkitLoc.getX() - player.getLocation().getX()) * Math.abs(bukkitLoc.getX() - player.getLocation().getX())) + (Math.abs(bukkitLoc.getZ() - player.getLocation().getZ()) * Math.abs(bukkitLoc.getZ() - player.getLocation().getZ())));
		 				if (dist < radius) {
		 					
		 					//Need to use packet listeners, and modify the sent packet so each selected player is glowing. Need to use the EntityMetadata packet listener as well.min
		 					nearbyPlayers.add(bukkitPlayer.getUniqueId());
		 					
		 					PacketContainer packet = plugin.protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
		 					packet.getIntegers().write(0, bukkitPlayer.getEntityId());
		 					
		 					WrappedDataWatcher dataModifier = new WrappedDataWatcher();
                			Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
                			dataModifier.setEntity(bukkitPlayer);
                			dataModifier.setObject(0, serializer, (byte) (0x40), true);
                		    packet.getWatchableCollectionModifier().write(0, dataModifier.getWatchableObjects());
                		    try {
								plugin.protocolManager.sendServerPacket(player, packet);
							} catch (InvocationTargetException e) {
								e.printStackTrace();
							}
		 				}
		 			}
		 		});
    		}
		};
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, glowElves, 40L);
	}
}
