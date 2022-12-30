package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sq = "CREATE TABLE IF NOT EXISTS usersdb.allusers (" +
                "  id BIGINT(4) NOT NULL AUTO_INCREMENT," +
                "  name VARCHAR(50) NOT NULL," +
                "  lastName VARCHAR(70) NOT NULL," +
                "  age INT NOT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);";
        Transaction tran = null;
        try (Session ses = Util.getSession().openSession()) {
            tran = ses.beginTransaction();
            ses.createSQLQuery(sq).executeUpdate();
            tran.commit();
        } catch (Exception e) {
            if (tran != null) tran.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        Transaction tran = null;
        try (Session ses = Util.getSession().openSession()) {
            tran = ses.beginTransaction();
            ses.createSQLQuery("DROP TABLE IF EXISTS allusers;")
                    .executeUpdate();
            tran.commit();
        } catch (Exception e) {
            if (tran != null) tran.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction tran = null;
        try (Session ses = Util.getSession().openSession()) {
            tran = ses.beginTransaction();
            ses.createSQLQuery("INSERT INTO allusers (name, lastName, age) VALUES (:name,:lastName,:age)")
                    .setParameter("name", name)
                    .setParameter("lastName", lastName)
                    .setParameter("age", age)
                    .executeUpdate();
            tran.commit();
            System.out.println("User с именем - " + name + " добавлен в базу");
        } catch (Exception e) {
            if (tran != null) tran.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Transaction tran = null;
        try (Session ses = Util.getSession().openSession()) {
            tran = ses.beginTransaction();
            User user = ses.get(User.class, id);
            ses.delete(user);
            tran.commit();
        } catch (Exception e) {
            if (tran != null) tran.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session ses = Util.getSession().openSession()) {
            return ses.createQuery("from User").list();

        }
    }

    @Override
    public void cleanUsersTable() {
        Transaction tran = null;
        try (Session ses = Util.getSession().openSession()) {
            tran = ses.beginTransaction();
            ses.createQuery("delete User");
            tran.commit();
        } catch (Exception e) {
            if (tran != null) tran.rollback();
            e.printStackTrace();
        }
    }
}
