package by.kamtech.telegrambot.counterman.config;

public class Keys {

    public static final ConfigKey BOT_TOKEN = new ConfigKey(
            "bot.token", Integer.class);

    public static final ConfigKey BOT_USERNAME = new ConfigKey(
            "bot.username", Integer.class);

    public static final ConfigKey HIBERNATE_CONNECTION_DRIVER = new ConfigKey(
            "hibernate.connection.driver_class", String.class);

    public static final ConfigKey HIBERNATE_CONNECTION_URL = new ConfigKey(
            "hibernate.connection.url", String.class);

    public static final ConfigKey HIBERNATE_CONNECTION_USERNAME = new ConfigKey(
            "hibernate.connection.username", String.class);

    public static final ConfigKey HIBERNATE_CONNECTION_PASSWORD = new ConfigKey(
            "hibernate.connection.password", String.class);

    public static final ConfigKey HIBERNATE_CONNECTION_POOL = new ConfigKey(
            "hibernate.connection.pool_size", Integer.class);

    public static final ConfigKey HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS = new ConfigKey(
            "hibernate.current_session_context_class", String.class);

    public static final ConfigKey HIBERNATE_SHOW_SQL = new ConfigKey(
            "hibernate.show_sql", Boolean.class);

}
