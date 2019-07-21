/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter.Entry;

/**
 *
 * @author Camilo
 */
public class DBConnect {
    private Connection conn;
    private String url;
    private String user;
    private String pwd;
    private String pathDriver;
    
    public DBConnect() {
        this.url = "jdbc:postgresql://localhost:5432/Flores";
        this.user = "postgres";
        this.pwd = "1234";
        this.pathDriver = "org.postgresql.Driver";
        this.connect();
    }

    public DBConnect(Connection conn, String url, String user, String pwd, String pathDriver) {
        this.url = url;
        this.user = user;
        this.pwd = pwd;
        this.pathDriver = pathDriver;
        this.connect();
    }
    
    private void connect(){
         try {
            Class.forName(this.getPathDriver());
            this.setConn(DriverManager.getConnection(getUrl(), getUser(), getPwd()));
            System.out.println("Successfull connection");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Driver not located");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Server not found");
        }
    }
    
    public void closeConnection(){
        try {
            this.conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ResultSet executeQuery(String query, LinkedList<String> params){
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i+1, params.get(i));
            }
            return stmt.executeQuery();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public boolean executeQueryWithOutResultSet(String query, LinkedList<String> params){
        try {
            PreparedStatement stmt = this.conn.prepareStatement(query);
            for (int i = 0; i < params.size(); i++) {
                stmt.setString(i+1, params.get(i));
            }
            return stmt.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBConnect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    /**
     * @return the pathDriver
     */
    public String getPathDriver() {
        return pathDriver;
    }

    /**
     * @param pathDriver the pathDriver to set
     */
    public void setPathDriver(String pathDriver) {
        this.pathDriver = pathDriver;
    }
}