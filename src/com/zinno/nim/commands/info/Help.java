package com.zinno.nim.commands.info;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.zinno.nim.commands.SubCommand;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.HoverEvent.Action;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Help implements SubCommand {

	public void runCommand(CommandSender sender, Command cmd, String[] args) {
		if(!(sender instanceof Player)) {
			String[] commandList = new String[] {
					"/nim start",
					"/nim start <OpponentName>",
					"/nim accept",
					"/nim expire",
					"/nim leave",
					"/nim tutorial",
					"/nim help"
			};
			sender.sendMessage(ChatColor.DARK_AQUA + "Available commands:");
			for(String command : commandList) {
				sender.sendMessage(ChatColor.AQUA + "  - " + command);
			}
			return;
		}
		Player player = (Player) sender;
		List<TextComponent> tcList = new ArrayList<TextComponent>();
		tcList.add(createTC("- /nim           ", "The base for all NIM related commands", "/nim"));
		tcList.add(createTC("- /nim start           ", "Begin a NIM game with the computer", "/nim start"));
		tcList.add(createTC("- /nim start <Opponent Name>           ", "Sends a NIM game invitation to the desired online opponent", ""));
		tcList.add(createTC("- /nim accept           ", "Begin a NIM game with the player who sent an invitation", "/nim accept"));
		tcList.add(createTC("- /nim expire <InviteName>           ", "Delete a NIM game invitation from the desired player", ""));
		tcList.add(createTC("- /nim leave           ", "Leave the NIM game you were playing", "/nim leave"));
		tcList.add(createTC("- /nim tutorial           ", "Learn to play NIM through a tutorial", "/nim tutorial"));
		tcList.add(createTC("- /nim help           ", "Get a list of NIM commands", "/nim help"));
		
		player.sendMessage(ChatColor.DARK_AQUA + "Psst... Hover for More info, Click to Run the command");
		TextComponent tcTitle = new TextComponent("NIM commands:");
		tcTitle.setColor(ChatColor.DARK_PURPLE);
		tcTitle.setBold(true);
		ComponentBuilder cb = new ComponentBuilder("Hover for more info, Click to Run the command").color(ChatColor.BLUE);
		tcTitle.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, cb.create()));
		player.spigot().sendMessage(tcTitle);
		for(TextComponent tc : tcList) {
			player.spigot().sendMessage(tc);
		}
	}
	
	public TextComponent createTC(String title, String info, String command) {
		TextComponent tc = new TextComponent(title);
		tc.setColor(ChatColor.LIGHT_PURPLE);
		ComponentBuilder bc = new ComponentBuilder(info).color(ChatColor.BLUE);
		tc.setHoverEvent(new HoverEvent(Action.SHOW_TEXT, bc.create()));
		if(command!="") {
			tc.setClickEvent(new ClickEvent(net.md_5.bungee.api.chat.ClickEvent.Action.RUN_COMMAND, command));
		}
		return tc;
	}

}
