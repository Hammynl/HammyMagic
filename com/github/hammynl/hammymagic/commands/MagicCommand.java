package com.github.hammynl.hammymagic.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.hammynl.hammymagic.Magic;
import com.github.hammynl.hammymagic.utils.SwitchUtil;

public class MagicCommand implements CommandExecutor {
	
	private Magic plugin = Magic.getPlugin(Magic.class);
	
	public static ItemStack createItem(Material material, int amount, String nameInput, ArrayList<String> lore) {
	    ItemStack item = new ItemStack(material, amount);
	    ItemMeta itemmeta = item.getItemMeta();
	    itemmeta.setDisplayName(nameInput);
	    itemmeta.addEnchant(Enchantment.LUCK, 1, true);
	    itemmeta.setLore(lore);
	    itemmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    item.setItemMeta(itemmeta);
	    return item;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) return true;
		
		if(args.length == 0 && sender.hasPermission("hammymagic.admin.help")) {
			sender.sendMessage(plugin.changeColor("&5&l>>"));
			sender.sendMessage(plugin.changeColor("&5&l>> &d&lHammy&c&lMagic&d, A plugin made with &c&l<3&d by Hammynl"));
			sender.sendMessage(plugin.changeColor("&5&l>>"));
			sender.sendMessage(plugin.changeColor("&5&l>> &5&lCommands"));
			sender.sendMessage(plugin.changeColor("&5&l>>"));
			sender.sendMessage(plugin.changeColor("&5&l>> &d/magic &7&l>> &dShows this help page"));
			sender.sendMessage(plugin.changeColor("&5&l>> &d/magic wizardwand &7&l>> &dAdds a wizard wand to your inventory"));
			sender.sendMessage(plugin.changeColor("&5&l>> &d/magic reload &7&l>> &dReload the plugin "));
			sender.sendMessage(plugin.changeColor("&5&l>>&d&m"));
			
		} else if(args.length == 1) {
			
			if(sender.hasPermission("hammymagic.magic.wizardwand") && args[0].equalsIgnoreCase("wizardwand")) { 
				Player p = (Player) sender;
				ArrayList<String> lore = new ArrayList<String>();
				ArrayList<SwitchUtil> spellsSorted = new ArrayList<SwitchUtil>();
				for(String s : plugin.getConfig().getStringList("wizardwand.lore")) {
					lore.add(plugin.changeColor(s));
				}
				for (SwitchUtil spells : SwitchUtil.values()) { 
					if(plugin.getConfBool("use-spells." + spells.toString().toLowerCase())) {
						spellsSorted.add(spells);
					}
				}
				p.getInventory().addItem(createItem(Material.BLAZE_ROD, 1, plugin.changeColor(plugin.getConfString(spellsSorted.get(0).getName())), lore));
				p.sendMessage(plugin.changeColor(plugin.getLangString("prefix") + plugin.getLangString("wizardwandcommand")));
				return true;
			} 
			
			if(sender.hasPermission("hammymagic.admin.reload") && args[0].equalsIgnoreCase("reload")) { 
				Player p = (Player) sender;
				plugin.lang.loadLangFile();
				plugin.reloadConfig();
				p.sendMessage(plugin.changeColor(plugin.getLangString("prefix") + plugin.getLangString("reloadcommand")));
				return true;
			}
		}
		return true;
	}
	
}
