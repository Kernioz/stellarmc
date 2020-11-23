package fr.kernioz.connection;

import fr.kernioz.Evania;
import net.krakeens.redis.jedis.Jedis;
import net.krakeens.redis.jedis.JedisPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.commons.lang3.StringUtils;

public class SingleDatabaseConnector extends DatabaseConnector{

    private final String masterIp;
    protected JedisPool mainPool;
    protected JedisPool cachePool;

    public SingleDatabaseConnector(Evania evania, String masterIp, String password){
        this.evania = evania;
        this.masterIp = masterIp;
        this.password = password;

        System.out.println("[EvaniaCore » Redis] Intializing connection...");
        try {
            initiateConnections();
        } catch (InterruptedException e){
            System.out.println("[EvaniaCore » Redis] Error connection...");
            e.printStackTrace();
        }
    }

    @Override
    public void killConnections() {
        mainPool.destroy();
        cachePool.destroy();
    }

    @Override
    public void initiateConnections() throws InterruptedException {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(1024);
        config.setMaxWaitMillis(5000);


        String[] mainParts = StringUtils.split(masterIp, ":");
        int mainPort = (mainParts.length > 1) ? Integer.decode(mainParts[1]) : 6379;

        this.mainPool = new JedisPool(config, mainParts[0], mainPort, 5000, password);
        this.cachePool = new JedisPool(config, mainParts[0], mainPort, 5000, password,1);


        System.out.println("[EvaniaCore » Redis » DB] Connection initialized..");
    }

    @Override
    public void disable() {
        System.out.println("[EvaniaCore » Redis » DB] Connection stopped..");
        killConnections();
    }


    @Override
    public Jedis getResource() {
        return mainPool.getResource();
    }

    @Override
    public Jedis getBungeeResource() {
        return cachePool.getResource();
    }

}
