package Icebreaker.Commands;

import Icebreaker.Main;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;

public class ListGroupsCommand extends PluginCommand {

    private Main plugin;

    public ListGroupsCommand(String name, Main plugin) {
        super(name, plugin);
        this.plugin = plugin;
        this.description = "View all groups which exist on the server";
        this.setAliases(new String[]{"groups"});
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        sender.sendMessage(plugin.formats.getKeys().toString());
        return true;
    }
}
