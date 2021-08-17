package RedBusters;


import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;


import net.minecraft.server.v1_12_R1.IChatBaseComponent;
import net.minecraft.server.v1_12_R1.PacketPlayOutChat;
import net.minecraft.server.v1_12_R1.IChatBaseComponent.ChatSerializer;

public class Utils {
	
// Send a single-Button Message
	public static void SendInterractiveMessage(Player p,  String msg, String CmdMsg, String HoverMsg, String Action, String Command) {
		
		String json = "[\"\",\"msg\",{\"text\":\"CmdMsg\",\"clickEvent\":{\"action\":\"Action\",\"value\":\"Command\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"HoverMsg\"]}}]";
		if(HoverMsg == "") {
			json = json.replace(",\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"HoverMsg\"]}", "");
		}
		json = json.replace("msg", msg);
		json = json.replace("CmdMsg", CmdMsg);
		json = json.replace("HoverMsg", HoverMsg);
		json = json.replace("Action", Action);
		json = json.replace("Command", Command);
		json = json.replace("&", "§");
		
		 IChatBaseComponent component = ChatSerializer.a(json);
		 PacketPlayOutChat packet = new PacketPlayOutChat(component);
		 
		 ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
			 
		 
		 
		
	}
	
// Send a Double Button message
	public static void choose(Player p, String msg, String CmdMsg, String HoverMsg,String Action1, String Command, String msg2, String CmdMsg2, String HoverMsg2, String Action2, String Command2) {
		String json = "[\"\",\"msg1   \",{\"text\":\"CmdMsg1  \",\"clickEvent\":{\"action\":\"Action1\",\"value\":\"Command1\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"HoverMsg1\"]}},{\"text\":\"  ||  \",\"color\":\"dark_gray\"},{\"text\":\" CmdMsg2\",\"clickEvent\":{\"action\":\"Action2\",\"value\":\"Command2\"},\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"HoverMsg2\"]}}]";
		
		if(HoverMsg == "") {
			json = json.replace(",\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"HoverMsg1\"]}", "");
		}

		if(HoverMsg2 == "") {
			json = json.replace(",\"hoverEvent\":{\"action\":\"show_text\",\"value\":[\"HoverMsg2\"]}", "");
		}
		
		json = json.replace("msg1", msg);
		json = json.replace("CmdMsg1", CmdMsg);
		json = json.replace("HoverMsg1", HoverMsg);
		json = json.replace("Action1", Action1);
		json = json.replace("Command1", Command);
		json = json.replace("msg2", msg2);
		json = json.replace("CmdMsg2", CmdMsg2);
		json = json.replace("HoverMsg2", HoverMsg2);
		json = json.replace("Action2", Action2);
		json = json.replace("Command2", Command2);
		json = json.replace("&", "§");
		if( Action1 == "suggest_command" || Action2 == "suggest_command") {
			
		} else {
			json = json.replace("%player%", p.getName());
		}
			
		IChatBaseComponent component = ChatSerializer.a(json);
		 PacketPlayOutChat packet = new PacketPlayOutChat(component);
		 
		 ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
	}
}
