package com.zinno.nim.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface SubCommand {
	
	public void runCommand(CommandSender sender, Command cmd, String[] args);
	
}
