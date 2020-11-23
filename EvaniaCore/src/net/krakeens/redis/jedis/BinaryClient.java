package net.krakeens.redis.jedis;

import static net.krakeens.redis.jedis.Protocol.toByteArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.krakeens.redis.jedis.params.set.SetParams;
import net.krakeens.redis.jedis.params.sortedset.ZAddParams;
import net.krakeens.redis.jedis.params.sortedset.ZIncrByParams;
import net.krakeens.redis.util.SafeEncoder;

public class BinaryClient extends Connection {
  public enum LIST_POSITION {
    BEFORE, AFTER;
    public final byte[] raw;

    private LIST_POSITION() {
      raw = SafeEncoder.encode(name());
    }
  }

  private boolean isInMulti;

  private String password;

  private int db;

  private boolean isInWatch;

  public boolean isInMulti() {
    return isInMulti;
  }

  public boolean isInWatch() {
    return isInWatch;
  }

  public BinaryClient() {
    super();
  }

  public BinaryClient(final String host) {
    super(host);
  }

  public BinaryClient(final String host, final int port) {
    super(host, port);
  }

  private byte[][] joinParameters(byte[] first, byte[][] rest) {
    byte[][] result = new byte[rest.length + 1][];
    result[0] = first;
    System.arraycopy(rest, 0, result, 1, rest.length);
    return result;
  }

  public void setPassword(final String password) {
    this.password = password;
  }

  public void setDb(int db) {
    this.db = db;
  }

  @Override
  public void connect() {
    if (!isConnected()) {
      super.connect();
      if (password != null) {
        auth(password);
        getStatusCodeReply();
      }
      if (db > 0) {
        select(Long.valueOf(db).intValue());
        getStatusCodeReply();
      }
    }
  }

  public void ping() {
    sendCommand(Protocol.Command.PING);
  }

  public void set(final byte[] key, final byte[] value) {
    sendCommand(Protocol.Command.SET, key, value);
  }

  public void set(final byte[] key, final byte[] value, final SetParams params) {
    sendCommand(Protocol.Command.SET, params.getByteParams(key, value));
  }

  public void get(final byte[] key) {
    sendCommand(Protocol.Command.GET, key);
  }

  public void quit() {
    db = 0;
    sendCommand(Protocol.Command.QUIT);
  }

  public void exists(final byte[]... keys) {
    sendCommand(Protocol.Command.EXISTS, keys);
  }

  public void exists(final byte[] key) {
    sendCommand(Protocol.Command.EXISTS, key);
  }

  public void del(final byte[]... keys) {
    sendCommand(Protocol.Command.DEL, keys);
  }

  public void type(final byte[] key) {
    sendCommand(Protocol.Command.TYPE, key);
  }

  public void flushDB() {
    sendCommand(Protocol.Command.FLUSHDB);
  }

  public void keys(final byte[] pattern) {
    sendCommand(Protocol.Command.KEYS, pattern);
  }

  public void randomKey() {
    sendCommand(Protocol.Command.RANDOMKEY);
  }

  public void rename(final byte[] oldkey, final byte[] newkey) {
    sendCommand(Protocol.Command.RENAME, oldkey, newkey);
  }

  public void renamenx(final byte[] oldkey, final byte[] newkey) {
    sendCommand(Protocol.Command.RENAMENX, oldkey, newkey);
  }

  public void dbSize() {
    sendCommand(Protocol.Command.DBSIZE);
  }

  public void expire(final byte[] key, final int seconds) {
    sendCommand(Protocol.Command.EXPIRE, key, Protocol.toByteArray(seconds));
  }

  public void expireAt(final byte[] key, final long unixTime) {
    sendCommand(Protocol.Command.EXPIREAT, key, Protocol.toByteArray(unixTime));
  }

  public void ttl(final byte[] key) {
    sendCommand(Protocol.Command.TTL, key);
  }

  public void select(final int index) {
    sendCommand(Protocol.Command.SELECT, Protocol.toByteArray(index));
  }

  public void move(final byte[] key, final int dbIndex) {
    sendCommand(Protocol.Command.MOVE, key, Protocol.toByteArray(dbIndex));
  }

  public void flushAll() {
    sendCommand(Protocol.Command.FLUSHALL);
  }

  public void getSet(final byte[] key, final byte[] value) {
    sendCommand(Protocol.Command.GETSET, key, value);
  }

  public void mget(final byte[]... keys) {
    sendCommand(Protocol.Command.MGET, keys);
  }

  public void setnx(final byte[] key, final byte[] value) {
    sendCommand(Protocol.Command.SETNX, key, value);
  }

  public void setex(final byte[] key, final int seconds, final byte[] value) {
    sendCommand(Protocol.Command.SETEX, key, Protocol.toByteArray(seconds), value);
  }

