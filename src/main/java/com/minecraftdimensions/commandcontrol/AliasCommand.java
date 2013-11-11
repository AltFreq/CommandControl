package com.minecraftdimensions.commandcontrol;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class AliasCommand extends Command {

	String cmd;
	CommandControl plugin;

	public AliasCommand(CommandControl plugin, String name) {
		super(name);
		cmd = name;
		this.plugin = plugin;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer pp = (ProxiedPlayer) sender;
		String command = plugin.serverCommands.get(cmd);
		if (command.charAt(0) == '/') {
			pp.chat(command);
		} else {
			pp.chat("/" + command);
		}

	}

}
