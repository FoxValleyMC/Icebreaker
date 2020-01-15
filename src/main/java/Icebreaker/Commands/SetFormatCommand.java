package Icebreaker.Commands;

import Icebreaker.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;

import java.util.HashMap;
import java.util.Map;

public class SetFormatCommand extends PluginCommand {

    private Main plugin;

    public SetFormatCommand(String name, Main plugin) {
        super(name, plugin);
        this.plugin = plugin;
        this.description = "Set the chat format";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("icebreaker.format") || sender.isOp()) {
                if (args.length >= 2) {
                    String group = args[0].toLowerCase();
                    if (plugin.formats.exists(group)) {
                        StringBuilder format = new StringBuilder();
                        for (int i = 1; i < args.length; i++) {
                            format.append(" ").append(args[i]);
                        }
                        setFormat(group, format.toString());
                        sender.sendMessage("Successfully set format for group " + group);
                    } else {
                        sender.sendMessage("Group "+group+" does not exist!");
                    }
                } else {
                    sender.sendMessage("Usage: /setformat <group> <format>");
                    sender.sendMessage("Get color codes by using '&' symbol");
                }
            }
        } else {
            sender.sendMessage("Commands can only be executed from in-game!");
        }
        return true;
    }

    private void setFormat(String group, String format) {
        Map<String, String> chatFormat = new HashMap<>();
        Map<String, String> tag = new HashMap<>();
        chatFormat.put("chat-format", format);
        tag.put("tag", "{player}");
        plugin.formats.set(group, new Map[]{chatFormat, tag});
        plugin.formats.save();
        plugin.formats.reload();
    }

}
