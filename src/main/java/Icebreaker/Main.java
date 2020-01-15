package Icebreaker;

import Icebreaker.Commands.*;
import Icebreaker.Events.*;
import Icebreaker.Handler.DatabaseHandler;
import cn.nukkit.command.CommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

public class Main extends PluginBase {

    public Config formats;
    public Config config;
    private static Main instance;

    @Override
    public void onLoad() {
        CommandMap commandMap = getServer().getCommandMap();
        commandMap.register("Icebreaker", new ListGroupsCommand("listgroups", this));
        commandMap.register("Icebreaker", new SetFormatCommand("setformat", this));
        commandMap.register("Icebreaker", new SetDefaultCommand("setdefaultgroup", this));
        commandMap.register("Icebreaker", new SetGroupCommand("setgroup", this));
        commandMap.register("Icebreaker", new CreateGroupCommand("creategroup", this));
        commandMap.register("Icebreaker", new RemoveGroupCommand("removegroup", this));
    }

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        this.saveResource("config.yml", false);
        this.saveResource("formats.yml", false);
        formats = new Config(this.getDataFolder()+"/formats.yml", Config.YAML);
        config = new Config(this.getDataFolder()+"/config.yml", Config.YAML);
        instance = this;
    }

    public static Main getInstance() {
        return instance;
    }

}
