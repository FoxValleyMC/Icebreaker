package Icebreaker.Commands;

import Icebreaker.Handler.DatabaseHandler;
import Icebreaker.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.utils.TextFormat;

public class SetGroupCommand extends PluginCommand {

    private Main plugin;

    public SetGroupCommand(String name, Main plugin) {
        super(name, plugin);
        this.plugin = plugin;
        this.description = "Assigns player to specified group";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("icebreaker.set") || sender.isOp()) {
                if (args.length == 2) {
                    String name = args[0];
                    String group = args[1].toLowerCase();
                    Player player = getPlugin().getServer().getPlayer(name);
                    if (plugin.formats.exists(group)) {
                        if (player != null) {
                            if (!DatabaseHandler.query(player.getUniqueId().toString(), "uuid").get("group").equals(group)) {
                                setGroup(player, group);
                                sender.sendMessage(player.getName() + "'s group was updated successfully!");
                            } else {
                                sender.sendMessage("group "+group+" is already assigned to "+player.getName());
                            }
                        } else {
                            sender.sendMessage("That player does not appear to be online!");
                        }
                    } else {
                        sender.sendMessage("Group " + group + " does not exist!");
                    }
                } else {
                    sender.sendMessage("Usage: /setgroup <player> <group>");
                }
            }
        } else {
            sender.sendMessage("Commands can only be executed from in-game!");
        }
        return true;
    }

    private void setGroup(Player player, String group) {
        String uuid = player.getUniqueId().toString();
        DatabaseHandler.update(uuid, "group", group);
        player.sendMessage(TextFormat.DARK_AQUA+"Your group was updated to: "+TextFormat.GOLD+group);
    }

}
