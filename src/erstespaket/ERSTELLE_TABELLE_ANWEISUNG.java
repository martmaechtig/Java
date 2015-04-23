package erstespaket;

import java.sql.*;

public class ERSTELLE_TABELLE_ANWEISUNG
{
    private final static String ERSTELLE_TABELLE_ANWEISUNG =
            "CREATE TABLE UNSERE_HELDEN" + 
            "(ID INT NOT NULL AUTO_INCREMENT,"
            + "Name VARCHAR(32) NOT NULL,"
            + "Klasse VARCHAR(32) NOT NULL,"
            + "Charisma INT NOT NULL,"
            + "Stärke INT NOT NULL,"
            + "Ausdauer INT NOT NULL,"
            + "Erfahrung INT NOT NULL,"
            + "PRIMARY KEY(ID)"
            + ")";
    
    private final static String LOESCHE_TABLE = 
            "DROP TABLE IF EXISTS UNSERE_HELDEN";
    
    private final static String ABFRAGE = 
            "SELECT * FROM UNSERE_HELDEN";
    
    private static Connection erstelleVerbindung() throws ClassNotFoundException,
            SQLException
    {
        Class.forName("org.gjt.mm.mysql.Driver");
        Connection verbindung = DriverManager.getConnection("jdbc:mysql://localhost:3306/java", "root", "root");
        return verbindung;
    }
    
    
    public static void main(String[] args)
    {
        try(Connection verbindung = erstelleVerbindung()) 
        {
            
            Statement clear = verbindung.createStatement();
            clear.executeUpdate(LOESCHE_TABLE);
            clear.close();
            DatabaseMetaData metaDaten = verbindung.getMetaData();
            ResultSet tabellen = metaDaten.getTables(null, "APP", "UNSERE_HELDEN", null);
            if (!tabellen.next()) {
                Statement anweisung = verbindung.createStatement();
                anweisung.executeUpdate(ERSTELLE_TABELLE_ANWEISUNG);
                anweisung.close();
            }
            
            Statement einfuegeAnweisung = verbindung.createStatement();
            einfuegeAnweisung.executeUpdate
                (
                    "INSERT INTO UNSERE_HELDEN "
                            + "(Name, Klasse, Charisma, Stärke, Ausdauer, Erfahrung) "
                            + "VALUES "
                            + "('SchröDanger', 'Zwergenkrieger', 11,11,11,200),"
                            + "('ZaroDing', 'Elf', 67,67,67,320)"
                );
            einfuegeAnweisung.close();
            
            Statement abfrageAnweisung = verbindung.createStatement();
            ResultSet ergebnis = abfrageAnweisung.executeQuery(ABFRAGE);
            System.out.println("ID   Name   Klasse   Charisma   Stärke   Ausdauer   Erfharung");
            while (ergebnis.next())
            {
                String id = ergebnis.getString("ID");
                String name = ergebnis.getString("Name");
                String klasse = ergebnis.getString("Klasse");
                int charisma = ergebnis.getInt("Charisma");
                int staerke = ergebnis.getInt("Stärke");
                int ausdauer = ergebnis.getInt("Ausdauer");
                int erfahrung = ergebnis.getInt("Erfahrung");
                System.out.println(id + "    " + name + klasse + charisma + staerke + ausdauer + erfahrung);
            }
            
            
            //Statement anweisung = verbindung.createStatement();
            //anweisung.executeUpdate(LOESCHE_TABLE);
            //anweisung.executeUpdate(ERSTELLE_TABELLE_ANWEISUNG);
            //anweisung.close();
        } catch (ClassNotFoundException e) 
        {
            System.err.println("Datenbank-Treiber nicht gefunden");
        } catch (SQLException e) 
        {
            System.err.println("SQL Fehler");
            e.printStackTrace();
        }
    }
}
