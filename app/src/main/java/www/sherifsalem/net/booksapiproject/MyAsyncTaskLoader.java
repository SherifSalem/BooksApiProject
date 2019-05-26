package www.sherifsalem.net.booksapiproject;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class MyAsyncTaskLoader extends AsyncTaskLoader<List<BooksModel>> {

    public static final String TAG = MyAsyncTaskLoader.class.getSimpleName();

    private String mUrl;



    public MyAsyncTaskLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public List<BooksModel> loadInBackground() {

        if (mUrl == null){
            return null;
        }

        List<BooksModel> booksModelList = QueryUtils.fetchBooksData(mUrl);
        return booksModelList;
    }
}
