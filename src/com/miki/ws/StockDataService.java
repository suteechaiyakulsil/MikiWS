package com.miki.ws;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.*;

import com.miki.model.StockModel;

import dao.StockIndexDao;
 
@Path("/stockindexservice")
public class StockDataService {
	
	  @Path("{stockIndex}")
	  @GET
	  @Produces("application/json")
	  public Response stockDetail(@PathParam("stockIndex") String stockIndex) throws JSONException {
		System.out.println(stockIndex);
		JSONObject jsonObject = new JSONObject();
		StockIndexDao dao = new StockIndexDao();
		
		List<StockModel> stocks = new ArrayList<StockModel>();
		stocks = dao.getStockByIndex(stockIndex);
		System.out.println(stocks.size());
		jsonObject.put("result",stocks);
		
		return Response.status(200).entity(jsonObject.get("result").toString()).build();
	  }
	  
	  @Path("{stockIndex}/{lastUpdate}/{last}")
	  @GET
	  @Produces("application/json")
	  public Response updateStoc(@PathParam("stockIndex") String stockIndex,
			  					 @PathParam("lastUpdate") String lastUpdate,
			  					 @PathParam("last") String last) throws JSONException, ParseException {
		
		JSONObject jsonObject = new JSONObject();
		StockIndexDao dao = new StockIndexDao();
				
		StockModel stock = new StockModel();
		StockModel stockResult = new StockModel();
		stock.setIndex(stockIndex);
		stock.setLast(last);
		DateFormat df = new SimpleDateFormat("yyy-MM-dd-HH.mm");
		java.util.Date javaDate =  df.parse(lastUpdate);
		long javaTime = javaDate.getTime();
		
		java.sql.Timestamp sqlTimestamp = new java.sql.Timestamp((100000 * (javaTime/ 100000)));
		
		stock.setLastUpdate(sqlTimestamp);
		dao.updateStockByLastUpdateAndIndex(stock);
		
		stockResult = dao.getStockByLastUpdateAndIndex(stock);
		jsonObject.put("SetIndex",stockResult.getIndex());
		jsonObject.put("Last",stockResult.getLast());
		jsonObject.put("LastUpdate",stockResult.getLastUpdate());
		
		return Response.status(200).entity(jsonObject.toString()).build();
	  }

}