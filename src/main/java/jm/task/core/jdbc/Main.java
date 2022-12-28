package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        User user = new User("Denis", "Romanov", (byte) 32);
        User user2 = new User("Sonya", "Kurkova", (byte) 47);
        User user3 = new User("Kristina", "Ivanova", (byte) 15);
        User user4 = new User("Zhenya", "Matunkina", (byte) 44);
        List<User> use = new ArrayList<>();
        use.add(user);
        use.add(user2);
        use.add(user3);
        use.add(user4);
        //UserService usserv = new UserServiceImpl(new UserDaoJDBCImpl());
        UserService usserv = new UserServiceImpl(new UserDaoHibernateImpl());
        usserv.createUsersTable();
        use.forEach(t -> usserv.saveUser(t.getName(), t.getLastName(), t.getAge()));
//       usserv.getAllUsers().forEach(System.out::println);
//       usserv.cleanUsersTable();
//        usserv.dropUsersTable();

    }
}
