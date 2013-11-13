package com.minecraftdimensions.commandcontrol;

import java.io.*;
import java.util.HashMap;

import com.minecraftdimensions.commandcontrol.config.Config;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

public class CommandControl extends Plugin {
	ProxyServer proxy;

    public static HashMap<String, String []> serverCommands = new HashMap<>();
	public static HashMap<String, String []> proxyCommands = new HashMap<>();
    public static CommandControl INSTANCE;

	public void onEnable() {
		proxy = ProxyServer.getInstance();
        INSTANCE = this;
		getConfig();
		registerListener();
        registerCommands();
	}

    private void registerCommands() {
        proxy.getPluginManager().registerCommand( this, new ccReloadCommand("ccreload", "commandcontrol.reload", "commandcontrolreload") );
    }

    private void registerListener() {

        proxy.getPluginManager().registerListener( this, new CommandListener() );

    }

    protected static void getConfig() {
        File file = new File( INSTANCE.getDataFolder().getAbsoluteFile() + File.separator + "config.yml" );
        if ( !file.exists() ) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch ( IOException e ) {
    e.printStackTrace();
}


            InputStream is = INSTANCE.getClass().getClassLoader().getResourceAsStream( "config.yml" );

            try {

                OutputStream os;

                os = new FileOutputStream( file );

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ( ( bytesRead = is.read( buffer ) ) != -1 ) {
                    try {
                        os.write( buffer, 0, bytesRead );
                    } catch ( IOException e ) {
                        e.printStackTrace();
                    }
                }
                is.close();
                os.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
        String configpath = File.separator + "plugins" + File.separator + "CommandControl" + File.separator + "config.yml";
        Config config = new Config( configpath );
		for (String data : config.getSubNodes( "ServerCommands" )) {
            String value = config.getString( "ServerCommands." + data, null );
            String values[];
            if(value.contains( "&&" )){
                values = value.split( "&&" );
            } else {
                values = new String[] {value};
            }
            if(!data.startsWith( "/" )){
                data="/"+data;
            }
            for(int x = 0; x< values.length; x++){
                if(!(values[x].startsWith( "/" ) || values[x].startsWith( "{server}") || values[x].startsWith( "{proxy}" ))){
                    values[x]="/"+values[x];
                }
            }
			serverCommands.put(data,values);
		}
		for (String data : config.getSubNodes( "BungeeCommands" )) {

            String value = config.getString( "BungeeCommands." + data, null );
            String values [];
            if(value.contains( "&&" )){
                values = value.split( "&&" );
            } else{
                values = new String[] {value};
            }
            if(!data.startsWith( "/" )){
                data="/"+data;
            }
            for(int x = 0; x< values.length; x++){
                if(values[x].startsWith( "/" )){
                    values[x]=values[x].substring( 1,values[x].length() )  ;
                }
            }
			proxyCommands.put(data,values);
		}
	}

}
