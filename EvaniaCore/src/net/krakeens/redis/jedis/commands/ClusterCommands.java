package net.krakeens.redis.jedis.commands;

import java.util.List;

import net.krakeens.redis.jedis.JedisCluster;

public interface ClusterCommands {
  String clusterNodes();

  String clusterMeet(final String ip, final int port);

  String clusterAddSlots(final int... slots);

  String clusterDelSlots(final int... slots);

  String clusterInfo();

  List<String> clusterGetKeysInSlot(final int slot, final int count);

  String clusterSetSlotNode(final int slot, final String nodeId);

  String clusterSetSlotMigrating(final int slot, final String nodeId);

  String clusterSetSlotImporting(final int slot, final String nodeId);

  String clusterSetSlotStable(final int slot);

  String clusterForget(final String nodeId);

  String clusterFlushSlots();

  Long clusterKeySlot(final String key);

  Long clusterCountKeysInSlot(final int slot);

  String clusterSaveConfig();

  String clusterReplicate(final String nodeId);

  List<String> clusterSlaves(final String nodeId);

  String clusterFailover();

  List<Object> clusterSlots();

  String clusterReset(JedisCluster.Reset resetType);

  String readonly();
}
