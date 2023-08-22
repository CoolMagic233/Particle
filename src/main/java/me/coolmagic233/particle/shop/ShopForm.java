package me.coolmagic233.particle.shop;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.plugin.Plugin;
import me.coolmagic233.particle.Main;
import me.coolmagic233.particle.ParticleData;

import java.util.ArrayList;

public class ShopForm implements Listener {
    private ShopConfig shopConfig;
    private Main plugin;
    public static final int SHOP_MAIN = 202371124;
    public static final int SHOP_ENABLE = 202371139;
    public static final int SHOP_BUY = 2023711232;
    public static final int SHOP_ENABLE_WALK = 202371125;
    public static final int SHOP_ENABLE_BEAT = 202371127;
    public static final int SHOP_ENABLE_DEATH = 202371128;
    public static final int SHOP_ENABLE_ARROW = 202371129;
    public static final int SHOP_BUY_WALK = 202371126;
    public static final int SHOP_BUY_ARROW = 202371130;
    public static final int SHOP_BUY_DEATH = 202371131;
    public static final int SHOP_BUY_BEAT = 202371133;

    public ShopForm(ShopConfig shopConfig, Main plugin) {
        this.shopConfig = shopConfig;
        this.plugin = plugin;
    }

    @EventHandler
    public void onForm(PlayerFormRespondedEvent e){
        Player player = e.getPlayer();
        FormResponseSimple form = (FormResponseSimple) e.getResponse();
        ParticleData pd = plugin.getParticleData(player);
        if (form != null) {
            switch (e.getFormID()) {
                case SHOP_MAIN:
                    if (form.getClickedButtonId() == 0) {
                        FormWindowSimple simple_enable = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                        simple_enable.addButton(new ElementButton("行走特效"));
                        simple_enable.addButton(new ElementButton("死亡特效"));
                        simple_enable.addButton(new ElementButton("箭矢轨道特效"));
                        simple_enable.addButton(new ElementButton("击杀特效"));
                        player.showFormWindow(simple_enable, SHOP_ENABLE);
                    }
                    if (form.getClickedButtonId() == 1) {
                        FormWindowSimple simple_buy = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                        simple_buy.addButton(new ElementButton("死亡特效"));
                        simple_buy.addButton(new ElementButton("行走特效"));
                        simple_buy.addButton(new ElementButton("箭矢轨道特效"));
                        simple_buy.addButton(new ElementButton("击杀特效"));
                        player.showFormWindow(simple_buy, SHOP_BUY);
                    }
                    break;
                case SHOP_BUY:
                    switch (form.getClickedButtonId()) {
                        case 0:
                            FormWindowSimple simple_buy_death = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (ShopItem value : shopConfig.getItems_death().values()) {
                                simple_buy_death.addButton(new ElementButton(value.getDisplay()));
                            }
                            player.showFormWindow(simple_buy_death, SHOP_BUY_DEATH);
                            break;
                        case 1:
                            FormWindowSimple simple_buy_walk = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (ShopItem value : shopConfig.getItems_walk().values()) {
                                simple_buy_walk.addButton(new ElementButton(value.getDisplay()));
                            }
                            player.showFormWindow(simple_buy_walk, SHOP_BUY_WALK);
                            break;
                        case 2:
                            FormWindowSimple simple_buy_arrow = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (ShopItem value : shopConfig.getItems_arrow().values()) {
                                simple_buy_arrow.addButton(new ElementButton(value.getDisplay()));
                            }
                            player.showFormWindow(simple_buy_arrow, SHOP_BUY_ARROW);
                            break;
                        case 3:
                            FormWindowSimple simple_buy_beat = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (ShopItem value : shopConfig.getItems_beat().values()) {
                                simple_buy_beat.addButton(new ElementButton(value.getDisplay()));
                            }
                            player.showFormWindow(simple_buy_beat, SHOP_BUY_BEAT);
                            break;
                        default:
                            break;
                    }
                    break;
                case SHOP_ENABLE:
                    switch (form.getClickedButtonId()) {
                        case 1:
                            FormWindowSimple simple_enable_death = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (String value : plugin.getParticleData(player).getDeath()) {
                                simple_enable_death.addButton(new ElementButton(value));
                            }
                            player.showFormWindow(simple_enable_death, SHOP_ENABLE_DEATH);
                            break;
                        case 0:
                            FormWindowSimple simple_enable_walk = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (String value : plugin.getParticleData(player).getWalk()) {
                                simple_enable_walk.addButton(new ElementButton(value));
                            }
                            player.showFormWindow(simple_enable_walk, SHOP_ENABLE_WALK);
                            break;
                        case 2:
                            FormWindowSimple simple_enable_arrow = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (String value : plugin.getParticleData(player).getArrow()) {
                                simple_enable_arrow.addButton(new ElementButton(value));
                            }
                            player.showFormWindow(simple_enable_arrow, SHOP_ENABLE_ARROW);
                            break;
                        case 3:
                            FormWindowSimple simple_enable_beat = new FormWindowSimple(Main.getInstance().getDescription().getName(), "");
                            for (String value : plugin.getParticleData(player).getBeat()) {
                                simple_enable_beat.addButton(new ElementButton(value));
                            }
                            player.showFormWindow(simple_enable_beat, SHOP_ENABLE_BEAT);
                            break;
                        default:
                            break;
                    }
                    break;
                case SHOP_BUY_DEATH:
                    plugin.getShop().buy(player, form.getClickedButton().getText(), SHOP_BUY_DEATH);
                    break;
                case SHOP_BUY_WALK:
                    plugin.getShop().buy(player, form.getClickedButton().getText(), SHOP_BUY_WALK);
                    break;
                case SHOP_BUY_ARROW:
                    plugin.getShop().buy(player, form.getClickedButton().getText(), SHOP_BUY_ARROW);
                    break;
                case SHOP_BUY_BEAT:
                    plugin.getShop().buy(player, form.getClickedButton().getText(), SHOP_BUY_BEAT);
                    break;
                case SHOP_ENABLE_ARROW:
                    if (pd.getRuntime_arrow().equals("none")) {
                        plugin.setParticle(player, pd.getArrow().get(form.getClickedButtonId()), "arrow");
                        player.sendMessage(" >> 已装备粒子效果 "+ pd.getArrow().get(form.getClickedButtonId()));
                    } else {
                        plugin.setParticle(player, "none", "arrow");
                        player.sendMessage(" >> 已卸下粒子效果 "+ pd.getArrow().get(form.getClickedButtonId()));
                    }
                    break;
                case SHOP_ENABLE_BEAT:
                    if (pd.getRuntime_beat().equals("none")) {
                        plugin.setParticle(player, pd.getBeat().get(form.getClickedButtonId()), "beat");
                        player.sendMessage(" >> 已装备粒子效果 "+ pd.getBeat().get(form.getClickedButtonId()));
                    } else {
                        plugin.setParticle(player, "none", "beat");
                        player.sendMessage(" >> 已卸下粒子效果 "+ pd.getBeat().get(form.getClickedButtonId()));
                    }
                    break;
                case SHOP_ENABLE_WALK:
                    if (pd.getRuntime_walk().equals("none")) {
                        plugin.setParticle(player, pd.getWalk().get(form.getClickedButtonId()), "walk");
                        player.sendMessage(" >> 已装备粒子效果 "+ pd.getWalk().get(form.getClickedButtonId()));
                    } else {
                        plugin.setParticle(player, "none", "walk");
                        player.sendMessage(" >> 已卸下粒子效果 "+ pd.getWalk().get(form.getClickedButtonId()));
                    }
                    break;
                case SHOP_ENABLE_DEATH:
                    if (pd.getRuntime_death().equals("none")) {
                        plugin.setParticle(player, pd.getDeath().get(form.getClickedButtonId()), "death");
                        player.sendMessage(" >> 已装备粒子效果 "+ pd.getDeath().get(form.getClickedButtonId()));
                    } else {
                        plugin.setParticle(player, "none", "death");
                        player.sendMessage(" >> 已卸下粒子效果 "+ pd.getDeath().get(form.getClickedButtonId()));
                    }
                    break;

            }
        }
    }
}
