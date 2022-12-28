package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        String sq = "CREATE TABLE IF NOT EXISTS `usersdb`.`allusers` (" +
                "  `id` BIGINT(4) NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(50) NOT NULL," +
                "  `lastName` VARCHAR(70) NOT NULL," +
                "  `age` INT NOT NULL," +
                "  PRIMARY KEY (`id`)," +
                "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE);";
        try (Connection con = Util.getConn(); Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            st.execute(sq);
            if (!con.isClosed()) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {

            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }


    public void dropUsersTable() {
        try (Connection con = Util.getConn(); Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            st.execute("DROP TABLE IF EXISTS allusers;");
            if (!con.isClosed()) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }


    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConn(); PreparedStatement st = con.prepareStatement("INSERT INTO allusers (name, lastName, age) VALUES (?,?,?)")) {
            con.setAutoCommit(false);
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.execute();
            System.out.println("User с именем - " + name + " добавлен в базу");
            if (!con.isClosed()) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConn(); PreparedStatement st = con.prepareStatement("DELETE FROM allusers WHERE id = ?")) {
            con.setAutoCommit(false);
            st.setLong(1, id);
            st.execute();
            if (!con.isClosed()) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try (Connection con = Util.getConn(); Statement st = con.createStatement()) {
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

    public void cleanUsersTable() {
        try (Connection con = Util.getConn(); Statement st = con.createStatement()) {
            con.setAutoCommit(false);
            st.execute("DELETE FROM allusers");
            if (!con.isClosed()) {
                con.commit();
            } else {
                con.rollback();
            }
            con.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Ошибка БД");
            e.printStackTrace();
        }
    }
}


