package com.github.hammynl.hammymagic.spells;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.hammynl.hammymagic.Magic;
import com.github.hammynl.hammymagic.utils.CooldownUtil;

public class Barrage implements Listener {

	public HashMap<UUID, Integer> seconds = new HashMap<UUID, Integer>();
	private Magic plugin = Magic.getPlugin(Magic.class);

	@EventHandler
	public void WizardWandBarrage(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		Location ploc = p.getLocation();
		UUID uuid = p.getUniqueId();

		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& p.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD 
				&& p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.changeColor("&d&o&lWizard Wand &7[Barrage]"))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p);
			Location loc = p.getTargetBlock(null, 100).getLocation();
			seconds.put(uuid, 0);
			p.playSound(ploc, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
			new BukkitRunnable() {
				@Override
				public void run() {
					seconds.put(uuid, seconds.get(uuid) + 1);
					loc.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, loc, 0);
					if (seconds.get(uuid) == 100 || seconds.get(uuid) == 103 || seconds.get(uuid) == 106 || seconds.get(uuid) == 109) {
						loc.getWorld().strikeLightning(loc);
					}
					if (seconds.get(uuid) > 112) {
						loc.getWorld().strikeLightning(loc);
						loc.getWorld().createExplosion(loc, 8.0F);
						this.cancel();
					}
				}
			}.runTaskTimer(plugin, 0, 1);
		}
	}
}
