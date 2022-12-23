package jm.task.core.jdbc.dao;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        try(Statement st = new Util().getConn().createStatement()) {
            st.execute(sq);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try(Statement st = new Util().getConn().createStatement()) {
            st.executeUpdate("DROP TABLE IF EXISTS allusers;");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try(PreparedStatement st = new Util().getConn().prepareStatement("INSERT INTO allusers (name, lastName, age) VALUES (?,?,?)")) {
            st.setString(1, name);
            st.setString(2, lastName);
            st.setByte(3, age);
            st.execute();
            System.out.println("User с именем - "+name+" добавлен в базу");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try(Statement st = new Util().getConn().createStatement()) {
            st.executeUpdate("DELETE FROM allusers WHERE id = "+id+";");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> user = new ArrayList<>();
        try (Statement st = new Util().getConn().createStatement(); ResultSet resultSet = st.executeQuery("SELECT * FROM allusers")) {
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String secondName = resultSet.getString("lastName");
                byte age = (byte) (resultSet.getInt("age"));
                user.add( new User(name,secondName,age));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    public void cleanUsersTable() {
        try(Statement st = new Util().getConn().createStatement()) {
            st.executeUpdate("DELETE FROM allusers");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
