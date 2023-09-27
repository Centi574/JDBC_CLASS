package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }
    @Override
    public void createUsersTable() {
        try (SessionFactory factory = Util.getSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            session.createSQLQuery("CREATE TABLE IF NOT EXISTS user (\n" +
                                   "    id BIGINT PRIMARY KEY AUTO_INCREMENT,\n" +
                                   "    name VARCHAR(128) NOT NULL ,\n" +
                                   "    lastname VARCHAR(128) NOT NULL ,\n" +
                                   "    age SMALLINT CHECK ( age > 0 ) NOT NULL\n" +
                                   ")").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void dropUsersTable() {
        try (SessionFactory factory = Util.getSessionFactory()) {
            Session session = factory.getCurrentSession();
            session.beginTransaction();

            session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();

            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (SessionFactory factory = Util.getSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeUserById(long id) {

        try (SessionFactory factory = Util.getSessionFactory()) {
            Session session = factory.getCurrentSession();

            session.beginTransaction();
            session.delete(session.get(User.class, id));
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (SessionFactory factory = Util.getSessionFactory()) {

            Session session = factory.getCurrentSession();
            session.beginTransaction();

            List<User> fromUser = session.createQuery("from User").getResultList();
            session.getTransaction().commit();
            System.out.println(fromUser);

            return fromUser;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (SessionFactory factory = Util.getSessionFactory()) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        session.createQuery("delete User").executeUpdate();
        session.getTransaction().commit();
        }

    }
}
