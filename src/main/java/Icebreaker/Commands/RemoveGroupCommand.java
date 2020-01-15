package Icebreaker.Commands;

import Icebreaker.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;

public class RemoveGroupCommand extends PluginCommand {

    private Main plugin;

    public RemoveGroupCommand(String name, Main plugin) {
        super(name, plugin);
        this.plugin = plugin;
        this.description = "remove specified group from server";
        this.setAliases(new String[]{"rmgroup", "delgroup"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("icebreaker.remove") || sender.isOp()) {
                if (args.length == 1) {
                    String group = args[0].toLowerCase();
                    if (plugin.formats.exists(group)) {
                        if (!plugin.config.getString("default-group").equals(group)) {
                            removeGroup(group);
                            sender.sendMessage(TextFormat.DARK_AQUA+"Successfully removed group "+TextFormat.GOLD+group);
                        } else {
                            sender.sendMessage("You cannot delete the default group!");
                        }
                    } else {
                        sender.sendMessage("Group "+group+" doesn't exist");
                    }
                } else {
                    sender.sendMessage("Usage: /rmgroup <group>");
                }
            }
        } else {
            sender.sendMessage("Commands can only be executed from in-game!");
        }
        return true;
    }

    private void removeGroup(String group) {
        plugin.formats.remove(group);
        plugin.formats.save();
        plugin.formats.reload();
    }

}
