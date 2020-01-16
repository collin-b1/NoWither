package io.github.slaggo;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class NoWither extends JavaPlugin implements Listener {

	FileConfiguration config = getConfig();

	@Override
	public void onEnable() {
		getConfigFile();
		
		getServer().getPluginManager().registerEvents(this, this);
	}
	
	public void getConfigFile() {
        config.options().copyDefaults(true);
        saveConfig();
	}
	
	public void reloadConfigFile() {
		getServer().getPluginManager().getPlugin("NoWither").reloadConfig();
	}
	
	@EventHandler
	public void onWitherSpawn (EntitySpawnEvent e) {
		if (e.getEntity().getType() == EntityType.WITHER) {
			if (!checkPos(e.getLocation().getX(), e.getLocation().getZ()) || !config.getBoolean("witherSpawn")) {
				e.setCancelled(true);
			}
		}
	}
	
	public boolean checkPos(double x, double z) {
		if (config.getInt("fromX") <= x && config.getInt("toX") >= x && config.getInt("fromZ") <= z && config.getInt("toZ") >= z) {
			return false; // Wither Spawn attempt IS within the disallowed area
		} else {
			return true;  // Wither Spawn attempt IS NOT within the disallowed area
		}
	}
}
