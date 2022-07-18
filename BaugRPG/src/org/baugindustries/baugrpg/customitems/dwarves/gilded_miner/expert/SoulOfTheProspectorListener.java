package org.baugindustries.baugrpg.customitems.dwarves.gilded_miner.expert;

import java.util.HashMap;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Profession;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SoulOfTheProspectorListener implements Listener {
	private Main plugin;
	
	HashMap<UUID, Integer> minedBlockCount = new HashMap<UUID, Integer>();
	
	
	public SoulOfTheProspectorListener(Main plugin) {
		this.plugin = plugin;
	}
	

	
	@EventHandler
	public void onInventoryClick(BlockBreakEvent event) {
		Player player = event.getPlayer();

		if (plugin.getProfession(player) == null || !plugin.getProfession(player).equals(Profession.GILDED_MINER)) return;
		
		if (!Recipes.SOUL_OF_THE_PROSPECTOR.playerIsCarrying(player, plugin)) return;
		
		if (minedBlockCount.containsKey(player.getUniqueId())) {
			minedBlockCount.put(player.getUniqueId(), minedBlockCount.get(player.getUniqueId()) + 1);
			
			if (minedBlockCount.get(player.getUniqueId()) == 9) {
				minedBlockCount.remove(player.getUniqueId());
			}
			
			return;
		} else {
			minedBlockCount.put(player.getUniqueId(), 1);
		}
		
		BlockFace face = player.getFacing();
		if (!face.isCartesian()) {
			face = toCartesian(face);
		}
		
		if (player.getLocation().getPitch() > 60) {
			face = BlockFace.DOWN;
		} else if (player.getLocation().getPitch() < -60) {
			face = BlockFace.UP;
		}
		
		
		if (face.equals(BlockFace.DOWN) || face.equals(BlockFace.UP)) {
			for (int i = -1; i <= 1; i++) {
				for (int c = -1; c <= 1; c++) {
					if (!(i == 0 && c == 0)) {
						if (isMineable(event.getBlock().getRelative(i, 0, c))) {
							player.breakBlock(event.getBlock().getRelative(i, 0, c));
						}
					}
				}
			}
		} else if (face.equals(BlockFace.EAST) || face.equals(BlockFace.WEST)) {
			for (int i = -1; i <= 1; i++) {
				for (int c = -1; c <= 1; c++) {
					if (!(i == 0 && c == 0)) {
						if (isMineable(event.getBlock().getRelative(0, i, c))) {
							player.breakBlock(event.getBlock().getRelative(0, i, c));
						}
					}
				}
			}
		} else if (face.equals(BlockFace.NORTH) || face.equals(BlockFace.SOUTH)) {
			for (int i = -1; i <= 1; i++) {
				for (int c = -1; c <= 1; c++) {
					if (!(i == 0 && c == 0)) {
						if (isMineable(event.getBlock().getRelative(i, c, 0))) {
							player.breakBlock(event.getBlock().getRelative(i, c, 0));
						}
					}
				}
			}
		}
		
		
	}

	
	public static boolean isMineable(Block block) {
		switch (block.getType()) {
			case BEDROCK:
				return false;
			case COMMAND_BLOCK:
				return false;
			case END_PORTAL_FRAME:
				return false;
			case END_PORTAL:
				return false;
			case BARRIER:
				return false;
			case STRUCTURE_BLOCK:
				return false;
			default:
				return true;
		}
	}
	
	public static BlockFace toCartesian(BlockFace face) {
		switch (face) {
			case DOWN:
				return BlockFace.DOWN;
			case EAST:
				return BlockFace.EAST;
			case EAST_NORTH_EAST:
				return BlockFace.EAST;
			case EAST_SOUTH_EAST:
				return BlockFace.EAST;
			case NORTH:
				return BlockFace.NORTH;
			case NORTH_EAST:
				return BlockFace.EAST;
			case NORTH_NORTH_EAST:
				return BlockFace.NORTH;
			case NORTH_NORTH_WEST:
				return BlockFace.NORTH;
			case NORTH_WEST:
				return BlockFace.NORTH;
			case SELF:
				return BlockFace.DOWN;
			case SOUTH:
				return BlockFace.SOUTH;
			case SOUTH_EAST:
				return BlockFace.SOUTH;
			case SOUTH_SOUTH_EAST:
				return BlockFace.SOUTH;
			case SOUTH_SOUTH_WEST:
				return BlockFace.SOUTH;
			case SOUTH_WEST:
				return BlockFace.WEST;
			case UP:
				return BlockFace.UP;
			case WEST:
				return BlockFace.WEST;
			case WEST_NORTH_WEST:
				return BlockFace.WEST;
			case WEST_SOUTH_WEST:
				return BlockFace.WEST;
			default:
				return BlockFace.DOWN;
		}
	}
	
}
