package feCapes;

import java.net.HttpURLConnection;
import java.net.URL;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StringUtils;

public class CapeHandler
{
	/*
	 * Server depented settings
	 */
	public static boolean overrideMojang = true; 
	public static String serverURL = "http://driesgames.game-server.cc/capes/";
	
	public static void callHook(EntityPlayer player)
	{
		if (hasMojangCape(player.username))
			return;
		
		player.cloakUrl = serverURL + getCapeName(player.username);
	}
	
	/**
	 * @param username
	 * @return true if user has a mojang cape.
	 */
	public static boolean hasMojangCape(String username)
	{
		if (!overrideMojang) return true;
		try 
		{
			URL url = new URL("http://skins.minecraft.net/MinecraftCloaks/" + getCapeName(username));
			HttpURLConnection huc = (HttpURLConnection) url.openConnection();
			huc.setConnectTimeout(10000);
			huc.connect();
			return huc.getResponseCode() != 404;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}

	private static String getCapeName(String username)
	{
		return StringUtils.stripControlCodes(username) + ".png";
	}
}
