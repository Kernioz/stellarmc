package net.krakeens.redis.jedis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import net.krakeens.redis.util.SafeEncoder;

public class ScanParams {
  private List<byte[]> params = new ArrayList<byte[]>();
  public final static String SCAN_POINTER_START = String.valueOf(0);
  public final static byte[] SCAN_POINTER_START_BINARY = SafeEncoder.encode(SCAN_POINTER_START);

  public ScanParams match(final byte[] pattern) {
    params.add(Protocol.Keyword.MATCH.raw);
    params.add(pattern);
    return this;
  }

  public ScanParams match(final String pattern) {
    params.add(Protocol.Keyword.MATCH.raw);
    params.add(SafeEncoder.encode(pattern));
    return this;
  }

  public ScanParams count(final int count) {
    params.add(Protocol.Keyword.COUNT.raw);
    params.add(Protocol.toByteArray(count));
    return this;
  }

  public Collection<byte[]> getParams() {
    return Collections.unmodifiableCollection(params);
  }
}
