package com.github.hammynl.hammymagic.spells;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

import com.github.hammynl.hammymagic.Magic;
import com.github.hammynl.hammymagic.utils.CooldownUtil;
import com.github.hammynl.hammymagic.utils.SwitchUtil;

public class Protect implements Listener {

	private Magic plugin = Magic.getPlugin(Magic.class);

	@EventHandler
	public void WizardWandProtect(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();
		ItemStack MainItem = p.getInventory().getItemInMainHand();

		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& MainItem.getType() == Material.BLAZE_ROD 
				&& MainItem.getItemMeta().getDisplayName().equals(plugin.changeColor(plugin.getConfString(SwitchUtil.PROTECT.getName())))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p); 
			for(int degree = 0; degree < 360; degree = degree + 4) {	
				Location location = p.getLocation().add(0, 0, 0);
				double radians = Math.toRadians(degree);
			    double x = Math.cos(radians) * 1;
			    double z = Math.sin(radians) * 1;
			    location.add(x,0,z);
			    location.getWorld().spawnParticle(Particle.FLAME, location, 0);
			    location.subtract(x,0,z);
			}
			for(int degree = 0; degree < 360; degree = degree + 4) {	
				Location location = p.getLocation().add(0, 2, 0);
				double radians = Math.toRadians(degree);
			    double x = Math.cos(radians) * 1;
			    double z = Math.sin(radians) * 1;
			    location.add(x,0,z);
			    location.getWorld().spawnParticle(Particle.FLAME, location, 0);
			    location.subtract(x,0,z);
			}
			p.setHealth(p.getHealth() + 4);
		}
	}
}
