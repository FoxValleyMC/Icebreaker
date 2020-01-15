package Icebreaker.Commands;

import Icebreaker.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;

import java.util.HashMap;
import java.util.Map;

public class CreateGroupCommand extends PluginCommand {

    private Main plugin;

    public CreateGroupCommand(String name, Main plugin) {
        super(name, plugin);
        this.setAliases(new String[]{"addgroup"});
        this.description = "Creates a new group";
        this.plugin = plugin;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("icebreaker.create") || sender.isOp()) {
                if (args.length == 1) {
                    String group = args[0].toLowerCase();
                    if (!plugin.formats.exists(group)) {
                        createGroup(group);
                        sender.sendMessage(TextFormat.DARK_AQUA+"Successfully created group "+TextFormat.GOLD+group);
                    } else {
                        sender.sendMessage("Group "+group+"already exists!");
                    }
                } else {
                    sender.sendMessage("Usage: /addgroup <newGroup>");
                }
            }
        } else {
            sender.sendMessage("Commands can only be executed from in-game!");
        }
        return true;
    }

    private void createGroup(String group) {
        Map<String, String> format = new HashMap<>();
        Map<String, String> tag = new HashMap<>();
        format.put("chat-format", "["+group+"]" + " <{player}> {msg}");
        tag.put("tag", "{player}");
        plugin.formats.set(group, new Map[]{format, tag});
        plugin.formats.save();
        plugin.formats.reload();
    }
}
