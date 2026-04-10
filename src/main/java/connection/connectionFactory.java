package connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author Master
 */
public class ConnectionFactory {
//dnlawnldwa
    //Dados da conexão
        private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
        private static final String URL = "jdbc:mysql://db:3306/estoque_db?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        private static final String USER = "root";
        private static final String PASSWORD = "Marinalva12@12@12@";
    //Métodos
        
        public static Connection getConnection() throws ClassNotFoundException, SQLException{
            Connection con = null;
            
            try {
                Class.forName(DRIVER);
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                
            } catch (NullPointerException e) {
                System.err.println("SQLException: Não foi conectado ao banco.");
            }
            
            return con;
             
        }
}