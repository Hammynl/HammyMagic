package com.github.hammynl.hammymagic.spells;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.LargeFireball;
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

public class Fireball implements Listener {

	private Magic plugin = Magic.getPlugin(Magic.class);

	@EventHandler
	public void WizardWandFireball(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack MainItem = p.getInventory().getItemInMainHand();

		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& MainItem.getType() == Material.BLAZE_ROD 
				&& MainItem.getItemMeta().getDisplayName().equals(plugin.changeColor(plugin.getConfString(SwitchUtil.FIREBALL.getName())))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p);
			LargeFireball fireball = (LargeFireball) p.launchProjectile(LargeFireball.class);
			fireball.setVelocity(p.getLocation().getDirection().multiply(2));
			p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1, 1);
			new BukkitRunnable() {
				@Override
				public void run() {
					if (fireball.isDead()) {
						for (Player all : Bukkit.getOnlinePlayers()) {
							all.playSound(fireball.getLocation(), Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 1);
						}
						fireball.getLocation().getWorld().createExplosion(fireball.getLocation(), 8.0F);
						this.cancel();
					}
					
					Location loc = fireball.getLocation();
					loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
					loc.getWorld().playEffect(loc, Effect.SMOKE, 1);
					loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
					loc.getWorld().playEffect(loc, Effect.SMOKE, 1);
					loc.getWorld().playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
					loc.getWorld().playEffect(loc, Effect.SMOKE, 1);
				}
			}.runTaskTimer(plugin, 0, 1);
		}
	}
}
