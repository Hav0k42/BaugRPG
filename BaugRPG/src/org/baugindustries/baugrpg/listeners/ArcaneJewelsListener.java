package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.baugindustries.baugrpg.ArmorStandEntity;
import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class ArcaneJewelsListener implements Listener {
	private Main plugin;
	public ArcaneJewelsListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerAttackEvent(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		Player attacker = (Player) event.getDamager();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + attacker.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
	 	if (!(skillsconfig.contains("ArcaneJeweler1") && skillsconfig.getBoolean("ArcaneJeweler1"))) return;
	 	double percentage = 0.005;
	 	if (Math.random() > percentage) return;
	 	
		List<ArmorStandEntity> jewels = new ArrayList<ArmorStandEntity>();
		int layers = 20;
		double layerDist = 0.625;
		double initRadius = 0.8;
		double radiusIncr = 0.1;
		double jewelsPerLayer = 15;
		
		Boolean parallel = true;
		
		Location loc = event.getEntity().getLocation().subtract(0, 1.8, 0);
		
		Material[] gems = {Material.DIAMOND, Material.EMERALD, Material.AMETHYST_SHARD, Material.RAW_GOLD, Material.NETHER_STAR};
		
		for (int i = 0; i < layers; i++) {
			ArmorStandEntity jewelLayer = new ArmorStandEntity(loc);
			for (int c = 0; c < jewelsPerLayer; c++) {
				
				double angle = (c / jewelsPerLayer) * 2 * Math.PI;
				
				double offsetX = initRadius * Math.cos(angle);
				double offsetZ= initRadius * Math.sin(angle);
				int parallelOffset = 0;
				if (parallel) {
					parallelOffset = 90;
				}
				double yaw = Math.toDegrees(angle) + parallelOffset;
				jewelLayer.add(gems[(int)(Math.random() * gems.length)], new Vector(offsetX, layerDist * i, offsetZ), yaw);
			}
			initRadius += radiusIncr;
			jewelLayer.spawn();
			jewels.add(jewelLayer);
			
		}
		
		int cloudDensity = 200;
		Material[] cloudMats = {Material.DEEPSLATE, Material.DEEPSLATE, Material.DEEPSLATE, Material.DEEPSLATE, Material.DEEPSLATE, Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE, Material.GOLD_ORE};
		ArmorStandEntity cloudLayer = new ArmorStandEntity(loc);
		double cloudHeight = (layerDist * layers) + 1;
		initRadius += 1;
		for (int i = 0; i < cloudDensity; i++) {
			double radius = (Math.random() * initRadius * 2) - initRadius;
			double angle = Math.random() * 2 * Math.PI;
			cloudLayer.add(cloudMats[(int)(Math.random() * cloudMats.length)], new Vector(radius * Math.cos(angle), cloudHeight + ((Math.random() * 2) - 1), radius * Math.sin(angle)), Math.random() * 360, Math.random() * 360);
		}
		cloudLayer.spawn();
		jewels.add(cloudLayer);
		
		double speed = 0.2;
		runMoveTree(jewels, 0, new Vector(((Math.random() * 2) - 1), 0, ((Math.random() * 2) - 1)).normalize().multiply(speed), speed);
		plugin.activeTornadoes.add(jewels);
		
	}
	
	private void runMoveTree(List<ArmorStandEntity> jewels, int time, Vector direction, double speed) {
		double fallSpeed = 0.3;
		Runnable moveTree = new Runnable() {
			public void run() {
				
				Location baseLoc = jewels.get(0).getCenterLoc();
				
				BlockIterator itr = new BlockIterator(baseLoc.getWorld(), jewels.get(jewels.size() - 1).getCenterLoc().toVector().add(new Vector(0, 4, 0)), new Vector(0, -1, 0), 0, 20);
				double bottomBlockPos = baseLoc.getY() - 1.8;
		 		while (itr.hasNext()) {
		 			Block block = itr.next();
		 			if (block.getType().isSolid() || block.getType().equals(Material.WATER)) {
		 				bottomBlockPos = block.getY() + 1;
		 				break;
		 			}
		 		}
				
		 		double yOffset = 0;
		 		if (bottomBlockPos - 1.8 < baseLoc.getY()) {
		 			yOffset = -fallSpeed;
		 		} else if (bottomBlockPos - 1.8 > baseLoc.getY()) {
		 			yOffset = fallSpeed;
		 		}
				
				double spiralRadius = 0.02;
				for (int i = 0; i < jewels.size(); i++) {
					ArmorStandEntity jewelLayer = jewels.get(i);
					
					double newX = ((spiralRadius * i) * Math.cos(time + (i * 360 / Math.PI))) + jewelLayer.getCenterLoc().getX() + direction.getX();
					double newZ = ((spiralRadius * i) * Math.sin(time + (i * 360 / Math.PI))) + jewelLayer.getCenterLoc().getZ() + direction.getZ();
					double newY = jewelLayer.getCenterLoc().getY() + yOffset;
					
					jewelLayer.setLocation(new Location(baseLoc.getWorld(), newX, newY, newZ));
					if (i != jewels.size() - 1) {
						jewelLayer.rotate((float) (jewelLayer.getRotation() + i + 4));
					}
				}
				
				//define center of each layer using a vertical spiral.
				if (time < 400) {
					runMoveTree(jewels, time + 1, direction.add(new Vector(((Math.random() * 0.4) - 0.2), 0,((Math.random() * 0.4) - 0.2))).normalize().multiply(speed), speed);
					baseLoc.getWorld().getNearbyEntities(baseLoc, 8, 8, 8).forEach(entity -> {
						if (entity instanceof Player) {
							Player player = (Player) entity;
							if (plugin.getRace(player) == 3) {
								player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 200, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 200, 0));
								player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 60, 0));
							}
						}
					});
				} else {
					jewels.forEach(jewelLayer -> {
						jewelLayer.kill();
					});
					plugin.activeTornadoes.remove(jewels);
				}
			}
		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, moveTree, 1L);
	}
}
