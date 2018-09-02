package com.github.m0ttii.cpb;


import com.github.m0ttii.cpb.configuration.Configuration;
import com.github.m0ttii.cpb.listener.PostLoginListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * Created by Adrian D. on 31.08.2018.
 */
public class CPB extends Plugin {

    @Getter
    private static CPB instance;

    public void onEnable(){
        instance = this;
        if(!getDataFolder().exists())
            getDataFolder().mkdir();

        new Configuration();
        new PostLoginListener();
    }

    public net.md_5.bungee.config.Configuration getConfig() {
        return Configuration.getConfiguration().getConfig();
    }
}
