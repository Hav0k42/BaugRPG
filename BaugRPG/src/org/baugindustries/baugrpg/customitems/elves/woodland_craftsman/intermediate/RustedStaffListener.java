package org.baugindustries.baugrpg.customitems.elves.woodland_craftsman.intermediate;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class RustedStaffListener implements Listener {
	private Main plugin;

	public RustedStaffListener(Main plugin) {
		this.plugin = plugin;
		
	}
	
	
	@EventHandler
	public void onClickCopper(PlayerInteractEvent event) {
		
		Player player = event.getPlayer();
		
		if (event.getHand().equals(EquipmentSlot.HAND)) {
			if (!(Recipes.RUSTED_STAFF.matches(plugin, player.getInventory().getItemInMainHand()))) return;
	    } else {
			if (!(Recipes.RUSTED_STAFF.matches(plugin, player.getInventory().getItemInOffHand()))) return;
	    }
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) event.setCancelled(true);
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) event.setCancelled(true);
		
		
		Material[] blocks = {Material.COPPER_BLOCK, Material.EXPOSED_COPPER, Material.WEATHERED_COPPER, Material.OXIDIZED_COPPER};
		
		Material[] cut = {Material.CUT_COPPER, Material.EXPOSED_CUT_COPPER, Material.WEATHERED_CUT_COPPER, Material.OXIDIZED_CUT_COPPER};

		Material[] stairs = {Material.CUT_COPPER_STAIRS, Material.EXPOSED_CUT_COPPER_STAIRS, Material.WEATHERED_CUT_COPPER_STAIRS, Material.OXIDIZED_CUT_COPPER_STAIRS};

		Material[] slabs = {Material.CUT_COPPER_SLAB, Material.EXPOSED_CUT_COPPER_SLAB, Material.WEATHERED_CUT_COPPER_SLAB, Material.OXIDIZED_CUT_COPPER_SLAB};
		
		Material[][] copper = {blocks, cut, stairs, slabs};
		
		boolean match = false;
		Material[] copperType = null;
		int index = 0;
		for (Material[] types : copper) {
			for (int i = 0; i < types.length; i++) {
				if (event.getClickedBlock().getType().equals(types[i])) {
					match = true;
					index = i;
					copperType = types;
					break;
				}
			}
		}
		
		if (!match) return;
		
		
		
		if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			if (index == 3) return;
			index++;
		} else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (index == 0) return;
			index--;
		}
		BlockData tempData = event.getClickedBlock().getBlockData();
		event.getClickedBlock().setType(copperType[index]);

		if (event.getClickedBlock().getBlockData() instanceof Slab) {
			Slab data = (Slab) event.getClickedBlock().getBlockData();
			data.setType(((Slab)tempData).getType());
			data.setWaterlogged(((Slab)tempData).isWaterlogged());
			
			event.getClickedBlock().setBlockData(data);
		} else if (event.getClickedBlock().getBlockData() instanceof Stairs) {
			Stairs data = (Stairs) event.getClickedBlock().getBlockData();
			data.setFacing(((Stairs)tempData).getFacing());
			data.setHalf(((Stairs)tempData).getHalf());
			data.setShape(((Stairs)tempData).getShape());
			data.setWaterlogged(((Stairs)tempData).isWaterlogged());
			
			event.getClickedBlock().setBlockData(data);
		}
		
	}

}
