package com.minecraftdimensions.commandcontrol;

import java.util.HashMap;

import com.minecraftdimensions.command.config.Config;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;

public class CommandControl extends Plugin {
	ProxyServer proxy;

	private Config config;

	public HashMap<String, String> serverCommands = new HashMap<>();
	public HashMap<String, String> proxyCommands = new HashMap<>();

	public void onEnable() {
		proxy = ProxyServer.getInstance();
		getConfig();
		registerListener();
	}

    private void registerListener() {

        proxy.getPluginManager().registerListener( this, new CommandListener() );

    }

    private void getConfig() {
		String configpath = "/plugins/CommandControl/commands.yml";
		config = new Config(configpath);
		for (String data : config.getSubNodes("ServerCommands")) {
            if(!data.startsWith( "/" )){
                data="/"+data;
            }
			serverCommands.put(data,
					config.getString("ServerCommands." + data, null));
		}
		for (String data : config.getSubNodes("BungeeCommands")) {
            if(!data.startsWith( "/" )){
                data="/"+data;
            }
			proxyCommands.put(data,
					config.getString("BungeeCommands." + data, null));
		}
	}

}
