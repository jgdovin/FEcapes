package feCapes.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLCallHook;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

public class FeCapesPlugin implements IFMLLoadingPlugin, IFMLCallHook
{
	@Override
	public String[] getLibraryRequestClass() 
	{
		return null;
	}

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] {"feCapes.asm.FeCapesTransformer"};
	}

	@Override
	public String getModContainerClass() 
	{
		return "feCapes.FeCapes";
	}

	@Override
	public String getSetupClass() 
	{
		return "feCapes.asm.FeCapesPlugin";
	}

	@Override
	public void injectData(Map<String, Object> data) 
	{
		
	}
	
	@Override
	public Void call() throws Exception 
	{
		return null;
	}
}
