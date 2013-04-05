package feCapes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetworkHandler implements IConnectionHandler, IPacketHandler 
{
	/**
	 * Packets
	 */
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) 
	{
		/*
		 * Client
		 */
		try
		{
			if (packet.channel.equals(FeCapes.CHANNEL))
			{
				ByteArrayInputStream streambyte = new ByteArrayInputStream(packet.data);
				DataInputStream stream = new DataInputStream(streambyte);

				CapeHandler.overrideMojang = stream.readBoolean();
				CapeHandler.serverURL = stream.readUTF();
				
				FeCapes.log().info("Got packet from " + manager.getSocketAddress());
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Server
	 */
	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager)
	{
		ByteArrayOutputStream streambyte = new ByteArrayOutputStream();
		DataOutputStream stream = new DataOutputStream(streambyte);

		try
		{
			stream.writeBoolean(FeCapes.config().overrideMojang);
			stream.writeUTF(FeCapes.config().serverURL);
			
			stream.close();
			streambyte.close();
		}
		catch (Exception e)
		{
			FeCapes.log().warning("Error creating packet on login");
		}
		
		PacketDispatcher.sendPacketToPlayer(new Packet250CustomPayload(FeCapes.CHANNEL, streambyte.toByteArray()), (Player) player);
		
		((EntityPlayer)player).sendChatToPlayer("Packet send to you");
		FeCapes.log().info("Packet send to " + ((EntityPlayer)player).username);
	}

	/**
	 * Server
	 */
	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) 
	{
		return "";
	}

	/**
	 * Client
	 */
	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) 
	{
		
	}

	/**
	 * Client
	 */
	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) 
	{
		
	}

	/**
	 * Both
	 */
	@Override
	public void connectionClosed(INetworkManager manager) 
	{
		
	}

	/**
	 * Client
	 */
	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login)
	{
		
	}
}
