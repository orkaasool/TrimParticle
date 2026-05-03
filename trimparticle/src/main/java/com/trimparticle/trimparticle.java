package com.trimparticle;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.Registry;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimPattern;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class trimparticle extends JavaPlugin {

    // Store our mappings here for fast access
    private final Map<TrimPattern, Particle> trimParticles = new HashMap<>();

    @Override
    public void onEnable() {
        // register all the particle to trim mappings
        TrimPattern silence = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("silence"));
        trimParticles.put(silence, Particle.SCULK_SOUL);

        TrimPattern flow = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("flow"));
        trimParticles.put(flow, Particle.SMALL_GUST);

        TrimPattern tide = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("tide"));
        trimParticles.put(tide, Particle.BUBBLE_POP);

        TrimPattern bolt = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("bolt"));
        trimParticles.put(bolt, Particle.ELECTRIC_SPARK);

        TrimPattern spire = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("spire"));
        trimParticles.put(spire, Particle.ENCHANT);

        TrimPattern rib = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("rib"));
        trimParticles.put(rib, Particle.SOUL_FIRE_FLAME);

        TrimPattern vex = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("vex"));
        trimParticles.put(vex, Particle.NOTE);

        TrimPattern wild = Registry.TRIM_PATTERN.get(NamespacedKey.minecraft("wild"));
        trimParticles.put(wild, Particle.INFESTED);

        // run the particle task every 10 ticks (half a second)
        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                checkTrimAndSpawnParticle(player);
            }
        }, 0L, 10L); 
        
        getLogger().info("[trimparticle] has been enabled");
    }

    private void checkTrimAndSpawnParticle(Player player) {
        // a map to count how many pieces of each target trim the player has equipped
        Map<TrimPattern, Integer> trimPieceCounts = new HashMap<>();

        // loop through all 4 armor slots and count the trims
        for (ItemStack armorPiece : player.getInventory().getArmorContents()) {
            
            // skip empty slots or items without metadata
            if (armorPiece == null || !armorPiece.hasItemMeta()) continue;
            
            if (armorPiece.getItemMeta() instanceof ArmorMeta armorMeta) {
                if (armorMeta.hasTrim()) {
                    ArmorTrim trim = armorMeta.getTrim();
                    
                    if (trim != null) {
                        TrimPattern pattern = trim.getPattern();
                        
                        // each trim has a seperate count, only trigger when reached 4
                        if (trimParticles.containsKey(pattern)) {
                            trimPieceCounts.put(pattern, trimPieceCounts.getOrDefault(pattern, 0) + 1);
                        }
                    }
                }
            }
        }

        // check the final counts to see if they have a full set
        for (Map.Entry<TrimPattern, Integer> entry : trimPieceCounts.entrySet()) {
            
            // adjust the count check to 3 if needed
            if (entry.getValue() == 4) {
                
                // grab the particle associated with this full set
                Particle particleToSpawn = trimParticles.get(entry.getKey());
                Location spawnLoc = player.getLocation().add(0, 1.0, 0); // Center on the torso
                
                player.getWorld().spawnParticle(
                    particleToSpawn, 
                    spawnLoc, 
                    3,    // Amount of particles
                    0.3,  // X spread
                    0.5,  // Y spread
                    0.3,  // Z spread
                    0.01  // Speed/Extra data
                );
                
                break; 
            }
        }
    }
}