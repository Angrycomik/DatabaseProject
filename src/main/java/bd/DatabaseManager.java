package bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {
    private static Connection connection;
    private static String artistTable = "artysta";
    private static String songTable = "piosenka";


    public static void setConnection() throws SQLException {
        String url = "jdbc:postgresql://nakedly-defiant-walrus.data-1.use1.tembo.io:5432/postgres";
        String dbase = "postgres";
        String pass = "doHDbfxz5WpIUOEb";

        try {
            connection = DriverManager.getConnection(url, dbase, pass);
          } catch (SQLException se) {
            System.out.println("Brak polaczenia z baza danych.");

          }
    }

    public static Connection getConnection(){
          return connection;
    }
    
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    public static int getArtistID(String name){
        try { 
            PreparedStatement pst = connection.prepareStatement("SELECT id FROM projekt.artysta where nazwa=? ",ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                rs.close();
                pst.close();
                return rs.getInt(1);
            }
        }catch(Exception e){
            Utilities.showError(e);
        }
        return -1;
    }

    public static int getSongLastID(String name){
        try { 
            PreparedStatement pst = connection.prepareStatement("SELECT id FROM projekt.piosenka where nazwa=? ORDER BY id DESC LIMIT 1",ResultSet.CONCUR_UPDATABLE);
            pst.setString(1, name);
            ResultSet rs = pst.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch(Exception e){
            
            Utilities.showError(e);
        }
        return -1;
    }

    public static boolean InDatabase(String name,String table) throws Exception{
        if (!table.equals(artistTable) && !table.equals(songTable)) {
            throw new Exception("Tablica " + table + " nie istnieje");
        }
        try { 
            PreparedStatement pst = connection.prepareStatement("SELECT id FROM projekt." + table + " where nazwa=? ",ResultSet.CONCUR_UPDATABLE);
            pst.setString(1,name);
            ResultSet rs = pst.executeQuery();
            return rs.next();
            }
            catch(SQLException e)  {
                System.out.println(" Blad podczas przetwarzania danych:"+e); 
                return false;  
            }
        }

    public static void insertSong(){
        try { 
            PreparedStatement pstSong = getConnection().prepareStatement( "INSERT INTO projekt.piosenka (nazwa,napisana) VALUES (?,?)" );
            pstSong.setString(1, TempData.getSongName());
            pstSong.setInt(2, TempData.getYear());
            int rows = pstSong.executeUpdate();
            System.out.print("Polecenie -  INSERT Song - ilosc dodanych rekordow: " + String.valueOf(rows));
            insertSongExtraTable(getSongLastID(TempData.getSongName()),getArtistID(TempData.getArtistName()));
        }catch(Exception e){
            Utilities.showError(e);
        }
    }

    public static void insertSongExtraTable(int songID, int artistID){
        try { 
            PreparedStatement pst = getConnection().prepareStatement( "INSERT INTO projekt.artystapiosenka (piosenka_id,artysta_id) VALUES (?,?)" );
            pst.setInt(1, songID);
            pst.setInt(2, artistID);
            int rows = pst.executeUpdate();
            System.out.print("Polecenie -  INSERT SongArtistTable - ilosc dodanych rekordow: " + String.valueOf(rows));
        }catch(Exception e){
            Utilities.showError(e);
        }
    }

    public static void insertArtist(String name, int start,int end){
        try { 
            PreparedStatement pstArtist = DatabaseManager.getConnection().prepareStatement( "INSERT INTO projekt.artysta (nazwa,poczatek_kariery,koniec_kariery) VALUES (?,?,?)" );
            pstArtist.setString(1, name);
            pstArtist.setInt(2, start);
            pstArtist.setInt(3,end);
            int rows = pstArtist.executeUpdate();
            System.out.print("Polecenie -  INSERT Artist - ilosc dodanych rekordow: " + String.valueOf(rows));
        }catch(Exception e){
            Utilities.showError(e);
        }
    }
}

 