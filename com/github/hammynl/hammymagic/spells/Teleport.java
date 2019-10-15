package com.github.hammynl.hammymagic.spells;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.github.hammynl.hammymagic.Magic;
import com.github.hammynl.hammymagic.utils.CooldownUtil;
import com.github.hammynl.hammymagic.utils.SwitchUtil;

public class Teleport implements Listener {
	
	private Magic plugin = Magic.getPlugin(Magic.class);
	
	@EventHandler
	public void WizardWandTeleport(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		ItemStack MainItem = p.getInventory().getItemInMainHand();

		// Checks if the player is performing a right click, Is holding a blaze rod. Has a correct name on the blaze rod and is not on cooldown.
		boolean passedAllChecks = (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) 
				&& MainItem.getType() == Material.BLAZE_ROD 
				&& MainItem.getItemMeta().getDisplayName().equals(plugin.changeColor(plugin.getConfString(SwitchUtil.TELEPORT.getName())))
				&& !plugin.isDisabledWorld(p)
				&& !CooldownUtil.StorageWizardWand.containsKey(p.getUniqueId());
		
		if(passedAllChecks) {
			CooldownUtil.CooldownWizardWand(p);
			float pitch = p.getLocation().getPitch();
			float yaw = p.getLocation().getYaw();
			Location loc = p.getTargetBlock(null, 100).getLocation();
			loc.setYaw(yaw);
			loc.setPitch(pitch);
			p.teleport(loc);
			p.getWorld().playEffect(p.getLocation(), Effect.ENDER_SIGNAL, 2);
			p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP, 1, 1);
		}
			
	}
}
