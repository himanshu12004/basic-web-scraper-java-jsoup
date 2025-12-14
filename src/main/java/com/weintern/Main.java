package com.weintern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;

public class Main {

    public static void main(String[] args) {

        String paperName = "Amar Ujala";
        String url = "https://www.amarujala.com/";

        StringBuilder html = new StringBuilder();

        try {
            Document document = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10000)
                    .get();

            Elements headlines = document.select("h3");

            html.append("""
                    <!DOCTYPE html>
                    <html>
                    <head>
                        <title>Top News Headlines</title>
                        <style>
                            body {
                                font-family: Arial, sans-serif;
                                background-color: #f2f2f2;
                                padding: 20px;
                            }
                            h1, h2 {
                                text-align: center;
                            }
                            .news-card {
                                background: white;
                                margin: 10px auto;
                                padding: 12px;
                                width: 80%;
                                border-radius: 6px;
                                box-shadow: 0 2px 4px rgba(0,0,0,0.2);
                            }
                        </style>
                    </head>
                    <body>
                        <h1>Today's Top News</h1>
                    """);

            html.append("<h2>").append(paperName).append("</h2>");

            int count = 0;

            for (Element h : headlines) {
                String text = h.text().trim();

                if (!text.isEmpty() && text.length() > 8) {
                    html.append("<div class='news-card'>")
                            .append(text)
                            .append("</div>");
                    count++;
                }
                if (count == 10) break;
            }

            html.append("""
                    </body>
                    </html>
                    """);

            FileWriter writer = new FileWriter("news.html");
            writer.write(html.toString());
            writer.close();

            System.out.println("Top 10 headlines fetched successfully");
            System.out.println("Open news.html in browser");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
