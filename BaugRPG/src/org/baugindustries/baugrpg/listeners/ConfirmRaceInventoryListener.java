package org.baugindustries.baugrpg.listeners;

import java.util.Arrays;
import java.util.List;

import org.baugindustries.baugrpg.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.md_5.bungee.api.ChatColor;

public class ConfirmRaceInventoryListener implements Listener{

	
	private Main plugin;
	public ConfirmRaceInventoryListener(Main plugin) {
		this.plugin = plugin;
	}
	
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Player player = (Player)event.getWhoClicked();
			if (event.getClickedInventory() != null) {
				if (event.getView().getTitle().equals("Confirm Selection")) {
					
						if (event.getSlot() == 15) {//No

							int inventorySize = 54;
							String inventoryName = "Choose Your Race";
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
							infoItemMeta.setDisplayName(ChatColor.YELLOW + "Choose Your Race");
							List<String> infoItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "This race will determine the way you build", "the way you play, and the way you", "interact with players on this server.", "", "It cannot be changed later.");
							infoItemMeta.setLore(infoItemLore);
							infoItem.setItemMeta(infoItemMeta);
							inventory.setItem(13, infoItem);

							ItemStack chooseMenItem = new ItemStack(Material.NETHERITE_SWORD);
							ItemMeta chooseMenItemMeta = chooseMenItem.getItemMeta();
							chooseMenItemMeta.setDisplayName(ChatColor.DARK_AQUA + "Men");
							List<String> chooseMenItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the sword and horse combat,", "Men prefer to live in flat open areas", "with lots of room to roam about.");
							chooseMenItemMeta.setLore(chooseMenItemLore);
							chooseMenItem.setItemMeta(chooseMenItemMeta);
							inventory.setItem(28, chooseMenItem);
							
							ItemStack chooseElvesItem = new ItemStack(Material.BOW);
							ItemMeta chooseElvesItemMeta = chooseElvesItem.getItemMeta();
							chooseElvesItemMeta.setDisplayName(ChatColor.DARK_GREEN + "Elves");
							List<String> chooseElvesItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the bow and ranged combat", "Elves reside in wooded areas", "due to their love of nature.");
							chooseElvesItemMeta.setLore(chooseElvesItemLore);
							chooseElvesItem.setItemMeta(chooseElvesItemMeta);
							inventory.setItem(30, chooseElvesItem);
							
							ItemStack chooseDwarvesItem = new ItemStack(Material.NETHERITE_AXE);
							ItemMeta chooseDwarvesItemMeta = chooseDwarvesItem.getItemMeta();
							chooseDwarvesItemMeta.setDisplayName(ChatColor.DARK_PURPLE + "Dwarves");
							List<String> chooseDwarvesItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of the axe and melee combat", "Dwarves dwell within the earth", "constantly delving for the riches beneath the surface.");
							chooseDwarvesItemMeta.setLore(chooseDwarvesItemLore);
							chooseDwarvesItem.setItemMeta(chooseDwarvesItemMeta);
							inventory.setItem(32, chooseDwarvesItem);
							
							ItemStack chooseOrcsItem = new ItemStack(Material.NETHERITE_HELMET);
							ItemMeta chooseOrcsItemMeta = chooseOrcsItem.getItemMeta();
							chooseOrcsItemMeta.setDisplayName(ChatColor.DARK_RED + "Orcs");
							List<String> chooseOrcsItemLore = Arrays.asList(ChatColor.LIGHT_PURPLE + "Masters of iron forged weaponry and brutal combat", "Orcs smoulder in the fires of hell", "for they cannot go to the surface.");
							chooseOrcsItemMeta.setLore(chooseOrcsItemLore);
							chooseOrcsItem.setItemMeta(chooseOrcsItemMeta);
							inventory.setItem(34, chooseOrcsItem);

							
							
							
							event.getWhoClicked().openInventory(inventory);
							
							InventoryClickEvent.getHandlerList().unregister(this);
							plugin.getServer().getPluginManager().registerEvents(new ChooseRaceInventoryListener(plugin), plugin);
						} else if (event.getSlot() == 11) {//Yes
							
							PersistentDataContainer data = event.getWhoClicked().getPersistentDataContainer();
							ItemMeta raceInfoMeta = event.getInventory().getItem(4).getItemMeta();
							String selectedRace = raceInfoMeta.getLore().get(0);
							int selectedRaceInt = 0;
							if (selectedRace.contains("Men")) {
								selectedRaceInt = 1;
							} else if (selectedRace.contains("Elves")) {
								selectedRaceInt = 2;
							} else if (selectedRace.contains("Dwarves")) {
								selectedRaceInt = 3;
							} else if (selectedRace.contains("Orcs")) {
								selectedRaceInt = 4;
							}
							data.set(new NamespacedKey(plugin, "Race"), PersistentDataType.INTEGER, selectedRaceInt);//Men: 1, Elves: 2, Dwarves: 3, Orcs: 4
							
							InventoryClickEvent.getHandlerList().unregister(this);
							player.closeInventory();
						}
						event.setCancelled(true);
					}
				}
			}
		}
		
	}

