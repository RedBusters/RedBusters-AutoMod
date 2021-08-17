package RedBusters;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public class ConfigStuff {
	
	static ArrayList<String> forbidden = new ArrayList<String>();
	static Boolean CensorMode;
	static FileConfiguration cfg;
	static FileConfiguration msgs;
	static String prefix;
	static File messages_file;
	static File config_file;
	static Main main;
	static Boolean TrollMode;
	static List<String> Trolls;
	static HashMap<String, String> sanctions = new HashMap<String, String>();
	static Boolean hardCensorMode;
	static String Warning;
	static Boolean Warning_mode;
	
// called once onload
	static public void LoadConfig(Main MainRef) {
		
		
	// If no folder, we create it !
		main = MainRef;
		if(!main.getDataFolder().exists()) main.getDataFolder().mkdir();
		
	//Now our 2 files
		config_file = new File(main.getDataFolder(), "AutoMod.yml");
		if(!config_file.exists()) {
			try {
				config_file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cfg = YamlConfiguration.loadConfiguration(config_file);
		
		
		messages_file = new File(main.getDataFolder(), "messages.yml");
		if(!messages_file.exists()) {
			try {
				messages_file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		msgs = YamlConfiguration.loadConfiguration(messages_file);
		
		
		
		
	// Set some default settings
		if(!cfg.isSet("Censored_words")) {
			AddForbidden("nigger");
			AddForbidden("bitch");
			AddForbidden("asshole");
			AddForbidden("dick");
			save(config_file);		
		} 
		
		if(!cfg.isSet("Censor")) {
			cfg.set("Censor", false);
			CensorMode = false;
			save(config_file);		
		} 
		if(!cfg.isSet("hardcensor")) {
			cfg.set("hardcensor", false);
			hardCensorMode = false;
			save(config_file);		
		} 
		
		if(!cfg.isSet("trollmode")) {
			cfg.set("trollmode", false);
			CensorMode = false;
			save(config_file);		
		} 
		
		if(!cfg.isSet("trolls")) {
			ArrayList<String> trolls = new ArrayList<String>();
			trolls.add("i love Unicorns");
			trolls.add("i wear crocs");
			cfg.set("trolls", trolls);
			CensorMode = false;
			save(config_file);		
		} 
		
		if(!cfg.isSet("prefix")) {
			cfg.set("prefix", "§4RAM §C>> §f");
			save(config_file);
		} 
		if(!cfg.isSet("WarningMode")) {
			cfg.set("WarningMode", false);
			Warning_mode = false;
			save(config_file);		
		} 
		if(!cfg.isSet("sanctions")) {
			addSanction("ban", "§4ban %player%", "/ban %player%");		
		} 
		if(!cfg.isSet("WarningMsg")) {
			cfg.set("WarningMsg", "§4 Behave in the tchat ! your message has been reported to mods! ");
			save(config_file);	
			Warning = cfg.getString("WarningMsg");
		} 
		
		
		
	// Getting Config settings
		
		Trolls = cfg.getStringList("trolls");
		TrollMode = cfg.getBoolean("trollmode");
		CensorMode = cfg.getBoolean("Censor");
		prefix = cfg.getString("prefix");
		hardCensorMode = cfg.getBoolean("hardcensor");
		Warning_mode = cfg.getBoolean("WarningMode");
		Warning = cfg.getString("WarningMsg");
		
	// Adding sanctions to the list !
		if(cfg.isSet("sanctions")) {
			for( String sanction : cfg.getConfigurationSection("sanctions").getKeys(false)) {
				sanctions.put(cfg.getString("sanctions."+sanction+ ".name").toString(), cfg.getString("sanctions."+sanction+ ".command").toString());
			}
		}
		

	// adding our Forbidden Words
		if(cfg.isSet("Censored_words")) {
			forbidden.addAll(cfg.getStringList("Censored_words"));
		}
		
	}
	
	
	
	static public void SetWaringMessage(String msg) {
		Warning = msg.replace("&", "§");
		cfg.set("WarningMsg", msg.replace("&", "§"));
		save(config_file);
	}
	
	
	
	
// Sanctions 	
	public static ConfigurationSection GetSanctions(){
		return cfg.getConfigurationSection("sanctions");
	}
	
// deletes a sanction from config
	public static Boolean delSanction(String config) {
		if (cfg.isSet("sanctions."+config)){
			sanctions.remove(cfg.getString("sanctions."+config));
			cfg.set("sanctions."+config, null);
			
			save(config_file);
			return true;
		} else {
			return false;
		}
	}
// adds a sanction to config
	public static Boolean addSanction(String config_name, String displayname, String command) {
		
		if (cfg.isSet("sanctions."+config_name)) return false;
		
		cfg.set("sanctions."+config_name+".name", displayname);
		cfg.set("sanctions."+config_name+".command", command);
		sanctions.put(displayname, command);
		save(config_file);
		return true;
	}

	
	
	
	
	
// get troll list
	public static List<String> getTrolls(){
		return Trolls;
	}
// add a troll to our files
	public static void addTroll(String troll) {
		if(!Trolls.contains(troll)) {
			Trolls.add(troll);
			cfg.set("trolls", Trolls);
			save(config_file);
		}
	}
// Delete a troll
	public static void delTroll(int index) {
		if(Trolls.size() > index) {
			Trolls.remove(index);
			cfg.set("trolls", Trolls);
			save(config_file);
		}
	}
// return a troll from index
	public static String getTroll(int index) {
		if(Trolls.size() > index) {
			return Trolls.get(index);
		} else {
			return "";
		}
	}
	
	
	
	
	
	
	
	
	
	
	
// refreshes the config, on case of a direct modification of the file
	static public void RefreshConfig() {
		forbidden.clear();
		forbidden.addAll(cfg.getStringList("Censored_words"));
		CensorMode = cfg.getBoolean("Censor");
		prefix = cfg.getString("prefix");
		
	}
	
// add a word to the forbidden list
	static public void AddForbidden(String word) {
		forbidden.add(word);
		cfg.set("Censored_words", forbidden);
		save(config_file);
	}
		
// remove a word from forbidden list
	public static Boolean DelForbidden(String word) {
		
	// if the word is indeed forbidden, remove it, and send true, as deletion was successfull
		if (forbidden.contains(word)) {
			forbidden.remove(word);
			cfg.set("Censored_words", forbidden);
			save(config_file);
			return true;
		} else {
			
			return false;
		}

	}
	
// checks if a word is Forbidden
	static public Boolean isForbidden(String word) {
		
		if (forbidden.contains(word)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
// MODES

// Set censor mode on or off - replaces forbidden words by *
	static public void Set_censor_mode(Boolean bool) {
		CensorMode = bool;
		if (bool) {
			cfg.set("Censor", true);
		} else {
			cfg.set("Censor", false);
		}
		save(config_file);
		
		
	}
// Set hard censor mode on or off - Does Not send any forbidden word messages, mods get alert 
	static public void Set_hard_censor_mode(Boolean bool) {
		hardCensorMode = bool;
		if (bool) {
			cfg.set("hardcensor", true);
		} else {
			cfg.set("hardcensor", false);
		}
		save(config_file);
		
	}
// set if player should get a warning if he says something wrong
	static public void Set_warning_mode(Boolean bool) {
		Warning_mode = bool;
		if (bool) {
			cfg.set("WarningMode", true);
		} else {
			cfg.set("WarningMode", false);
		}
		save(config_file);
		
	}
// Replace the Whole messages by a fun sentence, like in overwatch
	static public void Set_troll_mode(Boolean bool) {
		TrollMode = bool;
		if (bool) {
			cfg.set("trollmode", true);
		} else {
			cfg.set("trollmode", false);
		}
		save(config_file);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// return a random troll
	public static String PickATroll() {
		Random random = new Random();
		
		return Trolls.get( random.nextInt(Trolls.size()) );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
// Add Message to file 
	static public void add_message(Player sender, String msg) {
		
	   DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
	   LocalDateTime now = LocalDateTime.now();  
	   String ToSave = dtf.format(now) + " : " + msg.replace("\"", "`").replace("'", "`") ;
	   ArrayList<String> UserMessages = new ArrayList<String>();
	   UserMessages.addAll(msgs.getStringList(sender.getName()));
	   UserMessages.add(ToSave);
	   msgs.set(sender.getName(), UserMessages);
	   save(messages_file);
	   
	}
	
// Get Censored Massages From File
	static public HashMap<String, List<String> > get_msgs(){
		
		HashMap<String, List<String>> messages = new HashMap<String, List<String>>();
		
		for(String key : msgs.getConfigurationSection("").getKeys(false)) {
			messages.put(key, msgs.getStringList(key));
		}
		return messages;
		
	}

// Remove a player's sencored messages from file
	@SuppressWarnings("deprecation")
	public static void delMsgs(String name) {
		if(name !="" && name != null) {
			if(Bukkit.getPlayer(name) != null || Bukkit.getOfflinePlayer(name) != null) {
				if(name !="") {
					msgs.set(name, null);
					save(messages_file);
				}
				
			}
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
		
// just save config file, to have less try/catch everywhere
	private static void save(File file){
		
		try {
			if(file == messages_file) { 
				msgs.save(file);
			
			} else if (file == config_file){
				cfg.save(config_file);
			}
			main.saveConfig();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
