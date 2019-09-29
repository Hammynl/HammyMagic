package com.github.hammynl.hammymagic.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
	public static HashMap<UUID, Integer> spell = new HashMap<UUID, Integer>();

	@EventHandler
	public void WizardWandSwitch(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ArrayList<String> lore = new ArrayList<String>();
		for (String s : plugin.getConfig().getStringList("wizardwand.lore")) lore.add(plugin.changeColor(s));
		ItemStack MainItem = p.getInventory().getItemInMainHand();
		ItemMeta ItemMeta = p.getInventory().getItemInMainHand().getItemMeta();

		if (!spell.containsKey(uuid)) spell.put(uuid, 0);

		if ((e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK)
			&& ItemMeta.getLore().equals(lore) && !plugin.isDisabledWorld(p)) {

			int number = spell.get(uuid);
			spell.remove(uuid);
			spell.put(uuid, number + 1); // Switches you to the next number / the next spell

			if (spell.get(uuid) == 1) {
				if (plugin.getConfBool("use-spells.barrage")) {
					ItemMeta.setDisplayName(plugin.changeColor("&d&o&lWizard Wand &7[Barrage]"));
					MainItem.setItemMeta(ItemMeta);
				} else if (!plugin.getConfBool("use-spells.barrage")) {
					spell.put(uuid, spell.get(uuid) + 1);
				}
			}
			if (spell.get(uuid) == 2) {
				if (plugin.getConfBool("use-spells.escape")) {
					ItemMeta.setDisplayName(plugin.changeColor("&d&o&lWizard Wand &7[Escape]"));
					MainItem.setItemMeta(ItemMeta);
				} else if (!plugin.getConfBool("use-spells.escape")) {
					spell.put(uuid, spell.get(uuid) + 1);
				}
			}
			if (spell.get(uuid) == 3) {
				if (plugin.getConfBool("use-spells.fireball")) {
					ItemMeta.setDisplayName(plugin.changeColor("&d&o&lWizard Wand &7[Fireball]"));
					MainItem.setItemMeta(ItemMeta);
				} else if (!plugin.getConfBool("use-spells.fireball")) {
					spell.put(uuid, spell.get(uuid) + 1);
				}
			}
			if (spell.get(uuid) == 4) {
				if (plugin.getConfBool("use-spells.lightning")) {
					ItemMeta.setDisplayName(plugin.changeColor("&d&o&lWizard Wand &7[Lightning]"));
					MainItem.setItemMeta(ItemMeta);
				} else if (!plugin.getConfBool("use-spells.lightning")) {
					spell.put(uuid, spell.get(uuid) + 1);
				}
			}
			if (spell.get(uuid) == 5) {
				if (plugin.getConfBool("use-spells.teleport")) {
					ItemMeta.setDisplayName(plugin.changeColor("&d&o&lWizard Wand &7[Teleport]"));
					MainItem.setItemMeta(ItemMeta);
				} else if (!plugin.getConfBool("use-spells.teleport")) {
					spell.put(uuid, spell.get(uuid) + 1);
				}
			}
			if (spell.get(uuid) == 6) {
				if (plugin.getConfBool("use-spells.thunderball")) {
					ItemMeta.setDisplayName(plugin.changeColor("&d&o&lWizard Wand &7[Thunderball]"));
					MainItem.setItemMeta(ItemMeta);
				} else if (!plugin.getConfBool("use-spells.thunderball")) {
					spell.put(uuid, spell.get(uuid) + 1);
				}
			}

			if (spell.get(uuid) >= 6) {
				spell.put(uuid, 0);
			}
		}
	}
}
