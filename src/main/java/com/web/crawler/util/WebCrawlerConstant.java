package com.web.crawler.util;

public class WebCrawlerConstant {
    public static final String SUCCESS             = "Success:";
    public static final String SKIPPED             = "Skipped:";
    public static final String ERROR               = "Error:";
    public static final String HREF                = "href";
    public static final String TYPE                = "text/html";
    public static final String HYPERLINK_TAG       = "a[href]";
    public static final String ERROR_MESSAGE       = "An unknown error occurred while displaying result";
    public static final String FILE_NOTFOUND_ERROR = "The internet json file was not found ";
    public static final String OUTPUT              = "Output for url - ";
    public static final String SEPERATOR           = "-------------------------";
    public static final String END_OF_OUTPUT       = "End of Output";
    public static final String INTERNET_1          = "http://localhost:8080/internet_1/p1.html";
    public static final String INTERNET_2          = "http://localhost:8080/internet_2/p1.html";
    public static final String INTERNET_3          = "http://localhost:8080/internet_3/p1.html";
    public static final String END_TEXT            = "----Thank You----";
    public static final String SELECTION_COMMENT   ="PLease Choose the internet file which you would like the crawl,\n"
                                                        + "1. Internet_1.Json \n"
                                                        + "2. Internet_2.Json \n"
                                                        + "3. Internet_3.Json \n"
                                                        + "4. Exit \n";
    public static final String INVALID            = "Invalid Selection";
    
    
    
    private WebCrawlerConstant() {
    }

}
