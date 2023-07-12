package me.coolmagic233.particle;

import cn.nukkit.Player;
import cn.nukkit.scheduler.AsyncTask;
import me.coolmagic233.datamanager.DataManager;
import me.onebone.economyapi.EconomyAPI;

import javax.xml.crypto.Data;
import java.util.ArrayList;

public class ParticleData {
    private Player player;
    private String runtime_walk;
    private String runtime_beat;
    private String runtime_arrow;
    private String runtime_death;
    private ArrayList<String> walk = new ArrayList<>();
    private ArrayList<String> beat = new ArrayList<>();
    private ArrayList<String> death = new ArrayList<>();
    private ArrayList<String> arrow = new ArrayList<>();

    public ArrayList<String> getWalk() {
        return walk;
    }
    public void addWalk(String s) {
      walk.add(s);
        save();
    }
    public void addWalk(String s,double coins){
        if(getWalk().contains(s)){
            player.sendMessage("You already have this particle.");
        }else {
            EconomyAPI.getInstance().reduceMoney(player,coins);
            addWalk(s);
            player.sendMessage("Buy successful!");
        }
    }
    public void addDeath(String s) {
        death.add(s);
        save();
    }
    public void addDeath(String s,double coins){
        if(getDeath().contains(s)){
            player.sendMessage("You already have this particle.");
        }else {
            EconomyAPI.getInstance().reduceMoney(player,coins);
            addDeath(s);
            player.sendMessage("Buy successful!");
        }
    }
    public void addArrow(String s) {
        arrow.add(s);
        save();
    }
    public void addArrow(String s,double coins) {
        if(getArrow().contains(s)){
            player.sendMessage("You already have this particle.");
        }else {
            EconomyAPI.getInstance().reduceMoney(player,coins);
            addArrow(s);
            player.sendMessage("Buy successful!");
        }
    }
    public void addBeat(String s) {
        beat.add(s);
        save();
    }
    public void addBeat(String s,double coins) {
        if(getBeat().contains(s)){
            player.sendMessage("You already have this particle.");
        }else {
            EconomyAPI.getInstance().reduceMoney(player,coins);
            addBeat(s);
            player.sendMessage("Buy successful!");
        }
    }

    public void save(){
        Main.getInstance().getServer().getScheduler().scheduleAsyncTask(Main.getInstance(), new AsyncTask() {
            @Override
            public void onRun() {
                DataManager.getInstance().setData(player,"particle_beat",Utils.formList(getBeat(),","));
                DataManager.getInstance().setData(player,"particle_death",Utils.formList(getDeath(),","));
                DataManager.getInstance().setData(player,"particle_walk",Utils.formList(getWalk(),","));
                DataManager.getInstance().setData(player,"particle_arrow",Utils.formList(getArrow(),","));
            }
        });
    }

    public ArrayList<String> getBeat() {
        return beat;
    }

    public ArrayList<String> getDeath() {
        return death;
    }

    public ArrayList<String> getArrow() {
        return arrow;
    }

    public String getRuntime_walk() {
        return runtime_walk;
    }

    public void setRuntime_walk(String runtime_walk) {
        this.runtime_walk = runtime_walk;
    }

    public String getRuntime_beat() {
        return runtime_beat;
    }

    public void setRuntime_beat(String runtime_beat) {
        this.runtime_beat = runtime_beat;
    }

    public String getRuntime_arrow() {
        return runtime_arrow;
    }

    public void setRuntime_arrow(String runtime_arrow) {
        this.runtime_arrow = runtime_arrow;
    }

    public String getRuntime_death() {
        return runtime_death;
    }

    public void setRuntime_death(String runtime_death) {
        this.runtime_death = runtime_death;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
