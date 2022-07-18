package org.baugindustries.baugrpg.customitems.dwarves.radiant_metallurgist.intermediate;

import java.util.Collection;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

public class HandForgeListener implements Listener {
	private Main plugin;
	
	public HandForgeListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPickUpOre(BlockBreakEvent event) {
		Player player = event.getPlayer();
		
		Material[] ores = {Material.COPPER_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.ANCIENT_DEBRIS};
		Material[] smelted = {Material.COPPER_INGOT, Material.IRON_INGOT, Material.GOLD_INGOT, Material.NETHERITE_SCRAP};
		
		boolean match = false;
		int index = 0;
		for (int i = 0; i < ores.length; i++) {
			if (event.getBlock().getType().equals(ores[i])) {
				match = true;
				index = i;
				break;
			}
		}
		
		if (!match) return;
		
		if (!Recipes.HAND_FORGE.playerIsCarrying(player, plugin)) return;
		
		ItemStack coal = null;
		int coalIndex = 0;
		for (int i = 0; i < player.getInventory().getSize(); i++) {
			if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).getType().equals(Material.COAL)) {
				coal = player.getInventory().getItem(i);
				coalIndex = i;
			}
		}
		if (coal == null) return;
		
		if (Math.random() < 0.125) {
			coal.setAmount(coal.getAmount() - 1);
			player.getInventory().setItem(coalIndex, coal);
		}
		
		event.setDropItems(false);
		
		Collection<ItemStack> drops = event.getBlock().getDrops(player.getInventory().getItemInMainHand(), player);
		int amount = drops.toArray(new ItemStack[drops.size()])[0].getAmount();
		
		player.getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(smelted[index], amount));
		
		
		
		
		
	}
	
	@EventHandler
	public void onPlaceForge(BlockPlaceEvent event) {
		if (!Recipes.HAND_FORGE.matches(plugin, event.getItemInHand())) return;
		event.setCancelled(true);
		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be placed");
	}

}
