package net.krakeens.redis.jedis.commands;

import java.util.List;

import net.krakeens.redis.util.Slowlog;

public interface AdvancedJedisCommands {
  List<String> configGet(String pattern);

  String configSet(String parameter, String value);

  String slowlogReset();

  Long slowlogLen();

  List<Slowlog> slowlogGet();

  List<Slowlog> slowlogGet(long entries);

  Long objectRefcount(String string);

  String objectEncoding(String string);

  Long objectIdletime(String string);
}
