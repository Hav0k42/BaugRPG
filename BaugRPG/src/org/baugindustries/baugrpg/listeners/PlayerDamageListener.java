package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_18_R2.CraftServer;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

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
	private int shepherdsGraceCooldownTime = 30;
	private int greedyReinforcementCooldownTime = 30;
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
		 	
		 	
		 	if (skillsconfig.contains("menDangerOn") && skillsconfig.getBoolean("menDangerOn")) {
		 		if (event.getCause().equals(DamageCause.FALL)) {
		 			if (skillsconfig.contains("menDanger")) {
		 				int lvl = skillsconfig.getInt("menDanger");
			 			event.setDamage(event.getDamage() * (1 - (lvl / 8.0)));
		 			}
		 		}
		 	}
		 	
		 	if (skillsconfig.contains("elfDangerOn") && skillsconfig.getBoolean("elfDangerOn")) {
		 		if (event.getCause().equals(DamageCause.DROWNING)) {
		 			if (skillsconfig.contains("elfDanger")) {
		 				int lvl = skillsconfig.getInt("elfDanger");
			 			event.setDamage(event.getDamage() * (1 - (lvl / 4.0)));
		 			}
		 		}
		 	}
		 	
		 	if (skillsconfig.contains("dwarfDangerOn") && skillsconfig.getBoolean("dwarfDangerOn")) {
		 		if (event.getCause().equals(DamageCause.ENTITY_ATTACK)) {
		 			if (!((EntityDamageByEntityEvent)event).getDamager().getType().equals(EntityType.PLAYER)) {
			 			if (skillsconfig.contains("dwarfDanger")) {
			 				int lvl = skillsconfig.getInt("dwarfDanger");
				 			event.setDamage(event.getDamage() * (1 - (lvl / 8.0)));
			 			}
		 			}
		 		}
		 	}
		 	
		 	if (skillsconfig.contains("orcDangerOn") && skillsconfig.getBoolean("orcDangerOn")) {
		 		if (event.getCause().equals(DamageCause.LAVA)) {
		 			if (skillsconfig.contains("orcDanger")) {
		 				int lvl = skillsconfig.getInt("orcDanger");
			 			event.setDamage(event.getDamage() * (1 - (lvl / 8.0)));
		 			}
		 		}
		 	}
		 	
		 	//Activate Steeled Armorer animation
		 	if (((skillsconfig.contains("SteeledArmorer1") && skillsconfig.getBoolean("SteeledArmorer1")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("SteeledArmorer1")) && event.getDamage() >= player.getHealth() && !plugin.steeledResolveCooldown.containsKey(player.getUniqueId())){
		 		
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
		 	
		 	
		 	
		 	
		 	if (plugin.shepherdsGraceCooldown.containsKey(player.getUniqueId())) {
		 		int minutesToMillis = 60000;
		 		if (plugin.shepherdsGraceCooldown.get(player.getUniqueId()) + (shepherdsGraceCooldownTime * minutesToMillis) < System.currentTimeMillis()) {
		 			plugin.shepherdsGraceCooldown.remove(player.getUniqueId());
		 		}
		 	}
		 	
		 	
		 	//Activate Verdant Shepherd Healing
		 	if (((skillsconfig.contains("VerdantShepherd1") && skillsconfig.getBoolean("VerdantShepherd1")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("VerdantShepherd1")) && player.getHealth() - event.getDamage() < 5 && event.getDamage() < player.getHealth() && !plugin.shepherdsGraceCooldown.containsKey(player.getUniqueId())) {
		 		plugin.shepherdsGraceCooldown.put(player.getUniqueId(), System.currentTimeMillis());
		 		plugin.shepherdsGraceTicks.put(player.getUniqueId(), 0L);
		 		runVerdantShepherdEffect(player.getUniqueId());
		 	}
		 	
		 	
		 	
		 	if (plugin.greedyReinforcementCooldown.containsKey(player.getUniqueId())) {
		 		int minutesToMillis = 60000;
		 		if (plugin.greedyReinforcementCooldown.get(player.getUniqueId()) + (greedyReinforcementCooldownTime * minutesToMillis) < System.currentTimeMillis()) {
		 			plugin.greedyReinforcementCooldown.remove(player.getUniqueId());
		 		}
		 	}
		 	
		 	
		 	//Activate Greedy Scrapper Reinforcements
		 	if (((skillsconfig.contains("GreedyScrapper2") && skillsconfig.getBoolean("GreedyScrapper2")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("GreedyScrapper2")) && player.getHealth() - event.getDamage() < 5 && event.getDamage() < player.getHealth() && !plugin.greedyReinforcementCooldown.containsKey(player.getUniqueId())) {
		 		plugin.greedyReinforcementCooldown.put(player.getUniqueId(), System.currentTimeMillis());
		 		plugin.greedyReinforcementTicks.put(player.getUniqueId(), 0L);
		 		runGreedyScrapperEffect(player.getUniqueId());
		 	}
		 	
		 	
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	public int getSteeledResolveCooldownTime( ) {
		return steeledResolveCooldownTime;
	}
	
	private void runTeleportPlayer(UUID uuid) {
		Runnable teleportPlayer = new Runnable() {
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
        			  player.teleport(location);
        			  
        			  
        			  Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
        				  PlayerConnection connection = ((CraftPlayer)onlinePlayer).getHandle().b;
            			  connection.a(new PacketPlayOutEntity.PacketPlayOutRelEntityMove(plugin.steeledResolveNpcId.get(uuid).intValue(), (short)0, (short)(0.005 * 4096), (short)0, true));
      		          });
        			  
        			  if (ticks % 20 == 0 ) {
        				  player.getWorld().playSound(initLoc, Sound.BLOCK_BEACON_AMBIENT, SoundCategory.MASTER, 2f, 1f);
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

	public int getShepherdsGraceCooldownTime() {
		return shepherdsGraceCooldownTime;
	}
	
	public int getGreedyReinforcementCooldownTime() {
		return greedyReinforcementCooldownTime;
	}
	
	
	private void runVerdantShepherdEffect(UUID uuid) {
		Runnable verdantShepherdEffect = new Runnable() {
			public void run() {
				Long ticks = plugin.shepherdsGraceTicks.get(uuid);
				
				if (Bukkit.getPlayer(uuid).isOnline()) {
					Player player = Bukkit.getPlayer(uuid);
					int radialParticleDensity = 100;
			 		int radius = 10;
			 		Color particleColor = Color.LIME;
			 		float particleSize = 1;
			 		World world = player.getWorld();
			 		double yPos = player.getLocation().getY();
			 		
			 		Vector startPos = player.getLocation().toVector().add(new Vector(0, -1, 0));
			 		Vector direction = new Vector(0, -1, 0);	 		
			 		
			 		BlockIterator itr = new BlockIterator(world, startPos, direction, 0, 20);
			 		while (itr.hasNext()) {
			 			Block block = itr.next();
			 			if (block.getType().isSolid()) {
			 				yPos = block.getY() + 1;
			 				break;
			 			}
			 		}

		 			Particle particle = Particle.REDSTONE;
		 			Particle.DustOptions options = new Particle.DustOptions(particleColor, particleSize);
			 		double pxPos = player.getLocation().getX();
			 		double pzPos = player.getLocation().getZ();
			 		for (int i = 0; i < radialParticleDensity; i++) {
			 			double angle = (((2 * Math.PI) / (radialParticleDensity)) * i);
			 			double xPos = (radius * Math.cos(angle)) + pxPos;
			 			double zPos = (radius * Math.sin(angle)) + pzPos;
			 			Location particleLoc = new Location(world, xPos, yPos, zPos);
			 			world.spawnParticle(particle, particleLoc, 1, options);
			 		}
			 		
			 		
			 		
			 		int linearParticleDensity = (int) (radialParticleDensity / Math.PI);
			 		double angle = (ticks * Math.PI * 2) / 200;
			 		double totalXDist = Math.abs(2 * (radius * Math.cos(angle)));
			 		double initXPos = (radius * Math.cos(angle)) + pxPos;
			 		double initZPos = (radius * Math.sin(angle)) + pzPos;
			 		if (initXPos - pxPos != 0) {//lines wont be vertical
			 			double slope = (initZPos - pzPos) / (initXPos - pxPos);
				 		for (int i = 0; i < linearParticleDensity; i++) {
				 			double xPos;
				 			if (initXPos > pxPos) {
				 				xPos = initXPos - ((totalXDist / linearParticleDensity) * i);
				 			} else {
				 				xPos = initXPos + ((totalXDist / linearParticleDensity) * i);
				 			}
				 			
				 			double zPos = slope * (xPos - player.getLocation().getX()) + pzPos;
				 			double zPos2 = -slope * (xPos - player.getLocation().getX()) + pzPos;
				 			Location particleLoc = new Location(world, xPos, yPos, zPos);
				 			Location particleLoc2 = new Location(world, xPos, yPos, zPos2);
				 			world.spawnParticle(particle, particleLoc, 1, options);
				 			world.spawnParticle(particle, particleLoc2, 1, options);
				 		}
			 		}
			 		
			 		double healAmount = 0.04;
			 		Bukkit.getOnlinePlayers().forEach(bukkitPlayer -> {
			 			if (!bukkitPlayer.getUniqueId().equals(uuid) && bukkitPlayer.getPersistentDataContainer().has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) && bukkitPlayer.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 1) {
			 				Location bukkitLoc = bukkitPlayer.getLocation();
			 				double dist = Math.sqrt((Math.abs(bukkitLoc.getX() - pxPos) * Math.abs(bukkitLoc.getX() - pxPos)) + (Math.abs(bukkitLoc.getZ() - pzPos) * Math.abs(bukkitLoc.getZ() - pzPos)));
			 				if (dist < 10) {
			 					bukkitPlayer.setHealth(bukkitPlayer.getHealth() + healAmount);
			 				}
			 			}
			 		});
			 		
			 		if (ticks % 20 == 0 ) {
			 			player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_AMBIENT, SoundCategory.MASTER, 2f, 1f);
			 		}
			 		
				}
		 		
		 		
				plugin.shepherdsGraceTicks.put(uuid, ticks + 1);
		 		
		 		if (ticks <= 200) {
		 			runVerdantShepherdEffect(uuid);
		 		}
			}
		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, verdantShepherdEffect, 1L);
 		
	}
	
	
	
	
	
	private void runGreedyScrapperEffect(UUID uuid) {
		Runnable greedyScrapperEffect = new Runnable() {
			public void run() {
				Long ticks = plugin.greedyReinforcementTicks.get(uuid);
				
				if (Bukkit.getPlayer(uuid).isOnline()) {
					Player player = Bukkit.getPlayer(uuid);
		 			Particle particle = Particle.REDSTONE;
			 		Color particleColor = Color.MAROON;
			 		float particleSize = 1;
		 			Particle.DustOptions options = new Particle.DustOptions(particleColor, particleSize);
			 		World world = player.getWorld();
			 		
			 		List<Player> nearbyOrcs = new ArrayList<Player>();
			 		
			 		Bukkit.getOnlinePlayers().forEach(bukkitPlayer -> {
			 			if (!bukkitPlayer.getUniqueId().equals(uuid) && bukkitPlayer.getPersistentDataContainer().has(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) && bukkitPlayer.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 4) {
			 				Location bukkitLoc = bukkitPlayer.getLocation();
			 				double dist = bukkitLoc.distance(player.getLocation());
			 				if (dist < 15) {
			 					nearbyOrcs.add(bukkitPlayer);
			 				}
			 			}
			 		});
			 		
			 		double particleDensity = 30;
			 		
			 		for (Player orc : nearbyOrcs) {
			 			Location orcLoc = orc.getLocation();
			 			orcLoc.add(0, 1, 0);
			 			double xDist = orcLoc.getX() - player.getLocation().getX();
			 			double yDist = orcLoc.getY() - (player.getLocation().getY() + 1);
			 			double zDist = orcLoc.getZ() - player.getLocation().getZ();

			 			for (double i = 0; i < particleDensity; i++) {
			 				Location particleLoc = new Location(player.getWorld(),
			 						orcLoc.getX() - (xDist * (i / particleDensity)),
			 						orcLoc.getY() - (yDist * (i / particleDensity)),
			 						orcLoc.getZ() - (zDist * (i / particleDensity)));
				 			world.spawnParticle(particle, particleLoc, 1, options);
			 			}
			 			orc.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, 1));
			 			orc.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 1));
			 		}
			 		
			 		if (ticks == 0) {
			 			player.getWorld().playSound(player.getLocation(), Sound.AMBIENT_CAVE, SoundCategory.MASTER, 2f, 1f);
			 		}
			 		
			 		
			 		
			 		
			 		
			 		
			 		
			 		double radius = 1.5;
			 		double orbCount = 7;
			 		double speed = 0.0;
			 		double offset = speed * ((double)ticks);
			 		double tilt = Math.toRadians(20);
			 		double tiltSpeed = -0.07;
			 		double tiltOffset = tiltSpeed * ((double)ticks);
			 		
			 		for (int i = 0; i < orbCount; i++) {
			 			
			 			double angle = (((2 * Math.PI) / (orbCount)) * i) + offset;
			 			double xPos = (radius * Math.cos(angle));
			 			double yPos = 1;
			 			double zPos = (radius * Math.sin(angle));
			 			
			 			
			 			//Rotate around x axis
			 			double xPos2 = xPos;
			 			double yPos2 = (yPos * Math.cos(tilt)) - (zPos * Math.sin(tilt));
			 			double zPos2 = (yPos * Math.sin(tilt)) + (zPos * Math.cos(tilt));
			 			
			 			
			 			//Rotate around y axis
			 			double xPos3 = (xPos2 * Math.cos(tiltOffset)) + (zPos2 * Math.sin(tiltOffset));
			 			double yPos3 = yPos2;
			 			double zPos3 = (-xPos2 * Math.sin(tiltOffset)) + (zPos2 * Math.cos(tiltOffset));
			 			
			 			
			 			
			 			Location particleLoc = new Location(player.getWorld(), xPos3 + player.getLocation().getX(), yPos3 + player.getLocation().getY(), zPos3 + player.getLocation().getZ());

			 			world.spawnParticle(particle, particleLoc, 1, options);
			 		}
			 		
			 		
			 		
				}
		 		
		 		
				plugin.greedyReinforcementTicks.put(uuid, ticks + 1);
		 		
		 		if (ticks <= 200) {
		 			runGreedyScrapperEffect(uuid);
		 		}
			}
		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, greedyScrapperEffect, 1L);
 		
	}
	
	
}
