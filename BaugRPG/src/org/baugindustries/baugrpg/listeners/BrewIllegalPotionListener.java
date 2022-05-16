package org.baugindustries.baugrpg.listeners;

import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

import net.md_5.bungee.api.ChatColor;

public class BrewIllegalPotionListener implements Listener {
	private Main plugin;
	PotionType[] illegalPotionTypes = {PotionType.INVISIBILITY, PotionType.INSTANT_DAMAGE};
	
	public BrewIllegalPotionListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(BrewEvent event) {
		List<ItemStack> results = event.getResults();
		for (int i = 0; i < results.size(); i++) {
			ItemStack potion = results.get(i);
			PotionMeta meta = (PotionMeta) potion.getItemMeta();
			PotionData data = meta.getBasePotionData();
			for (PotionType type : illegalPotionTypes) {
				if (data.getType().equals(type)) {
					int slot = i;
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					  public void run() {
							event.getContents().setItem(slot, plugin.createItem(Material.POTION, 1, ChatColor.RED + "You cannot brew that potion."));
					  }
				 }, 1L);
				}
			}
		}
	}

}