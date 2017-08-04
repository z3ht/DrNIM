package com.zinno.nim;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.zinno.nim.commands.NIM;
import com.zinno.nim.commands.exit.Leave;
import com.zinno.nim.commands.info.Help;
import com.zinno.nim.commands.info.Tutorial;
import com.zinno.nim.commands.start.Accept;
import com.zinno.nim.commands.start.Expire;
import com.zinno.nim.commands.start.Start;
import com.zinno.nim.commands.start.computer.ComputerOptions;
import com.zinno.nim.events.player.leave.LeaveGame;
import com.zinno.nim.events.player.leave.LeaveMiniGame;
import com.zinno.nim.events.player.move.Click;
import com.zinno.nim.events.player.move.ConfirmMove;
import com.zinno.nim.events.player.security.DropItem;
import com.zinno.nim.events.player.security.InventoryClick;
import com.zinno.nim.events.player.security.PlayerMove;
import com.zinno.nim.game.util.GameMaker;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		registerCommands();
		registerSubCommands();
		registerEvents();
	}

	@Override
	public void onDisable() {
		GameMaker.delAllGames();
	}

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new DropItem(), this);
		pm.registerEvents(new LeaveGame(), this);
		pm.registerEvents(new PlayerMove(), this);
		pm.registerEvents(new Click(), this);
		pm.registerEvents(new InventoryClick(), this);
		pm.registerEvents(new ConfirmMove(), this);
		pm.registerEvents(new LeaveMiniGame(), this);
		pm.registerEvents(new ComputerOptions(), this);
	}

	private void registerCommands() {
		getCommand("nim").setExecutor(new NIM());
	}

	private void registerSubCommands() {
		NIM.addCommand(Arrays.asList("start", "s", "invite", "inv"), new Start());
		NIM.addCommand(Arrays.asList("accept", "a"), new Accept());
		NIM.addCommand(Arrays.asList("expire", "e"), new Expire());
		NIM.addCommand(Arrays.asList("leave", "l", "exit", "quit"), new Leave());
		NIM.addCommand(Arrays.asList("help", "h"), new Help());
		NIM.addCommand(Arrays.asList("tutorial", "tut", "t"), new Tutorial());
	}

}
