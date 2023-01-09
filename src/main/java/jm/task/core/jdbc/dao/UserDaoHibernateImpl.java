package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;


public class UserDaoHibernateImpl implements UserDao {

    public Transaction tran = null;
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        String sq = "CREATE TABLE IF NOT EXISTS usersdb.allusers (" +
                "  id BIGINT(20) NOT NULL AUTO_INCREMENT," +
                "  name VARCHAR(50) NOT NULL," +
                "  lastName VARCHAR(70) NOT NULL," +
                "  age INT NOT NULL," +
                "  PRIMARY KEY (id)," +
                "  UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE);";
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
        try (Session ses = Util.getSession().openSession()) {
            tran = ses.beginTransaction();
            ses.save(new User(name, lastName, age));
            tran.commit();
            System.out.println("User с именем - " + name + " добавлен в базу");
        } catch (Exception e) {
            if (tran != null) tran.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
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
           return ses.createQuery("from User", User.class).list();

        }
    }

    @Override
    public void cleanUsersTable() {
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
