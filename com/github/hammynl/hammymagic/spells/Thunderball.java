package com.github.hammynl.hammymagic.spells;

import org.bukkit.Bukkit;
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

public class Thunderball implements Listener {
	
	private Magic plugin = Magic.getPlugin(Magic.class);

	@EventHandler
	public void WizardWandThunderball(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& p.getInventory().getItemInMainHand().getType() == Material.BLAZE_ROD 
				&& p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals(plugin.changeColor("&d&o&lWizard Wand &7[Thunderball]"))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p);
			Snowball ball = (Snowball) p.launchProjectile(Snowball.class);
			ball.setVelocity(p.getLocation().getDirection().multiply(3));
			new BukkitRunnable() {
				public void run() {
					Location loc = ball.getLocation();
					loc.getWorld().spawnParticle(Particle.BLOCK_DUST, loc, 3, Material.WHITE_STAINED_GLASS.createBlockData());
					if(ball.isDead()) {
						for(Player all : Bukkit.getOnlinePlayers()) all.playSound(ball.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
						ball.getLocation().getWorld().strikeLightning(ball.getLocation());
						ball.getLocation().getWorld().createExplosion(ball.getLocation(), 8.0F);
						this.cancel();
					}
				}
			}.runTaskTimer(plugin, 0, 1);
		}
	}
}
