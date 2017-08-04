package com.zinno.nim.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class Config {
	private Config() {}
	
	public static Plugin getPlugin() {
		return Bukkit.getPluginManager().getPlugin("DrNIM");
	}
}
