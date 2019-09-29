package com.github.hammynl.hammymagic;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class FileManager {

	private Magic plugin = Magic.getPlugin(Magic.class);
	public File langfile = new File(plugin.getDataFolder(), "language.yml");
	public FileConfiguration langconf;

	public void setupLangFile() {
		if (!plugin.getDataFolder().exists()) plugin.getDataFolder().mkdirs();
		if (!langfile.exists()) {
			try {
				langfile.createNewFile();
				loadLangFile();
				langconf.set("prefix", "&7[&c&l&oHammy&d&o&lMagic&7] ");
				langconf.set("wizardwandcommand", "&dYou have been given a &d&o&lWizard Wand");
				langconf.set("reloadcommand", "&a&lSuccesfully reloaded HammyWands");
				langconf.set("spellswitch", "&dSwitching your current spell to &6&l[SPELL]");
				langconf.options().copyDefaults(true);
				saveLangFile();
			} catch (IOException ex) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Language.yml file could not be created!");
			}
		}
		loadLangFile();
	}

	public void saveLangFile() {
		try {
			langconf.save(langfile);
		} catch (IOException ex) {
		}
	}

	public void loadLangFile() {
		langconf = YamlConfiguration.loadConfiguration(langfile);
	}
}
