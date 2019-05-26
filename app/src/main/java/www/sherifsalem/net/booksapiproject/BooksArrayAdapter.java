package www.sherifsalem.net.booksapiproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;



public class BooksArrayAdapter extends ArrayAdapter {
    public static final String author_separator = ", ";

    public BooksArrayAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View bookArrayView = convertView;
        if (bookArrayView == null){
            bookArrayView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item,parent, false);

            BooksModel booksModel = (BooksModel) getItem(position);

            TextView title =  bookArrayView.findViewById(R.id.book_title);
            TextView authors = bookArrayView.findViewById(R.id.book_author);
            TextView letter = bookArrayView.findViewById(R.id.firstLetter);
            assert booksModel != null;
            title.setText(booksModel.getBookTitle());

            authors.setText(authorString(booksModel.getAutherName()));

            char first = firstLetter(booksModel.getBookTitle());
            String firstLetter = "" + first;

          letter.setText(firstLetter);
        }


        return bookArrayView;


    }

    private String authorString (String[] array){
        String authorNames = "";
        for(int i = 0; i< array.length; i++){
            if (i == array.length-1){
                authorNames += array[i];
            } else {
                authorNames += array[i] + ", ";
            }
        }

      return authorNames;
    }

    private char firstLetter (String title){

    return title.charAt(0);
    }
}
