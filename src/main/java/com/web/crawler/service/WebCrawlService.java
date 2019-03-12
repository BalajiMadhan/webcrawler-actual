package com.web.crawler.service;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.crawler.util.WebCrawlerConstant;

public class WebCrawlService {
    
    private Logger logger = LoggerFactory.getLogger(WebCrawlService.class);
    
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
    private List<String> links = new LinkedList<>();
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesVisitedSuccessfully = new LinkedList<>();
    private Set<String> repeatedPages = new HashSet<>();
    private List<String> pagesToVisit = new LinkedList<>();
    private List<String> pagesFailedToVisit = new ArrayList<>();
    
    public void startWebCrawl(String webUrl) {
        boolean isNextUrlPresent=true;
        while(isNextUrlPresent) {
            String currentUrl;
            if(this.pagesToVisit.isEmpty()) {
                currentUrl = webUrl;
                this.pagesVisited.add(webUrl);
            }else {
                currentUrl = this.fetchingNextUrl();
            }
            if(currentUrl == null) {
                isNextUrlPresent=false;
            }else {
                links=new LinkedList<>();
                boolean success = this.webCrawlLogic(currentUrl);
                if(success) {
                    pagesVisitedSuccessfully.add(currentUrl);
                }else {
                    pagesFailedToVisit.add(currentUrl);
                }
                this.pagesToVisit.addAll(this.links);
                if(pagesToVisit.isEmpty()) {
                    isNextUrlPresent=false;
                }
            }
        }
        
        webCrawlResultDisplay(System.out,webUrl);
    }
    
    

    
    
    private String fetchingNextUrl(){
        String nextUrl = null;
        boolean nextUrlCheck=true;
        if(!pagesToVisit.isEmpty()) {
            nextUrl = this.pagesToVisit.remove(0);  
        }
        while(nextUrlCheck){
            if(this.pagesVisited.contains(nextUrl)) {
                repeatedPages.add(nextUrl);
                if(!pagesToVisit.isEmpty()) {
                    nextUrl = this.pagesToVisit.remove(0);  
                }else {
                    nextUrl = null;
                }
            }else {
                this.pagesVisited.add(nextUrl);
                nextUrlCheck=false;
            }
        }
        return nextUrl;
    }

    
    public boolean webCrawlLogic(String url){
        try{
            Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            if(!connection.response().contentType().contains(WebCrawlerConstant.TYPE)){
                return false;
            }
            Elements linksOnPage = htmlDocument.select(WebCrawlerConstant.HYPERLINK_TAG);
            for(Element link : linksOnPage)
            {
                this.links.add(link.absUrl(WebCrawlerConstant.HREF));
            }
            return true;
        }
        catch(IOException e){
           return false;
        }
    }

    
    public void webCrawlResultDisplay(PrintStream out, String webUrl) {
        ObjectMapper mapper = new ObjectMapper();
        out.println(WebCrawlerConstant.OUTPUT + webUrl);
        try {
            out.println(WebCrawlerConstant.SUCCESS);
            out.println(mapper.writeValueAsString(pagesVisitedSuccessfully));
            out.println();
            out.println(WebCrawlerConstant.SKIPPED);
            out.println(mapper.writeValueAsString(repeatedPages));
            out.println();
            out.println(WebCrawlerConstant.ERROR);
            out.println(mapper.writeValueAsString(pagesFailedToVisit));
            out.flush();
        } catch (IOException e) {
            logger.error(WebCrawlerConstant.ERROR_MESSAGE + e.getMessage());
        }
        out.println(WebCrawlerConstant.SEPERATOR +WebCrawlerConstant.END_OF_OUTPUT+WebCrawlerConstant.SEPERATOR );
    }
    
}
