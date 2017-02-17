package com.walrusone.skywars.nms.v1_9_R2;

import java.util.Iterator;
import java.util.List;
import net.minecraft.server.v1_9_R2.EntityHuman;
import net.minecraft.server.v1_9_R2.EntityPlayer;
import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;
import net.minecraft.server.v1_9_R2.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.craftbukkit.v1_9_R2.CraftChunk;
import org.bukkit.craftbukkit.v1_9_R2.CraftServer;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import com.walrusone.skywars.api.NMS;

public class NMSHandler
  implements NMS
{
  public void respawnPlayer(Player player)
  {
    ((CraftServer)Bukkit.getServer()).getHandle().moveToWorld(((CraftPlayer)player).getHandle(), 0, false);
  }
  
  @SuppressWarnings("deprecation")
public void updateChunks(org.bukkit.World world, List<org.bukkit.Chunk> chunks)
  {
    Iterator<EntityHuman> localIterator2;
    for (Iterator<Chunk> localIterator1 = chunks.iterator(); localIterator1.hasNext(); localIterator2.hasNext())
    {
      org.bukkit.Chunk currentChunk = (org.bukkit.Chunk)localIterator1.next();
      net.minecraft.server.v1_9_R2.World mcWorld = ((CraftChunk)currentChunk).getHandle().world;
      localIterator2 = mcWorld.players.iterator(); 
      EntityHuman eh = (EntityHuman)localIterator2.next();
      EntityPlayer ep = (EntityPlayer)eh;
      Player p = (Player)ep;
      p.getWorld().refreshChunk(currentChunk.getX(), currentChunk.getZ());
      continue;
    }
  }
  
  public void sendParticles(org.bukkit.World world, String type, float x, float y, float z, float offsetX, float offsetY, float offsetZ, float data, int amount)
  {
    EnumParticle particle = EnumParticle.valueOf(type);
    PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(particle, false, x, y, z, offsetX, offsetY, offsetZ, data, amount, new int[] { 1 });
    for (Player player : world.getPlayers())
    {
      CraftPlayer start = (CraftPlayer)player;
      EntityPlayer target = start.getHandle();
      PlayerConnection connect = target.playerConnection;
      connect.sendPacket(particles);
    }
  }
  
  public String getName(org.bukkit.inventory.ItemStack stack)
  {
    return CraftItemStack.asNMSCopy(stack).getName();
  }
  
  public FireworkEffect getFireworkEffect(Color one, Color two, Color three, Color four, Color five, FireworkEffect.Type type)
  {
    return FireworkEffect.builder().flicker(false).withColor(new Color[] { one, two, three, four }).withFade(five).with(type).trail(true).build();
  }
  
  public void sendTitle(Player player, int fadein, int stay, int fadeout, String title, String subtitle)
  {
    PlayerConnection pConn = ((CraftPlayer)player).getHandle().playerConnection;
    PacketPlayOutTitle pTitleInfo = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, null, fadein, stay, fadeout);
    pConn.sendPacket(pTitleInfo);
    if (subtitle != null)
    {
      subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
      subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
      IChatBaseComponent iComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subtitle + "\"}");
      PacketPlayOutTitle pSubtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, iComp);
      pConn.sendPacket(pSubtitle);
    }
    if (title != null)
    {
      title = title.replaceAll("%player%", player.getDisplayName());
      title = ChatColor.translateAlternateColorCodes('&', title);
      IChatBaseComponent iComp = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
      PacketPlayOutTitle pTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, iComp);
      pConn.sendPacket(pTitle);
    }
  }
  
  public boolean isOnePointSeven()
  {
    return false;
  }
}
