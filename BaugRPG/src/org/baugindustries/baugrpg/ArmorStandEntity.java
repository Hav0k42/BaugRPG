package org.baugindustries.baugrpg;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

public class ArmorStandEntity {
	List<Material> armorStandMaterials;
	List<Vector> armorStandOffsets;
	List<Double> armorStandYaws;
	List<Double> armorStandPitches;
	List<ArmorStand> armorStands;
	
	private double rotation;
	
	Location centerLoc;
	Boolean spawned;
	
	public ArmorStandEntity(Location centerLocation) {
		 armorStandMaterials = new ArrayList<Material>();
		 armorStandOffsets = new ArrayList<Vector>();
		 armorStandYaws = new ArrayList<Double>();
		 armorStandPitches = new ArrayList<Double>();
		 armorStands = new ArrayList<ArmorStand>();
		 centerLoc = centerLocation;
		 spawned = false;
		 rotation = 0;
	}
	
	
	public void add(Material mat, Vector offset) {
		add(mat, offset, 0, 0);
	}
	
	public void add(Material mat, Vector offset, double yaw) {
		add(mat, offset, yaw, 0);
	}
	
	public void add(Material mat, Vector offset, double yaw, double pitch) {
		armorStandMaterials.add(mat);
		armorStandOffsets.add(offset);
		armorStandYaws.add(yaw);
		armorStandPitches.add(pitch);
	}
	
	public void spawn() {
		if (!spawned) {//if entity is not spawned, spawn.
			for (int i = 0; i < armorStandMaterials.size(); i++) {
				Location newLoc = new Location(centerLoc.getWorld(), centerLoc.getX() + armorStandOffsets.get(i).getX(), centerLoc.getY() + armorStandOffsets.get(i).getY(), centerLoc.getZ() + armorStandOffsets.get(i).getZ(), armorStandYaws.get(i).floatValue(), armorStandPitches.get(i).floatValue());
				armorStands.add((ArmorStand) centerLoc.getWorld().spawnEntity(newLoc, EntityType.ARMOR_STAND));
				armorStands.get(i).setHeadPose(new EulerAngle(armorStandPitches.get(i), 0, 0) );
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
				Location newLoc = new Location(centerLoc.getWorld(), centerLoc.getX() + armorStandOffsets.get(i).getX(), centerLoc.getY() + armorStandOffsets.get(i).getY(), centerLoc.getZ() + armorStandOffsets.get(i).getZ(), armorStandYaws.get(i).floatValue(), armorStandPitches.get(i).floatValue());
				armorStands.get(i).teleport(newLoc);
			}
		}
	}
	
	public Location getCenterLoc() {
		return centerLoc;
	}
	
	public void rotate(float degrees) {
		//Rotates the entire entity to an absolute degree.
		if (spawned) {
			rotation = degrees;
			for (int i = 0; i < armorStandMaterials.size(); i++) {
				ArmorStand armorStand = armorStands.get(i);
				Vector offset = armorStandOffsets.get(i);
				
				if (!(offset.getX() == 0 && offset.getZ() == 0)) {
				
					double radius = Math.sqrt((offset.getX() * offset.getX()) + (offset.getZ() * offset.getZ()));
					double angle = Math.atan2(offset.getZ(), offset.getX()) + Math.toRadians(degrees);
					
					double newX = (radius * Math.cos(angle)) + centerLoc.getX();
					double newZ = (radius * Math.sin(angle)) + centerLoc.getZ();
					armorStand.teleport(new Location(armorStand.getWorld(), newX, armorStand.getLocation().getY(), newZ));
				}
				armorStand.setRotation(degrees + armorStandYaws.get(i).floatValue(), armorStand.getLocation().getPitch() + armorStandPitches.get(i).floatValue());
			}
		}
	}
	
	public void rotate() {
		//reset to default rotation
		rotate(0);
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public void glow(int ticks) {
		armorStands.forEach(stand -> {
			stand.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, ticks, 0));
		});
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
