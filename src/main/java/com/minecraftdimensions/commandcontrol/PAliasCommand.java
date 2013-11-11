package com.minecraftdimensions.commandcontrol;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class PAliasCommand extends Command {

	String cmd;
	CommandControl plugin;

	public PAliasCommand(CommandControl plugin, String name) {
		super(name);
		cmd = name;
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		String command = plugin.proxyCommands.get(cmd);
		if (command.charAt(0) == '/') {
			plugin.proxy.getPluginManager().dispatchCommand(sender, command.substring(1,command.length()));
		} else {
			plugin.proxy.getPluginManager().dispatchCommand(sender, command);
		}

	}

}
