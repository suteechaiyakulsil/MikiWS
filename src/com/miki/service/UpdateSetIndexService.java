package com.miki.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.miki.html.parser.SetElementParser;
import com.miki.model.StockModel;

import dao.StockIndexDao;



public class UpdateSetIndexService extends TimerTask{

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TimerTask tasknew = new UpdateSetIndexService();
		Timer timer = new Timer();
		timer.schedule(tasknew,0, 300000);   
		
	}
	
	@Override
	public void run() {
		
		SetElementParser parser = new SetElementParser();
		List<StockModel> stocks = new ArrayList<StockModel>();
		StockIndexDao dao = new StockIndexDao();
		stocks = parser.getSetIndexElement();
	    dao.updateStock(stocks);
	}  
}
