package org.baugindustries.baugrpg.listeners;


import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.phys.Vec3D;

public class PlayerJumpListener implements Listener {
	private Main plugin;
	public PlayerJumpListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerJumpEvent(PlayerMoveEvent event) {
		Player player = event.getPlayer();
	    Vector velocity = player.getVelocity();
	    
	    Location prevPos = plugin.positionData.get(player);
	    if (prevPos != null) {
		    Location currentPos = player.getLocation();
		    
		    float Xdelta = (float) (currentPos.getX() - prevPos.getX());
		    float Ydelta = (float) (currentPos.getY() - prevPos.getY());
		    float Zdelta = (float) (currentPos.getZ() - prevPos.getZ());
		    
		    File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		    
		    // Check if the player is moving "up"
		    if (velocity.getY() > 0 & !player.isSwimming() && skillsconfig.getBoolean("jumpOn")) {
		        // Default jump velocity
		        double jumpVelocity = (double) 0.42F; // Default jump velocity
		        PotionEffect jumpPotion = player.getPotionEffect(PotionEffectType.JUMP);
		        if (jumpPotion != null) {
		            // If player has jump potion add it to jump velocity
		            jumpVelocity += (double) ((float) jumpPotion.getAmplifier() + 1) * 0.1F;
		        }
		        // Check if player is not on ladder and if jump velocity calculated is equals to player Y velocity
		        if (player.getLocation().getBlock().getType() != Material.LADDER && Double.compare(velocity.getY(), jumpVelocity) == 0) {
		            velocity.setY((jumpVelocity / 2f) + (skillsconfig.getInt("jump") * 0.028f));
		            velocity.setX(Xdelta);
		            velocity.setZ(Zdelta);
		            player.setVelocity(velocity);
		        }
		    }
		    
		    if (player.isSwimming() && skillsconfig.getBoolean("swimOn")) {
		    	velocity.setX(Xdelta);
		    	velocity.setY(Ydelta);
	            velocity.setZ(Zdelta);
		    	double magnitude = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
		    	
		    	PotionEffect swimPotion = player.getPotionEffect(PotionEffectType.DOLPHINS_GRACE);
		    	ItemStack bootSlot = null;
		    	int striderLevel = 0;
		    	double baseSpeed = 0;
		    	if (player.getInventory().getBoots() != null) {
		    		bootSlot = player.getInventory().getBoots();
		    		if (bootSlot.containsEnchantment(Enchantment.DEPTH_STRIDER)) {
		    			striderLevel = bootSlot.getEnchantmentLevel(Enchantment.DEPTH_STRIDER);
		    		}
		    	}
		    	if (swimPotion != null) {
		        	switch (striderLevel) {
		        		case 0:
		        			baseSpeed = 0.49;
		        			break;
		        		case 1:
		        			baseSpeed = 1.33;
		        			break;
		        		case 2:
		        			baseSpeed = 2.19;
		        			break;
		        		case 3:
		        			baseSpeed = 3.03;
		        			break;
		        	}
		        } else {
		        	switch (striderLevel) {
	        		case 0:
	        			baseSpeed = 0.2;
	        			break;
	        		case 1:
	        			baseSpeed = 0.33;
	        			break;
	        		case 2:
	        			baseSpeed = 0.4;
	        			break;
	        		case 3:
	        			baseSpeed = 0.43;
	        			break;
		        	}
		        }
		    	double maxSpeed = baseSpeed * ((skillsconfig.getInt("swim") / 10f) + 1);
		    	
		    	double scalar = magnitude / maxSpeed;
		    	
		    	
		    	
		    	if (magnitude > maxSpeed) {
		    		velocity.setX(Xdelta / scalar);
//		    		velocity.setY(Ydelta / scalar);
		    		velocity.setZ(Zdelta / scalar);
		    	}
		    	magnitude = Math.sqrt(velocity.getX() * velocity.getX() + velocity.getZ() * velocity.getZ());
		    	player.setVelocity(velocity);
		    }
	    }
	    plugin.positionData.put(player, player.getLocation());
	}
}
