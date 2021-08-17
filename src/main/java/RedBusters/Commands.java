package RedBusters;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import org.apache.commons.lang3.StringUtils;

public class Commands implements CommandExecutor {
	
	public static UUID CurrentMod;
	public static String current_player_messages;

	
    public boolean onCommand(CommandSender sender, Command command, String arg, String[] args) {
		

		// Only for player, Sorry console :/ i guess you failed the captcha :')
			if (sender.hasPermission("automod.moderator") && sender instanceof Player) {
				
			// Automod Command
				if(command.getName().equalsIgnoreCase("automod")) {
					
					if(args.length == 0) {
						sender.sendMessage("§8===============================================\n\n");
						// tell all commands, also make them clickable
						Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
								"          §8>>>  §5Click Here to see Messages  §8<<<      ", "&3Click to moderate", "run_command" ,"/automod msgs");
						
						Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
								"          §8>>>  §5List Forbidden words  §8<<<      ", "", "run_command" ,"/automod words ");
						Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
								"          §8>>>  §5List Troll Sentences  §8<<<      ", "", "run_command" ,"/automod trolls list ");
						Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
								"          §8>>>  §5List Sanctions  §8<<<      ", "", "run_command" ,"/automod sanctions");
						Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
								"          §8>>>  §5Set Warning Message  §8<<<      \n", "§2Click here to write Command", "suggest_command" ,"/automod setwarn ");
						
						Utils.choose((Player)sender, ConfigStuff.prefix+" Toggle Censor:       ", "§2 Turn On ", "","run_command", "/automod censor on",
																		" &8 ||| ", " §cTurn off ", "","run_command", "/automod censor off");
						
						Utils.choose((Player)sender, ConfigStuff.prefix+" Toggle Hard Censor:", "§2 Turn On ", "","run_command", "/automod hardcensor on",
								" &8 ||| ", " §cTurn off ", "","run_command", "/automod hardcensor off");
						
						Utils.choose((Player)sender, ConfigStuff.prefix+" Toggle TrollMode:     ", "§2Turn On ", "","run_command", "/automod trolls on",
								" &8 ||| ", " §cTurn off ", "","run_command", "/automod trolls off");
						
						Utils.choose((Player)sender, ConfigStuff.prefix+" Toggle AutoWarn:     ", "§2Turn On ", "","run_command", "/automod warnmode on",
								" &8 ||| ", " §cTurn off ", "","run_command", "/automod warnmode off");

						Utils.choose((Player)sender, ConfigStuff.prefix, "§2Add Forbidden words", "Add words to forbidden list","suggest_command", "/automod addwords ",
								" &8|||", "§cRemove Forbidden words", " Delete words from Forbidden List ","suggest_command", "/automod delwords ");
						
						Utils.choose((Player)sender, ConfigStuff.prefix+"          ", "§2 Add a Troll ", "Add a troll Sentence","suggest_command", "/automod trolls add ",
								" &8 ||| ", "§c Remove a Troll", " Delete a troll from index ","suggest_command", "/automod trolls del ");
						
						Utils.choose((Player)sender, ConfigStuff.prefix+"      ", "§2 add a Sanction ", "add a sanction","suggest_command", "/automod sanctions add Name / DisplayName /command %player% ",
								" &8 ||| ", "§c Remove a Sanction", " Remove a sanction from clickables options ","suggest_command", "/automod sanctions del ");
					
						sender.sendMessage("\n§8===============================================");
					}
	
					if(args.length >= 1) {
					
						if(args[0].equalsIgnoreCase("setwarn")) {
							StringBuilder msg = new StringBuilder();
							for(int i = 1; i < args.length; i++) {
								msg.append(args[i]+" ");
							}
							String warning = msg.toString().replace("&", "§");
							sender.sendMessage(ConfigStuff.prefix + " you've set Warning message to: " + warning);
							ConfigStuff.SetWaringMessage(warning);
						}
						
						if(args.length ==2) {
							if(args[0].equalsIgnoreCase("warnmode")) {
								
								if(args[1].equalsIgnoreCase("on")) {
									ConfigStuff.Set_warning_mode(true);
									sender.sendMessage(ConfigStuff.prefix + " Warning mode is now on ! ");
								} 
								if(args[1].equalsIgnoreCase("off")) {
									ConfigStuff.Set_warning_mode(false);
									sender.sendMessage(ConfigStuff.prefix + " Warning mode is now off ! ");
								} 
								
							}
						}
						
						
						
					// change the censor mode 
						if(args[0].equalsIgnoreCase("censor")) {
							
							if(args.length >= 2) {
								if(args[1].equalsIgnoreCase("on")) {
									ConfigStuff.Set_censor_mode(true);
									sender.sendMessage(ConfigStuff.prefix + " Censor mode is now on ! ");
								}
								if(args[1].equalsIgnoreCase("off")) {
									ConfigStuff.Set_censor_mode(false);
									sender.sendMessage(ConfigStuff.prefix + " Censor mode is now off ! ");
								}
							} else {
								sender.sendMessage(ConfigStuff.prefix + "/automod censor [on/off] ");
							}
						}
						
						
						
						
						
						
						
					// Hard Censor Mode !
						if(args[0].equalsIgnoreCase("hardcensor")) {
							
							if(args.length >= 2) {
								if(args[1].equalsIgnoreCase("on")) {
									ConfigStuff.Set_hard_censor_mode(true);
									sender.sendMessage(ConfigStuff.prefix + "Hard Censor mode is now on ! ");
								}
								if(args[1].equalsIgnoreCase("off")) {
									ConfigStuff.Set_hard_censor_mode(false);
									sender.sendMessage(ConfigStuff.prefix + "Hard Censor mode is now off ! ");
								}
							} else {
								sender.sendMessage(ConfigStuff.prefix + "/automod hardcensor [on/off] ");
							}
						}
						
						
						
						
						
						
				// manage the troll mode
						if(args[0].equalsIgnoreCase("trolls")) {
							
							if(args.length >= 2) {
								
				// add a troll sentence
								if(args[1].equalsIgnoreCase("add")) {
									if(args.length > 3) {
										String str ="";
										StringBuilder b = new StringBuilder();
										for(int i=2; i< args.length; i++) {
											b.append(args[i]+ " ");
										}
										str = b.toString().replace("&", "§");
										ConfigStuff.addTroll(str);
										sender.sendMessage(ConfigStuff.prefix + " " + str + " §7|| &5 was successfully added to troll list ! ");
									}
								}
				// delete a troll by index
								if(args[1].equalsIgnoreCase("del")) {
									for(int i=2; i< args.length; i++) {
										sender.sendMessage(ConfigStuff.prefix + " " +ConfigStuff.getTroll(i) + " §7|| &5 was successfully deleted");
										ConfigStuff.delTroll(i);
									}
								}
				// list all troll sentences
								if(args[1].equalsIgnoreCase("list")) {
									List<String> Trolls = ConfigStuff.getTrolls();
									sender.sendMessage(ConfigStuff.prefix +" active Trolls are: \n ");
									for(int i=0; i< Trolls.size(); i++) {
										sender.sendMessage(ConfigStuff.prefix +"  " + i + " - " + Trolls.get(i));
									}
									Utils.SendInterractiveMessage((Player)sender, "\n"+ConfigStuff.prefix, 
											"want to go to the main menu?", "Click Here to return to the Automod Menu", "run_command" ,"/automod");
								
								}
				// turn on or off troll mode
								if(args[1].equalsIgnoreCase("on")) {
									ConfigStuff.Set_troll_mode(true);
									sender.sendMessage(ConfigStuff.prefix + " troll mode is now on ! ");
								}
								if(args[1].equalsIgnoreCase("off")) {
									ConfigStuff.Set_troll_mode(false);
									sender.sendMessage(ConfigStuff.prefix + " troll mode is now off ! ");
								}
							} else {
								sender.sendMessage(ConfigStuff.prefix + "/automod troll [on/off] ");
							}
						}
						
						
						
						
						
						
						
						
						
						
						
						
						
				// Add word to censor list
						if(args[0].equalsIgnoreCase("addwords")) {
							for (int i = 1; i< args.length; i++) {
								if (!ConfigStuff.isForbidden(args[i])){
									ConfigStuff.AddForbidden(args[i]);
								}
							}
							Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
									"Words added ! want to go to the main menu?", "Click Here to return to the Automod Menu", "run_command" ,"/automod");
						}
						
				// Remove word from list
						if(args[0].equalsIgnoreCase("delwords")) {
							for (int i = 1; i< args.length; i++) {
								if (ConfigStuff.isForbidden(args[i])){
									ConfigStuff.DelForbidden(args[i]);
								}
							}
							Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
									"Words Removed ! Do you want to go back ?", "Click Here to return to command list", "run_command" ,"/automod");
						}
						
				// List forbidden words
						if(args[0].equalsIgnoreCase("words")) {
							StringBuilder msg = new StringBuilder();
							msg.append(ConfigStuff.prefix+ " Forbidden words are: \n\n ");
							for (int i = 0; i< ConfigStuff.forbidden.size()-1 ; i++) {
								msg.append(ConfigStuff.forbidden.get(i) + ", ");
							}
							msg.append(ConfigStuff.forbidden.get(ConfigStuff.forbidden.size()-1) + ".");
							sender.sendMessage(msg.toString());
							Utils.SendInterractiveMessage((Player)sender, "\n" +ConfigStuff.prefix, 
									"Do you want to go back ?", "Click Here to return to command list", "run_command" ,"/automod");
						}
						
						
						
						
						
						
						
						
				// DEL MSG
						if(args.length >=2) {
							if(args[0].equalsIgnoreCase("del")) {
								ConfigStuff.delMsgs(args[1]);
								sender.sendMessage(ConfigStuff.prefix + " No sanction will Be applied !");
								if(args.length==2) {
									Bukkit.dispatchCommand(sender, "automod msgs");
								} else if(args.length >=2) {
									
									if (args[1].equalsIgnoreCase("tchat")) {
										// Moderated From live tchat, Do something
									}
									
									
								}
								
								
							}
						}
						
						
				// List Sanctions
						if(args[0].equalsIgnoreCase("sanctions")) {
							if(args.length <= 1) {
								sender.sendMessage(ConfigStuff.prefix+ " Sanctions are : \n");
								
								
								for(String sanction : ConfigStuff.GetSanctions().getKeys(false)) {
									sender.sendMessage(ConfigStuff.prefix+" " + sanction + " - '" + ConfigStuff.GetSanctions().getString(""+sanction+".name") + "§f' - " + ConfigStuff.GetSanctions().getString(""+sanction+".command")  );
								}
								Utils.SendInterractiveMessage((Player)sender, "\n" +ConfigStuff.prefix, 
										"Do you want to go back ?", "Click Here to return to command list", "run_command" ,"/automod");
								
							} else if (args.length <= 2 ) {
			 // Use tips, missing argument
								if(args[1].equalsIgnoreCase("add")) {
									Utils.SendInterractiveMessage((Player)sender, "\n" +ConfigStuff.prefix, 
											"/automod sanctions add {configname}/ §3Sanction display name §f/yourcommand %player% ", "Click Here to return to retry", "suggest_command" ,"/automod sanctions add ");
								}
								if(args[1].equalsIgnoreCase("del")) {
									Utils.SendInterractiveMessage((Player)sender, "\n" +ConfigStuff.prefix, 
											"/automod sanctions del {name} ", "Click Here to return to retry", "suggest_command" ,"/automod sanctions add ");
								
								}
							}
							
							
							if (args.length >= 3 ) {
			// Delete a sanction
								if(args[1].equalsIgnoreCase("del")) {
									Boolean success =ConfigStuff.delSanction(args[2]);
									sender.sendMessage(args[2]);
									if(success) {
										sender.sendMessage(ConfigStuff.prefix + " You deleted sanction named " + args[2]);
									} else {
										sender.sendMessage(ConfigStuff.prefix + " You failed to delete sanction named " + args[2] + " §fDoes it exist?");
										
										Utils.SendInterractiveMessage((Player)sender, "\n" +ConfigStuff.prefix, 
												"/automod sanctions del {name} ", "Click Here to return to retry", "suggest_command" ,"/automod sanctions add ");
									
									}
								}
							}
							if (args.length >=4) {
			// Add a sanction
								if(args[1].equalsIgnoreCase("add")) {
									StringBuilder arguments = new StringBuilder();
									for(String string : args) {
										arguments.append(string + " ");
									}
									String configname = arguments.toString().split("/")[0].replace("sanctions add ", "").replace(" ", "");
									String name = arguments.toString().split("/")[1].replace("&", "§");
									String cmd = arguments.toString().split("/")[2];
									ConfigStuff.addSanction(configname, name, cmd);
									sender.sendMessage(ConfigStuff.prefix + " You created sanction named " + name + "§f with effect : " + cmd.replace("%player%", "{The mean player}"));
									
									
								}
								
							}
						}
						
		
						
						
		// Sanction a Player
						if(args[0].equalsIgnoreCase("sanction")) {
							if(args.length >=3) {
								
							// if there is the sanction number
								if(StringUtils.isNumeric(args[2])) {
									String cmd = ConfigStuff.sanctions.values().toArray()[Integer.parseInt(args[2])].toString().replace("%player%", 
											args[1]).replace("%mod%", ((Player)sender).getName());
									
									
									ConfigStuff.delMsgs(current_player_messages);
								
									
									Bukkit.dispatchCommand(sender, cmd.replace("/", ""));
									current_player_messages = "";
									
								// 3 argument is for when seeing old messages, else "tchat" argument is added
									if(args.length==3) {
										Bukkit.dispatchCommand(sender, "automod msgs");
									} else if (args[3].equalsIgnoreCase("tchat")) {
										
										// Moderated From live tchat, Do something
									}
		
									
									
								}
								
							}
							
						}
			// End Mod Session, so that other mods can see saved messages
						if(args[0].equalsIgnoreCase("nomod")) {
							CurrentMod = null;
							sender.sendMessage(ConfigStuff.prefix + " You successfully ended your mod session !");
							Bukkit.dispatchCommand(sender, "automod");
						}
						
						
						
						
			// Saved Messages Display
						
						if(args[0].equalsIgnoreCase("msgs")) {
							
							if(!ConfigStuff.get_msgs().isEmpty()) {
								
								// Only one mod at a time is allowed here, to prevent two mod taking action
								// on the same message at the same time
								// 
								// If nobody take a look at msg, or if the current mod is the sender
								if(CurrentMod == null || CurrentMod.equals(((Player) sender).getUniqueId())) {
									
									CurrentMod = ((Player)sender).getUniqueId();
									
									
									String name = "" + ConfigStuff.get_msgs().keySet().toArray()[0];
									current_player_messages = name;
									List<String> messages = ConfigStuff.get_msgs().get(name); 
									
									sender.sendMessage("§8===============================================\n\n");
									sender.sendMessage(ConfigStuff.prefix + "Player "+ name + " sent : ");
									
								// list messages
									for(String message : messages) {
										for(String ForbiddenWord : ConfigStuff.forbidden) {
											message = message.replaceAll(ForbiddenWord, "§4"+ForbiddenWord + "§f");	
										}
										sender.sendMessage(ConfigStuff.prefix +" - " + message.split(" : ", 2)[0]+ " \" " + message.split(" : ", 2)[1]+ " \"");
										
									}
								// put sanction buttons
									Utils.SendInterractiveMessage((Player)sender, "\n"+ConfigStuff.prefix, 
											"No sanction", "Apply no sanctions and delete entry", "run_command" ,"/automod del "+ name);
									
									for(int i=0; i < ConfigStuff.sanctions.size(); i++) {
										
										Utils.SendInterractiveMessage((Player)sender, ConfigStuff.prefix, 
												ConfigStuff.sanctions.keySet().toArray()[i].toString().replace("%player%", name).replace("&", "§").replace("%mod%", ((Player)sender).getName() ) , "", "run_command" , "/automod sanction "+name +" "+i);			
										
									}
									Utils.SendInterractiveMessage((Player)sender, "\n" + ConfigStuff.prefix, 
											" §8>>> §5Stop Moderating §8<<<", "Click Here to return to Menu", "run_command" ,"/automod nomod");
									sender.sendMessage("§8===============================================\n\n");
									
								} else {
							// another mod is viewing saved messages
									sender.sendMessage(ConfigStuff.prefix + " " + Bukkit.getPlayer(CurrentMod).getName() + " is already taking a look at messages");
									
								}
								
							// il n'y a plus rien a voir ...
							} else {
								sender.sendMessage(ConfigStuff.prefix + " Nothing left to see");
								CurrentMod = null;
								Bukkit.dispatchCommand(sender, "automod");
							}
						}
					
					}
				}

				
			}
		
		
        return false;
    }
}
