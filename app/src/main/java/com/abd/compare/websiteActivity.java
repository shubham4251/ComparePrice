package com.abd.compare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class websiteActivity extends AppCompatActivity {

    private WebView webViewWebsite;
    private String query,webQuery;
    private String[]Query;
    private TextView fprice;
    private String flipprice;
    private TextView aprice;
    private String amaprice;
    private String ebayPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);

        //fprice = findViewById(R.id.text_view_fprice);
        //aprice = findViewById(R.id.text_view_aprice);

        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        webQuery = intent.getStringExtra(("webUrl"));
        if(query!=null) {
            Query = query.split(" ");
            query = Query[0];
            for (int i = 1; i < Query.length; i++) {
                query = query + "+" + Query[i];
            }
        }
        webViewWebsite = findViewById(R.id.webview_website);

        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        webViewWebsite.getSettings().setJavaScriptEnabled(true);



        webViewWebsite.getSettings().setAllowFileAccessFromFileURLs(true);
        webViewWebsite.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webViewWebsite.clearCache(true);
        webViewWebsite.clearHistory();
        webViewWebsite.getSettings().setAllowContentAccess(true);
        webViewWebsite.getSettings().setDomStorageEnabled(true);
        webViewWebsite.getSettings().setJavaScriptEnabled(true); // enable javascript
        webViewWebsite.getSettings().setBuiltInZoomControls(true);
        webViewWebsite.getSettings().setSupportZoom(true);
        webViewWebsite.getSettings().setLoadWithOverviewMode(true);
        webViewWebsite.getSettings().setUseWideViewPort(true);

        webViewWebsite.getSettings().setBuiltInZoomControls(true);
        webViewWebsite.getSettings().setDisplayZoomControls(false);

        webViewWebsite.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webViewWebsite.setScrollbarFadingEnabled(false);
        String url;
        if(query!=null){
            url = "https://www.flipkart.com/search?q="+query;
        }
        else{
            url = webQuery;
        }
        webViewWebsite.loadUrl(url);


        if(query!=null){

            Find find = new Find();
            find.execute();

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.item_amazon){
            Toast.makeText(this, "Amazon", Toast.LENGTH_SHORT).show();
            String url ="https://www.amazon.in/s?k="+query;
            webViewWebsite.loadUrl(url);
        }
        else if(item.getItemId()==R.id.item_flipkart){
            Toast.makeText(this, "Flipkart", Toast.LENGTH_SHORT).show();
            String url = "https://www.flipkart.com/search?q="+query;
            webViewWebsite.loadUrl(url);
        }
        else  if (item.getItemId()==R.id.item_ebay){
            Toast.makeText(this, "Shopclues", Toast.LENGTH_SHORT).show();
            String url ="https://www.ebay.com/sch/i.html?_from=R40&_trksid=m570.l1313&_nkw="+query;
            webViewWebsite.loadUrl(url);
        }
        else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private class Find extends AsyncTask<Void,Void,Void> {
        private String title;
        @Override
        protected Void doInBackground(Void... voids) {
            try{
                String url = "https://www.flipkart.com/search?q="+query;

                Log.d("title","Url "+url);
                Document doc = Jsoup.connect(url).get();
                Element ele = doc.getElementsByClass("_1vC4OE").first();
                Log.d("title","Price"+ele.text());
                flipprice  = ele.text();

            } catch (IOException e) {
                e.printStackTrace();
            }
            try{
                String url = "https://www.amazon.in/s?k="+query+"&ref=nb_sb_noss_2";
                Log.d("title","Url "+url);
                Document doc = Jsoup.connect(url).userAgent("web scrap").get();
                //System.out.println(doc.outerHtml());
                //String bookSelector = "span.aok-inline-block.zg-item";// > a.link-normal";
                String bookSelector = "span.a-offscreen";
                Element bookElements = doc.select(bookSelector).first();
                //System.out.println(bookElements.size());
                System.out.println(bookElements.text());
                amaprice = bookElements.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try{


                String url ="https://www.ebay.com/sch/i.html?_from=R40&_trksid=m570.l1313&_nkw="+query;
                Log.d("title","Url "+url);
                Document doc = Jsoup.connect(url).userAgent("web scrap").get();
                String bookSelector = "span.s-item__price";
                Element bookElements = doc.select(bookSelector).first();
                System.out.println("ebay price is: "+bookElements.text());
                ebayPrice=bookElements.text();


            }
            catch (IOException e){
                e.printStackTrace();

            }
            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            //fprice.setText(flipprice);
            //aprice.setText(amaprice);
            StringBuffer buffer = new StringBuffer();
            buffer.append("Flipkart: "+ flipprice+"\n");
            buffer.append("Amazon: "+amaprice+"\n");
            buffer.append("Ebay: "+ebayPrice+"\n");
            AlertDialog.Builder builder = new AlertDialog.Builder(websiteActivity.this);
            builder.setCancelable(true);
            builder.setTitle(title);
            builder.setMessage(buffer);
            builder.show();

        }
    }


}
