package com.craftcostaserver.djmichaelsaler.residencecosta;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class ThreadParcelas implements Runnable{

	ResidenceCosta plugin;
	int numero;
	String args;
	CommandSender sender;
	public ThreadParcelas(ResidenceCosta plugin, int numero, String args, CommandSender sender) {
		this.plugin = plugin;
		this.numero = numero;
		this.args = args;
		this.sender = sender;
	}

	@Override
	public void run() {
		plugin.text = new ArrayList<String>();
		Date now =new Date();
		long currenttime = now.getTime();
		long diasrestar = ((long) 35*24*3600*1000);
		long timeago = currenttime - diasrestar;				
		plugin.residences = plugin.rmanager.getResidenceList(false,true);

		for(int i = 0; i<plugin.residences.size();i++){
			if(timeago > plugin.getServer().getOfflinePlayer(plugin.rmanager.getByName(plugin.residences.get(i)).getOwner()).getLastPlayed() &&
					plugin.residences.get(i).contains("CB.") && !plugin.residences.get(i).contains("CB.pv") && !plugin.getServer().getOfflinePlayer(plugin.rmanager.getByName(plugin.residences.get(i)).getOwner()).isOnline()){
				plugin.text.add(ChatColor.YELLOW+plugin.getServer().getOfflinePlayer(plugin.rmanager.getByName(plugin.residences.get(i)).getOwner()).getName()+
						": "+ChatColor.GREEN+plugin.residences.get(i)+" "+ChatColor.DARK_RED+((currenttime-plugin.getServer().getOfflinePlayer(plugin.rmanager.getByName(plugin.residences.get(i)).getOwner()).getLastPlayed())/86400000)+"d");
				//logger.info(getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getName()+": "+residences.get(i));
			}
		}
		
		try{
			numero = Integer.parseInt(args);
		}catch(ArrayIndexOutOfBoundsException e){

		}catch(NumberFormatException e){
			sender.sendMessage("Numero de pagina incorrecto");
			//return false;
		}
		//logger.info(""+text.size());
		plugin.mostrarTexto(sender, numero, plugin.text.toArray(new String [plugin.text.size()]));
	}

}
