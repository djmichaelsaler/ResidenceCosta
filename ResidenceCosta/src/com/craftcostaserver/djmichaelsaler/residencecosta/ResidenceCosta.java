package com.craftcostaserver.djmichaelsaler.residencecosta;

import java.util.ArrayList;
import java.util.Date;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.java.JavaPlugin;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.ResidenceManager;

public class ResidenceCosta extends JavaPlugin{
	ResidenceManager rmanager;
	ArrayList<OfflinePlayer> allplayers;
	RCCommandListener commandlistener;

	@Override
	public void onEnable() {
		rmanager = Residence.getResidenceManager();
		commandlistener = new RCCommandListener(this);
	}
	
	@Override
	public void onDisable() {

	}
	
	protected void arrayToCollectionOffPlayers(int dias){
		int numpl = getServer().getOfflinePlayers().length;
		allplayers = new ArrayList<OfflinePlayer>(numpl);
		long currenttime = System.currentTimeMillis();
		long timeago = currenttime - dias*24*60*60*1000;
		
		getLogger().info("Current: "+currenttime+" timeago: "+timeago);
		
		for (int i=1; i<numpl;i++){
			if(timeago > getServer().getOfflinePlayers()[i].getLastPlayed()){
				allplayers.add(getServer().getOfflinePlayers()[i]);
				getLogger().info(Long.toString(getServer().getOfflinePlayers()[i].getLastPlayed()));
			}
			
		}
	}
}
