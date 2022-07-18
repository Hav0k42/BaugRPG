package org.baugindustries.baugrpg.listeners;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.block.data.Directional;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

public class SignShopListener implements Listener{
	private Main plugin;
	public SignShopListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onClickSign(PlayerInteractEvent event) {
		
		if (!(event.getAction().equals(Action.LEFT_CLICK_BLOCK) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK))) return;
		
		Player player = (Player) event.getPlayer();
		if (event.getClickedBlock().getState() instanceof Sign) {
			Sign sign = (Sign)event.getClickedBlock().getState();
			String title = sign.getLocation().getBlockX() + "" + sign.getLocation().getBlockY() + "" + sign.getLocation().getBlockZ() + "" + sign.getLocation().getWorld().getUID();
			
			if (!(sign.getBlockData() instanceof Directional)) return;
			
			BlockFace signFace = ((Directional)sign.getBlockData()).getFacing();
			Block blockBehindSign = null;
			switch (signFace) {
	           case WEST:
	                blockBehindSign = event.getClickedBlock().getRelative(BlockFace.EAST);
	                break;
	           case EAST:
	               blockBehindSign = event.getClickedBlock().getRelative(BlockFace.WEST);
	               break;
	           case NORTH:
	               blockBehindSign = event.getClickedBlock().getRelative(BlockFace.SOUTH);
	               break;
	           case SOUTH:
	               blockBehindSign = event.getClickedBlock().getRelative(BlockFace.NORTH);
	               break;
	           default:
	               break;
			}
			
			File signfile = new File(plugin.getDataFolder() + File.separator + "shops.yml");
		 	FileConfiguration signconfig = YamlConfiguration.loadConfiguration(signfile);
		 	
		 	File econfile = new File(plugin.getDataFolder() + File.separator + "econ.yml");
		 	FileConfiguration econconfig = YamlConfiguration.loadConfiguration(econfile);
		 	
			if (plugin.signData.containsKey(player) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {

				ItemStack item = null;
				if (event.getHand().equals(EquipmentSlot.HAND)) {
					item = player.getInventory().getItemInMainHand();
				} else {
					item = player.getInventory().getItemInOffHand();
				}
				
				if (item != null) {
					
					sign.setLine(0, ChatColor.DARK_PURPLE + "[Dwarven Shop]");
					sign.setLine(1, plugin.signData.get(player).get(0));
					sign.setLine(2, plugin.signData.get(player).get(1));
					sign.setLine(3, plugin.signData.get(player).get(2));
					sign.update();
					
					ConfigurationSection signSection = signconfig.createSection(title);
				 	
					
					
				 	signSection.set("item", item);
				 	signSection.set("owner", player.getUniqueId().toString());
				 	signSection.set("chestLoc", blockBehindSign.getLocation());
					
				 	try {
						signconfig.save(signfile);
					} catch (IOException e) {
						e.printStackTrace();
					}
					plugin.signData.remove(player);
				} else {
					player.sendMessage(ChatColor.GREEN + "Please click the sign with an item");
				}
			} else if (!signconfig.contains(title) && player.isSneaking() && !plugin.signChatEscape.containsKey(player) && event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				if (plugin.getRace(player) == 3) {
					player.sendMessage(ChatColor.GREEN + "What do you want to title the sign shop? >");
					plugin.signChatEscape.put(player, 1);
				}
			} else {
				if (signconfig.contains(title)) {//sign is a registered sign shop
					
					int buyPrice = Integer.parseInt(sign.getLine(2).substring(5));
					int sellPrice = Integer.parseInt(sign.getLine(3).substring(6));
					int customerBal = (int)econconfig.get(player.getUniqueId().toString());
					
					ConfigurationSection signSection = signconfig.getConfigurationSection(title);
					
					OfflinePlayer player2 = plugin.getServer().getOfflinePlayer(UUID.fromString((String) signSection.get("owner")));
					ItemStack goodsType = (ItemStack)signSection.getItemStack("item");
					boolean match = false;
					for (Recipes recipe : Recipes.values()) {
						if (recipe.matches(plugin, goodsType)) {
							match = true;
							break;
						}
					}
					if (!match) {
						goodsType = new ItemStack(goodsType.getType());
					}
					int vendorBal = (int)econconfig.get(player2.getUniqueId().toString());
					
					
					
					if (blockBehindSign.getState() instanceof Chest) {
						Chest chestShop = (Chest)blockBehindSign.getState();
					
						
						
						if (player.getUniqueId().equals(player2.getUniqueId())) {
							player.sendMessage(ChatColor.RED + "You can't buy or sell items from your own shop.");
						} else {
							if (player.isSneaking()) {//shift to buy/sell 10 instead of 1
								goodsType.setAmount(10);
								if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {//Buy 10
									if (customerBal > buyPrice * 10) {
										if (chestShop.getInventory().containsAtLeast(goodsType, 10)) {
											int goodsCount = 0;
											while (goodsCount < 10) {
												for (int i = 0; i < chestShop.getInventory().getSize(); i++) {
													if (chestShop.getInventory().getItem(i) != null && chestShop.getInventory().getItem(i).isSimilar(goodsType)) {
														if (chestShop.getInventory().getItem(i).getAmount() + goodsCount < 10) {
															goodsCount += chestShop.getInventory().getItem(i).getAmount();
															chestShop.getInventory().setItem(i, null);
															break;
														} else {
															ItemStack newItem = chestShop.getInventory().getItem(i);
															newItem.setAmount(newItem.getAmount() - (10 - goodsCount));
															goodsCount = 10;
															chestShop.getInventory().setItem(i, newItem);
															break;
														}
													}
												}
											}
											Inventory contents = player.getInventory();
											
											int nullCount = 0;
											for (int i = 0; i < contents.getContents().length; i++) {
												if (contents.getContents()[i] == null) {
													nullCount++;
												}
											}
											if (nullCount < 1) {
												player.sendMessage(ChatColor.RED + "Your inventory is full");
												chestShop.getInventory().addItem(goodsType);
												return;
											} else {
												player.getInventory().addItem(goodsType);
											}
											econconfig.set(player.getUniqueId().toString(), customerBal - (buyPrice * 10));
											econconfig.set(player2.getUniqueId().toString(), vendorBal + (buyPrice * 10));
											player.sendMessage(ChatColor.YELLOW + "Purchased 10 items. Your new balance is: " + (customerBal - (buyPrice * 10)) + ".");
											
											
											try {
												econconfig.save(econfile);
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											player.sendMessage(ChatColor.YELLOW + "The shop is out of stock.");
										}
									} else {
										player.sendMessage(ChatColor.YELLOW + "You don't have enough money to pay for this.");
									}
								} else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {//Sell 10
									if (vendorBal > sellPrice * 10) {
										if (player.getInventory().containsAtLeast(goodsType, 10)) {
											int goodsCount = 0;
											while (goodsCount < 10) {
												for (int i = 0; i < player.getInventory().getSize(); i++) {
													if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).isSimilar(goodsType)) {
														if (player.getInventory().getItem(i).getAmount() + goodsCount < 10) {
															goodsCount += player.getInventory().getItem(i).getAmount();
															player.getInventory().setItem(i, null);
															break;
														} else {
															ItemStack newItem = player.getInventory().getItem(i);
															newItem.setAmount(newItem.getAmount() - (10 - goodsCount));
															goodsCount = 10;
															player.getInventory().setItem(i, newItem);
															break;
														}
													}
												}
											}
											Inventory contents = chestShop.getInventory();
											
											int nullCount = 0;
											for (int i = 0; i < contents.getContents().length; i++) {
												if (contents.getContents()[i] == null) {
													nullCount++;
												}
											}
											if (nullCount < 1) {
												player.sendMessage(ChatColor.RED + "This chestshop is full");
												player.getInventory().addItem(goodsType);
												return;
											} else {
												chestShop.getInventory().addItem(goodsType);
											}
											econconfig.set(player.getUniqueId().toString(), customerBal + (sellPrice * 10));
											econconfig.set(player2.getUniqueId().toString(), vendorBal - (sellPrice * 10));
											player.sendMessage(ChatColor.YELLOW + "Sold 10 items. Your new balance is: " + (customerBal + (sellPrice * 10)) + ".");
											try {
												econconfig.save(econfile);
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											player.sendMessage(ChatColor.YELLOW + "You don't have enough of this item to sell.");
										}
									} else {
										player.sendMessage(ChatColor.YELLOW + "The vendor doesn't have enough money to pay for this.");
									}
								}
							} else {
								goodsType.setAmount(1);
								if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {//Buy 1
									if (customerBal > buyPrice) {
										if (chestShop.getInventory().containsAtLeast(goodsType, 1)) {
											for (int i = 0; i < chestShop.getInventory().getSize(); i++) {
												if (chestShop.getInventory().getItem(i) != null && chestShop.getInventory().getItem(i).isSimilar(goodsType)) {
													ItemStack newItem = chestShop.getInventory().getItem(i);
													newItem.setAmount(newItem.getAmount() - 1);
													chestShop.getInventory().setItem(i, newItem);
													break;
												}
											}
											
											Inventory contents = player.getInventory();
											
											int nullCount = 0;
											for (int i = 0; i < contents.getContents().length; i++) {
												if (contents.getContents()[i] == null) {
													nullCount++;
												}
											}
											if (nullCount < 1) {
												player.sendMessage(ChatColor.RED + "Your inventory is full");
												chestShop.getInventory().addItem(goodsType);
												return;
											} else {
												player.getInventory().addItem(goodsType);
											}
											
											
											econconfig.set(player.getUniqueId().toString(), customerBal - (buyPrice));
											econconfig.set(player2.getUniqueId().toString(), vendorBal + (buyPrice));
											player.sendMessage(ChatColor.YELLOW + "Purchased item. Your new balance is: " + (customerBal - buyPrice) + ".");
											try {
												econconfig.save(econfile);
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											player.sendMessage(ChatColor.YELLOW + "The shop is out of stock.");
										}
									} else {
										player.sendMessage(ChatColor.YELLOW + "You don't have enough money to pay for this.");
									}
								} else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {//Sell 1
									if (vendorBal > sellPrice) {
										if (player.getInventory().containsAtLeast(goodsType, 1)) {
												for (int i = 0; i < player.getInventory().getSize(); i++) {
													if (player.getInventory().getItem(i) != null && player.getInventory().getItem(i).isSimilar(goodsType)) {
														ItemStack newItem = player.getInventory().getItem(i);
														newItem.setAmount(newItem.getAmount() - 1);
														player.getInventory().setItem(i, newItem);
														break;
													}
												}
											Inventory contents = chestShop.getInventory();
											
											int nullCount = 0;
											for (int i = 0; i < contents.getContents().length; i++) {
												if (contents.getContents()[i] == null) {
													nullCount++;
												}
											}
											if (nullCount < 1) {
												player.sendMessage(ChatColor.RED + "This chestshop is full");
												player.getInventory().addItem(goodsType);
												return;
											} else {
												chestShop.getInventory().addItem(goodsType);
											}
											
											econconfig.set(player.getUniqueId().toString(), customerBal + sellPrice);
											econconfig.set(player2.getUniqueId().toString(), vendorBal - sellPrice);
											player.sendMessage(ChatColor.YELLOW + "Sold item. Your new balance is: " + (customerBal + sellPrice) + ".");
											try {
												econconfig.save(econfile);
											} catch (IOException e) {
												e.printStackTrace();
											}
										} else {
											player.sendMessage(ChatColor.YELLOW + "You don't have enough of this item to sell.");
										}
									} else {
										player.sendMessage(ChatColor.YELLOW + "The vendor doesn't have enough money to pay for this.");
									}
								}
							}
						}
						
						
						
						
						
					
					} else {
						player.sendMessage(ChatColor.YELLOW + "This signshop doesn't have a chest.");
					}
					
				}
				
			}
		}
	}
}