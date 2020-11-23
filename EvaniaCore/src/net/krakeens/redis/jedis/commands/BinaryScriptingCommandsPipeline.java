package net.krakeens.redis.jedis.commands;

import java.util.List;

import net.krakeens.redis.jedis.Response;

public interface BinaryScriptingCommandsPipeline {

  Response<Object> eval(byte[] script, byte[] keyCount, byte[]... params);

  Response<Object> eval(byte[] script, int keyCount, byte[]... params);

  Response<Object> eval(byte[] script, List<byte[]> keys, List<byte[]> args);

  Response<Object> eval(byte[] script);

  Response<Object> evalsha(byte[] script);

  Response<Object> evalsha(byte[] sha1, List<byte[]> keys, List<byte[]> args);

  Response<Object> evalsha(byte[] sha1, int keyCount, byte[]... params);
}
