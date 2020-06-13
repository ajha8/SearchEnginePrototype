package com.user.searchengine;

import java.util.*;

public class WebCrawler {

    // private fields for basic web crawling
    private static final int MAX_PAGES = 10;
    private Set<String> pagesVisited = new HashSet<>();
    private List<String> pagesToVisit = new ArrayList<>();



    public void search(String url, String searchWord){
        while(this.pagesVisited.size()< MAX_PAGES){
            String currentUrl = "";
            WebCrawlerHelper helper = new WebCrawlerHelper();
            if(this.pagesToVisit.isEmpty()){
                currentUrl = url;
                this.pagesVisited.add(currentUrl);
            }else{
                currentUrl = this.nextUrl();
            }
            helper.crawl(currentUrl);
            boolean success = helper.searchForWord(searchWord);
            if(success){
                System.out.println(String.format("**Success** Word %s found at %s", searchWord, currentUrl));
                break;
            }else {
                System.out.println(searchWord+ " not found");
            }
            this.pagesToVisit.addAll(helper.getLinks());
            System.out.println("\n**Done** Visited " + this.pagesVisited.size() + " web page(s)");
        }
    }

    //method to fetch next Url
    private String nextUrl() {
        String nextUrl;
        do {
            nextUrl = this.pagesToVisit.remove(0);
        } while (this.pagesVisited.contains(nextUrl));

        this.pagesVisited.add(nextUrl);
        return nextUrl;
    }
}
