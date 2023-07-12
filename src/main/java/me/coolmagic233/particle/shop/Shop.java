package me.coolmagic233.particle.shop;

import cn.nukkit.Player;
import me.coolmagic233.datamanager.DataManager;
import me.coolmagic233.particle.Main;
import me.onebone.economyapi.EconomyAPI;

public class Shop{
    private DataManager dataManager;
    private Main plugin;
    private ShopConfig shopConfig;

    public Shop(DataManager dataManager, Main plugin, ShopConfig shopConfig) {
        this.dataManager = dataManager;
        this.plugin = plugin;
        this.shopConfig = shopConfig;
    }

    public void buy(Player player, String key , int type){
        switch (type){
            case ShopForm.SHOP_BUY_DEATH :
                for (ShopItem value : shopConfig.getItems_death().values()) {
                    if(key.equals(value.getDisplay())){
                        if(EconomyAPI.getInstance().myMoney(player) >= value.getCoins()){
                            plugin.getParticleData(player).addDeath(value.getName(),value.getCoins());
                        }else {
                            player.sendMessage("Not enough money to bug");
                        }
                        break;
                    }
                }break;
            case ShopForm.SHOP_BUY_ARROW:
                for (ShopItem value : shopConfig.getItems_arrow().values()) {
                    if(key.equals(value.getDisplay())){
                        if(EconomyAPI.getInstance().myMoney(player) >= value.getCoins()){
                            plugin.getParticleData(player).addArrow(value.getName(), value.getCoins());
                        }else {
                            player.sendMessage("Not enough money to bug");
                        }
                        break;
                    }
                }break;
            case ShopForm.SHOP_BUY_BEAT :
                for (ShopItem value : shopConfig.getItems_beat().values()) {
                    if(key.equals(value.getDisplay())){
                        if(EconomyAPI.getInstance().myMoney(player) >= value.getCoins()){
                            plugin.getParticleData(player).addBeat(value.getName(), value.getCoins());
                        }else {
                            player.sendMessage("Not enough money to bug");
                        }
                        break;
                    }
                }break;
            case ShopForm.SHOP_BUY_WALK:
                for (ShopItem value : shopConfig.getItems_walk().values()) {
                    if(key.equals(value.getDisplay())){
                        if(EconomyAPI.getInstance().myMoney(player) >= value.getCoins()){
                            plugin.getParticleData(player).addWalk(value.getName(),value.getCoins());
                        }else {
                            player.sendMessage("Not enough money to bug");
                        }
                        break;
                    }
                }break;
        }
    }
}
