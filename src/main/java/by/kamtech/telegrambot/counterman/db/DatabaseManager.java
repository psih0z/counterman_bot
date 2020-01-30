package by.kamtech.telegrambot.counterman.db;

import by.kamtech.telegrambot.counterman.dao.Expense;
import by.kamtech.telegrambot.counterman.dao.Position;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class DatabaseManager {

    private static volatile DatabaseManager instance;

    private static volatile Session session;

    public static DatabaseManager getInstance() {
        final DatabaseManager currentInstance;
        if (instance == null) {
            synchronized (DatabaseManager.class) {
                if (instance == null) {
                    instance = new DatabaseManager();
                }
                currentInstance = instance;
            }
        } else {
            currentInstance = instance;
        }
        return currentInstance;
    }

    public DatabaseManager() {
        session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }

    public void saveExpense(int userId, long chatId, double amount) {
        Transaction tx = session.beginTransaction();
        session.save(new Expense(userId, chatId, amount));
        tx.commit();
    }

    public double sum(int userId) {
        Query query = session.createQuery("select sum(amount) from Expense where userId = :userId and chatId = :chatId");
        query.setParameter("userId", userId);
        return (Double) query.uniqueResult();
    }

    public Optional<Position> getObjectPosition(int userId, long chatId) {
        Query query = session.createQuery("from Position where userId = :userId and chatId = :chatId");
        query.setParameter("userId", userId);
        query.setParameter("chatId", chatId);

        List<Position> positions = query.list();
        return positions.stream().findFirst();
    }

    public int getPosition(int userId, long chatId) {
        Position position = getObjectPosition(userId, chatId).orElse(new Position());
        return position.getPosition();
    }

    public void insertPosition(int userId, long chatId, int pos) {
        Position position = getObjectPosition(userId, chatId).orElse(new Position(userId, chatId, pos));
        position.setPosition(pos);

        Transaction tx = session.beginTransaction();
        session.saveOrUpdate(position);
        tx.commit();
    }

}
