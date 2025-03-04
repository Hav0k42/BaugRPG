package org.baugindustries.baugrpg.listeners;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.persistence.PersistentDataType;

public class HorseListener implements Listener{

	
	private Main plugin;
	int buffedHorseSpeed = 3;
	public HorseListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPlayerInteractEntity (VehicleEnterEvent event) {
	    if (event.getVehicle() instanceof AbstractHorse) {

	        AbstractHorse horse = (AbstractHorse) event.getVehicle();
	        Player player = (Player) event.getEntered();
	        
	        if (player.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 1) {//Check if the player is of the race of Men
	        	horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * buffedHorseSpeed);
	        }
	    }
	}
	
	
	
	@EventHandler
    public void exit(VehicleExitEvent event) {
        if (event.getVehicle() instanceof AbstractHorse) {
            Player player = (Player) event.getExited();
            AbstractHorse horse = (AbstractHorse) event.getVehicle();
            
            if (player.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 1) {//Check if the player is of the race of Men
            	horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() / buffedHorseSpeed);
	        }
        }
    }

	
}
