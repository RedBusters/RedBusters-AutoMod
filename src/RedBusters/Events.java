package RedBusters;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class Events implements Listener {
	
	static ArrayList<UUID> moderators = new ArrayList<UUID>();
	
	// check for a mod to login, Wait 2 secs, and tell them if there are messages
	 @EventHandler
	 public void onPlayerJoin(PlayerJoinEvent e) {
		 
		 
	// if it is a mod
		 if (e.getPlayer().hasPermission("automod.moderator")){
			 if(!moderators.contains(e.getPlayer().getUniqueId())) {
				 moderators.add(e.getPlayer().getUniqueId());
			 }
			 
			 Bukkit.getScheduler().runTaskLater(ConfigStuff.main, new Runnable() {
				  @Override
				  public void run() {
					  
					  
				// no messages?
					  if(ConfigStuff.get_msgs().isEmpty()) {
							 e.getPlayer().sendMessage(ConfigStuff.prefix + "No messages containing forbidden word ;)");
						 } else {
							 
						// moderator still in the online moderator list?
							  if(moderators.contains(e.getPlayer().getUniqueId())) {
								  Utils.SendInterractiveMessage(e.getPlayer(), ConfigStuff.prefix+" There is "+ ConfigStuff.get_msgs().size()+
										 " Messages containing forbidden words. ", "\n      §8>>> §4Click here to See them §8 <<< ", "Here are the naughty messages ! ", "run_command" , "/automod msgs");				  
						
							  }
						  }
				  }
			}, new Long(40));
				 

		}
		
	 }
	 public void onPlayerLeave(PlayerQuitEvent e) {
		 if (e.getPlayer().getUniqueId().equals( Commands.CurrentMod ) ) Commands.CurrentMod = null;
		 if (moderators.contains(e.getPlayer().getUniqueId())) {
			 moderators.remove(e.getPlayer().getUniqueId());
		 }
		 
	 }
	 
	 
	 // check if message contain a forbidden keyword
	 @EventHandler
	 public void AsyncPlayerChatEvent(AsyncPlayerChatEvent e) {
		 
	// exclude moderators from check
		if (!e.getPlayer().hasPermission("automod.moderator")) {
			
			String msg = e.getMessage();
			String Original = e.getMessage();
			Boolean ContainForbidden = false;
			
		// forbidden word check
			for(String ForbiddenWord : ConfigStuff.forbidden) {
				if(msg.toLowerCase().contains(ForbiddenWord)) {
					ContainForbidden = true;
					
				// censor the message if censor mode on !
					if (ConfigStuff.CensorMode) {
						StringBuilder censor = new StringBuilder();
						for (int i=0; i<ForbiddenWord.length(); i = i+1) {
							censor.append("*");
						}
						msg = msg.replaceAll(ForbiddenWord, censor.toString());
						
					}
				// Replaces the message, if Troll Mode activated
					if(ConfigStuff.TrollMode) {
						msg = ConfigStuff.PickATroll();
					}
					
					
					
				}
			}
			e.setMessage(msg);
			
		
		// save those, in case we cancel the event later	
			Player player = e.getPlayer();
			String player_name = e.getPlayer().getName();
			
		// Hard censor strikes !
			if(ConfigStuff.hardCensorMode && ContainForbidden ) e.setCancelled(true);
			
			
		// WarnMode warns 1 tick later, so that the warning comes after the message
			if(ConfigStuff.Warning_mode) {
				
				Bukkit.getScheduler().runTaskLater(ConfigStuff.main, new Runnable() {
					  @Override
					  public void run() {
						  e.getPlayer().sendMessage(ConfigStuff.Warning);
					  }
				}, new Long(1));
				
			}
			
		// Send Moderators their Sanction Pannel !
			if(ContainForbidden) {
				ConfigStuff.add_message(e.getPlayer(), Original);
				Bukkit.getScheduler().runTaskLater(ConfigStuff.main, new Runnable() {
					  @Override
					  public void run() {
						 for(UUID moderator : moderators) {
								
								Bukkit.getPlayer(moderator).sendMessage("§8===============================================\n\n");
								Bukkit.getPlayer(moderator).sendMessage(ConfigStuff.prefix + "The original message was : "+ Original);
								Utils.SendInterractiveMessage(Bukkit.getPlayer(moderator), "\n"+ConfigStuff.prefix, 
										"No sanction", "Apply no sanctions and delete entry", "run_command" ,"/automod del "+player+" tchat");
								for(int i=0; i < ConfigStuff.sanctions.size(); i++) {
									Utils.SendInterractiveMessage(Bukkit.getPlayer(moderator), ConfigStuff.prefix, 
											ConfigStuff.sanctions.keySet().toArray()[i].toString().replace("%player%", player_name).replace("&", "§").replace("%mod%", Bukkit.getPlayer(moderator).getName() ) , "", "run_command" , "/automod sanction "+player_name+" "+i + " tchat");			
									
								}
								Bukkit.getPlayer(moderator).sendMessage("§8===============================================\n\n");
					 
						 }					  
					}
				}, new Long(1));
			}	
		}
	 }	 
}
	 

	
	


