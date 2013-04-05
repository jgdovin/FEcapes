package feCapes;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class FeCapesConfig 
{
	private Configuration config;
	
	public boolean overrideMojang;
	public String serverURL;
	
	protected FeCapesConfig(File file)
	{
		FeCapes.log().info("Making config.");
		config = new Configuration(file);
		
		/*
		 * Config stuff
		 */
		String cat = "FeCapes";
		config.addCustomCategoryComment(cat, "The settings only work server side.\nThe server sends over stuff to the clients.");
		
		overrideMojang = config.get(cat, "overrideMojang", true, "Override Mojang capes.").getBoolean(true);
		serverURL = config.get(cat, "serverURL", "", "Your cape server.").getString();
		
		config.save();
	}
}
