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
        try (Connection con = Util.getConn()) {
            con.setAutoCommit(false);
            try (Statement st = con.createStatement()) {
                st.execute(sq);
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Ошибка БД");
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection con = Util.getConn()) {
            con.setAutoCommit(false);
            try (Statement st = con.createStatement()) {
                st.execute("DROP TABLE IF EXISTS allusers;");
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Ошибка БД");
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection con = Util.getConn()) {
            con.setAutoCommit(false);
            try (PreparedStatement st = con.prepareStatement("INSERT INTO allusers (name, lastName, age) VALUES (?,?,?)")) {
                st.setString(1, name);
                st.setString(2, lastName);
                st.setByte(3, age);
                st.execute();
                System.out.println("User с именем - " + name + " добавлен в базу");
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Ошибка БД");
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection con = Util.getConn()) {
            con.setAutoCommit(false);
            try (PreparedStatement st = con.prepareStatement("DELETE FROM allusers WHERE id = ?")) {
                st.setLong(1, id);
                st.execute();
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Ошибка БД");
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try (Connection con = Util.getConn()) {
            con.setAutoCommit(false);

            try (Statement st = con.createStatement(); ResultSet resultSet = st.executeQuery("SELECT * FROM allusers")) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    String secondName = resultSet.getString("lastName");
                    byte age = (byte) (resultSet.getInt("age"));
                    user.add(new User(id, name, secondName, age));
                }
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Ошибка БД");
            }
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void cleanUsersTable() {
        try (Connection con = Util.getConn()) {
            con.setAutoCommit(false);

            try (Statement st = con.createStatement()) {
                st.execute("DELETE FROM allusers");
            } catch (SQLException e) {
                con.rollback();
                con.setAutoCommit(true);
                System.out.println("Ошибка БД");
            }

            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


