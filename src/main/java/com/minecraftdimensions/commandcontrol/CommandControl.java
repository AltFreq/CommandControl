package com.minecraftdimensions.commandcontrol;

import java.util.HashMap;

import com.minecraftdimensions.command.config.Config;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CommandControl extends Plugin {
	ProxyServer proxy;

	private Config config;

	public HashMap<String, String> serverCommands = new HashMap<String, String>();
	public HashMap<String, String> proxyCommands = new HashMap<String, String>();

	public void onEnable() {
		proxy = ProxyServer.getInstance();
		getConfig();
		registerCommands();
	}

	private void getConfig() {
		String configpath = "/plugins/CommandControl/commands.yml";
		config = new Config(configpath);
		for (String data : config.getSubNodes("ServerCommands")) {
			serverCommands.put(data,
					config.getString("ServerCommands." + data, null));
		}
		for (String data : config.getSubNodes("BungeeCommands")) {
			proxyCommands.put(data,
					config.getString("BungeeCommands." + data, null));
		}
	}

	private void registerCommands() {
		for (String data : serverCommands.keySet()) {
			ProxyServer.getInstance().getPluginManager()
					.registerCommand(this, new AliasCommand(this, data));
		}
		for (String data : proxyCommands.keySet()) {
			ProxyServer.getInstance().getPluginManager()
					.registerCommand(this, new PAliasCommand(this, data));
		}
	}

}
