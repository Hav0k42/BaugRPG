package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R1.CraftServer;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.BlockIterator;

import com.comphenix.protocol.PacketType;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.network.protocol.game.PacketPlayOutEntity;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutEntityHeadRotation;
import net.minecraft.network.protocol.game.PacketPlayOutNamedEntitySpawn;
import net.minecraft.network.protocol.game.PacketPlayOutPlayerInfo;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.server.level.WorldServer;
import net.minecraft.server.network.PlayerConnection;

public class PlayerDamageListener implements Listener {
	private Main plugin;
	private int steeledResolveCooldownTime = 50;
	public PlayerDamageListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerDamageEvent(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			
			
			File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
			
		 	if (skillsconfig.getBoolean("resistanceOn")) {
			 	double damageDivider = ((0.5 / 10) * skillsconfig.getInt("resistance"));
				double initDamage = event.getDamage();
				event.setDamage(initDamage - (initDamage * damageDivider));
		 	}
		 	
		 	if (plugin.steeledResolveCooldown.containsKey(player.getUniqueId())) {
		 		int minutesToMillis = 60000;
		 		if (plugin.steeledResolveCooldown.get(player.getUniqueId()) + (steeledResolveCooldownTime * minutesToMillis) < System.currentTimeMillis()) {
		 			plugin.steeledResolveCooldown.remove(player.getUniqueId());
		 		}
		 	}
		 	
		 	if (skillsconfig.contains("SteeledArmorer1") && skillsconfig.getBoolean("SteeledArmorer1") && event.getDamage() > player.getHealth() && !plugin.steeledResolveCooldown.containsKey(player.getUniqueId())){
		 		
		 		plugin.steeledResolveCooldown.put(player.getUniqueId(), System.currentTimeMillis());
		 		player.setGameMode(GameMode.SPECTATOR);
		 		MinecraftServer nmsServer = ((CraftServer) Bukkit.getServer()).getServer();
		        WorldServer nmsWorld = ((CraftWorld)player.getWorld()).getHandle(); 
		        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), player.getName()); // Change "playername" to the name the NPC should have, max 16 characters.
		        
		        
		        String texture = "ewogICJ0aW1lc3RhbXAiIDogMTY0MDc2OTQyMjgwMCwKICAicHJvZmlsZUlkIiA6ICI4NTA5OWZjYjA2ODA0OTRhYTEzZDkyMzUxYTYyMDBhMiIsCiAgInByb2ZpbGVOYW1lIiA6ICJTdGVlbCIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jNDExZDQ4ZWViZTNiMWEzMjdhZDVmZWZlNDhiOTg1MmY4YTQ0OTkzMGM5YjJmZWRhZWVmMmQ1ZjdhZDIwYTZlIgogICAgfQogIH0KfQ==";
		        String signature = "HqOr0xNHfrbwZsigVb7NSwjyjGMEcy/QcIiw/D+dg9WywXxeBcLdWTvmuh6G7tc/zxBDrkRs5EC4LlWB6TZxeNKoDbsZGGRhanFwOfV6JH41INoettMt1n3zsnyaYwIssgAja0p+BB+klwzZm8NNjoevaTsIfMlmk7OLJaDPasv5rZkHkT0xt7BL1Rj4pc5EK7iWDdSVSBpRt7qlAIkZHFIPAesJpTHgmGiyjgFxqAAWWE88B5sgojTmzwbU/4JhOvDon8cfZRpWhf25tKvkzOIqz0W4DYWsE9LpOT+mNfd4fViFHC+oa4CloIMQ4RtlIwJoirVJT2QL5Irk6YE5WTxbKb1NKyZOxrdtDYQROyqU16Oy1wHn/bcas3p2lS+WbjEU2Er9C1dOMgErcU6Ta/FyYzQtXZUIH8DIXT1AEHc2/A1LClKWiTEdiGhgVNhIo9w7nY5RTDymenPSxSSXFMVkyL8jLu2DKiM3iz3DPzYrjo07nemtlR6a4m+zVMeRKAUydj6gaYVytRyQIxdanXqkNFDu2e/DMlit4rUlYggb6Ax0hhFuWriYbxOixLQqlK5zWu4piOfeXmiQXu1aWDIGL+tGAJCYtIzzC2QC3jGu4MNhAtVOeX0siqFkp02Nic4tytbachw9lTUoczAkbC1qbP4L+RcjeqSDAnO5jxo=";
		        
