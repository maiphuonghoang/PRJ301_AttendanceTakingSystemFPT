/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;

/**
 *
 * @author ADMIN
 */
public class AccountDBContext extends DBContext{
    public Account getAccount(String username, String password) {
        try {
            String sql = "SELECT [username], [password] from [Account] where username = ? and password = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, password);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Account account = new Account(rs.getString(1), rs.getString(2));
                return account;
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public int getNumberOfRoles(String username, String url) {
        try {
            String sql = "select COUNT(*) AS Total from Account a inner join Account_Role ag on a.username = ag.username\n" +
                            "inner join [Role] g on g.gid = ag.gid\n" +
                            "inner join [Role_Feature] gf on gf.gid = g.gid\n" +
                            "inner join [Feature] f on gf.fid = f.fid\n" +
                            "where a.username = ? and f.url = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            stm.setString(2, url);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getInt("Total");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AccountDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
