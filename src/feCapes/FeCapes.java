package feCapes;

import java.util.Arrays;
import java.util.logging.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import cpw.mods.fml.common.DummyModContainer;
import cpw.mods.fml.common.LoadController;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.versioning.VersionParser;
import cpw.mods.fml.relauncher.Side;

public class FeCapes extends DummyModContainer
{
	public static final String VERSION = "@VERION@";
	public static final String CHANNEL = "FeCapes";
	
	NetworkHandler networkHandler = new NetworkHandler();
	
	public FeCapes()
	{
		super(new ModMetadata());
		ModMetadata meta = getMetadata();
		meta.modId = "FeCapes";
		meta.name = "ForgeEssentials Capes";
		meta.version = VERSION;
		meta.authorList = Arrays.asList("Dries007");
		meta.description = "A mod that gives capes based on ForgeEssentials permissions.";
		meta.url = "https://github.com/dries007/FEcapes";
		meta.dependencies = Arrays.asList(VersionParser.parseVersionReference("Forge"));
		meta.useDependencyInformation = true;
		instance = this;
	}
	
	@Override
    public boolean isNetworkMod() {return true;}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller)
	{
		bus.register(this);
		return true;
	}
	
	// Instance
	private static FeCapes instance;
	public static FeCapes instance() {return instance;}
	
	// Config
	private FeCapesConfig config;
	public static FeCapesConfig config() {return instance.config;}
	
	// Logger
	private Logger logger;
	public static Logger log() {return instance.logger;}
	
	public Side side;
	
	/*
	 * Mod methods
	 */
	
	@Subscribe
	public void preInit(FMLPreInitializationEvent e)
	{
		logger = e.getModLog();
		config = new FeCapesConfig(e.getSuggestedConfigurationFile());
		side = e.getSide();
	}
	
	@Subscribe
	public void init(FMLInitializationEvent e)
	{
		NetworkRegistry.instance().registerConnectionHandler(networkHandler);
		NetworkRegistry.instance().registerChannel(networkHandler, CHANNEL, side);
	}
}