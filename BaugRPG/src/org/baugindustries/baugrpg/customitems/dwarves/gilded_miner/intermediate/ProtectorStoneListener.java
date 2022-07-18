package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import org.bukkit.ChatColor;

public class ProtectorStoneListener implements Listener {
	private Main plugin;

	
	public ProtectorStoneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		if (!Recipes.PROTECTOR_STONE.playerIsCarrying(player, plugin)) return;
		
		PotionEffect resistance = player.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
		if (resistance == null) {
			player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20, 0));
			return;
		}
		if (resistance.getAmplifier() > 0) return;
		if (resistance.getDuration() > 12000) return;
		
		player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, resistance.getDuration() + 20, 0));
		
	}
	
	@EventHandler
	public void onUseEgg(PlayerInteractEvent event) {
		if (event.getHand() == null) return;
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.PROTECTOR_STONE.matches(plugin, event.getPlayer().getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.PROTECTOR_STONE.matches(plugin, event.getPlayer().getInventory().getItemInOffHand()))) return;
	    }

		event.getPlayer().sendMessage(ChatColor.RED + "This item cannot be used like that");
		event.setCancelled(true);
	}

}
