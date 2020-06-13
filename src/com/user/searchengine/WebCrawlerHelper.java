package com.user.searchengine;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.print.Doc;
import java.util.*;
import java.io.*;

public class WebCrawlerHelper {

    // USER_AGENT  - for browser simulation
    private static final String USER_AGENT =
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

    private List<String> links = new LinkedList<>();
    private Document htmlDocument;


    // this method crawls the web makes http request check reponse gathers links

    public boolean crawl(String url){

        try {
            Connection connection  = Jsoup.connect(url).userAgent(USER_AGENT);
            Document htmlDocument = connection.get();
            this.htmlDocument = htmlDocument;
            if(connection.response().statusCode() == 200){
                {
                    System.out.println("\n**Visiting** Received web page at " + url);
                }

                // check for pages other than HTML and return false if not HTML
                if(!connection.response().contentType().contains("text/html")) {
                    System.out.println("**Failure** Retrieved something other than HTML");
                    return false;
                }
                Elements linksOnPage = htmlDocument.select("a[href]");
                System.out.println("Found (" + linksOnPage.size() + ") links");
                for(Element link: linksOnPage) {
                    this.links.add(link.absUrl("href"));
                }
            }
        }catch (IOException e){
            System.out.println("Exception :"+ e.getLocalizedMessage());
            return false;
        }
        return true;
    }

    // perform search on HTML document retrieved
    public boolean searchForWord(String searchWord){
        if(htmlDocument == null){
            System.out.println("ERROR : call crawl first");
            System.exit(0);
            return false;
        }
        System.out.println("Searching for the word " + searchWord + "...");
        String bodyText = this.htmlDocument.body().text();
        return bodyText.toLowerCase().contains(searchWord.toLowerCase());
    }

    public List<String> getLinks()
    {
        return this.links;
    }
}
