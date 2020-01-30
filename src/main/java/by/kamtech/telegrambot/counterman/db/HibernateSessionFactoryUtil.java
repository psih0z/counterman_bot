package by.kamtech.telegrambot.counterman.db;

import by.kamtech.telegrambot.counterman.Context;
import by.kamtech.telegrambot.counterman.config.Keys;
import by.kamtech.telegrambot.counterman.dao.Expense;
import by.kamtech.telegrambot.counterman.dao.Position;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

public class HibernateSessionFactoryUtil {

    private static SessionFactory sessionFactory;

    public HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration()
                        .setProperty(Environment.DRIVER, Context.getConfig().getString(Keys.HIBERNATE_CONNECTION_DRIVER))
                        .setProperty(Environment.URL, Context.getConfig().getString(Keys.HIBERNATE_CONNECTION_URL))
                        .setProperty(Environment.USER, Context.getConfig().getString(Keys.HIBERNATE_CONNECTION_USERNAME))
                        .setProperty(Environment.PASS, Context.getConfig().getString(Keys.HIBERNATE_CONNECTION_PASSWORD))
                        .setProperty(Environment.POOL_SIZE, Context.getConfig().getString(Keys.HIBERNATE_CONNECTION_POOL))
                        .setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, Context.getConfig().getString(Keys.HIBERNATE_CURRENT_SESSION_CONTEXT_CLASS))
                        .setProperty(Environment.SHOW_SQL, Context.getConfig().getString(Keys.HIBERNATE_SHOW_SQL));

                configuration.addAnnotatedClass(Expense.class);
                configuration.addAnnotatedClass(Position.class);

                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
