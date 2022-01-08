package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.util.Collection;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;



public class EfficientBotanyListener implements Listener {
	private Main plugin;
	public EfficientBotanyListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onPlayerUse(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		File skillsfile = new File(plugin.getDataFolder() + File.separator + "skillsData" + File.separator + player.getUniqueId() + ".yml");
	 	FileConfiguration skillsconfig = YamlConfiguration.loadConfiguration(skillsfile);
	 	
        if (!(skillsconfig.contains("EnchantedBotanist1") && skillsconfig.getBoolean("EnchantedBotanist1"))) return;
    	if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
    	if (event.getItem() == null) return;
    	
    	String itemString = event.getItem().getType().name();
    	if (!itemString.substring(itemString.length() - 3).equals("HOE")) return;
    	
    	Block block = event.getClickedBlock();
    	if (!(block.getBlockData() instanceof Ageable)) return;
    	
    	Ageable crop = (Ageable)block.getBlockData();
    	if (crop.getAge() == crop.getMaximumAge()) {
    		crop.setAge(0);
    		Collection<ItemStack> drops = block.getDrops();
    		Material[] seeds = {Material.WHEAT_SEEDS, Material.BEETROOT_SEEDS, Material.CARROT, Material.POTATO, Material.NETHER_WART};
    		World world = player.getWorld();
    		drops.forEach(item -> {
    			for (int i = 0; i < seeds.length; i++ ) {
    				if (item.getType().equals(seeds[i])) {
    					item.setAmount(item.getAmount() - 1);
    					break;
    				}
    			}
    			if (item.getAmount() != 0) {
    				world.dropItem(block.getLocation(), item);
    			}
    		});
    		block.setBlockData(crop);
    	}
    	
    	
    	
	}
}
