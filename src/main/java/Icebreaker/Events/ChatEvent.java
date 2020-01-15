package Icebreaker.Events;

import Icebreaker.Handler.DatabaseHandler;
import Icebreaker.Main;
import Icebreaker.Utils.Colorize;
import Icebreaker.Utils.Placeholders;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerChatEvent;

import java.util.List;
import java.util.Map;

public class ChatEvent implements Listener {

    private Main plugin;

    public ChatEvent(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler()
    public void onChat(PlayerChatEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();
        String name = "";
        if (plugin.getServer().getPluginManager().getPlugin("AliasPro") != null) {
            try {
                name = AliasPro.AliasPro.getAlias(uuid);
            } catch (NoClassDefFoundError e) {
                name = player.getName();
            }
        } else {
            name = player.getName();
        }
        String group = DatabaseHandler.query(uuid, "uuid").get("group").toString();
        List<Map> map;
        String formats;
        if (plugin.formats.exists(group)) {
            map = plugin.formats.getMapList(group);
            formats = map.get(0).get("chat-format").toString();
        } else {
            DatabaseHandler.update(uuid, "group", plugin.config.getString("default-group"));
            map = plugin.formats.getMapList(plugin.config.getString("default-group"));
            formats = map.get(0).get("chat-format").toString();
        }
        String message = event.getMessage();
        String colorize = Colorize.Register(formats);
        String newMessage = Placeholders.Register(colorize, name, message);
        event.setFormat(newMessage);
    }
}