  public void mset(final byte[]... keysvalues) {
    sendCommand(Protocol.Command.MSET, keysvalues);
  }

  public void msetnx(final byte[]... keysvalues) {
    sendCommand(Protocol.Command.MSETNX, keysvalues);
  }

  public void decrBy(final byte[] key, final long integer) {
    sendCommand(Protocol.Command.DECRBY, key, Protocol.toByteArray(integer));
  }

  public void decr(final byte[] key) {
    sendCommand(Protocol.Command.DECR, key);
  }

  public void incrBy(final byte[] key, final long integer) {
    sendCommand(Protocol.Command.INCRBY, key, Protocol.toByteArray(integer));
  }

  public void incrByFloat(final byte[] key, final double value) {
    sendCommand(Protocol.Command.INCRBYFLOAT, key, Protocol.toByteArray(value));
  }

  public void incr(final byte[] key) {
    sendCommand(Protocol.Command.INCR, key);
  }

  public void append(final byte[] key, final byte[] value) {
    sendCommand(Protocol.Command.APPEND, key, value);
  }

  public void substr(final byte[] key, final int start, final int end) {
    sendCommand(Protocol.Command.SUBSTR, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void hset(final byte[] key, final byte[] field, final byte[] value) {
    sendCommand(Protocol.Command.HSET, key, field, value);
  }

  public void hget(final byte[] key, final byte[] field) {
    sendCommand(Protocol.Command.HGET, key, field);
  }

  public void hsetnx(final byte[] key, final byte[] field, final byte[] value) {
    sendCommand(Protocol.Command.HSETNX, key, field, value);
  }

  public void hmset(final byte[] key, final Map<byte[], byte[]> hash) {
    final List<byte[]> params = new ArrayList<byte[]>();
    params.add(key);

    for (final Entry<byte[], byte[]> entry : hash.entrySet()) {
      params.add(entry.getKey());
      params.add(entry.getValue());
    }
    sendCommand(Protocol.Command.HMSET, params.toArray(new byte[params.size()][]));
  }

  public void hmget(final byte[] key, final byte[]... fields) {
    final byte[][] params = new byte[fields.length + 1][];
    params[0] = key;
    System.arraycopy(fields, 0, params, 1, fields.length);
    sendCommand(Protocol.Command.HMGET, params);
  }

  public void hincrBy(final byte[] key, final byte[] field, final long value) {
    sendCommand(Protocol.Command.HINCRBY, key, field, Protocol.toByteArray(value));
  }

  public void hexists(final byte[] key, final byte[] field) {
    sendCommand(Protocol.Command.HEXISTS, key, field);
  }

  public void hdel(final byte[] key, final byte[]... fields) {
    sendCommand(Protocol.Command.HDEL, joinParameters(key, fields));
  }

  public void hlen(final byte[] key) {
    sendCommand(Protocol.Command.HLEN, key);
  }

  public void hkeys(final byte[] key) {
    sendCommand(Protocol.Command.HKEYS, key);
  }

  public void hvals(final byte[] key) {
    sendCommand(Protocol.Command.HVALS, key);
  }

  public void hgetAll(final byte[] key) {
    sendCommand(Protocol.Command.HGETALL, key);
  }

  public void rpush(final byte[] key, final byte[]... strings) {
    sendCommand(Protocol.Command.RPUSH, joinParameters(key, strings));
  }

  public void lpush(final byte[] key, final byte[]... strings) {
    sendCommand(Protocol.Command.LPUSH, joinParameters(key, strings));
  }

  public void llen(final byte[] key) {
    sendCommand(Protocol.Command.LLEN, key);
  }

  public void lrange(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.LRANGE, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void ltrim(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.LTRIM, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void lindex(final byte[] key, final long index) {
    sendCommand(Protocol.Command.LINDEX, key, Protocol.toByteArray(index));
  }

  public void lset(final byte[] key, final long index, final byte[] value) {
    sendCommand(Protocol.Command.LSET, key, Protocol.toByteArray(index), value);
  }

  public void lrem(final byte[] key, long count, final byte[] value) {
    sendCommand(Protocol.Command.LREM, key, Protocol.toByteArray(count), value);
  }

  public void lpop(final byte[] key) {
    sendCommand(Protocol.Command.LPOP, key);
  }

  public void rpop(final byte[] key) {
    sendCommand(Protocol.Command.RPOP, key);
  }

  public void rpoplpush(final byte[] srckey, final byte[] dstkey) {
    sendCommand(Protocol.Command.RPOPLPUSH, srckey, dstkey);
  }

  public void sadd(final byte[] key, final byte[]... members) {
    sendCommand(Protocol.Command.SADD, joinParameters(key, members));
  }

  public void smembers(final byte[] key) {
    sendCommand(Protocol.Command.SMEMBERS, key);
  }

  public void srem(final byte[] key, final byte[]... members) {
    sendCommand(Protocol.Command.SREM, joinParameters(key, members));
  }

  public void spop(final byte[] key) {
    sendCommand(Protocol.Command.SPOP, key);
  }

  public void spop(final byte[] key, final long count) {
    sendCommand(Protocol.Command.SPOP, key, Protocol.toByteArray(count));
  }

  public void smove(final byte[] srckey, final byte[] dstkey, final byte[] member) {
    sendCommand(Protocol.Command.SMOVE, srckey, dstkey, member);
  }

  public void scard(final byte[] key) {
    sendCommand(Protocol.Command.SCARD, key);
  }

  public void sismember(final byte[] key, final byte[] member) {
    sendCommand(Protocol.Command.SISMEMBER, key, member);
  }

  public void sinter(final byte[]... keys) {
    sendCommand(Protocol.Command.SINTER, keys);
  }

  public void sinterstore(final byte[] dstkey, final byte[]... keys) {
    final byte[][] params = new byte[keys.length + 1][];
    params[0] = dstkey;
    System.arraycopy(keys, 0, params, 1, keys.length);
    sendCommand(Protocol.Command.SINTERSTORE, params);
  }

  public void sunion(final byte[]... keys) {
    sendCommand(Protocol.Command.SUNION, keys);
  }

  public void sunionstore(final byte[] dstkey, final byte[]... keys) {
    byte[][] params = new byte[keys.length + 1][];
    params[0] = dstkey;
    System.arraycopy(keys, 0, params, 1, keys.length);
    sendCommand(Protocol.Command.SUNIONSTORE, params);
  }

  public void sdiff(final byte[]... keys) {
    sendCommand(Protocol.Command.SDIFF, keys);
  }

  public void sdiffstore(final byte[] dstkey, final byte[]... keys) {
    byte[][] params = new byte[keys.length + 1][];
    params[0] = dstkey;
    System.arraycopy(keys, 0, params, 1, keys.length);
    sendCommand(Protocol.Command.SDIFFSTORE, params);
  }

  public void srandmember(final byte[] key) {
    sendCommand(Protocol.Command.SRANDMEMBER, key);
  }

  public void zadd(final byte[] key, final double score, final byte[] member) {
    sendCommand(Protocol.Command.ZADD, key, Protocol.toByteArray(score), member);
  }

  public void zadd(final byte[] key, final double score, final byte[] member,
      final ZAddParams params) {
    sendCommand(Protocol.Command.ZADD, params.getByteParams(key, Protocol.toByteArray(score), member));
  }

  public void zadd(final byte[] key, final Map<byte[], Double> scoreMembers) {
    ArrayList<byte[]> args = new ArrayList<byte[]>(scoreMembers.size() * 2 + 1);
    args.add(key);
    args.addAll(convertScoreMembersToByteArrays(scoreMembers));

    byte[][] argsArray = new byte[args.size()][];
    args.toArray(argsArray);

    sendCommand(Protocol.Command.ZADD, argsArray);
  }

  public void zadd(final byte[] key, final Map<byte[], Double> scoreMembers,
      final ZAddParams params) {
    ArrayList<byte[]> args = convertScoreMembersToByteArrays(scoreMembers);
    byte[][] argsArray = new byte[args.size()][];
    args.toArray(argsArray);

    sendCommand(Protocol.Command.ZADD, params.getByteParams(key, argsArray));
  }

  public void zrange(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.ZRANGE, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void zrem(final byte[] key, final byte[]... members) {
    sendCommand(Protocol.Command.ZREM, joinParameters(key, members));
  }

  public void zincrby(final byte[] key, final double score, final byte[] member) {
    sendCommand(Protocol.Command.ZINCRBY, key, Protocol.toByteArray(score), member);
  }

  public void zincrby(final byte[] key, final double score, final byte[] member,
      final ZIncrByParams params) {
    // Note that it actually calls ZADD with INCR option, so it requires Redis 3.0.2 or upper.
    sendCommand(Protocol.Command.ZADD, params.getByteParams(key, Protocol.toByteArray(score), member));
  }

  public void zrank(final byte[] key, final byte[] member) {
    sendCommand(Protocol.Command.ZRANK, key, member);
  }

  public void zrevrank(final byte[] key, final byte[] member) {
    sendCommand(Protocol.Command.ZREVRANK, key, member);
  }

  public void zrevrange(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.ZREVRANGE, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void zrangeWithScores(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.ZRANGE, key, Protocol.toByteArray(start), Protocol.toByteArray(end), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeWithScores(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.ZREVRANGE, key, Protocol.toByteArray(start), Protocol.toByteArray(end), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zcard(final byte[] key) {
    sendCommand(Protocol.Command.ZCARD, key);
  }

  public void zscore(final byte[] key, final byte[] member) {
    sendCommand(Protocol.Command.ZSCORE, key, member);
  }

  public void multi() {
    sendCommand(Protocol.Command.MULTI);
    isInMulti = true;
  }

  public void discard() {
    sendCommand(Protocol.Command.DISCARD);
    isInMulti = false;
    isInWatch = false;
  }

  public void exec() {
    sendCommand(Protocol.Command.EXEC);
    isInMulti = false;
    isInWatch = false;
  }

  public void watch(final byte[]... keys) {
    sendCommand(Protocol.Command.WATCH, keys);
    isInWatch = true;
  }

  public void unwatch() {
    sendCommand(Protocol.Command.UNWATCH);
    isInWatch = false;
  }

  public void sort(final byte[] key) {
    sendCommand(Protocol.Command.SORT, key);
  }

  public void sort(final byte[] key, final SortingParams sortingParameters) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(key);
    args.addAll(sortingParameters.getParams());
    sendCommand(Protocol.Command.SORT, args.toArray(new byte[args.size()][]));
  }

  public void blpop(final byte[][] args) {
    sendCommand(Protocol.Command.BLPOP, args);
  }

  public void blpop(final int timeout, final byte[]... keys) {
    final List<byte[]> args = new ArrayList<byte[]>();
    for (final byte[] arg : keys) {
      args.add(arg);
    }
    args.add(Protocol.toByteArray(timeout));
    blpop(args.toArray(new byte[args.size()][]));
  }

  public void sort(final byte[] key, final SortingParams sortingParameters, final byte[] dstkey) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(key);
    args.addAll(sortingParameters.getParams());
    args.add(Protocol.Keyword.STORE.raw);
    args.add(dstkey);
    sendCommand(Protocol.Command.SORT, args.toArray(new byte[args.size()][]));
  }

  public void sort(final byte[] key, final byte[] dstkey) {
    sendCommand(Protocol.Command.SORT, key, Protocol.Keyword.STORE.raw, dstkey);
  }

  public void brpop(final byte[][] args) {
    sendCommand(Protocol.Command.BRPOP, args);
  }

  public void brpop(final int timeout, final byte[]... keys) {
    final List<byte[]> args = new ArrayList<byte[]>();
    for (final byte[] arg : keys) {
      args.add(arg);
    }
    args.add(Protocol.toByteArray(timeout));
    brpop(args.toArray(new byte[args.size()][]));
  }

  public void auth(final String password) {
    setPassword(password);
    sendCommand(Protocol.Command.AUTH, password);
  }

  public void subscribe(final byte[]... channels) {
    sendCommand(Protocol.Command.SUBSCRIBE, channels);
  }

  public void publish(final byte[] channel, final byte[] message) {
    sendCommand(Protocol.Command.PUBLISH, channel, message);
  }

  public void unsubscribe() {
    sendCommand(Protocol.Command.UNSUBSCRIBE);
  }

  public void unsubscribe(final byte[]... channels) {
    sendCommand(Protocol.Command.UNSUBSCRIBE, channels);
  }

  public void psubscribe(final byte[]... patterns) {
    sendCommand(Protocol.Command.PSUBSCRIBE, patterns);
  }

  public void punsubscribe() {
    sendCommand(Protocol.Command.PUNSUBSCRIBE);
  }

  public void punsubscribe(final byte[]... patterns) {
    sendCommand(Protocol.Command.PUNSUBSCRIBE, patterns);
  }

  public void pubsub(final byte[]... args) {
    sendCommand(Protocol.Command.PUBSUB, args);
  }

  public void zcount(final byte[] key, final double min, final double max) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZCOUNT, key, byteArrayMin, byteArrayMax);
  }

  public void zcount(final byte[] key, final byte min[], final byte max[]) {
    sendCommand(Protocol.Command.ZCOUNT, key, min, max);
  }

  public void zcount(final byte[] key, final String min, final String max) {
    sendCommand(Protocol.Command.ZCOUNT, key, min.getBytes(), max.getBytes());
  }

  public void zrangeByScore(final byte[] key, final double min, final double max) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, byteArrayMin, byteArrayMax);
  }

  public void zrangeByScore(final byte[] key, final byte[] min, final byte[] max) {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min, max);
  }

  public void zrangeByScore(final byte[] key, final String min, final String max) {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min.getBytes(), max.getBytes());
  }

