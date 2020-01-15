package Icebreaker.Events;

import Icebreaker.Handler.DatabaseHandler;
import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Map;

public class JoinEvent implements Listener {

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String uuid = player.getUniqueId().toString();

        if (DatabaseHandler.query(uuid, "uuid") == null) {
            Map<String, Object> data = new HashMap<>();
            data.put("uuid", uuid);
            data.put("group", "guest");
            DatabaseHandler.createNew(data);
        }
    }
}
