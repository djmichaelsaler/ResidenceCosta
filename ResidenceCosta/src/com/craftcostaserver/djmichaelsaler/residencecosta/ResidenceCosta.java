package com.craftcostaserver.djmichaelsaler.residencecosta;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.protection.CuboidArea;
import com.bekvon.bukkit.residence.protection.ResidenceManager;

public class ResidenceCosta extends JavaPlugin{
	ResidenceManager rmanager;
	public final Logger logger = Logger.getLogger("Minecraft");
	//ArrayList<OfflinePlayer> allplayers;
	ArrayList<String> residences;
	ArrayList<String> text;
	
	private String [] helptext = {"help        -Muestra esta ayuda",
                                  "purge days  -Elimina las res sin uso de x dias",
                                  "parcelas    -Muestra las parcelas con mas de 35 dias"};

	@Override
	public void onEnable() {
		this.logger.info("[ResidenceCosta] Puglin cargado");
		//this.allplayers = new ArrayList<OfflinePlayer>();
	}
	
	@Override
	public void onDisable() {

	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		this.rmanager = Residence.getResidenceManager();
		int numero=1;
		if(command.getName().equalsIgnoreCase("residencecosta")){
			if(sender.hasPermission("residencecosta.residencecosta")){
				if(args.length == 0){
					mostrarTexto(sender,numero,this.helptext);//imprimir ayuda
				}
				else if(args[0].equalsIgnoreCase("help")){
					try{
						numero = Integer.parseInt(args[1]);
					}catch(ArrayIndexOutOfBoundsException e){

					}catch(NumberFormatException e){
						sender.sendMessage("Numero de pagina incorrecto");
						return false;
					}
					mostrarTexto(sender,numero,this.helptext);
				}
				else if(args[0].equalsIgnoreCase("purge")){
					try{
						numero = Integer.parseInt(args[1]);
					}catch(ArrayIndexOutOfBoundsException e){
						sender.sendMessage("No has introducido el numero de dias");
						return false;
					}catch(NumberFormatException e){
						sender.sendMessage("No has introducido un formato de numero correcto");
						return false;
					}


					long currenttime = System.currentTimeMillis();
					long diasrestar = ((long)numero*24*3600*1000);
					long timeago = currenttime - diasrestar;

					residences = rmanager.getResidenceList(false,true);

					for(int i = 0; i<residences.size();i++){
						if(timeago > getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getLastPlayed() &&
								!residences.get(i).contains("CB.")){
							
							sender.sendMessage(ChatColor.DARK_GREEN+getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getName()+": "+ChatColor.GOLD+residences.get(i));
							
							//Location high = rmanager.getByName(residences.get(i)).getAreaArray()[0].getHighLoc();
							//Location low = rmanager.getByName(residences.get(i)).getAreaArray()[0].getLowLoc();
							//getServer().getWorld("world").
							
						}
					}
				}
				else if(args[0].equalsIgnoreCase("parcelas")){
					text = new ArrayList<String>();
					long currenttime = System.currentTimeMillis();
					long diasrestar = ((long) 35*24*3600*1000);
					long timeago = currenttime - diasrestar;				
					residences = rmanager.getResidenceList(false,true);

					for(int i = 0; i<residences.size();i++){
						if(timeago > getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getLastPlayed() &&
								residences.get(i).contains("CB.") && !residences.get(i).contains("CB.pv") && !getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).isOnline()){
							text.add(ChatColor.YELLOW+getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getName()+
									": "+ChatColor.GREEN+residences.get(i)+" "+ChatColor.DARK_RED+((currenttime-getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getLastPlayed())/86400000)+"d");
							//logger.info(getServer().getOfflinePlayer(rmanager.getByName(residences.get(i)).getOwner()).getName()+": "+residences.get(i));
						}
					}

					try{
						numero = Integer.parseInt(args[1]);
					}catch(ArrayIndexOutOfBoundsException e){

					}catch(NumberFormatException e){
						sender.sendMessage("Numero de pagina incorrecto");
						return false;
					}

					//logger.info(""+text.size());
					mostrarTexto(sender, numero, text.toArray(new String [text.size()]));
				}
				return true;
			}
			return false;
		}
		else if(command.getName().equalsIgnoreCase("resinfo")){		
			if(sender.hasPermission("residencecosta.resinfo")){
				if(args.length > 2 || args.length == 0){
					sender.sendMessage("Numero de argumnentos incorrecto");
					return false;
				}
				else{
					residences = rmanager.getResidenceList(args[0],false,true);
					sender.sendMessage(ChatColor.RED+"Residences de: "+ChatColor.GREEN+args[0]);
					mostrarTexto(sender, 1, residences.toArray(new String[residences.size()]));
					logger.info(residences.toString());
				}
			}
			return true;
		}		
		return false;
	}
	
	private void mostrarTexto(CommandSender sender,int pagina,String[] texto){
		
		int inicio=(pagina-1)*9;
		int fin;
		
		if(texto.length-inicio<9){
			fin=texto.length;
		}else{
			fin=inicio+9;
		}
		
		for(int i=inicio;i<fin;i++){
			sender.sendMessage(texto[i]);
		}
		sender.sendMessage(ChatColor.YELLOW+"Mostrando pagina "+pagina+" de "+(int)Math.ceil((texto.length/9)+1));
	}
}
