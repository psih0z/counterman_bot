package by.kamtech.telegrambot.counterman;

import by.kamtech.telegrambot.counterman.config.Config;
import by.kamtech.telegrambot.counterman.config.Keys;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;

public final class Context {

    private static Config config;

    public static Config getConfig() {
        return config;
    }

    public static void init(String configFile) {
        try {
            config = new Config(configFile);

            migrate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void migrate() {
        Flyway flyway = Flyway.configure().dataSource(
                config.getString(Keys.HIBERNATE_CONNECTION_URL),
                config.getString(Keys.HIBERNATE_CONNECTION_USERNAME),
                config.getString(Keys.HIBERNATE_CONNECTION_PASSWORD))
                .load();

        MigrationInfoService migrationInfoService = flyway.info();
        MigrationInfo[] migrationInfo = migrationInfoService.all();

        for(MigrationInfo mi: migrationInfo)
        {
            String version = mi.getVersion().toString();
            String state = mi.getState().isApplied()+"";
            System.out.println(String.format("Is target version %s applied? %s", version, state));
        }

//        flyway.baseline();
        flyway.migrate();
        System.out.println(flyway.info()
                .current()
                .getVersion()
                .getVersion());
    }

}
