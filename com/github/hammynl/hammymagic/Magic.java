package com.github.hammynl.hammymagic;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.hammynl.hammymagic.commands.MagicCommand;
import com.github.hammynl.hammymagic.spells.Barrage;
import com.github.hammynl.hammymagic.spells.Escape;
import com.github.hammynl.hammymagic.spells.Fireball;
import com.github.hammynl.hammymagic.spells.Lightning;
import com.github.hammynl.hammymagic.spells.Spark;
import com.github.hammynl.hammymagic.spells.Teleport;
import com.github.hammynl.hammymagic.spells.Thunderball;
import com.github.hammynl.hammymagic.utils.CooldownUtil;
import com.github.hammynl.hammymagic.utils.SpellSwitch;

public class Magic extends JavaPlugin implements Listener {

	public FileManager lang;

	@Override
	public void onEnable() {
		@SuppressWarnings("unused")
		MetricsLite metrics = new MetricsLite(this);
		setupLangFile();
		registerCommands();
		registerEvents();
		saveDefaultConfig();
	}
	
	public int getConfInt(String bool) {
		return getConfig().getInt(bool);
	}

	public String getConfString(String bool) {
		return getConfig().getString(bool);
	}

	public boolean getConfBool(String bool) {
		return getConfig().getBoolean(bool);
	}

	public String changeColor(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}

	public void registerEvents() {
		PluginManager PluginManager = Bukkit.getPluginManager();
		PluginManager.registerEvents(new Fireball(), this);
		PluginManager.registerEvents(new Escape(), this);
		PluginManager.registerEvents(new Teleport(), this);
		PluginManager.registerEvents(new SpellSwitch(), this);
		PluginManager.registerEvents(new Lightning(), this);
		PluginManager.registerEvents(new Barrage(), this);
		PluginManager.registerEvents(new CooldownUtil(), this);
		PluginManager.registerEvents(new Thunderball(), this);
		PluginManager.registerEvents(new Spark(), this);
	}

	public boolean isDisabledWorld(Player p) {
		for (String s : getConfig().getStringList("disabled-worlds"))
			if (p.getWorld().getName().equalsIgnoreCase(s))
				return true;
		return false;
	}

	public void registerCommands() {
		this.getCommand("magic").setExecutor(new MagicCommand());
	}

	public void setupLangFile() {
		FileManager man = new FileManager();
		man.setupLangFile();
		this.lang = man;
	}

	public String getLangString(String s) {
		return ChatColor.translateAlternateColorCodes('&', lang.langconf.getString(s));
	}

	public ArrayList<String> getLangList(String s) {
		return (ArrayList<String>) lang.langconf.getStringList(s);
	}
}
