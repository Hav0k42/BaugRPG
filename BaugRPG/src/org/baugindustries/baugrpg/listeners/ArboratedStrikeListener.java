package org.baugindustries.baugrpg.listeners;


import org.baugindustries.baugrpg.ArmorStandEntity;
import org.baugindustries.baugrpg.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class ArboratedStrikeListener implements Listener {
	private Main plugin;
	public ArboratedStrikeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void PlayerAttackEvent(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) return;
		
		ArmorStandEntity tree = new ArmorStandEntity(event.getEntity().getLocation().add(0, -15, 0));
		//one block in a armor stand thing is 5/8ths of a real block (0.625)
		double height = 0;
		final double headLength = 0.625;
		int treeHeight = 12;//height of the trunk
		int leafHeight = 7;//height that the leaves begin
		int leafWidth = 8;//width of the leaves
		
		
		for (int i = 0; i < treeHeight; i++) {
			tree.add(Material.OAK_LOG, new Vector(headLength / 2, height, headLength / 2));
			tree.add(Material.OAK_LOG, new Vector(-headLength / 2, height, headLength / 2));
			tree.add(Material.OAK_LOG, new Vector(headLength / 2, height, -headLength / 2));
			tree.add(Material.OAK_LOG, new Vector(-headLength / 2, height, -headLength / 2));
			height += 0.625;
		}
		
		height = leafHeight * headLength;
		for (int l = 0; l < 3; l++) {
			for (double i = -((leafWidth / 2) * headLength) + (headLength / 2); i < ((leafWidth / 2) * headLength) + (headLength / 2); i += headLength) {
				for (double c = -((leafWidth / 2) * headLength) + (headLength / 2); c < ((leafWidth / 2) * headLength) + (headLength / 2); c += headLength) {
					if (!(Math.abs(c) == 0.3125 && (Math.abs(i) == 0.3125))) {
						tree.add(Material.OAK_LEAVES, new Vector(i, height, c));
					}
				}
				
			}
			height += headLength;
		}
		
		leafWidth -= 4;
		
		for (int l = 0; l < 2; l++) {
			for (double i = -((leafWidth / 2) * headLength) + (headLength / 2); i < ((leafWidth / 2) * headLength) + (headLength / 2); i += headLength) {
				for (double c = -((leafWidth / 2) * headLength) + (headLength / 2); c < ((leafWidth / 2) * headLength) + (headLength / 2); c += headLength) {
					if (!(Math.abs(c) == 0.3125 && (Math.abs(i) == 0.3125))) {
						tree.add(Material.OAK_LEAVES, new Vector(i, height, c));
					}
				}
				
			}
			height += headLength;
		}
		for (double i = -((leafWidth / 2) * headLength) + (headLength / 2); i < ((leafWidth / 2) * headLength) + (headLength / 2); i += headLength) {
			for (double c = -((leafWidth / 2) * headLength) + (headLength / 2); c < ((leafWidth / 2) * headLength) + (headLength / 2); c += headLength) {
				tree.add(Material.OAK_LEAVES, new Vector(i, height, c));
			}
			
		}
		
		tree.spawn();
		Vector startPos = event.getEntity().getLocation().toVector().add(new Vector(0, -1, 0));
 		Vector direction = new Vector(0, -1, 0);	 		
 		
 		double yPos = 0;
 		BlockIterator itr = new BlockIterator(event.getEntity().getLocation().getWorld(), startPos, direction, 0, 20);
		while (itr.hasNext()) {
 			Block block = itr.next();
 			if (block.getType().isSolid()) {
 				yPos = block.getY() + 1;
 				break;
 			}
 		}
		Location initLoc = new Location(event.getEntity().getLocation().getWorld(), event.getEntity().getLocation().getX(), yPos, event.getEntity().getLocation().getZ());
		runMoveTree(tree, initLoc);
		
	}
	
	private void runMoveTree(ArmorStandEntity tree, Location initLoc) {
		Runnable teleportPlayer = new Runnable() {
			public void run() {
				if (tree.getCenterLoc().getY() + 2 < initLoc.getY()) {
					initLoc.getWorld().playSound(initLoc, Sound.BLOCK_GRASS_STEP, 2, 1);
					tree.setLocation(tree.getCenterLoc().add(0, 2, 0));
					if (initLoc.getY() - tree.getCenterLoc().getY() < 10) {
						//Launch any nearby Entites: radius 2.5
						initLoc.getWorld().getNearbyEntities(initLoc, 2, 2, 2).forEach(entity -> {
							Vector launchVector = entity.getLocation().toVector().subtract(initLoc.toVector().setY(initLoc.getY() - 2));
							entity.setVelocity(launchVector.multiply(0.5));
						});
					}
					if (initLoc.getY() - tree.getCenterLoc().getY() < 7) {
						//Launch any nearby Entites: radius 5
						initLoc.getWorld().getNearbyEntities(initLoc, 5, 5, 5).forEach(entity -> {
							Vector launchVector = entity.getLocation().toVector().subtract(initLoc.toVector().setY(initLoc.getY() - 2));
							entity.setVelocity(launchVector.multiply(0.25));
						});
					}
					runMoveTree(tree, initLoc);
					
				} else {
					tree.setLocation(initLoc);
					initLoc.getWorld().playSound(initLoc, Sound.BLOCK_GRASS_STEP, 2, 1);
					initLoc.getWorld().playSound(initLoc, Sound.BLOCK_GRASS_STEP, 2, 0);
					initLoc.getWorld().playSound(initLoc, Sound.BLOCK_GRASS_STEP, 2, 0.2f);
					initLoc.getWorld().playSound(initLoc, Sound.BLOCK_GRASS_STEP, 2, 0.5f);
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							tree.kill();
						}
					}, 100L);
				}
  		  }
  		};
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, teleportPlayer, 1L);
	}
}
