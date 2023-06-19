package org.lessons.java.nations;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/nations";
        String user = "root";
        String password = "";


        try (Connection connection = DriverManager.getConnection(url,user,password)){
            System.out.println(connection.getCatalog());
            Scanner scanner = new Scanner(System.in);

            String query = """
                            SELECT c.name AS country_name, c.country_id, r.name AS region_name, cn.name AS continent_name
                            FROM countries c
                            JOIN regions r ON c.region_id = r.region_id
                            JOIN continents cn ON r.continent_id = cn.continent_id
                            WHERE c.name LIKE ?
                            ORDER BY c.name
                            """;

            try(PreparedStatement ps = connection.prepareStatement(query)){
                System.out.println("Cosa ti sto chiedendo?");
                String userInput = scanner.nextLine();
                ps.setString(1,"%" + userInput + "%");
                try(ResultSet rs = ps.executeQuery()){
                    while(rs.next()){
                        String name = rs.getString("country_name");
                        String country_id = rs.getString("country_id");
                        String region_name = rs.getString("region_name");
                        String continent_name = rs.getString("continent_name");

                        System.out.println("Nation name: " + name);
                        System.out.println("Nation ID: " + country_id);
                        System.out.println("Region name: " + region_name);
                        System.out.println("Continent name: " + continent_name);
                        System.out.println("**************************");
                    }
                }
            }
        } catch(SQLException e){
            System.out.print("Unable to connect to DataBase 'Nations'");
            e.printStackTrace();
        }
    }
}