  public void zrevrangeByScore(final byte[] key, final double max, final double min) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, byteArrayMax, byteArrayMin);
  }

  public void zrevrangeByScore(final byte[] key, final byte[] max, final byte[] min) {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max, min);
  }

  public void zrevrangeByScore(final byte[] key, final String max, final String min) {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max.getBytes(), min.getBytes());
  }

  public void zrangeByScore(final byte[] key, final double min, final double max, final int offset,
      int count) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, byteArrayMin, byteArrayMax, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count));
  }

  public void zrangeByScore(final byte[] key, final String min, final String max, final int offset,
      int count) {

    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min.getBytes(), max.getBytes(), Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count));
  }

  public void zrevrangeByScore(final byte[] key, final double max, final double min,
      final int offset, int count) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, byteArrayMax, byteArrayMin, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count));
  }

  public void zrevrangeByScore(final byte[] key, final String max, final String min,
      final int offset, int count) {

    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max.getBytes(), min.getBytes(), Protocol.Keyword.LIMIT.raw,
      Protocol.toByteArray(offset), Protocol.toByteArray(count));
  }

  public void zrangeByScoreWithScores(final byte[] key, final double min, final double max) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, byteArrayMin, byteArrayMax, Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrangeByScoreWithScores(final byte[] key, final String min, final String max) {

    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min.getBytes(), max.getBytes(), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeByScoreWithScores(final byte[] key, final double max, final double min) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, byteArrayMax, byteArrayMin, Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeByScoreWithScores(final byte[] key, final String max, final String min) {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max.getBytes(), min.getBytes(), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrangeByScoreWithScores(final byte[] key, final double min, final double max,
      final int offset, final int count) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, byteArrayMin, byteArrayMax, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrangeByScoreWithScores(final byte[] key, final String min, final String max,
      final int offset, final int count) {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min.getBytes(), max.getBytes(), Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeByScoreWithScores(final byte[] key, final double max, final double min,
      final int offset, final int count) {

    byte byteArrayMin[] = (min == Double.NEGATIVE_INFINITY) ? "-inf".getBytes() : Protocol.toByteArray(min);
    byte byteArrayMax[] = (max == Double.POSITIVE_INFINITY) ? "+inf".getBytes() : Protocol.toByteArray(max);

    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, byteArrayMax, byteArrayMin, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeByScoreWithScores(final byte[] key, final String max, final String min,
      final int offset, final int count) {

    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max.getBytes(), min.getBytes(), Protocol.Keyword.LIMIT.raw,
      Protocol.toByteArray(offset), Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrangeByScore(final byte[] key, final byte[] min, final byte[] max, final int offset,
      int count) {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min, max, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count));
  }

  public void zrevrangeByScore(final byte[] key, final byte[] max, final byte[] min,
      final int offset, int count) {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max, min, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count));
  }

  public void zrangeByScoreWithScores(final byte[] key, final byte[] min, final byte[] max) {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min, max, Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeByScoreWithScores(final byte[] key, final byte[] max, final byte[] min) {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max, min, Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrangeByScoreWithScores(final byte[] key, final byte[] min, final byte[] max,
      final int offset, final int count) {
    sendCommand(Protocol.Command.ZRANGEBYSCORE, key, min, max, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count),
      Protocol.Keyword.WITHSCORES.raw);
  }

  public void zrevrangeByScoreWithScores(final byte[] key, final byte[] max, final byte[] min,
      final int offset, final int count) {
    sendCommand(Protocol.Command.ZREVRANGEBYSCORE, key, max, min, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset),
      Protocol.toByteArray(count), Protocol.Keyword.WITHSCORES.raw);
  }

  public void zremrangeByRank(final byte[] key, final long start, final long end) {
    sendCommand(Protocol.Command.ZREMRANGEBYRANK, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void zremrangeByScore(final byte[] key, final byte[] start, final byte[] end) {
    sendCommand(Protocol.Command.ZREMRANGEBYSCORE, key, start, end);
  }

  public void zremrangeByScore(final byte[] key, final String start, final String end) {
    sendCommand(Protocol.Command.ZREMRANGEBYSCORE, key, start.getBytes(), end.getBytes());
  }

  public void zunionstore(final byte[] dstkey, final byte[]... sets) {
    final byte[][] params = new byte[sets.length + 2][];
    params[0] = dstkey;
    params[1] = Protocol.toByteArray(sets.length);
    System.arraycopy(sets, 0, params, 2, sets.length);
    sendCommand(Protocol.Command.ZUNIONSTORE, params);
  }

  public void zunionstore(final byte[] dstkey, final ZParams params, final byte[]... sets) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(dstkey);
    args.add(Protocol.toByteArray(sets.length));
    for (final byte[] set : sets) {
      args.add(set);
    }
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZUNIONSTORE, args.toArray(new byte[args.size()][]));
  }

  public void zinterstore(final byte[] dstkey, final byte[]... sets) {
    final byte[][] params = new byte[sets.length + 2][];
    params[0] = dstkey;
    params[1] = Protocol.toByteArray(sets.length);
    System.arraycopy(sets, 0, params, 2, sets.length);
    sendCommand(Protocol.Command.ZINTERSTORE, params);
  }

  public void zinterstore(final byte[] dstkey, final ZParams params, final byte[]... sets) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(dstkey);
    args.add(Protocol.toByteArray(sets.length));
    for (final byte[] set : sets) {
      args.add(set);
    }
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZINTERSTORE, args.toArray(new byte[args.size()][]));
  }

  public void zlexcount(final byte[] key, final byte[] min, final byte[] max) {
    sendCommand(Protocol.Command.ZLEXCOUNT, key, min, max);
  }

  public void zrangeByLex(final byte[] key, final byte[] min, final byte[] max) {
    sendCommand(Protocol.Command.ZRANGEBYLEX, key, min, max);
  }

  public void zrangeByLex(final byte[] key, final byte[] min, final byte[] max, final int offset,
      final int count) {
    sendCommand(Protocol.Command.ZRANGEBYLEX, key, min, max, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count));
  }

  public void zrevrangeByLex(final byte[] key, final byte[] max, final byte[] min) {
    sendCommand(Protocol.Command.ZREVRANGEBYLEX, key, max, min);
  }

  public void zrevrangeByLex(final byte[] key, final byte[] max, final byte[] min,
      final int offset, final int count) {
    sendCommand(Protocol.Command.ZREVRANGEBYLEX, key, max, min, Protocol.Keyword.LIMIT.raw, Protocol.toByteArray(offset), Protocol.toByteArray(count));
  }

  public void zremrangeByLex(byte[] key, byte[] min, byte[] max) {
    sendCommand(Protocol.Command.ZREMRANGEBYLEX, key, min, max);
  }

  public void save() {
    sendCommand(Protocol.Command.SAVE);
  }

  public void bgsave() {
    sendCommand(Protocol.Command.BGSAVE);
  }

  public void bgrewriteaof() {
    sendCommand(Protocol.Command.BGREWRITEAOF);
  }

  public void lastsave() {
    sendCommand(Protocol.Command.LASTSAVE);
  }

  public void shutdown() {
    sendCommand(Protocol.Command.SHUTDOWN);
  }

  public void info() {
    sendCommand(Protocol.Command.INFO);
  }

  public void info(final String section) {
    sendCommand(Protocol.Command.INFO, section);
  }

  public void monitor() {
    sendCommand(Protocol.Command.MONITOR);
  }

  public void slaveof(final String host, final int port) {
    sendCommand(Protocol.Command.SLAVEOF, host, String.valueOf(port));
  }

  public void slaveofNoOne() {
    sendCommand(Protocol.Command.SLAVEOF, Protocol.Keyword.NO.raw, Protocol.Keyword.ONE.raw);
  }

  public void configGet(final byte[] pattern) {
    sendCommand(Protocol.Command.CONFIG, Protocol.Keyword.GET.raw, pattern);
  }

  public void configSet(final byte[] parameter, final byte[] value) {
    sendCommand(Protocol.Command.CONFIG, Protocol.Keyword.SET.raw, parameter, value);
  }

  public void strlen(final byte[] key) {
    sendCommand(Protocol.Command.STRLEN, key);
  }

  public void sync() {
    sendCommand(Protocol.Command.SYNC);
  }

  public void lpushx(final byte[] key, final byte[]... string) {
    sendCommand(Protocol.Command.LPUSHX, joinParameters(key, string));
  }

  public void persist(final byte[] key) {
    sendCommand(Protocol.Command.PERSIST, key);
  }

  public void rpushx(final byte[] key, final byte[]... string) {
    sendCommand(Protocol.Command.RPUSHX, joinParameters(key, string));
  }

  public void echo(final byte[] string) {
    sendCommand(Protocol.Command.ECHO, string);
  }

  public void linsert(final byte[] key, final LIST_POSITION where, final byte[] pivot,
      final byte[] value) {
    sendCommand(Protocol.Command.LINSERT, key, where.raw, pivot, value);
  }

  public void debug(final DebugParams params) {
    sendCommand(Protocol.Command.DEBUG, params.getCommand());
  }

  public void brpoplpush(final byte[] source, final byte[] destination, final int timeout) {
    sendCommand(Protocol.Command.BRPOPLPUSH, source, destination, Protocol.toByteArray(timeout));
  }

  public void configResetStat() {
    sendCommand(Protocol.Command.CONFIG, Protocol.Keyword.RESETSTAT.name());
  }

  public void setbit(byte[] key, long offset, byte[] value) {
    sendCommand(Protocol.Command.SETBIT, key, Protocol.toByteArray(offset), value);
  }

  public void setbit(byte[] key, long offset, boolean value) {
    sendCommand(Protocol.Command.SETBIT, key, Protocol.toByteArray(offset), Protocol.toByteArray(value));
  }

  public void getbit(byte[] key, long offset) {
    sendCommand(Protocol.Command.GETBIT, key, Protocol.toByteArray(offset));
  }

  public void bitpos(final byte[] key, final boolean value, final BitPosParams params) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(key);
    args.add(Protocol.toByteArray(value));
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.BITPOS, args.toArray(new byte[args.size()][]));
  }

  public void setrange(byte[] key, long offset, byte[] value) {
    sendCommand(Protocol.Command.SETRANGE, key, Protocol.toByteArray(offset), value);
  }

  public void getrange(byte[] key, long startOffset, long endOffset) {
    sendCommand(Protocol.Command.GETRANGE, key, Protocol.toByteArray(startOffset), Protocol.toByteArray(endOffset));
  }

  public int getDB() {
    return db;
  }

  @Override
  public void disconnect() {
    db = 0;
    super.disconnect();
  }

  @Override
  public void close() {
    db = 0;
    super.close();
  }

  public void resetState() {
    if (isInWatch()) unwatch();
  }

  private void sendEvalCommand(Protocol.Command command, byte[] script, byte[] keyCount, byte[][] params) {

    final byte[][] allArgs = new byte[params.length + 2][];

    allArgs[0] = script;
    allArgs[1] = keyCount;

    for (int i = 0; i < params.length; i++)
      allArgs[i + 2] = params[i];

    sendCommand(command, allArgs);
  }

  public void eval(byte[] script, byte[] keyCount, byte[][] params) {
    sendEvalCommand(Protocol.Command.EVAL, script, keyCount, params);
  }

  public void eval(byte[] script, int keyCount, byte[]... params) {
    eval(script, Protocol.toByteArray(keyCount), params);
  }

  public void evalsha(byte[] sha1, byte[] keyCount, byte[]... params) {
    sendEvalCommand(Protocol.Command.EVALSHA, sha1, keyCount, params);
  }

  public void evalsha(byte[] sha1, int keyCount, byte[]... params) {
    sendEvalCommand(Protocol.Command.EVALSHA, sha1, Protocol.toByteArray(keyCount), params);
  }

  public void scriptFlush() {
    sendCommand(Protocol.Command.SCRIPT, Protocol.Keyword.FLUSH.raw);
  }

  public void scriptExists(byte[]... sha1) {
    byte[][] args = new byte[sha1.length + 1][];
    args[0] = Protocol.Keyword.EXISTS.raw;
    for (int i = 0; i < sha1.length; i++)
      args[i + 1] = sha1[i];

    sendCommand(Protocol.Command.SCRIPT, args);
  }

  public void scriptLoad(byte[] script) {
    sendCommand(Protocol.Command.SCRIPT, Protocol.Keyword.LOAD.raw, script);
  }

  public void scriptKill() {
    sendCommand(Protocol.Command.SCRIPT, Protocol.Keyword.KILL.raw);
  }

  public void slowlogGet() {
    sendCommand(Protocol.Command.SLOWLOG, Protocol.Keyword.GET.raw);
  }

  public void slowlogGet(long entries) {
    sendCommand(Protocol.Command.SLOWLOG, Protocol.Keyword.GET.raw, Protocol.toByteArray(entries));
  }

  public void slowlogReset() {
    sendCommand(Protocol.Command.SLOWLOG, Protocol.Keyword.RESET.raw);
  }

  public void slowlogLen() {
    sendCommand(Protocol.Command.SLOWLOG, Protocol.Keyword.LEN.raw);
  }

  public void objectRefcount(byte[] key) {
    sendCommand(Protocol.Command.OBJECT, Protocol.Keyword.REFCOUNT.raw, key);
  }

  public void objectIdletime(byte[] key) {
    sendCommand(Protocol.Command.OBJECT, Protocol.Keyword.IDLETIME.raw, key);
  }

  public void objectEncoding(byte[] key) {
    sendCommand(Protocol.Command.OBJECT, Protocol.Keyword.ENCODING.raw, key);
  }

  public void bitcount(byte[] key) {
    sendCommand(Protocol.Command.BITCOUNT, key);
  }

  public void bitcount(byte[] key, long start, long end) {
    sendCommand(Protocol.Command.BITCOUNT, key, Protocol.toByteArray(start), Protocol.toByteArray(end));
  }

  public void bitop(BitOP op, byte[] destKey, byte[]... srcKeys) {
    Protocol.Keyword kw = Protocol.Keyword.AND;
    int len = srcKeys.length;
    switch (op) {
    case AND:
      kw = Protocol.Keyword.AND;
      break;
    case OR:
      kw = Protocol.Keyword.OR;
      break;
    case XOR:
      kw = Protocol.Keyword.XOR;
      break;
    case NOT:
      kw = Protocol.Keyword.NOT;
      len = Math.min(1, len);
      break;
    }

    byte[][] bargs = new byte[len + 2][];
    bargs[0] = kw.raw;
    bargs[1] = destKey;
    for (int i = 0; i < len; ++i) {
      bargs[i + 2] = srcKeys[i];
    }

    sendCommand(Protocol.Command.BITOP, bargs);
  }

  public void sentinel(final byte[]... args) {
    sendCommand(Protocol.Command.SENTINEL, args);
  }

  public void dump(final byte[] key) {
    sendCommand(Protocol.Command.DUMP, key);
  }

  public void restore(final byte[] key, final int ttl, final byte[] serializedValue) {
    sendCommand(Protocol.Command.RESTORE, key, Protocol.toByteArray(ttl), serializedValue);
  }

  public void pexpire(final byte[] key, final long milliseconds) {
    sendCommand(Protocol.Command.PEXPIRE, key, Protocol.toByteArray(milliseconds));
  }

  public void pexpireAt(final byte[] key, final long millisecondsTimestamp) {
    sendCommand(Protocol.Command.PEXPIREAT, key, Protocol.toByteArray(millisecondsTimestamp));
  }

  public void pttl(final byte[] key) {
    sendCommand(Protocol.Command.PTTL, key);
  }

  public void psetex(final byte[] key, final long milliseconds, final byte[] value) {
    sendCommand(Protocol.Command.PSETEX, key, Protocol.toByteArray(milliseconds), value);
  }

  public void srandmember(final byte[] key, final int count) {
    sendCommand(Protocol.Command.SRANDMEMBER, key, Protocol.toByteArray(count));
  }

  public void clientKill(final byte[] client) {
    sendCommand(Protocol.Command.CLIENT, Protocol.Keyword.KILL.raw, client);
  }

  public void clientGetname() {
    sendCommand(Protocol.Command.CLIENT, Protocol.Keyword.GETNAME.raw);
  }

  public void clientList() {
    sendCommand(Protocol.Command.CLIENT, Protocol.Keyword.LIST.raw);
  }

  public void clientSetname(final byte[] name) {
    sendCommand(Protocol.Command.CLIENT, Protocol.Keyword.SETNAME.raw, name);
  }

  public void time() {
    sendCommand(Protocol.Command.TIME);
  }

  public void migrate(final byte[] host, final int port, final byte[] key, final int destinationDb,
      final int timeout) {
    sendCommand(Protocol.Command.MIGRATE, host, Protocol.toByteArray(port), key, Protocol.toByteArray(destinationDb),
      Protocol.toByteArray(timeout));
  }

  public void hincrByFloat(final byte[] key, final byte[] field, double increment) {
    sendCommand(Protocol.Command.HINCRBYFLOAT, key, field, Protocol.toByteArray(increment));
  }

  public void scan(final byte[] cursor, final ScanParams params) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.SCAN, args.toArray(new byte[args.size()][]));
  }

  public void hscan(final byte[] key, final byte[] cursor, final ScanParams params) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(key);
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.HSCAN, args.toArray(new byte[args.size()][]));
  }

  public void sscan(final byte[] key, final byte[] cursor, final ScanParams params) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(key);
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.SSCAN, args.toArray(new byte[args.size()][]));
  }

  public void zscan(final byte[] key, final byte[] cursor, final ScanParams params) {
    final List<byte[]> args = new ArrayList<byte[]>();
    args.add(key);
    args.add(cursor);
    args.addAll(params.getParams());
    sendCommand(Protocol.Command.ZSCAN, args.toArray(new byte[args.size()][]));
  }

  public void waitReplicas(int replicas, long timeout) {
    sendCommand(Protocol.Command.WAIT, Protocol.toByteArray(replicas), Protocol.toByteArray(timeout));
  }

  public void cluster(final byte[]... args) {
    sendCommand(Protocol.Command.CLUSTER, args);
  }

  public void asking() {
    sendCommand(Protocol.Command.ASKING);
  }

  public void pfadd(final byte[] key, final byte[]... elements) {
    sendCommand(Protocol.Command.PFADD, joinParameters(key, elements));
  }

  public void pfcount(final byte[] key) {
    sendCommand(Protocol.Command.PFCOUNT, key);
  }

  public void pfcount(final byte[]... keys) {
    sendCommand(Protocol.Command.PFCOUNT, keys);
  }

  public void pfmerge(final byte[] destkey, final byte[]... sourcekeys) {
    sendCommand(Protocol.Command.PFMERGE, joinParameters(destkey, sourcekeys));
  }

  public void readonly() {
    sendCommand(Protocol.Command.READONLY);
  }

  private ArrayList<byte[]> convertScoreMembersToByteArrays(final Map<byte[], Double> scoreMembers) {
    ArrayList<byte[]> args = new ArrayList<byte[]>(scoreMembers.size() * 2);

    for (Entry<byte[], Double> entry : scoreMembers.entrySet()) {
      args.add(Protocol.toByteArray(entry.getValue()));
      args.add(entry.getKey());
    }

    return args;
  }
}
