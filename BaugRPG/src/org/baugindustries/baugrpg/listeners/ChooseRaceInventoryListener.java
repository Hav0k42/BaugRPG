package org.baugindustries.baugrpg.listeners;


import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class ChooseRaceInventoryListener implements Listener{
	
	
	private Main plugin;
	public ChooseRaceInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Choose Your Race")) {
					
						
						String chosenRace = "";
						if (event.getSlot() == 28) {//Men
							chosenRace = "Men";
						} else if (event.getSlot() == 30) {//Elves
							chosenRace = "Elves";
						} else if (event.getSlot() == 32) {//Dwarves
							chosenRace = "Dwarves";
						} else if (event.getSlot() == 34) {//Orcs
							chosenRace = "Orcs";
						}
						
						if (!chosenRace.equals("")) {
							int inventorySize = 27;
							String inventoryName = "Confirm Selection";
							Inventory inventory = Bukkit.createInventory(null, inventorySize, inventoryName);
							for (int i = 0; i < inventorySize; i++) {
								ItemStack backgroundItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
								ItemMeta backgroundItemMeta = backgroundItem.getItemMeta();
								backgroundItemMeta.setDisplayName(" ");
								backgroundItem.setItemMeta(backgroundItemMeta);
								inventory.setItem(i, backgroundItem);
							}
							
							ItemStack infoItem = new ItemStack(Material.NETHER_STAR);
							ItemMeta infoItemMeta = infoItem.getItemMeta();
							infoItemMeta.setDisplayName(ChatColor.YELLOW + "Confirm Selection");
							List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Are you sure you want to choose " + chosenRace + "?");
							infoItemMeta.setLore(infoItemLore);
							infoItem.setItemMeta(infoItemMeta);
							inventory.setItem(4, infoItem);
							
							ItemStack denySelectionItem = new ItemStack(Material.RED_STAINED_GLASS_PANE);
							ItemMeta denySelecitonItemMeta = denySelectionItem.getItemMeta();
							denySelecitonItemMeta.setDisplayName(ChatColor.RED + "No");
							denySelectionItem.setItemMeta(denySelecitonItemMeta);
							inventory.setItem(15, denySelectionItem);
							
							ItemStack confirmSelectionItem = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
							ItemMeta confirmSelecitonItemMeta = confirmSelectionItem.getItemMeta();
							confirmSelecitonItemMeta.setDisplayName(ChatColor.GREEN + "Yes");
							confirmSelectionItem.setItemMeta(confirmSelecitonItemMeta);
							inventory.setItem(11, confirmSelectionItem);
							
							
							player.openInventory(inventory);
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new ConfirmRaceInventoryListener(plugin), plugin);
						}
						
						event.setCancelled(true);
					}
				}
				
			}
		}
	}

