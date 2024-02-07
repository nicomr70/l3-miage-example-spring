package fr.uga.l3miage.tp1.exo1.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DatabaseScriptExecutor {

    public static void createDatabaseExo1() {
        try {
            // Chargement du Driver JDBC
            Class.forName("org.postgresql.Driver");
            // Établissement de la connexion
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "postgres";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Création d'une déclaration
            Statement statement = connection.createStatement();


            // Exécution d'une requête de sélection
            String query = """
                            create table bike_triumph
                    (
                        immat             varchar(255) not null
                            primary key,
                        is_automatic      boolean,
                        capcity_cc        integer,
                        ciruculation_date date,
                        cylinder_number   varchar(255),
                        have_shifter      boolean,
                        shifter_type      integer
                    );
                    """;
            statement.execute(query);
            statement.close();

            statement = connection.createStatement();

            String query2 = """
                     create table car_porshe
                     (
                         immat varchar(8) not null primary key,
                         circulation_date  date,
                         cylinder_capacity double precision,
                         power             integer,
                         power_type        integer,
                         torque            integer,
                         weight            integer,
                         weight_unity      varchar(255)
                     );
                    """;
            statement.execute(query2);

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dropDatabase(){
        try {
            // Chargement du Driver JDBC
            Class.forName("org.postgresql.Driver");
            // Établissement de la connexion
            String url = "jdbc:postgresql://localhost:5432/postgres";
            String username = "postgres";
            String password = "postgres";
            Connection connection = DriverManager.getConnection(url, username, password);

            // Création d'une déclaration
            Statement statement = connection.createStatement();


            // Exécution d'une requête de sélection
            String query = "drop table bike_triumph";
            statement.execute(query);
            statement.close();

            statement = connection.createStatement();
            String query2 = "drop table car_porshe";
            statement.execute(query2);

            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
