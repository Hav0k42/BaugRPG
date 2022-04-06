package org.baugindustries.baugrpg.protection;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.protection.ClaimData.State;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;

import net.md_5.bungee.api.ChatColor;

public class Claiming implements Listener {
	private List<ClaimData> claimingData = new ArrayList<ClaimData>();
	private long sustainTime = 400;
	private Main plugin;
	private Material mainMat1 = Material.GOLD_BLOCK;
	private Material mainMat2 = Material.REDSTONE_BLOCK;
	private Material secondaryMat1 = Material.GLOWSTONE;
	private Material secondaryMat2 = Material.SHROOMLIGHT;
	public Claiming(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void RightClickShovel(PlayerInteractEvent event) {
		if (!(event.hasItem() && event.getItem().getType().equals(Material.GOLDEN_SHOVEL))) return;
		if (event.getClickedBlock() == null) return;
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		int race = plugin.getRace(player);
		if (!(race == 1 || race == 3)) return;
		if (!isBlockInChunkClaim(event.getClickedBlock())) return;
		int playerClaimBlocks = getPlayerClaimBlocks(player);

		event.setCancelled(true);
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		if (!claimsConfig.contains("personalClaims")) {
			claimsConfig.createSection("personalClaims");
		}
		
		int dataIndex = -1;
		for (int i = 0; i < claimingData.size(); i++) {
			if (claimingData.get(i).getUUID().equals(player.getUniqueId())) {
				dataIndex = i;
				break;
			}
		}
		
		ClaimData claimData;
		
		if (dataIndex == -1) {
			claimData = new ClaimData(player.getUniqueId(), event.getClickedBlock().getLocation());
		} else {
			claimData = claimingData.get(dataIndex);
		}
		
		claimData.setLastClick(player.getWorld().getGameTime());
		String claimID = null;
		switch (claimData.getState()) {
			case IDLE:
				claimID = getBlockOwner(event.getClickedBlock());
				if (claimID != null) {
					ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
					UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
					if (ownerUUID.equals(player.getUniqueId())) {
						player.sendMessage(ChatColor.GOLD + "You already have a claim here. Resize this claim by selecting a corner.");
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYOWN);
					} else {
						player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYELSE);
					}
				} else {
					player.sendMessage(ChatColor.GOLD + "First block selected, right click the block you want to be the opposite corner of your claim. Click the same block to cancel.");
					event.setCancelled(true);
					//Use protocolLib to make this block appear as a diamond block surrounded by glowstone.
					World world = player.getWorld();
					Block clickedBlock = event.getClickedBlock();
					
					claimData.setLocation1(event.getClickedBlock().getLocation());
					
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(clickedBlock), Material.DIAMOND_BLOCK, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() + 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() - 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() + 1)), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() - 1)), Material.GLOWSTONE, player));
					claimData.setState(State.CLAIMING);
				}
				break;
			case CLAIMING:
				claimData.setLocation2(event.getClickedBlock().getLocation());
				
				if (claimData.getXDist() < 4 || claimData.getZDist() < 4) {
					if (claimData.getXDist() == 0 && claimData.getZDist() == 0) {
						claimData.setState(State.IDLE);
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						player.sendMessage(ChatColor.YELLOW + "Claiming cancelled.");
					} else {
						player.sendMessage(ChatColor.RED + "Claims must be at least 5 x 5 in size.");
					}
				} else {
					claimID = getClaimOverlap(claimData.getLocation1(), claimData.getLocation2());
					if (claimID != null) {
						ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
						UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
						if (ownerUUID.equals(player.getUniqueId())) {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.CLAIMINGOVERLAPOWN);
						} else {
							player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
							claimData.setState(State.CLAIMINGOVERLAPELSE);
						}
					} else {
						int area = claimData.getArea();
						if (playerClaimBlocks < area) {
							player.sendMessage(ChatColor.RED + "You do not have enough claim blocks to claim this area. Available claim blocks: " + playerClaimBlocks + ".");
							break;
						}
						player.sendMessage(ChatColor.GOLD + "You have successfully claimed this region. To resize it, select a corner with your shovel.");
						setPlayerClaimBlocks(player, playerClaimBlocks - area);
						player.sendMessage(ChatColor.GOLD + "Remaining claim blocks: " + (playerClaimBlocks - area));
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(claimData.getLocation1(), claimData.getLocation2(), player, mainMat1, secondaryMat1));
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ConfigurationSection newClaim = playerClaims.createSection(claimData.getLocation1().getBlockX() + "," + claimData.getLocation1().getBlockZ() + "," + claimData.getLocation2().getBlockX() + "," + claimData.getLocation2().getBlockZ());
						newClaim.set("owner", player.getUniqueId().toString());
						newClaim.set("location1", claimData.getLocation1());
						newClaim.set("location2", claimData.getLocation2());
						claimData.setState(State.DISPLAYOWN);
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case RESIZING:
				claimData.setLocation2(event.getClickedBlock().getLocation());
				
				if (claimData.getXDist() < 4 || claimData.getZDist() < 4) {
					player.sendMessage(ChatColor.RED + "Claims must be at least 5 x 5 in size.");
				} else {
					claimID = getClaimOverlap(claimData.getLocation1(), claimData.getLocation2(), claimData.getResizingKey());
					if (claimID != null) {
						ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
						UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
						if (ownerUUID.equals(player.getUniqueId())) {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.RESIZINGOVERLAPOWN);
						} else {
							player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
							claimData.setState(State.RESIZINGOVERLAPELSE);
						}
					} else {
						int area = claimData.getArea();
						
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						
						ClaimData tempData = new ClaimData(player.getUniqueId(), playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation1(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation2(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location2"));
						if (playerClaimBlocks + tempData.getArea() < area) {
							player.sendMessage(ChatColor.RED + "You do not have enough claim blocks to claim this area. Available claim blocks: " + playerClaimBlocks + ".");
							break;
						}
						
						player.sendMessage(ChatColor.GOLD + "You have successfully resized your claim.");
						setPlayerClaimBlocks(player, playerClaimBlocks + tempData.getArea() - area);
						player.sendMessage(ChatColor.GOLD + "Remaining claim blocks: " + (playerClaimBlocks + tempData.getArea() - area));
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(claimData.getLocation1(), claimData.getLocation2(), player, mainMat1, secondaryMat1));
						
						
						playerClaims.set(claimData.getResizingKey(), null);
						claimData.setResizingKey(null);
						
						
						ConfigurationSection newClaim = playerClaims.createSection(claimData.getLocation1().getBlockX() + "," + claimData.getLocation1().getBlockZ() + "," + claimData.getLocation2().getBlockX() + "," + claimData.getLocation2().getBlockZ());
						newClaim.set("owner", player.getUniqueId().toString());
						newClaim.set("location1", claimData.getLocation1());
						newClaim.set("location2", claimData.getLocation2());
						claimData.setState(State.DISPLAYOWN);
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case DISPLAYELSE:
				claimData.getTempBlocks().forEach(block -> {
					updateChangedBlock(block, player);
				});
				claimData.resetTempBlocks();
				claimID = getBlockOwner(event.getClickedBlock());
				if (claimID != null) {
					ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
					UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
					if (ownerUUID.equals(player.getUniqueId())) {
						player.sendMessage(ChatColor.GOLD + "You already have a claim here. Resize this claim by selecting a corner.");
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYOWN);
					} else {
						player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYELSE);
					}
				} else {
					player.sendMessage(ChatColor.GOLD + "First block selected, right click the block you want to be the opposite corner of your claim. Click the same block to cancel.");
					event.setCancelled(true);
					//Use protocolLib to make this block appear as a diamond block surrounded by glowstone.
					World world = player.getWorld();
					Block clickedBlock = event.getClickedBlock();
					
					claimData.setLocation1(event.getClickedBlock().getLocation());
					
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(clickedBlock), Material.DIAMOND_BLOCK, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() + 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() - 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() + 1)), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() - 1)), Material.GLOWSTONE, player));
					claimData.setState(State.CLAIMING);
				}
				break;
			case DISPLAYOWN:
				claimData.getTempBlocks().forEach(block -> {
					updateChangedBlock(block, player);
				});
				claimData.resetTempBlocks();
				claimID = getBlockOwner(event.getClickedBlock());
				if (claimID != null) {
					ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
					UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
					if (ownerUUID.equals(player.getUniqueId())) {
						
						int[][] corners = getCorners(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"));
						int cornerIndex = -1;
						for (int i = 0; i < corners.length; i++) {
							int[] corner = corners[i];
							if (corner[0] == event.getClickedBlock().getX() && corner[1] == event.getClickedBlock().getZ()) {
								cornerIndex = i;
								break;
							}
						}
						if (cornerIndex != -1) {
							player.sendMessage(ChatColor.GOLD + "Resizing Claim. Right click where you want to move this corner.");
							claimData.setResizingKey(claimID);
							claimData.setState(State.RESIZING);
							
							switch (cornerIndex) {
								case 0:
									cornerIndex = 2;
									break;
								case 1:
									cornerIndex = 3;
									break;
								case 2:
									cornerIndex = 0;
									break;
								case 3:
									cornerIndex = 1;
									break;
							}
							
							claimData.setLocation1(new Location(currentClaim.getLocation("location1").getWorld(), corners[cornerIndex][0], player.getLocation().getBlockY(), corners[cornerIndex][1]));
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.addTempBlock(changeBlock(event.getClickedBlock(), Material.DIAMOND_BLOCK, player));
							
						} else {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here. Resize this claim by selecting a corner.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.DISPLAYOWN);
						}
					} else {
						player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYELSE);
					}
				} else {
					player.sendMessage(ChatColor.GOLD + "First block selected, right click the block you want to be the opposite corner of your claim. Click the same block to cancel.");
					event.setCancelled(true);
					//Use protocolLib to make this block appear as a diamond block surrounded by glowstone.
					World world = player.getWorld();
					Block clickedBlock = event.getClickedBlock();
					
					claimData.setLocation1(event.getClickedBlock().getLocation());
					
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(clickedBlock), Material.DIAMOND_BLOCK, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() + 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() - 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() + 1)), Material.GLOWSTONE, player));
					claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() - 1)), Material.GLOWSTONE, player));
					claimData.setState(State.CLAIMING);
				}
				break;
			case CLAIMINGOVERLAPOWN:
				claimData.setLocation2(event.getClickedBlock().getLocation());
				if (claimData.getXDist() < 4 || claimData.getZDist() < 4) {
					if (claimData.getXDist() == 0 && claimData.getZDist() == 0) {
						claimData.setState(State.IDLE);
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						player.sendMessage(ChatColor.YELLOW + "Claiming cancelled.");
					} else {
						player.sendMessage(ChatColor.RED + "Claims must be at least 5 x 5 in size.");
					}
				} else {
					claimID = getClaimOverlap(claimData.getLocation1(), claimData.getLocation2());
					if (claimID != null) {
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						Block clickedBlock = claimData.getLocation1().getBlock();
						World world = clickedBlock.getWorld();
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(clickedBlock), Material.DIAMOND_BLOCK, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() + 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() - 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() + 1)), Material.GLOWSTONE, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() - 1)), Material.GLOWSTONE, player));
						
						ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
						UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
						if (ownerUUID.equals(player.getUniqueId())) {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.CLAIMINGOVERLAPOWN);
						} else {
							player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
							claimData.setState(State.CLAIMINGOVERLAPELSE);
						}
					} else {
						int area = claimData.getArea();
						if (playerClaimBlocks < area) {
							player.sendMessage(ChatColor.RED + "You do not have enough claim blocks to claim this area. Available claim blocks: " + playerClaimBlocks + ".");
							break;
						}
						player.sendMessage(ChatColor.GOLD + "You have successfully claimed this region. To resize it, select a corner with your shovel.");
						setPlayerClaimBlocks(player, playerClaimBlocks - area);
						player.sendMessage(ChatColor.GOLD + "Remaining claim blocks: " + (playerClaimBlocks - area));
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(claimData.getLocation1(), claimData.getLocation2(), player, mainMat1, secondaryMat1));
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ConfigurationSection newClaim = playerClaims.createSection(claimData.getLocation1().getBlockX() + "," + claimData.getLocation1().getBlockZ() + "," + claimData.getLocation2().getBlockX() + "," + claimData.getLocation2().getBlockZ());
						newClaim.set("owner", player.getUniqueId().toString());
						newClaim.set("location1", claimData.getLocation1());
						newClaim.set("location2", claimData.getLocation2());
						claimData.setState(State.DISPLAYOWN);
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case CLAIMINGOVERLAPELSE:
				claimData.setLocation2(event.getClickedBlock().getLocation());
				if (claimData.getXDist() < 4 || claimData.getZDist() < 4) {
					if (claimData.getXDist() == 0 && claimData.getZDist() == 0) {
						claimData.setState(State.IDLE);
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						player.sendMessage(ChatColor.YELLOW + "Claiming cancelled.");
					} else {
						player.sendMessage(ChatColor.RED + "Claims must be at least 5 x 5 in size.");
					}
				} else {
					claimID = getClaimOverlap(claimData.getLocation1(), claimData.getLocation2());
					if (claimID != null) {
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						Block clickedBlock = claimData.getLocation1().getBlock();
						World world = clickedBlock.getWorld();
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(clickedBlock), Material.DIAMOND_BLOCK, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() + 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX() - 1, clickedBlock.getY(), clickedBlock.getZ())), Material.GLOWSTONE, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() + 1)), Material.GLOWSTONE, player));
						claimData.addTempBlock(changeBlock(plugin.getTopBlock(world.getBlockAt(clickedBlock.getX(), clickedBlock.getY(), clickedBlock.getZ() - 1)), Material.GLOWSTONE, player));
						
						ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
						UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
						if (ownerUUID.equals(player.getUniqueId())) {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.CLAIMINGOVERLAPOWN);
						} else {
							player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
							claimData.setState(State.CLAIMINGOVERLAPELSE);
						}
					} else {
						int area = claimData.getArea();
						if (playerClaimBlocks < area) {
							player.sendMessage(ChatColor.RED + "You do not have enough claim blocks to claim this area. Available claim blocks: " + playerClaimBlocks + ".");
							break;
						}
						player.sendMessage(ChatColor.GOLD + "You have successfully claimed this region. To resize it, select a corner with your shovel.");
						setPlayerClaimBlocks(player, playerClaimBlocks - area);
						player.sendMessage(ChatColor.GOLD + "Remaining claim blocks: " + (playerClaimBlocks - area));
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(claimData.getLocation1(), claimData.getLocation2(), player, mainMat1, secondaryMat1));
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ConfigurationSection newClaim = playerClaims.createSection(claimData.getLocation1().getBlockX() + "," + claimData.getLocation1().getBlockZ() + "," + claimData.getLocation2().getBlockX() + "," + claimData.getLocation2().getBlockZ());
						newClaim.set("owner", player.getUniqueId().toString());
						newClaim.set("location1", claimData.getLocation1());
						newClaim.set("location2", claimData.getLocation2());
						claimData.setState(State.DISPLAYOWN);
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case RESIZINGOVERLAPOWN:
				claimData.setLocation2(event.getClickedBlock().getLocation());
				
				if (claimData.getXDist() < 4 || claimData.getZDist() < 4) {
					player.sendMessage(ChatColor.RED + "Claims must be at least 5 x 5 in size.");
				} else {
					claimID = getClaimOverlap(claimData.getLocation1(), claimData.getLocation2(), claimData.getResizingKey());
					if (claimID != null) {
						ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
						UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
						
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ClaimData tempData = new ClaimData(player.getUniqueId(), playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation2(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location2"));
						
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(tempData.getLocation1(), tempData.getLocation2(), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.addTempBlock(changeBlock(tempData.getLocation2().getBlock(), Material.DIAMOND_BLOCK, player));
						
						if (ownerUUID.equals(player.getUniqueId())) {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.RESIZINGOVERLAPOWN);
						} else {
							player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
							claimData.setState(State.RESIZINGOVERLAPELSE);
						}
					} else {
						int area = claimData.getArea();
						
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ClaimData tempData = new ClaimData(player.getUniqueId(), playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation1(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation2(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location2"));
						if (playerClaimBlocks + tempData.getArea() < area) {
							player.sendMessage(ChatColor.RED + "You do not have enough claim blocks to claim this area. Available claim blocks: " + playerClaimBlocks + ".");
							break;
						}
						
						player.sendMessage(ChatColor.GOLD + "You have successfully resized your claim.");
						setPlayerClaimBlocks(player, playerClaimBlocks + tempData.getArea() - area);
						player.sendMessage(ChatColor.GOLD + "Remaining claim blocks: " + (playerClaimBlocks + tempData.getArea() - area));
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(claimData.getLocation1(), claimData.getLocation2(), player, mainMat1, secondaryMat1));
						
						
						playerClaims.set(claimData.getResizingKey(), null);
						claimData.setResizingKey(null);
						
						
						ConfigurationSection newClaim = playerClaims.createSection(claimData.getLocation1().getBlockX() + "," + claimData.getLocation1().getBlockZ() + "," + claimData.getLocation2().getBlockX() + "," + claimData.getLocation2().getBlockZ());
						newClaim.set("owner", player.getUniqueId().toString());
						newClaim.set("location1", claimData.getLocation1());
						newClaim.set("location2", claimData.getLocation2());
						claimData.setState(State.DISPLAYOWN);
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
			case RESIZINGOVERLAPELSE:
				claimData.setLocation2(event.getClickedBlock().getLocation());
				
				if (claimData.getXDist() < 4 || claimData.getZDist() < 4) {
					player.sendMessage(ChatColor.RED + "Claims must be at least 5 x 5 in size.");
				} else {
					claimID = getClaimOverlap(claimData.getLocation1(), claimData.getLocation2(), claimData.getResizingKey());
					if (claimID != null) {
						ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
						UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
						
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ClaimData tempData = new ClaimData(player.getUniqueId(), playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation1(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation2(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location2"));
						
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(tempData.getLocation1(), tempData.getLocation2(), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.addTempBlock(changeBlock(tempData.getLocation2().getBlock(), Material.DIAMOND_BLOCK, player));
						
						if (ownerUUID.equals(player.getUniqueId())) {
							player.sendMessage(ChatColor.GOLD + "You already have a claim here.");
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
							claimData.setState(State.RESIZINGOVERLAPOWN);
						} else {
							player.sendMessage(ChatColor.RED + "You cannot claim here because it is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
							claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
							claimData.setState(State.RESIZINGOVERLAPELSE);
						}
					} else {
						int area = claimData.getArea();
						
						ConfigurationSection playerClaims;
						if (claimsConfig.contains("personalClaims")) {
							playerClaims = claimsConfig.getConfigurationSection("personalClaims");
						} else {
							playerClaims = claimsConfig.createSection("personalClaims");
						}
						
						ClaimData tempData = new ClaimData(player.getUniqueId(), playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation1(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location1"));
						tempData.setLocation2(playerClaims.getConfigurationSection(claimData.getResizingKey()).getLocation("location2"));
						if (playerClaimBlocks + tempData.getArea() < area) {
							player.sendMessage(ChatColor.RED + "You do not have enough claim blocks to claim this area. Available claim blocks: " + playerClaimBlocks + ".");
							break;
						}
						
						player.sendMessage(ChatColor.GOLD + "You have successfully resized your claim.");
						setPlayerClaimBlocks(player, playerClaimBlocks + tempData.getArea() - area);
						player.sendMessage(ChatColor.GOLD + "Remaining claim blocks: " + (playerClaimBlocks + tempData.getArea() - area));
						claimData.getTempBlocks().forEach(block -> {
							updateChangedBlock(block, player);
						});
						claimData.resetTempBlocks();
						claimData.addTempBlocks(showClaimBounds(claimData.getLocation1(), claimData.getLocation2(), player, mainMat1, secondaryMat1));
						
						
						playerClaims.set(claimData.getResizingKey(), null);
						claimData.setResizingKey(null);
						
						
						ConfigurationSection newClaim = playerClaims.createSection(claimData.getLocation1().getBlockX() + "," + claimData.getLocation1().getBlockZ() + "," + claimData.getLocation2().getBlockX() + "," + claimData.getLocation2().getBlockZ());
						newClaim.set("owner", player.getUniqueId().toString());
						newClaim.set("location1", claimData.getLocation1());
						newClaim.set("location2", claimData.getLocation2());
						claimData.setState(State.DISPLAYOWN);
						try {
							claimsConfig.save(claimsFile);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				break;
		}
		
		final int tempDataIndex;
		if (dataIndex == -1) {
			tempDataIndex = claimingData.size();
		} else {
			tempDataIndex = dataIndex;
		}
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				ClaimData currentData = claimingData.get(tempDataIndex);
				if (currentData.getLastClick() + sustainTime - 1 <= player.getWorld().getGameTime() && (currentData.getState().equals(State.DISPLAYOWN) || currentData.getState().equals(State.DISPLAYELSE))) {
					currentData.setState(State.IDLE);
					currentData.getTempBlocks().forEach(block -> {
						updateChangedBlock(block, player);
					});
					currentData.resetTempBlocks();
					claimingData.set(tempDataIndex, currentData);
				}
			}
		}, sustainTime);

		if (dataIndex == -1) {
			claimingData.add(claimData);
		} else {
			claimingData.set(dataIndex, claimData);
		}
		
		
	}
	
	
	@EventHandler
	public void RightClickStick(PlayerInteractEvent event) {
		if (!(event.hasItem() && event.getItem().getType().equals(Material.STICK))) return;
		if (event.getClickedBlock() == null) return;
		if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		Player player = event.getPlayer();
		int race = plugin.getRace(player);
		if (!(race == 1 || race == 3)) return;
		if (!isBlockInChunkClaim(event.getClickedBlock())) return;
		
		event.setCancelled(true);

		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		if (!claimsConfig.contains("personalClaims")) {
			claimsConfig.createSection("personalClaims");
		}
		
		
		int dataIndex = -1;
		for (int i = 0; i < claimingData.size(); i++) {
			if (claimingData.get(i).getUUID().equals(player.getUniqueId())) {
				dataIndex = i;
				break;
			}
		}
		
		ClaimData claimData;
		
		if (dataIndex == -1) {
			claimData = new ClaimData(player.getUniqueId(), event.getClickedBlock().getLocation());
		} else {
			claimData = claimingData.get(dataIndex);
		}
		
		claimData.setLastClick(player.getWorld().getGameTime());
		
		switch (claimData.getState()) {
			case IDLE:
				if (getBlockOwner(event.getClickedBlock()) != null) {
					String claimID = getBlockOwner(event.getClickedBlock());
					ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
					UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
					if (ownerUUID.equals(player.getUniqueId())) {
						player.sendMessage(ChatColor.GOLD + "You own this block. Resize this claim by selecting a corner with a golden shovel.");
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYOWN);
					} else {
						player.sendMessage(ChatColor.RED + "This block is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYELSE);
					}
				} else {
					player.sendMessage(ChatColor.YELLOW + "This block is not owned by anyone. Run /claim for more information. \nAvailable claim blocks: " + getPlayerClaimBlocks(player));
				}
				break;
			case CLAIMING:
				break;
			case RESIZING:
				break;
			case DISPLAYELSE:
				if (getBlockOwner(event.getClickedBlock()) != null) {
					claimData.getTempBlocks().forEach(block -> {
						updateChangedBlock(block, player);
					});
					claimData.resetTempBlocks();
					String claimID = getBlockOwner(event.getClickedBlock());
					ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
					UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
					if (ownerUUID.equals(player.getUniqueId())) {
						player.sendMessage(ChatColor.GOLD + "You own this block. Resize this claim by selecting a corner with a golden shovel.");
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYOWN);
					} else {
						player.sendMessage(ChatColor.RED + "This block is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYELSE);
					}
				} else {
					player.sendMessage(ChatColor.YELLOW + "This block is not owned by anyone. Run /claim for more information. \nAvailable claim blocks: " + getPlayerClaimBlocks(player));
				}
				break;
			case DISPLAYOWN:
				if (getBlockOwner(event.getClickedBlock()) != null) {
					claimData.getTempBlocks().forEach(block -> {
						updateChangedBlock(block, player);
					});
					claimData.resetTempBlocks();
					String claimID = getBlockOwner(event.getClickedBlock());
					ConfigurationSection currentClaim = claimsConfig.getConfigurationSection("personalClaims").getConfigurationSection(claimID);
					UUID ownerUUID = UUID.fromString(currentClaim.getString("owner"));
					if (ownerUUID.equals(player.getUniqueId())) {
						player.sendMessage(ChatColor.GOLD + "You own this block. Resize this claim by selecting a corner with a golden shovel.");
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat1, secondaryMat1, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYOWN);
					} else {
						player.sendMessage(ChatColor.RED + "This block is owned by: " + Bukkit.getOfflinePlayer(ownerUUID).getName());
						claimData.addTempBlocks(showClaimBounds(currentClaim.getLocation("location1"), currentClaim.getLocation("location2"), player, mainMat2, secondaryMat2, event.getClickedBlock().getY()));
						claimData.setState(State.DISPLAYELSE);
					}
				} else {
					player.sendMessage(ChatColor.YELLOW + "This block is not owned by anyone. Run /claim for more information. \nAvailable claim blocks: " + getPlayerClaimBlocks(player));
				}
				break;
			case CLAIMINGOVERLAPOWN:
				break;
			case CLAIMINGOVERLAPELSE:
				break;
			case RESIZINGOVERLAPOWN:
				break;
			case RESIZINGOVERLAPELSE:
				break;
		}
		
		final int tempDataIndex;
		if (dataIndex == -1) {
			tempDataIndex = claimingData.size();
		} else {
			tempDataIndex = dataIndex;
		}
		
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			public void run() {
				ClaimData currentData = claimingData.get(tempDataIndex);
				if (currentData.getLastClick() + sustainTime - 1 <= player.getWorld().getGameTime() && (currentData.getState().equals(State.DISPLAYOWN) || currentData.getState().equals(State.DISPLAYELSE))) {
					currentData.setState(State.IDLE);
					currentData.getTempBlocks().forEach(block -> {
						updateChangedBlock(block, player);
					});
					currentData.resetTempBlocks();
					claimingData.set(tempDataIndex, currentData);
				}
			}
		}, sustainTime);

		if (dataIndex == -1) {
			claimingData.add(claimData);
		} else {
			claimingData.set(dataIndex, claimData);
		}
		
	}
	
	
	private boolean isBlockInChunkClaim(Block block) {
		String[] races = {"men", "dwarf"};
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		Chunk currentChunk = block.getChunk();
		
		for (String race : races) {
			List<String> chunks = claimsConfig.getStringList(race + "ChunkClaims");
			if (chunks.contains(getChunkID(currentChunk))) {
				return true;
			}
		}
		return false;
	}
	
	private String getChunkID(Chunk chunk) {
		return chunk.getX() + "," + chunk.getZ() + ":" + chunk.getWorld().getUID().toString();
	}
	
	private int getPlayerClaimBlocks(Player player) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		ConfigurationSection claimBlocks;
		if (leaderConfig.contains("claimBlocks")) {
			claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
		} else {
			claimBlocks = leaderConfig.createSection("claimBlocks");
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		String uuid = player.getUniqueId().toString();
		
		if (claimBlocks.contains(uuid)) {
			return claimBlocks.getInt(uuid);
		} else {
			claimBlocks.set(uuid, plugin.startingClaimBlocks);
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return plugin.startingClaimBlocks;
		}
	}
	
	private void setPlayerClaimBlocks(Player player, int blocks) {
		File leaderDataFile = new File(plugin.getDataFolder() + File.separator + "leaderData.yml");
		FileConfiguration leaderConfig = YamlConfiguration.loadConfiguration(leaderDataFile);
		
		ConfigurationSection claimBlocks;
		if (leaderConfig.contains("claimBlocks")) {
			claimBlocks = leaderConfig.getConfigurationSection("claimBlocks");
		} else {
			claimBlocks = leaderConfig.createSection("claimBlocks");
			try {
				leaderConfig.save(leaderDataFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		claimBlocks.set(player.getUniqueId().toString(), blocks);
		try {
			leaderConfig.save(leaderDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getBlockOwner(Block block) {
		//returns the claimID that the current block belongs to. Not the owner UUID, the claimID.
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		ConfigurationSection playerClaims;
		if (claimsConfig.contains("personalClaims")) {
			playerClaims = claimsConfig.getConfigurationSection("personalClaims");
		} else {
			playerClaims = claimsConfig.createSection("personalClaims");
		}
		
		String[] keys = playerClaims.getKeys(false).toArray(new String[playerClaims.getKeys(false).size()]);
		
		for (String key : keys) {
			ConfigurationSection claim = playerClaims.getConfigurationSection(key);
			Location loc1 = claim.getLocation("location1");
			Location loc2 = claim.getLocation("location2");
			int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
			int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
			int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
			int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
			if (block.getWorld().equals(loc1.getWorld())) {
				if (block.getX() >= minX && block.getX() <= maxX && block.getZ() >= minZ && block.getZ() <= maxZ) {
					return key;
				}
			}
		}
		
		return null;
	}
	
	private String getClaimOverlap(Location location1, Location location2) {
		//returns the claimID that the current block belongs to. Not the owner UUID, the claimID.
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		ConfigurationSection playerClaims;
		if (claimsConfig.contains("personalClaims")) {
			playerClaims = claimsConfig.getConfigurationSection("personalClaims");
		} else {
			playerClaims = claimsConfig.createSection("personalClaims");
		}
		
		String[] keys = playerClaims.getKeys(false).toArray(new String[playerClaims.getKeys(false).size()]);
		
		for (String key : keys) {
			ConfigurationSection claim = playerClaims.getConfigurationSection(key);
			Location loc1 = claim.getLocation("location1");
			Location loc2 = claim.getLocation("location2");
			int[][] locationCorners = getCorners(location1, location2);
			int[][] locCorners = getCorners(loc1, loc2);
			if (location1.getWorld().equals(loc1.getWorld())) {
				if (doOverlap(locationCorners[0], locationCorners[2], locCorners[0], locCorners[2])) {
					return key;
				}
			}
		}
		return null;
	}
	
	private String getClaimOverlap(Location location1, Location location2, String ignoreKey) {
		//returns the claimID that the current block belongs to. Not the owner UUID, the claimID.
		
		File claimsFile = new File(plugin.getDataFolder() + File.separator + "claims.yml");
		FileConfiguration claimsConfig = YamlConfiguration.loadConfiguration(claimsFile);
		
		ConfigurationSection playerClaims;
		if (claimsConfig.contains("personalClaims")) {
			playerClaims = claimsConfig.getConfigurationSection("personalClaims");
		} else {
			playerClaims = claimsConfig.createSection("personalClaims");
		}
		
		String[] keys = playerClaims.getKeys(false).toArray(new String[playerClaims.getKeys(false).size()]);
		
		for (String key : keys) {
			if (key.equals(ignoreKey)) {
				continue;
			}
			ConfigurationSection claim = playerClaims.getConfigurationSection(key);
			Location loc1 = claim.getLocation("location1");
			Location loc2 = claim.getLocation("location2");
			int[][] locationCorners = getCorners(location1, location2);
			int[][] locCorners = getCorners(loc1, loc2);
			if (location1.getWorld().equals(loc1.getWorld())) {
				if (doOverlap(locationCorners[0], locationCorners[2], locCorners[0], locCorners[2])) {
					return key;
				}
			}
		}
		return null;
	}
	
	private Block changeBlock(Block block, Material mat, Player player) {
		//Uses protocolLib to temporarily change the block at this location to the given material
		PacketContainer changeBlock = plugin.protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
		changeBlock.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
		changeBlock.getBlockData().write(0, WrappedBlockData.createData(mat));
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			 public void run() {
				 try {
						plugin.protocolManager.sendServerPacket(player, changeBlock);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
				}
			 }
		}, 1L);
		return block;
	}
	
	private void updateChangedBlock(Block block, Player player) {
		//Uses protocolLib to temporarily change the block at this location to the given material
		PacketContainer changeBlock = plugin.protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
		changeBlock.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
		changeBlock.getBlockData().write(0, WrappedBlockData.createData(block.getType()));
		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			 public void run() {
				 try {
						plugin.protocolManager.sendServerPacket(player, changeBlock);
					} catch (InvocationTargetException e) {
						e.printStackTrace();
				}
			 }
		}, 1L);
	}
	
//	private Block tempChangeBlock(Block block, Material mat, Player player, long time) {
//		//Uses protocolLib to temporarily change the block at this location to the given material
//		PacketContainer changeBlock = plugin.protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGE);
//		changeBlock.getBlockPositionModifier().write(0, new BlockPosition(block.getX(), block.getY(), block.getZ()));
//		changeBlock.getBlockData().write(0, WrappedBlockData.createData(mat));
//		plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//			 public void run() {
//				 try {
//						plugin.protocolManager.sendServerPacket(player, changeBlock);
//						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//							 public void run() {
//								updateChangedBlock(block, player);
//							 }
//						}, time);
//					} catch (InvocationTargetException e) {
//						e.printStackTrace();
//				}
//			 }
//		}, 1L);
//		return block;
//	}
//	
//	private Block tempChangeBlock(Block block, Material mat, Player player) {
//		return tempChangeBlock(block, mat, player, sustainTime);
//	}
	
	private List<Block> showClaimBounds(Location loc1, Location loc2, Player player, Material mainMat, Material secondaryMat, int yPos) {
		loc1.setY(yPos);
		loc2.setY(yPos);
		return showClaimBounds(loc1, loc2, player, mainMat, secondaryMat);
	}
	
	private List<Block> showClaimBounds(Location loc1, Location loc2, Player player, Material mainMat, Material secondaryMat) {
		List<Block> locations = new ArrayList<Block>();
		int[][] corners = getCorners(loc1, loc2);
		int yPos = (loc1.getBlockY() + loc2.getBlockY()) / 2;
		World world = loc1.getWorld();
		for (int i = 0; i < corners.length; i++) {
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[i][0], yPos, corners[i][1])), mainMat, player));
		}
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[0][0], yPos, corners[0][1] + 1)), secondaryMat, player));
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[0][0] - 1, yPos, corners[0][1])), secondaryMat, player));
		
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[1][0], yPos, corners[1][1] - 1)), secondaryMat, player));
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[1][0] - 1, yPos, corners[1][1])), secondaryMat, player));
		
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[2][0], yPos, corners[2][1] - 1)), secondaryMat, player));
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[2][0] + 1, yPos, corners[2][1])), secondaryMat, player));
		
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[3][0], yPos, corners[3][1] + 1)), secondaryMat, player));
		locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[3][0] + 1, yPos, corners[3][1])), secondaryMat, player));
		
		int intervalDist = 8;
		
		for (int i = corners[0][1] + intervalDist; i < corners[1][1] - (intervalDist * 1.5) ; i += intervalDist) {
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[0][0], yPos, i)), mainMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[0][0], yPos, i + 1)), secondaryMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[0][0], yPos, i - 1)), secondaryMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[2][0], yPos, i)), mainMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[2][0], yPos, i + 1)), secondaryMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(corners[2][0], yPos, i - 1)), secondaryMat, player));
		}
		
		for (int i = corners[2][0] + intervalDist; i < corners[1][0] - (intervalDist * 1.5); i += intervalDist) {
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(i, yPos, corners[0][1])), mainMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(i - 1, yPos, corners[0][1])), secondaryMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(i + 1, yPos, corners[0][1])), secondaryMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(i, yPos, corners[1][1])), mainMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(i - 1, yPos, corners[1][1])), secondaryMat, player));
			locations.add(changeBlock(plugin.getTopBlock(world.getBlockAt(i + 1, yPos, corners[1][1])), secondaryMat, player));
		}
		return locations;
	}
	
	private int[] getNorthEastCorner(Location loc1, Location loc2) {//X+, Z-
		int[] coords = {Math.max(loc1.getBlockX(), loc2.getBlockX()), Math.min(loc1.getBlockZ(), loc2.getBlockZ())};
		return coords;
	}
	
	private int[] getSouthEastCorner(Location loc1, Location loc2) {//X+, Z+
		int[] coords = {Math.max(loc1.getBlockX(), loc2.getBlockX()), Math.max(loc1.getBlockZ(), loc2.getBlockZ())};
		return coords;
	}
	
	private int[] getSouthWestCorner(Location loc1, Location loc2) {//X-, Z+
		int[] coords = {Math.min(loc1.getBlockX(), loc2.getBlockX()), Math.max(loc1.getBlockZ(), loc2.getBlockZ())};
		return coords;
	}
	
	private int[] getNorthWestCorner(Location loc1, Location loc2) {//X-, Z-
		int[] coords = {Math.min(loc1.getBlockX(), loc2.getBlockX()), Math.min(loc1.getBlockZ(), loc2.getBlockZ())};
		return coords;
	}
	
	private int[][] getCorners(Location loc1, Location loc2) {
		int[][] corners = {getNorthEastCorner(loc1, loc2), getSouthEastCorner(loc1, loc2), getSouthWestCorner(loc1, loc2), getNorthWestCorner(loc1, loc2)};
		return corners;
	}
	
	private boolean doOverlap(int[] rect1corner1, int[] rect1corner2, int[] rect2corner1, int[] rect2corner2) {
		
		
		if (rect1corner1[0] == rect1corner2[0] || rect1corner1[1] == rect1corner2[1] || rect2corner1[0] == rect2corner2[0] || rect2corner1[1] == rect2corner2[1]) return false;
		
		if (rect1corner1[0] < rect2corner2[0] || rect2corner1[0] < rect1corner2[0]) return false;
		
		if (rect1corner2[1] < rect2corner1[1] || rect2corner2[1] < rect1corner1[1]) return false;
		
		return true;
	}
}
