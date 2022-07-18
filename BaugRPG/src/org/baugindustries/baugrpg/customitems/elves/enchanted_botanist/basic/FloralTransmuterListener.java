package org.baugindustries.baugrpg.customitems.elves.enchanted_botanist.basic;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.entity.Panda;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class FloralTransmuterListener implements Listener {
	
	Main plugin;
	
	public FloralTransmuterListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void clickFlower(PlayerInteractEvent event) {

		Player player = event.getPlayer();
		
		if (!(event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.FLORAL_TRANSMUTER.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.FLORAL_TRANSMUTER.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		Material[] tulips = {Material.ORANGE_TULIP, Material.PINK_TULIP, Material.RED_TULIP, Material.WHITE_TULIP};
		
		Material[] smallBushes = {Material.AZURE_BLUET, Material.BLUE_ORCHID, Material.LILY_OF_THE_VALLEY};
		
		Material[] stemmedFlowers = {Material.CORNFLOWER, Material.ALLIUM, Material.OXEYE_DAISY, Material.POPPY, Material.DANDELION};
		
		Material[] tallBushes = {Material.PEONY, Material.ROSE_BUSH, Material.LILAC, Material.SUNFLOWER};
		
		Material[][] flowers = {tulips, smallBushes, stemmedFlowers, tallBushes};
		
		Block block = event.getClickedBlock();
		
		
		for (Material[] types : flowers) {
			for (Material flower : types) {
				if (block.getType().equals(flower)) {
					if (block.getBlockData() instanceof Bisected) {
						Block otherBlock;
						Material newMat = types[(int)(Math.random() * types.length)];
						if (block.getLocation().subtract(0, 1, 0).getBlock().getType().equals(block.getType())) {
							//clicked top block
							otherBlock = block.getLocation().subtract(0, 1, 0).getBlock();
							otherBlock.setType(newMat, false);
							block.setType(newMat, false);
							
							Bisected topData = (Bisected) block.getBlockData();
							topData.setHalf(Half.TOP);
							block.setBlockData(topData);
						} else {
							//clicked bottom block
							otherBlock = block.getLocation().add(0, 1, 0).getBlock();
							block.setType(newMat, false);
							otherBlock.setType(newMat, false);

							Bisected topData = (Bisected) otherBlock.getBlockData();
							topData.setHalf(Half.TOP);
							otherBlock.setBlockData(topData);
						}

					} else {
						block.setType(types[(int)(Math.random() * types.length)]);
					}
					player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, block.getLocation().add(0.5, 0.7, 0.5), 4, 0.2, 0.2, 0.2);
					player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.MASTER, 2f, 1f);
					break;
				}
			}
		}
		
		event.setCancelled(true);
		
		
		
	}
	
	@EventHandler
	public void onInventoryClick(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		
		if (!(event.getRightClicked() instanceof Panda)) return;
		
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!Recipes.FLORAL_TRANSMUTER.matches(plugin, player.getInventory().getItemInMainHand())) return;
		} else {
			if (!Recipes.FLORAL_TRANSMUTER.matches(plugin, player.getInventory().getItemInOffHand())) return;
		}

		event.setCancelled(true);
		
	}

}
