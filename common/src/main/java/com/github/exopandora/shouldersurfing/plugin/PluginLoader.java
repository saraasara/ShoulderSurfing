package com.github.exopandora.shouldersurfing.plugin;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ServiceLoader;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.github.exopandora.shouldersurfing.ShoulderSurfing;
import com.github.exopandora.shouldersurfing.api.IShoulderSurfingPlugin;

public abstract class PluginLoader
{
	private static final PluginLoader INSTANCE = ServiceLoader.load(PluginLoader.class).findFirst().orElseThrow();
	private static final String ENTRYPOINT_KEY = "entrypoint";
	protected static final String PLUGIN_JSON_PATH = "shouldersurfing_plugin.json";
	
	public abstract void loadPlugins();
	
	protected void loadPlugin(String modid, Path path)
	{
		try(Reader reader = Files.newBufferedReader(path))
		{
			JsonObject configuration = JsonParser.parseReader(reader).getAsJsonObject();
			
			if(configuration.has(ENTRYPOINT_KEY))
			{
				String entrypoint = configuration.get(ENTRYPOINT_KEY).getAsString();
				IShoulderSurfingPlugin plugin = (IShoulderSurfingPlugin) Class.forName(entrypoint).getConstructor().newInstance();
				plugin.register(ShoulderSurfingRegistrar.getInstance());
				ShoulderSurfing.LOGGER.info("Registered plugin " + modid);
			}
			else
			{
				ShoulderSurfing.LOGGER.error("Plugin for " + modid + " does not contain an entrypoint");
			}
		}
		catch(Exception e)
		{
			ShoulderSurfing.LOGGER.error("Failed to register plugin " + modid, e);
		}
	}
	
	public static PluginLoader getInstance()
	{
		return INSTANCE;
	}
}
