package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;

public class HorseListener implements Listener{

	
	private Main plugin;
	public int buffedHorseSpeed = 3;
	public double buffedHorseHealth = 1.5;
	public HorseListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPlayerInteractEntity (VehicleEnterEvent event) {
	    if (event.getVehicle() instanceof AbstractHorse) {

	        AbstractHorse horse = (AbstractHorse) event.getVehicle();
	        Player player = (Player) event.getEntered();
	        File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	        if ((skillsconfig.contains("StableMaster1") && skillsconfig.getBoolean("StableMaster1")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("StableMaster1")) {
	        	horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * buffedHorseSpeed);
	        }
	        if ((skillsconfig.contains("StableMaster3") && skillsconfig.getBoolean("StableMaster3")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("StableMaster3")) {
	        	double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
	        	horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * buffedHorseHealth);
	        	horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
	        }
	        plugin.mountedPlayers.add(player);
	    }
	}
	
	
	
	@EventHandler
    public void exit(VehicleExitEvent event) {
        if (event.getVehicle() instanceof AbstractHorse) {
            Player player = (Player) event.getExited();
            AbstractHorse horse = (AbstractHorse) event.getVehicle();
            File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
		 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
		 	if ((skillsconfig.contains("StableMaster1") && skillsconfig.getBoolean("StableMaster1")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("StableMaster1")) {
            	horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() / buffedHorseSpeed);
	        }
		 	if ((skillsconfig.contains("StableMaster3") && skillsconfig.getBoolean("StableMaster3")) || plugin.magnetizedIdolListener.getActiveBestowedAbility(player.getUniqueId()).equals("StableMaster3")) {
            	double healthPercentage = horse.getHealth() / horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue();
            	horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() / buffedHorseHealth);
            	horse.setHealth(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * healthPercentage);
	        }
            plugin.mountedPlayers.remove(player);
        }
    }

	public int getBuffedHorseSpeed() {
		return buffedHorseSpeed;
	}
	
	public double getBuffedHorseHealth() {
		return buffedHorseHealth;
	}
}
