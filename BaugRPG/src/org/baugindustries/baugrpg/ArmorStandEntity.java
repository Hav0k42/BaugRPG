package org.baugindustries.baugrpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class ArmorStandEntity {
	List<Material> armorStandMaterials;
	List<Vector> armorStandOffsets;
	List<ArmorStand> armorStands;
	
	Location centerLoc;
	Boolean spawned;
	
	public ArmorStandEntity(Location centerLocation) {
		 armorStandMaterials = new ArrayList<Material>();
		 armorStandOffsets = new ArrayList<Vector>();
		 armorStands = new ArrayList<ArmorStand>();
		 centerLoc = centerLocation;
		 spawned = false;
	}
	
	
	public void add(Material mat, Vector offset) {
		armorStandMaterials.add(mat);
		armorStandOffsets.add(offset);
	}
	
	public void spawn() {
		if (!spawned) {//if entity is not spawned, spawn.
			for (int i = 0; i < armorStandMaterials.size(); i++) {
				Location newLoc = new Location(centerLoc.getWorld(), centerLoc.getX() + armorStandOffsets.get(i).getX(), centerLoc.getY() + armorStandOffsets.get(i).getY(), centerLoc.getZ() + armorStandOffsets.get(i).getZ());
				armorStands.add((ArmorStand) centerLoc.getWorld().spawnEntity(newLoc, EntityType.ARMOR_STAND));
				armorStands.get(i).setInvisible(true);
				armorStands.get(i).setInvulnerable(true);
				armorStands.get(i).setGravity(false);
				armorStands.get(i).setMarker(true);
				armorStands.get(i).getEquipment().setHelmet(new ItemStack(armorStandMaterials.get(i)));
			}
			spawned = true;
		}
	}
	
	public void kill() {
		if (spawned) {
			armorStands.forEach(armorStand -> {
				armorStand.remove();
			});
			spawned = false;
			armorStands = new ArrayList<ArmorStand>();
			//Reset armor stands list so it doesn't get duplicated on a second spawn.
		}
	}
	
	public void setLocation(Location loc) {
		centerLoc = loc;
		//update center location, and if entity is spawned update each armorstands location. 
		if (spawned) {
			for (int i = 0; i < armorStandMaterials.size(); i++) {
				Location newLoc = new Location(centerLoc.getWorld(), centerLoc.getX() + armorStandOffsets.get(i).getX(), centerLoc.getY() + armorStandOffsets.get(i).getY(), centerLoc.getZ() + armorStandOffsets.get(i).getZ());
				armorStands.get(i).teleport(newLoc);
			}
		}
	}
	
	public Location getCenterLoc() {
		return centerLoc;
	}
	
	
	//this method doesnt work. stands with no gravity can't have a velocity.
	
//	public void setVelocity(Vector velocity) {
//		if (spawned) {
//			//doesn't make sense to change armor stand velocity if armor stand does not exist
//			for (int i = 0; i < armorStandMaterials.size(); i++) {
//				armorStands.get(i).setVelocity(velocity);
//			}
//		}
//	}
	
	
	
}
