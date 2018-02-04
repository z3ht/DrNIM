package com.zinno.nim.commands.info;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;

public class HelpItem {
	
	private String name;
	private String info;
	private String cmd;
	
	public HelpItem(String name, String info, String cmd) {
		this.name = name;
		this.info = info;
		this.cmd = cmd;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getInfo() {
		return info;
	}
	
	public void setInfo(String info) {
		this.info = info;
	}
	
	public String getCmd() {
		return cmd;
	}
	
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	
}
