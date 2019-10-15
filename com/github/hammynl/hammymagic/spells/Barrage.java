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
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.hammynl.hammymagic.Magic;
import com.github.hammynl.hammymagic.utils.CooldownUtil;
import com.github.hammynl.hammymagic.utils.SwitchUtil;

public class Barrage implements Listener {

	public HashMap<UUID, Integer> seconds = new HashMap<UUID, Integer>();
	private Magic plugin = Magic.getPlugin(Magic.class);

	@EventHandler
	public void WizardWandBarrage(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ItemStack MainItem = p.getInventory().getItemInMainHand();

		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& MainItem.getType() == Material.BLAZE_ROD 
				&& MainItem.getItemMeta().getDisplayName().equals(plugin.changeColor(plugin.getConfString(SwitchUtil.BARRAGE.getName())))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p);
			Location loc = p.getTargetBlock(null, 100).getLocation().add(0, 1, 0);
			seconds.put(uuid, 0);
			p.playSound(p.getLocation(), Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1, 1);
			new BukkitRunnable() {
				@Override
				public void run() {
					seconds.put(uuid, seconds.get(uuid) + 1);
					loc.getWorld().spawnParticle(Particle.SMOKE_LARGE, loc, 0);
					if (seconds.get(uuid) == 60 || seconds.get(uuid) == 63 ) {
						loc.getWorld().strikeLightning(loc);
					}
					if (seconds.get(uuid) > 66) {
						seconds.remove(uuid);
						loc.getWorld().strikeLightning(loc);
						loc.getWorld().createExplosion(loc, 4.0F);
						this.cancel();
					}
				}
			}.runTaskTimer(plugin, 0, 1);
		}
	}
}
