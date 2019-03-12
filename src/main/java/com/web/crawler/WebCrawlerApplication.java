package com.web.crawler;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.web.crawler.service.WebCrawlService;
import com.web.crawler.util.WebCrawlerConstant;

@SpringBootApplication
public class WebCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebCrawlerApplication.class, args);
		WebCrawlService webCrawlService;
		List<String> urlList=new LinkedList<>();
		urlList.add(WebCrawlerConstant.INTERNET_1);
		urlList.add(WebCrawlerConstant.INTERNET_2);
		urlList.add(WebCrawlerConstant.INTERNET_3);
		 StringBuilder userDisplayMessage=new StringBuilder();
		 userDisplayMessage.append(WebCrawlerConstant.SELECTION_COMMENT);
		while(true) {
		    webCrawlService=new WebCrawlService();
		    Scanner scanner = new Scanner(System.in);
                    System.out.print(userDisplayMessage.toString());
                    String input=scanner.next();
                    int selectedNumber=Integer.parseInt(input);
                    selectedNumber--;
                    if(selectedNumber > 2) {
                        System.out.println(WebCrawlerConstant.END_TEXT);
                        break;
                    }else {
                        webCrawlService.startWebCrawl(urlList.get(selectedNumber));  
                    }
                    
		}
		   
		
	      
		
		
	}

}
