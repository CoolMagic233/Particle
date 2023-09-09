package me.coolmagic233.particle;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.entity.projectile.EntityArrow;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.ProjectileHitEvent;
import cn.nukkit.event.entity.ProjectileLaunchEvent;
import cn.nukkit.event.player.PlayerDeathEvent;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.AsyncTask;
import cn.nukkit.scheduler.Task;
import me.coolmagic233.datamanager.DataManager;
import me.coolmagic233.particle.particles.death.SurroundingLifting;
import me.coolmagic233.particle.shop.Shop;
import me.coolmagic233.particle.shop.ShopConfig;
import me.coolmagic233.particle.shop.ShopForm;
import org.apache.logging.log4j.core.async.ThreadNameCachingStrategy;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main extends PluginBase implements Listener {
    private static Main main;
    private boolean debug;
    private boolean EDR;
    private ShopConfig shopConfig;
    private Shop shop;
    private List<ParticleData> particles = new ArrayList<>();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        debug = getConfig().getBoolean("debug");
        EDR = getConfig().getBoolean("EDR");
        getServer().getPluginManager().registerEvents(this,this);
        getServer().getCommandMap().register("", new Command("pc","Particle Command") {
            @Override
            public boolean execute(CommandSender commandSender, String s, String[] strings) {
                if (strings.length >= 1) {
                    switch (strings[0]) {
                        //p set player type etype
                        case "set":
                            if(commandSender.isOp()){
                                if(strings.length == 4){
                                    Player player = getServer().getPlayer(strings[1]);
                                    if(player == null) return true;
                                    if(setParticle(player,strings[2],strings[3])){
                                        commandSender.sendMessage("设置粒子成功!");
                                    }else {
                                        commandSender.sendMessage("设置粒子失败");
                                    }
                                }
                            }
                            break;
                        case "menu":
                            if(commandSender.isPlayer()){
                                FormWindowSimple simple_main = new FormWindowSimple(Main.getInstance().getDescription().getName(),"");
                                simple_main.addButton(new ElementButton("装备粒子"));
                                simple_main.addButton(new ElementButton("购买粒子"));
                                ((Player) commandSender).showFormWindow(simple_main, ShopForm.SHOP_MAIN);
                            }
                            break;
                        case "add":
                            if(commandSender.isOp()){
                                Player player = getServer().getPlayer(strings[1]);
                                if (player == null){
                                    commandSender.sendMessage("添加粒子失败,请确认玩家是否在线!");
                                    return true;
                                }
                                switch (strings[3]){
                                    case "arrow":
                                        getParticleData(player).addArrow(strings[2],0);
                                        break;
                                    case "walk":
                                        getParticleData(player).addWalk(strings[2],0);
                                        break;
                                    case "beat":
                                        getParticleData(player).addBeat(strings[2],0);
                                        break;
                                    case "death":
                                        getParticleData(player).addDeath(strings[2],0);
                                        break;
                                    default: commandSender.sendMessage("不存在粒子类型 "+strings[3]);
                                }
                            }break;
                            case "del":
                            if(commandSender.isOp()){
                                Player player = getServer().getPlayer(strings[1]);
                                if (player == null){
                                    commandSender.sendMessage("移除粒子失败,请确认玩家是否在线!");
                                    return true;
                                }
                                switch (strings[3]){
                                    case "arrow":
                                        getParticleData(player).removeArrow(strings[2]);
                                        break;
                                    case "walk":
                                        getParticleData(player).removeWalk(strings[2]);
                                        break;
                                    case "beat":
                                        getParticleData(player).removeBeat(strings[2]);
                                        break;
                                    case "death":
                                        getParticleData(player).removeDeath(strings[2]);
                                        break;
                                    default: commandSender.sendMessage("不存在粒子类型 "+strings[3]);
                                }
                            }break;
                        case "help":
                            if (strings.length == 1) {
                                commandSender.sendMessage("========Particle Helps =======");
                                commandSender.sendMessage("/pc set player type etype");
                                commandSender.sendMessage("/pc add/del player type etype");
                                commandSender.sendMessage("/pc help ");
                            }
                            break;
                        default:break;
                    }
                }
                return false;
            }
        });
        //init config
        shopConfig = new ShopConfig(getConfig(),this).load();
        if(getServer().getPluginManager().getPlugin("DataManager") != null){
            shop = new Shop(DataManager.getInstance(),this,shopConfig);
        }
        getServer().getPluginManager().registerEvents(new ShopForm(shopConfig,this),this);
        //register variables
        DataManager.getInstance().registerVariable("particle_runtime");
        DataManager.getInstance().registerVariable("particle_beat");
        DataManager.getInstance().registerVariable("particle_death");
        DataManager.getInstance().registerVariable("particle_walk");
        DataManager.getInstance().registerVariable("particle_arrow");

        main = this;
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        getServer().getScheduler().scheduleDelayedTask(new Task() {
            @Override
            public void onRun(int i) {
                try{
                    List<Object> particle_runtime = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_runtime")),",");
                    ParticleData particleData = new ParticleData();
                    particleData.setPlayer(player);
                    if(particle_runtime.size() < 2) {
                        DataManager.getInstance().setData(player,"particle_runtime","walk.none,beat.none,death.none,arrow.none");
                        particle_runtime = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_runtime")),",");
                    }
                    for (Object particleRuntime : particle_runtime) {
                        String[] strings = String.valueOf(particleRuntime).split("\\.");
                        switch (strings[0]){
                            case "beat": particleData.setRuntime_beat(strings[1]);break;
                            case "walk": particleData.setRuntime_walk(strings[1]);break;
                            case "death": particleData.setRuntime_death(strings[1]);break;
                            case "arrow": particleData.setRuntime_arrow(strings[1]);break;
                            default: break;
                        }
                    }
                    List particle_beat = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_beat")),",");
                    List particle_walk = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_walk")),",");
                    List particle_death = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_death")),",");
                    List particle_arrow = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_arrow")),",");
                    for (Object beat : particle_beat) {
                        if(!particleData.getBeat().contains(String.valueOf(beat))){
                            particleData.getBeat().add(String.valueOf(beat));
                        }
                    }
                    for (Object death : particle_death) {
                        if(!particleData.getDeath().contains(String.valueOf(death))){
                            particleData.getDeath().add(String.valueOf(death));
                        }
                    }
                    for (Object walk : particle_walk) {
                        if(!particleData.getWalk().contains(String.valueOf(walk))){
                            particleData.getWalk().add(String.valueOf(walk));
                        }
                    }
                    for (Object arrow : particle_arrow) {
                        if(!particleData.getArrow().contains(String.valueOf(arrow))){
                            particleData.getArrow().add(String.valueOf(arrow));
                        }
                    }
                    if(!particles.contains(particleData)){
                        particles.add(particleData);
                    }
                }catch (Exception exception){
                    exception.printStackTrace();
                    player.sendMessage("§c粒子数据同步失败, 请联系开发组解决, 错误代码: §e" + exception.getMessage());
                    return;
                }
            }
        },40);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player player = e.getEntity();
        Server.getInstance().getScheduler().scheduleAsyncTask(this, new SurroundingLifting(getParticleData(player)));
        Player killer = (Player) e.getEntity().getKiller();
        switch (getParticleData(killer).getRuntime_beat()){
            case "lightning":
                EntityLightning lightning = new EntityLightning(e.getEntity().getChunk(), EntityLightning.getDefaultNBT(e.getEntity()));
                lightning.spawnToAll();
                break;
            default:break;
        }
    }

    @EventHandler
    public void onPre(ProjectileLaunchEvent e){
        Player player = (Player) e.getEntity().shootingEntity;
        if(player != null){
            getServer().getScheduler().scheduleRepeatingTask(new Task() {
                //缓冲箭矢,避免提前消失
                int tick = 0;
                @Override
                public void onRun(int i) {
                       try {
                           if(e.getEntity() instanceof EntityArrow){
                               e.getEntity().getLevel().addParticleEffect(e.getEntity().getLocation(),ParticleEffect.valueOf(getParticleData(player).getRuntime_arrow()));
                                   tick ++;
                                   //箭矢在碰撞的3s内被清除
                                   if (tick >= 60){
                                       this.cancel();
                                   }
                               }
                       }catch (Exception ignored){
                           this.cancel();
                       }
                    }
            },2);
        }
    }



    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Iterator<ParticleData> iterator = particles.iterator();
        while (iterator.hasNext()) {
            ParticleData particleData = (ParticleData) iterator.next();
            if(particleData.getPlayer().getName().equals(e.getPlayer().getName())){
                iterator.remove();
            }
        }
    }
    @EventHandler
    public void onMove(PlayerMoveEvent e){
        try{
            ParticleData particleData = null;
            for (ParticleData particle : particles) {
                if(particle.getPlayer().getName().equals(e.getPlayer().getName())){
                    particleData = particle;
                    break;
                }
            }
            if (particleData != null) {
                e.getPlayer().getLevel().addParticleEffect(e.getPlayer().getLocation(), ParticleEffect.valueOf(particleData.getRuntime_walk()));
            }
        }catch (Exception ignored){}
    }

    public String getLanguage(String key,String[] params){
        String message = getConfig().getString("language."+key);
        for(int i = 0; i < params.length; ++i) {
            message = message.replace("%" + (i + 1), params[i]);
        }
        return message;
    }

    public boolean getPrefixEnable(){
        return getConfig().getBoolean("language.prefix");
    }

    public static Main getInstance(){
        return main;
    }

    public long getMs() {
        return System.currentTimeMillis();
    }

    public String getDate(long temp){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(temp);
    }

    public long addMs(int time) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd");
        Date date = simpleDateFormat.parse(String.valueOf(time));
        long a = date.getTime();
        return getMs() + a;
    }

    public boolean setParticle(Player player, String particle , String type) {
        ParticleData particleData = null;
        for (ParticleData data : particles) {
            if(data.getPlayer().getName().equals(player.getName())){
                particleData = data;
                break;
            }
        }
        List<Object> particle_runtime = Utils.toList(String.valueOf(DataManager.getInstance().getData(player,"particle_runtime")),",");
        if(particle_runtime.size() < 2) {
            DataManager.getInstance().setData(player,"particle_runtime","walk.none,beat.none,death.none,arrow.none");
            return true;
        }
        HashMap<String,String> map = new HashMap<>();
        for (Object o : particle_runtime) {
            String[] ob = String.valueOf(o).split("\\.");
            map.put(ob[0],ob[1]);
            if(ob[0].equals(type)){
                map.put(ob[0],particle);
            }
        }
        if (particleData != null) {
            switch (type) {
                case "beat":
                    particleData.setRuntime_beat(particle);
                    break;
                case "walk":
                    particleData.setRuntime_walk(particle);
                    break;
                case "death":
                    particleData.setRuntime_death(particle);
                    break;
                case "arrow":
                    particleData.setRuntime_arrow(particle);
                    break;
                default:
                    break;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        if(map.containsKey(type)) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey()).append(".").append(entry.getValue()).append(",");
            }
        }
        DataManager.getInstance().setData(player,"particle_runtime",String.join(",", stringBuilder.toString().split(",")));
        return true;
    }
    public ParticleData getParticleData(Player player){
        for (ParticleData data : particles) {
            if(data.getPlayer().getName().equals(player.getName())){
                return data;
            }
        }
        return null;
    }

    public Shop getShop(){
        return shop;
    }

    public boolean EDRisEnable() {
        return EDR;
    }
}