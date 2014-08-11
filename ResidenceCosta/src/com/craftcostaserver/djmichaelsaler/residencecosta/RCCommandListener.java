package com.craftcostaserver.djmichaelsaler.residencecosta;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class RCCommandListener {
	private String [] helptext = {"help       -Muestra esta ayuda",
			                      "purge days -Elimina las res sin uso de x dias"};
	ResidenceCosta plugin;

	public RCCommandListener(ResidenceCosta plugin){
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
		int numero=0;
		plugin.logger.info("Comando");
		if(command.getName().equalsIgnoreCase("residencecosta")){
			if(args.length == 0){
				mostrarAyuda(sender,numero);//imprimir ayuda
			}
			else if(args[0].equalsIgnoreCase("help")){
				try{
					numero = Integer.parseInt(args[1]);
				}catch(ArrayIndexOutOfBoundsException e){
					//pagina = 0;
				}catch(NumberFormatException e){
					sender.sendMessage("Numero de pagina incorrecto");
				}
				mostrarAyuda(sender,numero);
			}
			else if(args[0].equalsIgnoreCase("purge")){
				try{
					numero = Integer.parseInt(args[1]);
				}catch(ArrayIndexOutOfBoundsException e){
					sender.sendMessage("No has introducido el numero de dias");
				}catch(NumberFormatException e){
					sender.sendMessage("No has introducido un formato de numero correcto");
				}				
			}
			return true;
		}
		return false;
	}
	
	private void mostrarAyuda(CommandSender sender,int pagina){
		
		int inicio=pagina*9;
		int fin;
		
		if(inicio+9>helptext.length){
			fin=helptext.length-inicio;
		}else{
			fin=inicio+9;
		}
		
		for(int i=inicio;i<fin;i++){
			sender.sendMessage(helptext[i]);
		}
	}

}
