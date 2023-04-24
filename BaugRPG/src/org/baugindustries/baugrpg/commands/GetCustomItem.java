package org.baugindustries.baugrpg.commands;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.baugindustries.baugrpg.Main;
import org.baugindustries.baugrpg.Recipes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import org.bukkit.ChatColor;

public class GetCustomItem implements CommandExecutor {
	
	private Main plugin;
	
	public GetCustomItem(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("GetCustomItem").setExecutor(this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Only players may execute this command.");
			return true;
		}
		
		Player player = (Player)sender;
		
		if (!player.hasPermission("minecraft.command.op")) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}
		
		if (args.length == 0) {
			player.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage is: /GetCustomItem <ItemMethodName> <Amount>");
			return true;
		}
		
		Method getResultItem;
		ItemStack tempItem = null;
		
		
		try {
			try {
				getResultItem = plugin.itemManager.getClass().getDeclaredMethod(args[0], (Class<?>[])(null));
			} catch (NoSuchMethodException e) {
				try {
					getResultItem = plugin.itemManager.getClass().getDeclaredMethod(args[0], Player.class);
				} catch (NoSuchMethodException e1) {
					try {
						getResultItem = plugin.itemManager.getClass().getDeclaredMethod(args[0], int.class);
					} catch (NoSuchMethodException e2) {
						player.sendMessage(ChatColor.RED + "Error: this method name does not exist, or this item was not intended to be obtained through this command. If you have questions contact the dev.");
						return true;
					}
				}
			}
			Class<?>[] types = getResultItem.getParameterTypes();
			if (types.length > 1) {
				player.sendMessage(ChatColor.RED + "Error: this method name does not exist, or this item was not intended to be obtained through this command. If you have questions contact the dev.");
				return true;
			}
			if (types.length == 0) {
				tempItem = (ItemStack)getResultItem.invoke(plugin.itemManager, (Object[])null);
			} else if (types[0].equals(Player.class)) {
				tempItem = (ItemStack)getResultItem.invoke(plugin.itemManager, player);
			} else if (types[0].equals(int.class)) {
				tempItem = (ItemStack)getResultItem.invoke(plugin.itemManager, plugin.getRace(player));
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException e) {
			player.sendMessage(ChatColor.RED + "Error: this method name does not exist, or this item was not intended to be obtained through this command. If you have questions contact the dev.");
			e.printStackTrace();
			return true;
		}
		
		if (tempItem == null) {
			player.sendMessage(ChatColor.RED + "Error: this method name does not exist, or this item was not intended to be obtained through this command. If you have questions contact the dev.");
			return true;
		}
		
		int amount = 1;
		if (args.length > 1) {
			if (plugin.isInteger(args[1])) {
				amount = Integer.parseInt(args[1]);
			} else {
				player.sendMessage(ChatColor.RED + "Please enter an integer for the amount of items you want.");
				return true;
			}
		}
		if (amount < 0) {
			player.sendMessage(ChatColor.RED + "Please enter a positive number for the amount of items you want.");
			return true;
		}
		
		if (amount > 2048) {
			player.sendMessage(ChatColor.RED + "Ayo??? Please enter a reasonable number for the amount of items you want.");
			return true;
		}
		
		if (Recipes.BACKPACK.matches(plugin, tempItem)) {
			if (args.length > 1 && plugin.isInteger(args[1])) {
				player.getInventory().addItem(plugin.itemManager.getBackpackItem(Integer.parseInt(args[1])));
				return true;
			} else {
				player.sendMessage(ChatColor.RED + "Please enter an integer for the backpack ID you want.");
				return true;
			}
		}
		
		tempItem.setAmount(amount);
		
//		player.getWorld().dropItem(player.getLocation(), tempItem);
		player.getInventory().addItem(tempItem);
		return true;
	}

}
