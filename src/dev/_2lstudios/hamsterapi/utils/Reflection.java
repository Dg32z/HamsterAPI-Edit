package dev._2lstudios.hamsterapi.utils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class Reflection {
	private final String version;
	private final Map<String, Class<?>> classes = new HashMap<>();

	public Reflection(final String version) {
		this.version = version;
	}

	public Class<?> getClass(final String className) throws ClassNotFoundException {
		if (this.classes.containsKey(className)) {
			return this.classes.get(className);
		}

		final Class<?> craftBukkitClass = Class.forName(className);

		this.classes.put(className, craftBukkitClass);

		return craftBukkitClass;
	}

	public Object getField(final Object object, final Class<?> fieldType) throws IllegalAccessException {
		if (object == null) {
			throw new IllegalAccessException("Tried to access field from a null object");
		}

		for (final Field field : object.getClass().getFields()) {
			if (field.getType().equals(fieldType)) {
				final boolean accessible = field.isAccessible();

				field.setAccessible(true);

				final Object value = field.get(object);

				field.setAccessible(accessible);

				return value;
			}
		}

		return null;
	}

	private Class<?> getNewNetMinecraftClass(String key) {
		try {
			return getClass("net.minecraft." + key);
		} catch (final ClassNotFoundException e) {
			/* Ignored */
		}

		return null;
	}

	private Class<?> getNetMinecraftClass(String key) {
		try {
			final int lastDot = key.lastIndexOf(".");
			final String lastKey = key.substring(lastDot > 0 ? lastDot + 1 : 0, key.length());

			return getClass("net.minecraft.server." + this.version + "." + lastKey);
		} catch (final ClassNotFoundException e) {
			/* Ignored */
		}

		return getNewNetMinecraftClass(key);
	}

	private Class<?> getNewCraftBukkitClass(String key) {
		try {
			return getClass("org.bukkit.craftbukkit." + this.version + "." + key);
		} catch (final ClassNotFoundException e) {
			/* Ignored */
		}

		return null;
	}

	private Class<?> getCraftBukkitClass(String key) {
		try {
			final int lastDot = key.lastIndexOf(".");
			final String lastKey = key.substring(lastDot > 0 ? lastDot + 1 : 0, key.length());

			return getClass("org.bukkit.craftbukkit." + this.version + "." + lastKey);
		} catch (final ClassNotFoundException e) {
			/* Ignored */
		}

		return getNewCraftBukkitClass(key);
	}

	public Class<?> getItemStack() {
		return getNetMinecraftClass("world.item.ItemStack");
	}

	public Class<?> getMinecraftKey() {
		return getNetMinecraftClass("resources.MinecraftKey");
	}

	public Class<?> getEnumProtocol() {
		return getNetMinecraftClass("network.EnumProtocol");
	}

	public Class<?> getEnumProtocolDirection() {
		return getNetMinecraftClass("network.protocol.EnumProtocolDirection");
	}

	public Class<?> getNetworkManager() {
		return getNetMinecraftClass("network.NetworkManager");
	}

	public Class<?> getPacketDataSerializer() {
		return getNetMinecraftClass("network.PacketDataSerializer");
	}

	public Class<?> getPacket() {
		return getNetMinecraftClass("network.protocol.Packet");
	}

	public Class<?> getIChatBaseComponent() {
		return getNetMinecraftClass("network.chat.IChatBaseComponent");
	}

	public Class<?> getPacketPlayOutKickDisconnect() {
		return getNetMinecraftClass("network.protocol.game.PacketPlayOutKickDisconnect");
	}

	public Class<?> getPacketPlayOutTitle() {
		return getNetMinecraftClass("network.protocol.game.PacketPlayOutTitle");
	}

	public Class<?> getPacketPlayOutChat() {
		return getNetMinecraftClass("network.protocol.game.PacketPlayOutChat");
	}

	public Class<?> getPlayerConnection() {
		return getNetMinecraftClass("server.network.PlayerConnection");
	}

	public Class<?> getClientboundSetTitlesAnimationPacket() {
		return getNetMinecraftClass("network.protocol.game.ClientboundSetTitlesAnimationPacket");
	}

	public Class<?> getClientboundSetTitleTextPacket() {
		return getNetMinecraftClass("network.protocol.game.ClientboundSetTitleTextPacket");
	}

	public Class<?> getClientboundSetSubtitleTextPacket() {
		return getNetMinecraftClass("network.protocol.game.ClientboundSetSubtitleTextPacket");
	}

	public Class<?> getChatMessageType() {
		return getNetMinecraftClass("network.chat.ChatMessageType");
	}

	public Class<?> getCraftItemStack() {
		return getCraftBukkitClass("inventory.CraftItemStack");
	}
}
