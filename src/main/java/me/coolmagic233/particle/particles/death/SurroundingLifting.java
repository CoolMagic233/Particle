package me.coolmagic233.particle.particles.death;

import cn.nukkit.Player;
import cn.nukkit.level.ParticleEffect;
import cn.nukkit.math.Vector3;
import cn.nukkit.scheduler.AsyncTask;
import me.coolmagic233.particle.ParticleData;
import me.coolmagic233.particle.Utils;

import java.util.LinkedList;

public class SurroundingLifting extends AsyncTask {
    private ParticleData particleData;

    public SurroundingLifting(ParticleData particleData) {
        this.particleData = particleData;
    }

    @Override
    public void onRun() {
        Player player = particleData.getPlayer();
        LinkedList<Vector3> list = Utils.getRoundEdgePoint(player.getLocation(), 1);
        double y = 0;
        for (Vector3 vector3 : list) {
            vector3.y = y+0.2;
            y = vector3.y;
            try {
                player.getLevel().addParticleEffect(vector3,ParticleEffect.valueOf(particleData.getRuntime_death()));
            }catch (Exception ignored){}
        }
    }
}
