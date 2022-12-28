package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

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
        Session ses = null;
        try {
            ses = Util.getSession().openSession();
            ses.beginTransaction();
            ses.createSQLQuery(sq).executeUpdate();
            ses.getTransaction().commit();
        } catch (Exception e) {
            assert ses != null;
            ses.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            assert ses != null;
            ses.close();
        }
    }


    @Override
    public void dropUsersTable() {
        Session ses = null;
        try {
            ses = Util.getSession().openSession();
            ses.beginTransaction();
            ses.createSQLQuery("DROP TABLE IF EXISTS allusers;").executeUpdate();
            ses.getTransaction().commit();
        } catch (Exception e){
            assert ses != null;
            ses.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            assert ses != null;
            ses.close();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session ses = null;
        try {
            ses = Util.getSession().openSession();
            ses.beginTransaction();
            ses.createSQLQuery("INSERT INTO allusers (name, lastName, age) VALUES (:name,:lastName,:age)")
                    .setParameter("name", name)
                    .setParameter("lastName", lastName)
                    .setParameter("age", age)
                    .executeUpdate();
            ses.getTransaction().commit();
        } catch (Exception e) {
            assert ses != null;
            ses.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            assert ses != null;
            ses.close();
        }
    }

    @Override
    public void removeUserById(long id) {

    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public void cleanUsersTable() {

    }
}
