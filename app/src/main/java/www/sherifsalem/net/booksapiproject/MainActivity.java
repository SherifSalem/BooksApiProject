package www.sherifsalem.net.booksapiproject;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<BooksModel>> {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String queryUrl = "https://www.googleapis.com/books/v1/volumes?q=";
    private ArrayList<BooksModel> booksArray;
    private BooksArrayAdapter booksArrayAdapter;
    private Button searchButton;
    EditText searchEditText;
    private String queryKey;
    private TextView emptyView;
    private static final int LOADER_ID = 99;
    private ProgressBar progressBar;
    private Bundle savedList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        booksArray = new ArrayList<>();
        emptyView = findViewById(R.id.empty);
        progressBar = findViewById(R.id.progress);

        searchButton = findViewById(R.id.search_button);
        searchEditText = findViewById(R.id.search_edit_text);
        savedList = new Bundle();


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryKey = searchEditText.getText().toString();

                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()){
                    LoaderManager loaderManager = getLoaderManager();
                    loaderManager.initLoader(LOADER_ID, null, MainActivity.this).forceLoad();


                } else {
                    emptyView.setText(R.string.no_connection);
                    progressBar.setVisibility(View.GONE);
                }


                ListView bookListview = findViewById(R.id.books_listview);
                bookListview.setEmptyView(emptyView);
                booksArrayAdapter = new BooksArrayAdapter(MainActivity.this,R.layout.listview_item,booksArray);
                bookListview.setAdapter(booksArrayAdapter);




//                BooksAsyncTask asyncTask = new BooksAsyncTask();
//
//                if (queryKey == null) {
//                    return;
//                }
//                asyncTask.execute(queryUrl + queryKey);

            }

        });



    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public Loader<List<BooksModel>> onCreateLoader(int id, Bundle args) {
        Log.d(TAG,"create loader");
        return new  MyAsyncTaskLoader(this, queryUrl + queryKey);
    }

    @Override
    public void onLoadFinished(Loader<List<BooksModel>> loader, List<BooksModel> data) {

            booksArrayAdapter.clear();
        if (data != null && !data.isEmpty()){
            booksArrayAdapter.addAll(data);
           // savedList.putStringArrayList(getResources().getString(R.string.saved_list),data);
            emptyView.setText(R.string.no_record_found);
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<BooksModel>> loader) {

       booksArrayAdapter.clear();



    }



    //    private  class BooksAsyncTask extends AsyncTask<String, Void, List<BooksModel>>{
//    @Override
//    protected void onPreExecute() {
//     //   progressBar.setVisibility(View.VISIBLE);
//
//    }
//
//    @Override
//    protected List<BooksModel> doInBackground(String... urls) {
//        if (urls.length < 1 | urls[0] == null){
//            return null;
//        }
//
//        return QueryUtils.fetchBooksData(urls[0]);
//
//    }
//
//    @Override
//    protected void onPostExecute(List<BooksModel> booksModels) {
//        booksArrayAdapter.clear();
//
//        if (booksModels != null && !booksModels.isEmpty()){
//            booksArray.addAll(booksModels);
//        }
//    }
//}



}
