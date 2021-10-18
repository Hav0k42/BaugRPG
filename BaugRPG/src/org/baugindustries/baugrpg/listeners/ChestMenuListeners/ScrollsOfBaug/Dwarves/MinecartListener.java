package org.baugindustries.baugrpg.listeners.ChestMenuListeners.ScrollsOfBaug.Dwarves;

import org.baugindustries.baugrpg.Main;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.persistence.PersistentDataType;

public class MinecartListener implements Listener {
	
	private Main plugin;
	public MinecartListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void enter(VehicleEnterEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Player player = (Player) event.getEntered();
            Minecart minecart = (Minecart) event.getVehicle();
            
            if (player.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 3) {//Check if the player is of the race of Dwarf
            	minecart.setMaxSpeed(minecart.getMaxSpeed() * 4);
            	//figure out how to modify the minecart so it doesn't slow down, or behave as if it is on powered rails.
            }
        }
    }
	
	
	
	@EventHandler
    public void exit(VehicleExitEvent event) {
        if (event.getVehicle() instanceof Minecart) {
            Player player = (Player) event.getExited();
            Minecart minecart = (Minecart) event.getVehicle();
            
            if (player.getPersistentDataContainer().get(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER) == 3) {//Check if the player is of the race of Dwarf
            	minecart.setMaxSpeed(minecart.getMaxSpeed() / 4);
            }
        }
    }
	
}
