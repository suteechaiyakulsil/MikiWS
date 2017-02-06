package com.miki.html.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.miki.model.StockModel;

public class SetElementParser {
	
   List<StockModel> setList = new ArrayList<StockModel>();
   
   public List<StockModel> getSetIndexElement() {
	 
	   String html = "http://marketdata.set.or.th/mkt/sectorialindices.do?language=en&country=US";
      try {
         Document doc = Jsoup.connect(html).get();
         Elements tableElements = doc.select("#maincontent > div > div:nth-child(3) > div > div > table");         
         Elements tableRowElements = tableElements.select(":not(thead) tr");

         for (int i = 0; i < tableRowElements.size(); i++) {
            Element row = tableRowElements.get(i);         
            Elements rowItems = row.select("td");
            for (int j = 0; j < rowItems.size(); j++) {
            	
            	if (rowItems.get(j).text().equals("SET")) {
            		
            		addData(rowItems.get(j).text(), rowItems.get(j+1).text());
            		
            	} else if(rowItems.get(j).text().equals("SET50")) {
            		
            		addData(rowItems.get(j).text(), rowItems.get(j+1).text());
            		
            	} else if(rowItems.get(j).text().equals("SET100")) {
            		
            		addData(rowItems.get(j).text(), rowItems.get(j+1).text());
            		
            	}
            }
            	
         }

      } catch (IOException e) {
         e.printStackTrace();
      }
	return setList;
   }
   private List<StockModel> addData(String index, String last){
	   StockModel pair = new StockModel();
	   pair.setIndex(index);
	   pair.setLast(last);
	   setList.add(pair);
	   return setList;
   }
}