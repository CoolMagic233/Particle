package me.coolmagic233.particle.shop;

import cn.nukkit.plugin.Plugin;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.Map;

public class ShopConfig {
    private Config config;
    private Plugin plugin;
    private Map<String,ShopItem> items_walk = new HashMap<>();
    private Map<String,ShopItem> items_beat = new HashMap<>();
    private Map<String,ShopItem> items_death = new HashMap<>();
    private Map<String,ShopItem> items_arrow = new HashMap<>();

    public ShopConfig(Config config, Plugin plugin) {
        this.config = config;
        this.plugin = plugin;
    }

    public ShopConfig load(){
        try {
            //walk
            for (String s : config.getStringList("shop.walk")) {
                String[] strings = s.split(":");
                items_walk.put(strings[0],new ShopItem(strings[0],Integer.parseInt(strings[1]),strings[2]));
            }
            //death
            for (String s : config.getStringList("shop.death")) {
                String[] strings = s.split(":");
                items_death.put(strings[0],new ShopItem(strings[0],Integer.parseInt(strings[1]),strings[2]));
            }
            //beat
            for (String s : config.getStringList("shop.beat")) {
                String[] strings = s.split(":");
                items_beat.put(strings[0],new ShopItem(strings[0],Integer.parseInt(strings[1]),strings[2]));
            }
            //arrow
            for (String s : config.getStringList("shop.arrow")) {
                String[] strings = s.split(":");
                items_arrow.put(strings[0],new ShopItem(strings[0],Integer.parseInt(strings[1]),strings[2]));
            }
        }catch (Exception e){
            plugin.getLogger().warning("Shop items loaded failed!");
        }
        return this;
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    public Plugin getPlugin() {
        return plugin;
    }

    public void setPlugin(Plugin plugin) {
        this.plugin = plugin;
    }

    public Map<String, ShopItem> getItems_walk() {
        return items_walk;
    }

    public Map<String, ShopItem> getItems_beat() {
        return items_beat;
    }

    public Map<String, ShopItem> getItems_death() {
        return items_death;
    }

    public Map<String, ShopItem> getItems_arrow() {
        return items_arrow;
    }
}
