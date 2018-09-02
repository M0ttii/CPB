package com.github.m0ttii.cpb.listener;

import com.github.m0ttii.cpb.CPB;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.json.JSONObject;

import java.io.IOException;
import java.util.UUID;

import static com.github.m0ttii.cpb.json.JsonReader.readJsonFromUrl;

/**
 * Created by Adrian D. on 31.08.2018.
 */
public class PostLoginListener implements Listener {
    Integer value;
    String denymessage;
    Boolean executecommand;
    String command;
    String intaveid;

    public PostLoginListener(){
        this.value = CPB.getInstance().getConfig().getInt("value");
        this.denymessage = CPB.getInstance().getConfig().getString("denymessage");
        this.executecommand = CPB.getInstance().getConfig().getBoolean("execute-command");
        this.command = CPB.getInstance().getConfig().getString("command");
        this.intaveid = CPB.getInstance().getConfig().getString("intave-id");

        CPB.getInstance().getProxy().getPluginManager().registerListener(CPB.getInstance(), this);

    }

    @EventHandler
    public void onJoin(PostLoginEvent event) throws IOException {
        ProxiedPlayer player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        Integer playervalue = readJsonFromUrl("https://randylife.de/api/cheatprobability/" + this.intaveid + "/" + uuid).getInt("probability_resolved_value");
        if(playervalue >= this.value){
            if(executecommand == true){
                CPB.getInstance().getProxy().getPluginManager().dispatchCommand(CPB.getInstance().getProxy().getConsole(), this.command
                        .replace("{player", player.getName())
                        .replace("{uuid}", uuid.toString())
                        .replace("{value}", playervalue.toString()));
                return;
            }
            event.getPlayer().disconnect(this.denymessage
                    .replace("{player", player.getName())
                    .replace("{uuid}", uuid.toString())
                    .replace("{value}", playervalue.toString()));
        }
    }
}
