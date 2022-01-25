package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class GildedFortuneListener implements Listener{
	private Main plugin;
	public GildedFortuneListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void enter(BlockDropItemEvent event) {
		Player player = (Player) event.getPlayer();
		
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
        
        if (!(skillsconfig.contains("GildedMiner1") && skillsconfig.getBoolean("GildedMiner1"))) return;
        
       

        Material[] fortuneBuffedDrops = {Material.COAL, Material.DIAMOND, Material.EMERALD, Material.RAW_IRON, Material.RAW_GOLD, Material.RAW_COPPER, Material.GOLD_NUGGET, Material.QUARTZ, Material.LAPIS_LAZULI, Material.AMETHYST_SHARD, Material.GLOWSTONE_DUST, Material.MELON_SLICE, Material.NETHER_WART, Material.REDSTONE, Material.PRISMARINE_CRYSTALS, Material.PRISMARINE_SHARD, Material.SWEET_BERRIES};
        List<Item> drops = event.getItems();
        Collection<ItemStack> newDrops = new ArrayList<ItemStack>();
        drops.forEach(dropitem -> {
        	ItemStack drop = dropitem.getItemStack();
        	for (int i = 0; i < fortuneBuffedDrops.length; i++) {
        		if (drop.getType() == fortuneBuffedDrops[i]) {
        			drop.setAmount(drop.getAmount() + ((int)(Math.random() * 3)));
        		}
        	}
    		newDrops.add(drop);
        });
        newDrops.forEach(drop -> {
    		player.getWorld().dropItemNaturally(event.getBlock().getLocation(), drop);
        });
        event.setCancelled(true);
	}
}
