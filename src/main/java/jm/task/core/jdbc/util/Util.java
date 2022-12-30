package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/usersdb";
    private static final String USERNAME = "Kosmos";
    private static final String PASSWORD = "Denis@4491";
    private static SessionFactory session;
    private static Connection conn;


    public static Connection getConn()  {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
    public static SessionFactory getSession()  {
        if (session == null) {
            try {
                Configuration config = new Configuration();

                Properties setting = new Properties();
                setting.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                setting.put(Environment.URL, URL);
                setting.put(Environment.USER, USERNAME);
                setting.put(Environment.PASS, PASSWORD);
                setting.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                setting.put(Environment.SHOW_SQL, "true");
                //setting.put(Environment.HBM2DDL_AUTO, "create-drop");
                config.setProperties(setting);
                config.addAnnotatedClass(jm.task.core.jdbc.model.User.class);

                StandardServiceRegistry reg = new StandardServiceRegistryBuilder()
                        .applySettings(config.getProperties()).build();
                session = config.buildSessionFactory(reg);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return session;
    }
}