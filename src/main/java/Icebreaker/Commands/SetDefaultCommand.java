package Icebreaker.Commands;

import Icebreaker.Main;
import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;

public class SetDefaultCommand extends PluginCommand {

    private Main plugin;

    public SetDefaultCommand(String name, Main plugin) {
        super(name, plugin);
        this.plugin = plugin;
        this.description = "Sets the default group";
        this.setAliases(new String[]{"setdft"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            if (sender.hasPermission("icebreaker.default") || sender.isOp()) {
                if (args.length == 1) {
                    String group = args[0].toLowerCase();
                    if (plugin.formats.exists(group)) {
                        setDefaultGroup(group);
                        sender.sendMessage("Successfully set group " + group + " as default group!");
                    } else {
                        sender.sendMessage("Group "+group+" does not exist!");
                    }
                } else {
                    sender.sendMessage("Usage: /setdft <group>");
                }
            }
        } else {
            sender.sendMessage("Commands can only be executed from in-game!");
        }
        return true;
    }

    private void setDefaultGroup(String group) {
        plugin.config.set("default-group", group);
        plugin.config.save();
        plugin.config.reload();
    }

}
