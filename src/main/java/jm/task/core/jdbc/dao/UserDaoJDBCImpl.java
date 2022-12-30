package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection con = Util.getConn();
    public UserDaoJDBCImpl()  {
    }

    public void createUsersTable() throws SQLException {
        String sq = "CREATE TABLE IF NOT EXISTS `usersdb`.`allusers` (" +
                "  `id` BIGINT(4) NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(50) NOT NULL," +
                "  `lastName` VARCHAR(70) NOT NULL," +
                "  `age` INT NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        try (Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            st.execute(sq);
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Ошибка БД");
            e.printStackTrace();
        } finally {
            con.setAutoCommit(true);
        }
    }


    public void dropUsersTable() throws SQLException {
        try (Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            st.execute("DROP TABLE IF EXISTS allusers;");
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Ошибка БД");
            e.printStackTrace();
        } finally {
            con.setAutoCommit(true);
        }
    }


    public void saveUser(String name, String lastName, byte age) throws SQLException {
        try (PreparedStatement st = con.prepareStatement("INSERT INTO allusers ( name, lastName, age) VALUES (?,?,?)")) {
            con.setAutoCommit(false);
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.execute();
            con.commit();
            System.out.println("User с именем - " + name + " добавлен в базу");
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) throws SQLException {
        try (PreparedStatement st = con.prepareStatement("DELETE FROM allusers WHERE id = ?")) {
            con.setAutoCommit(false);
            st.setLong(1, id);
            st.execute();
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try (Statement st = con.createStatement()) {
            ResultSet resultSet = st.executeQuery("SELECT * FROM allusers");
            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                String secondName = resultSet.getString("lastName");
                byte age = (byte) (resultSet.getInt("age"));
                User u = new User(name, secondName, age);
                u.setId(id);
                user.add(u);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
        return user;
    }

    public void cleanUsersTable() throws SQLException {
        try (Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            st.execute("DELETE FROM allusers");
            con.commit();
        } catch (SQLException e) {
            con.rollback();
            System.out.println("Ошибка БД");
            e.printStackTrace();
        } finally {
            con.setAutoCommit(true);
        }
    }
}


