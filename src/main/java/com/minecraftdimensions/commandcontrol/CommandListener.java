package com.minecraftdimensions.commandcontrol;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;


public class CommandListener implements Listener {

    @EventHandler
    public void commandListen(ChatEvent e){

        if(!e.isCommand()){
            return;
        }
        if(e.isCancelled()){
            return;
        }
        if(!(e.getSender() instanceof ProxiedPlayer)){
            return;
        }
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if(!p.hasPermission( "commandcontrol.access" )) {
            return;
        }
        String message = e.getMessage();
        String [] args = message.split( " " );
        boolean sent = false;
        for(String newCommand:CommandControl.proxyCommands.keySet()) {
            if(newCommand.equalsIgnoreCase( message )){
                e.setCancelled( true );
                for(String coms:CommandControl.proxyCommands.get( newCommand )){
                    System.out.println( coms );
                    if(coms.startsWith( "{server}" )) {
                        coms = coms.replace( "{server}", "" );
                        if(!coms.startsWith( "/" )){
                            coms = "/"+coms;
                        }
                        p.chat( coms );
                        sent = true;
                    }else{
                        ProxyServer.getInstance().getPluginManager().dispatchCommand( p, coms );
                        sent = true;
                    }
                }
                if(sent){
                    return;
                }
                break;
            }else if(args.length>1 && newCommand.contains( "{arg" )){
                String [] words = newCommand.split( " " );
                boolean matched = true;
                ArrayList<Integer> list = new ArrayList<>();
                int y = 0;
                for(String word: words){
                    if( args.length>y && word.equalsIgnoreCase( args[y] )){
                        y++;
                    }else if(  args.length>y && word.equals( "{arg}" )){
                        list.add( y );
                        y++;
                    }else if( args.length>y && word.equals( "{args}" )){
                        while(args.length>y){
                            list.add( y );
                            y++;
                        }
                    }else{
                        matched = false;
                        break;
                    }
                }
                if(matched){
                    e.setCancelled( true );
                    int x = 0;

                    for(String coms:CommandControl.proxyCommands.get( newCommand )){
                        if(coms.contains( "{arg}" )){
                            while(coms.contains( "{arg}" )){
                                if(x<list.size())  {
                                    coms = coms.replaceFirst( "\\{arg\\}", args[list.get( x )] );
                                    list.remove( x );
                                }
                            }
                        }
                        if(coms.contains( "{args}" ))  {
                            String replaceArgs="";
                            for(int i: list){
                                replaceArgs+=" "+args[i];
                            }
                            if(replaceArgs.length()>0) {
                                coms = coms.replace( "{args}", replaceArgs.substring( 1,replaceArgs.length() ) );
                            }
                        }
                        if(coms.startsWith( "{server}" )) {
                            coms = coms.replace( "{server}", "" );
                            if(!coms.startsWith( "/" )){
                                coms = "/"+coms;
                            }
                            p.chat( coms );
                            sent = true;
                        } else{
                            ProxyServer.getInstance().getPluginManager().dispatchCommand( p, coms );
                            sent = true;
                        }
                    }
                    if(sent){
                        return;
                    }
                    break;
                }

            }

        }


        for(String newCommand:CommandControl.serverCommands.keySet()) {
            if(newCommand.equalsIgnoreCase( message )){
                e.setCancelled( true );
                for(String coms:CommandControl.serverCommands.get( newCommand )){
                    if(coms.startsWith( "{proxy}" )) {
                        coms = coms.replace( "{proxy}", "" );
                        if(coms.startsWith( "/" )){
                            coms = coms.substring( 1,coms.length() );
                        }
                        ProxyServer.getInstance().getPluginManager().dispatchCommand( p, coms );
                        sent = true;
                    }else{
                        p.chat( coms );
                        sent = true;
                    }
                }
                if(sent){
                    return;
                }
                break;
            }else if(args.length>1 && newCommand.contains( "{arg" )){
                String [] words = newCommand.split( " " );
                boolean matched = true;
                ArrayList<Integer> list = new ArrayList<>();
                int y = 0;
                for(String word: words){
                    if( args.length>y && word.equalsIgnoreCase( args[y] )){
                        y++;
                    }else if(  args.length>y && word.equals( "{arg}" )){
                        list.add( y );
                        y++;
                    }else if( args.length>y && word.equals( "{args}" )){
                        while(args.length>y){
                            list.add( y );
                            y++;
                        }
                    }else{
                        matched = false;
                        break;
                    }
                }
                if(matched){
                    e.setCancelled( true );
                    int x = 0;

                    for(String coms:CommandControl.serverCommands.get( newCommand )){
                        if(coms.contains( "{arg}" )){
                            while(coms.contains( "{arg}" )){
                                if(x<list.size())  {
                                    coms = coms.replaceFirst( "\\{arg\\}", args[list.get( x )] );
                                    list.remove( x );
                                }
                            }
                        }
                        if(coms.contains( "{args}" ))  {
                            String replaceArgs="";
                            for(int i: list){
                                replaceArgs+=" "+args[i];
                            }
                            if(replaceArgs.length()>0) {
                                coms = coms.replace( "{args}", replaceArgs.substring( 1,replaceArgs.length() ) );
                            }
                        }
                        if(coms.startsWith( "{proxy}" )) {
                            coms = coms.replace( "{proxy}", "" );
                            if(coms.startsWith( "/" )){
                                coms = coms.substring( 1,coms.length() ) ;
                            }
                            ProxyServer.getInstance().getPluginManager().dispatchCommand( p, coms );
                            sent = true;
                        } else{
                            p.chat( coms );
                            sent = true;
                        }
                    }
                    if(sent){
                        return;
                    }
                    break;
                }

            }

        }


    }
}
