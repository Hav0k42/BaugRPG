package org.baugindustries.baugrpg.listeners;

import java.io.File;

import org.baugindustries.baugrpg.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

public class MinecartMoveListener implements Listener{
	private Main plugin;
	public MinecartMoveListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
    public void enter(VehicleMoveEvent event) {
        if (event.getVehicle() instanceof Minecart) {
        	if (event.getVehicle().getPassenger() != null && event.getVehicle().getPassenger() instanceof Player) {
	            Player player = (Player) event.getVehicle().getPassenger();
	            Minecart minecart = (Minecart) event.getVehicle();
	            
	            File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	    	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	            
	            if (skillsconfig.contains("GildedMiner2") && skillsconfig.getBoolean("GildedMiner2")) {//Check if the player is of the race of Dwarf
	            	Vector velocity = new Vector();
	            	velocity.setX(10 * (minecart.getVelocity().getX()));
	            	velocity.setY(minecart.getVelocity().getY());
	            	velocity.setZ(10 * (minecart.getVelocity().getZ()));
	            	minecart.setVelocity(velocity);
	            	
	            }
        	}
        }
    }
}
