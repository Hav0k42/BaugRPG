package org.baugindustries.baugrpg;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class SignData {
	public SignData(Player owner, Material type, Location chestLocation) {
        this.owner = owner;
        this.type = type;
        this.chestLocation = chestLocation;
    }

    
    public SignData() {}

    private Player owner;
    private Material type;
    private Location chestLocation;

    // Getters and setters

    @Override
    public String toString() {
        return "\nOwner: " + owner + "\nType: " + type;
    }
    
    public Player getOwner() {
    	return owner;
    }
    
    public Material getType() {
    	return type;
    }
    
    public Location getChestLocation() {
    	return chestLocation;
    }
}
