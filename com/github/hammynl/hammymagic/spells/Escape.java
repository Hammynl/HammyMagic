package com.github.hammynl.hammymagic.spells;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.github.hammynl.hammymagic.Magic;
import com.github.hammynl.hammymagic.utils.CooldownUtil;

public class Escape implements Listener {

	private Magic plugin = Magic.getPlugin(Magic.class);

	@SuppressWarnings("deprecation")
	@EventHandler
	public void WizardWandEscape(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& p.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD 
				&& p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.changeColor("&d&o&lWizard Wand &7[Escape]"))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p);
			Snowball snowball = (Snowball) p.launchProjectile(Snowball.class);
			snowball.setVelocity(p.getLocation().getDirection().multiply(2));
			snowball.setPassenger(p);
			p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LAUNCH, 1, 1);
			new BukkitRunnable() {
				@Override
				public void run() {
					if (snowball.isDead())
						this.cancel();
					Location loc = snowball.getLocation();
					loc.getWorld().spawnParticle(Particle.SNOWBALL, loc, 1);
					loc.getWorld().spawnParticle(Particle.SNOWBALL, loc, 1);
					loc.getWorld().spawnParticle(Particle.SNOWBALL, loc, 1);
				}
			}.runTaskTimer(plugin, 0, 1);
		}
	}
}
