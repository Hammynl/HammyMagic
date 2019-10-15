package com.github.hammynl.hammymagic.utils;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.hammynl.hammymagic.Magic;

public class SpellSwitch implements Listener {

	private Magic plugin = Magic.getPlugin(Magic.class);

	@EventHandler
	public void wizardWandSwitch(PlayerInteractEvent e) {
		/*
		 * Getting some values so you can perform checks
		 */
		Player p = e.getPlayer();
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : plugin.getConfig().getStringList("wizardwand.lore")) lore.add(plugin.changeColor(s));
		ItemStack MainItem = p.getInventory().getItemInMainHand();
		ItemMeta ItemMeta = p.getInventory().getItemInMainHand().getItemMeta();

		/*
		 * Making the check in one huge boolean since everything has to be true
		 */
		boolean passedAllChecks = (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) 
				&& MainItem.getType() == Material.BLAZE_ROD 
				&& !plugin.isDisabledWorld(p)
				&& ItemMeta.getLore().equals(lore);
		
		/*
		 * If the user passed all checks, It will start the switching
		 */
		if(passedAllChecks) {
			/*
			 * Sorting out the spells that are enabled for further use
			 */
			ArrayList<SwitchUtil> spellsSorted = new ArrayList<SwitchUtil>();
			for (SwitchUtil spells : SwitchUtil.values()) {
				if (plugin.getConfBool("use-spells." + spells.toString().toLowerCase())) {
					spellsSorted.add(spells);
				}
			}
			/*
			 * Starting the switching of the wand.
			 */
			for(int i = 0; i < spellsSorted.size(); i++) {
				/*
				 * If enum number i is the same as the displayname, It goes to the next spell.
				 */
				if(ItemMeta.getDisplayName().equalsIgnoreCase(plugin.changeColor(plugin.getConfString(spellsSorted.get(i).getName())))) {
					try {
						// Trying to set it to i + 1.
						ItemMeta.setDisplayName(plugin.changeColor(plugin.getConfString(spellsSorted.get(i + 1).getName())));
					} catch(IndexOutOfBoundsException ex) {
						// If i + 1 was out of bounds, Reset back to 0 to reset the spell loop.
						ItemMeta.setDisplayName(plugin.changeColor(plugin.getConfString(spellsSorted.get(0).getName())));
					}
					MainItem.setItemMeta(ItemMeta);
					break;
				}
			}
		}
	}
}
