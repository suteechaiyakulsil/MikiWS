package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.utils.Util;
import com.miki.model.StockModel;

import java.util.ArrayList;
import java.util.List;


public class StockIndexDao {

	private Connection connection;

    public StockIndexDao() {
        connection = Util.getConnection();
    }

    public void updateStock(List<StockModel> stocks) {
    	try {
   	     for (StockModel stock : stocks) {
   	    	 
   	    	 	PreparedStatement preparedStatement = connection.prepareStatement("insert into Stock(SetIndex,Last,LastUpdate) values (?, ?, ? )");
//   	    	 			+ "ON DUPLICATE KEY UPDATE Last=VALUES(Last), LastUpdate=VALUES(LastUpdate)");
   	            
   	            preparedStatement.setString(1, stock.getIndex());
   	            preparedStatement.setString(2, stock.getLast());
   	            
   	            java.util.Date javaDate = new java.util.Date();
   	            long javaTime = javaDate.getTime();
   	            
   	            java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp((100000 * (javaTime/ 100000)));
   	            
   	            preparedStatement.setTimestamp(3, sqlTimestamp);
   	            
   	            preparedStatement.executeUpdate();
   	            System.out.println("Update data success for "+stock.getIndex() + " at "+sqlTimestamp);
   	        
   	     	}     
   	    } catch (SQLException e) {
               e.printStackTrace();
           } 
    }

   

    public List<StockModel> getAllStock() {
    	List<StockModel> stocks = new ArrayList<StockModel>();
        try {
            PreparedStatement preparedStatement = connection.
                    prepareStatement("select * from Stock ");
            
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
            	StockModel stock = new StockModel();
            	stock.setIndex(rs.getString("SetIndex"));
            	stock.setLast(rs.getString("Last"));
            	stock.setLastUpdate(rs.getTimestamp("LastUpdate"));
            	stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }
    
    public List<StockModel> getStockByIndex(String stockIndex) {
    	List<StockModel> stocks = new ArrayList<StockModel>();
        try {
        	
            PreparedStatement preparedStatement = connection.
            prepareStatement("SELECT * from Stock WHERE LastUpdate >= CURDATE() AND SetIndex=?");
            preparedStatement.setString(1, stockIndex);
            ResultSet rs = preparedStatement.executeQuery();
           
            while (rs.next()) {
            	StockModel stock = new StockModel();
            	stock.setIndex(rs.getString("SetIndex"));
            	stock.setLast(rs.getString("Last"));
            	stock.setLastUpdate(rs.getTimestamp("LastUpdate"));
            	stocks.add(stock);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return stocks;
    }
    
    public void updateStockByLastUpdateAndIndex(StockModel stock) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update Stock SET Last=? where SetIndex=? AND LastUpdate=?");
            
            preparedStatement.setString(1, stock.getLast());
            preparedStatement.setString(2, stock.getIndex());
            preparedStatement.setTimestamp(3, stock.getLastUpdate());
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public StockModel getStockByLastUpdateAndIndex(StockModel stock) {
    	StockModel stockResult = new StockModel();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * FROM Stock where Last=? AND SetIndex=? AND LastUpdate=?");
            
            preparedStatement.setString(1, stock.getLast());
            preparedStatement.setString(2, stock.getIndex());
            preparedStatement.setTimestamp(3, stock.getLastUpdate());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while (rs.next()) {
            	
            	stockResult.setIndex(rs.getString("SetIndex"));
            	stockResult.setLast(rs.getString("Last"));
            	stockResult.setLastUpdate(rs.getTimestamp("LastUpdate"));
            	
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stockResult;
    }
}
