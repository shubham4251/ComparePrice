package com.abd.compare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;

public class MainActivity extends AppCompatActivity {
    private EditText editTextSearch;
    private Button buttonSearch;
    private ListView listView;
    private ImageButton flipkart,amazon,snapdeal,myntra,ebay,jabong;
    String[] nameList = {"India","Pakistan","Bangladesh","southAfrica","Iran"};
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editTextSearch = findViewById(R.id.edit_text_search);
//        buttonSearch = findViewById(R.id.button_search);
//        listView = findViewById(R.id.myListview);
//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,nameList);
//        listView.setAdapter(adapter);
//
//        buttonSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String query = editTextSearch.getText().toString();
//                Intent websiteIntent = new Intent(MainActivity.this,websiteActivity.class);
//                websiteIntent.putExtra("query",query);
//                startActivity(websiteIntent);
//                finish();
//            }
//        });
        flipkart = findViewById(R.id.flipkart);
        amazon = findViewById(R.id.amazon);
        snapdeal = findViewById(R.id.snapdeal);
        myntra = findViewById(R.id.mynta);
        ebay = findViewById(R.id.ebay);
        jabong = findViewById(R.id.jabong);

        flipkart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.flipkart.com/";
                Intent websiteIntent = new Intent(MainActivity.this,websiteActivity.class);
                websiteIntent.putExtra("webUrl",url);
                startActivity(websiteIntent);

            }
        });
        amazon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.amazon.com/";
                sendToWebsite(url);

            }
        });
        snapdeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.snapdeal.com/";
                sendToWebsite(url);

            }
        });
        myntra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.myntra.com/";
                sendToWebsite(url);

            }
        });
        ebay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.ebay.com/";
                sendToWebsite(url);

            }
        });
        jabong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://www.jabong.com/";
                sendToWebsite(url);

            }
        });



    }
    public void sendToWebsite(String url){
        Intent websiteIntent = new Intent(MainActivity.this,websiteActivity.class);
        websiteIntent.putExtra("webUrl",url);
        startActivity(websiteIntent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("search once find everywhere");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String q = searchView.getQuery().toString();
                Log.d("onQueryTextSubmit",q);
                Intent websiteIntent = new Intent(MainActivity.this,websiteActivity.class);
                websiteIntent.putExtra("query",q);
                startActivity(websiteIntent);
                finish();
                Log.d("onQueryTextSubmit",q);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //adapter.getFilter().filter(newText);
                //String q =  searchView.getQuery().toString();
                //Log.d("onQueryTextChange",q);

                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}