		        gameProfile.getProperties().put("textures", new Property("textures", texture, signature));
		        EntityPlayer npc = new EntityPlayer(nmsServer, nmsWorld, gameProfile); // This will be the EntityPlayer (NPC) we send with the sendNPCPacket method.
		        
		        Location location = player.getLocation();
		        plugin.steeledResolveInitLoc.put(player.getUniqueId(), location);
		        npc.g(location.getX(), location.getY(), location.getZ());//MoveTo without yaw and pitch is .g, with is .a
		 		
		        plugin.steeledResolveNpcId.put(player.getUniqueId(), npc.ae());
		        
		        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
		        	PlayerConnection connection = ((CraftPlayer)onlinePlayer).getHandle().b;//.b returns the playerConnection.
		        	
		        	
		            connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, npc)); // Enum.a is addplayer
		            Runnable despawnNpc = new Runnable() {//check if any orcs are in sunlight
		      		  public void run() {
		      			  PacketPlayOutEntityDestroy killNpcPacket = new PacketPlayOutEntityDestroy(npc.ae());
		      			  
		      			  
		      			  connection.a(killNpcPacket);
		      		  }
		      		};
		      		
		      		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, despawnNpc, 400L);
		            connection.a(new PacketPlayOutNamedEntitySpawn(npc)); // .a is send packet
		            
		            
		            
		            Runnable removeFromTab = new Runnable() {//check if any orcs are in sunlight
			      		  public void run() {
			      			connection.a(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, npc));
			      		  }
			      		};
		      		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, removeFromTab, 1L);
		      		
		      		
		      		
		      		
		            connection.a(new PacketPlayOutEntityHeadRotation(npc, (byte) (location.getYaw() * 256 / 360)));
		        });
		        plugin.steeledResolveTpTicks.put(player.getUniqueId(), 0L);
	      		
	      		runTeleportPlayer(player.getUniqueId());
		        plugin.protocolManager.createPacket(PacketType.Play.Server.PLAYER_INFO);
		 		event.setCancelled(true);
		 		
		 	}
		}
	}
	
	public int getSteeledResolveCooldownTime( ) {
		return steeledResolveCooldownTime;
	}
	
	private void runTeleportPlayer(UUID uuid) {
		Runnable teleportPlayer = new Runnable() {//check if any orcs are in sunlight
    		  @SuppressWarnings("deprecation")
			public void run() {
    			  Player player;
    			  Long ticks = plugin.steeledResolveTpTicks.get(uuid);
    			  if (Bukkit.getOfflinePlayer(uuid).isOnline()) {
    				  player = Bukkit.getPlayer(uuid);
    				  
    				  Location initLoc = plugin.steeledResolveInitLoc.get(uuid);
        			  
        			  float viewingAngle = 27;
        			  
        			  
        			  
        			  double distanceScalar = 5;//distance from statue to nearest block in correct direction. Capped at 7
        			  
    			  
        			  double constant = 31.8309886184; //number for two rotations in 200 ticks
        			  
        			  double maxRiseHeight = 2;
        			  
        			  double risenNPCheight = ticks / (400f / maxRiseHeight);
        			  
        			  double heightShift = distanceScalar * Math.tan(Math.toRadians(viewingAngle)) + risenNPCheight;
        			  
        			  double xLoc = (Math.sin(ticks/constant) * distanceScalar) + initLoc.getX();
        			  double zLoc = (Math.cos(ticks/constant) * distanceScalar) + initLoc.getZ();
        			  float yaw = (float) ((-1.8 * ticks) + 180);
        			  Location location = new Location(player.getWorld(), xLoc, initLoc.getY() + heightShift, zLoc, yaw, viewingAngle);
        			  Iterator<Block> itr = new BlockIterator((LivingEntity) player, 15);
        			  
        			  double npcDist = Math.sqrt(
    						  (Math.abs(xLoc - initLoc.getX()) * Math.abs(xLoc - initLoc.getX())) +
    						  (Math.abs(zLoc - initLoc.getZ()) * Math.abs(zLoc - initLoc.getZ())) +
    						  (Math.abs(heightShift - risenNPCheight) * Math.abs(heightShift - risenNPCheight))
    								  ); 
        			  
        			  List<Block> frontBlocks = new ArrayList<Block>();
        			  
        			  while (itr.hasNext()) {
        				  Block block = itr.next();
        				  
        				  Location blockLoc = block.getLocation();
        				  
        				  double blockDist = Math.sqrt(
        						  (Math.abs(xLoc - blockLoc.getX()) * Math.abs(xLoc - blockLoc.getX())) +
        						  (Math.abs(zLoc - blockLoc.getZ()) * Math.abs(zLoc - blockLoc.getZ())) +
        						  (Math.abs((initLoc.getY() + heightShift) - blockLoc.getY()) * Math.abs((initLoc.getY() + heightShift) - blockLoc.getY())
        								  ));
        				  
        				  
        				  if (blockDist <= npcDist && !(block.getType().equals(Material.AIR) || block.getType().equals(Material.CAVE_AIR) || block.getType().equals(Material.VOID_AIR))) {//block is in front of player and is not air
        					  frontBlocks.add(block);
        				  } else if (blockDist > npcDist) {//block is behind and we dont care.
        					  break;
        				  }
        			  }
        			  
        			  if (frontBlocks.size() == 0) {
	        			  player.teleport(location);
        			  } else {
        				  
        				  Block block = frontBlocks.get(frontBlocks.size() - 1);
        				  
        				  Location blockLoc = block.getLocation();
        				  
        				  double blockDist = Math.sqrt(
        						  (Math.abs(xLoc - blockLoc.getX()) * Math.abs(xLoc - blockLoc.getX())) +
        						  (Math.abs(zLoc - blockLoc.getZ()) * Math.abs(zLoc - blockLoc.getZ())) +
        						  (Math.abs((initLoc.getY() + heightShift) - blockLoc.getY()) * Math.abs((initLoc.getY() + heightShift) - blockLoc.getY())
        								  ));
        				  
        				  distanceScalar = npcDist - (blockDist + 1.5); 
        				  
        				  if (distanceScalar < 0) {
        					  distanceScalar = 0.2;
        				  }
        				  
        				  
        				  xLoc = (Math.sin(ticks/constant) * distanceScalar) + initLoc.getX();
        				  heightShift = distanceScalar * Math.tan(Math.toRadians(viewingAngle)) + risenNPCheight;
        				  zLoc = (Math.cos(ticks/constant) * distanceScalar) + initLoc.getZ();
        				  
        				  
        				  location = new Location(player.getWorld(), xLoc, initLoc.getY() + heightShift, zLoc, yaw, viewingAngle);
        				  player.teleport(location);
        			  }
        			  
        			  
        			  
        			  
        			  
        			  
        			  
        			  
        			  Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
        				  PlayerConnection connection = ((CraftPlayer)onlinePlayer).getHandle().b;
            			  connection.a(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(plugin.steeledResolveNpcId.get(uuid).intValue(), (short)0, (short)(0.005 * 4096), (short)0, true));
      		          });
        			  
        			  if (ticks % 20 == 0 ) {
        				int secondsRemaining = (20 - (int)(ticks / 20));
        				ChatColor secondColor = ChatColor.RED;
        				if (secondsRemaining == 3) {
        					secondColor = ChatColor.GOLD;
        				} else if (secondsRemaining == 2) {
        					secondColor = ChatColor.YELLOW;
        				} else if (secondsRemaining == 1) {
        					secondColor = ChatColor.GREEN;
        				}
        				if (secondsRemaining != 0) {
        					player.sendTitle(secondColor + "" + secondsRemaining, "", 5, 15, 0);
        				}
      				  }
        			  plugin.steeledResolveTpTicks.put(uuid, ticks + 1);
        			  if (ticks > 400) {
        				  	player.teleport(plugin.steeledResolveInitLoc.get(uuid));
          					player.setGameMode(GameMode.SURVIVAL);
          					player.setHealth(player.getMaxHealth()); 
        			  }
    			  } else {
    				  plugin.steeledResolveDisconnectedPlayers.add(uuid);
    				  plugin.steeledResolveTpTicks.put(uuid, 401L);
    			  }
    			  
    			  
    			  
    			
    			if (ticks <= 400) {
    				runTeleportPlayer(uuid);
    			}
    		  }
    		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, teleportPlayer, 1L);
	}
	
	
	
}
