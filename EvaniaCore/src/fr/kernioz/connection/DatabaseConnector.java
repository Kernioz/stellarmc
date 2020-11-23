package fr.kernioz.connection;

import fr.kernioz.Evania;
import net.krakeens.redis.jedis.Jedis;

public abstract class DatabaseConnector {

    protected String password;
    protected Evania evania;

    public abstract Jedis getResource();
    public abstract Jedis getBungeeResource();
    public abstract void killConnections();
    public abstract void initiateConnections() throws InterruptedException;
    public abstract void disable();

}
