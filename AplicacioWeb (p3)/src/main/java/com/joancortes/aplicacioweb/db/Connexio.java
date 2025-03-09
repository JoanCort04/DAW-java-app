/*
 * Paquet: com.joancortes.aplicacioweb.db
 *
 * Descripció:
 * Aquest paquet conté classes relacionades amb la connexió a la base de dades
 * i la gestió de la comunicació entre l'aplicació web i la base de dades. 
 * Inclou classes que proporcionen funcionalitats per establir connexions 
 * a la base de dades i executar consultes SQL.
 *
 * Classes:
 * - Connexio: Classe que gestiona la connexió a la base de dades i proporciona
 *   mètodes per obtenir una connexió a la base de dades utilitzant les credencials
 *   configurades en el fitxer db.properties.
 *
 * Ús:
 * Aquest paquet es fa servir principalment per gestionar la connexió a la base de dades
 * des de diferents parts de l'aplicació web, com les funcionalitats de consulta i actualització
 * de dades.
 */
package com.joancortes.aplicacioweb.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 * Classe per gestionar la connexió a la base de dades.
 *
 * Aquesta classe s'encarrega de carregar les propietats de connexió des d'un fitxer
 * de configuració (db.properties) i establir la connexió amb la base de dades utilitzant
 * el driver de MariaDB/MySQL.
 */
public class Connexio {
    private static String url;
    private static String usuari;
    private static String contrasenya;

    /**
     * Bloc estàtic per carregar les propietats de la base de dades.
     * Aquest bloc s'executa automàticament quan la classe es carrega i estableix
     * la connexió amb la base de dades mitjançant les propietats especificades al fitxer
     * db.properties.
     */
    static {
        try (InputStream inputStream = Connexio.class.getClassLoader().getResourceAsStream("db.properties")) {
            // Verifica si el fitxer db.properties existeix al classpath
            if (inputStream == null) {
                throw new IOException("No s'ha trobat el fitxer db.properties en el classpath.");
            }

            Properties properties = new Properties();
            properties.load(inputStream);

            // Carrega les propietats de la base de dades
            url = properties.getProperty("db.url");
            usuari = properties.getProperty("db.usuario");
            contrasenya = properties.getProperty("db.contrasenya");

            // Comprova si alguna de les propietats és null
            if (url == null || usuari == null || contrasenya == null) {
                throw new IOException("Alguna de les propietats de la base de dades no està definida.");
            }

            // Carrega el driver de MariaDB/MySQL
            try {
                Class.forName("org.mariadb.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new IOException("Driver de MariaDB/MySQL no trobat: " + e.getMessage(), e);
            }

        } catch (IOException e) {
            // Imprimeix l'error si no es poden carregar les propietats
            System.err.println("Error al carregar les propietats de la base de dades: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Retorna una connexió a la base de dades utilitzant les propietats configurades.
     *
     * @return Connexió a la base de dades.
     * @throws SQLException Si no es pot establir la connexió.
     */
    public static Connection getConnexio() throws SQLException {
        // Verifica si les propietats de connexió estan configurades correctament
        if (url == null || usuari == null || contrasenya == null) {
            throw new SQLException("No s'ha configurat correctament la connexió a la base de dades.");
        }
        return DriverManager.getConnection(url, usuari, contrasenya);
    }

    /**
     * Mètode principal per provar la connexió a la base de dades.
     *
     * Aquest mètode prova de crear una connexió a la base de dades i imprimeix un missatge
     * segons si la connexió es va establir correctament o no.
     *
     * @param args Els arguments de la línia de comandament (no utilitzats en aquest cas).
     */
    public static void main(String[] args) {
        try (Connection conn = getConnexio()) {
            if (conn != null) {
                System.out.println("Connexió establerta correctament!");
            } else {
                System.out.println("Connexió no s'ha pogut establir.");
            }
        } catch (SQLException e) {
            // Captura i imprimeix errors de connexió
            System.err.println("Error en la connexió: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
