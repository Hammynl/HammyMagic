package com.github.hammynl.hammymagic.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.hammynl.hammymagic.Magic;

public class CooldownUtil implements Listener {
	

	public static HashMap<UUID, Integer> StorageWizardWand = new HashMap<UUID, Integer>();
	
	private static Magic plugin = Magic.getPlugin(Magic.class);
	

	public static void CooldownWizardWand(Player p) {
		if(plugin.getConfBool("wizardwand.cooldown.enabled") != true) return;
		UUID uuid = p.getUniqueId();
		StorageWizardWand.put(uuid, 1); 
		new BukkitRunnable() {
			@Override
			public void run() {
				StorageWizardWand.remove(uuid, 1);
				if(plugin.getConfBool("wizardwand.cooldown.options.sound")) p.getWorld().playSound(p.getLocation(), Sound.valueOf(plugin.getConfString("wizardwand.cooldown.values.sound")), 1, 1);
				if(plugin.getConfBool("wizardwand.cooldown.options.message")) p.sendMessage(plugin.changeColor(plugin.getLangString("prefix") + plugin.getConfString("wizardwand.cooldown.values.message")));
				if(plugin.getConfBool("wizardwand.cooldown.options.effect")) p.getEyeLocation().getWorld().playEffect(p.getLocation(), Effect.valueOf(plugin.getConfString("wizardwand.cooldown.values.effect")), 1);
				this.cancel();
			}
		}.runTaskTimer(plugin, plugin.getConfInt("wizardwand.cooldown.values.length") * 20, plugin.getConfInt("wizardwand.cooldown.values.length") * 20); 
	}
}
