package RedBusters;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public void onEnable() {
    	
    	// Tell Server Plugin is Alive ;)
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "RedBusters's AutoMod Plugin has been enabled !");
        

        ConfigStuff.LoadConfig(this);
        
        // Registering Automod Command
        this.getCommand("automod").setExecutor(new Commands());
        
        // Registering Our Event Listener 
        getServer().getPluginManager().registerEvents(new Events(), this);

    }
}