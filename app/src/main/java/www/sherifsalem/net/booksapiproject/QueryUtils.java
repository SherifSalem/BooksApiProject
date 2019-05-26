package www.sherifsalem.net.booksapiproject;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {

    public static final String TAG = QueryUtils.class.getSimpleName();

    private QueryUtils(){

    }

    /**
     * returns a list of boooks that been parsed from the json response
     * @param booksJson
     * @return
     */
    private static List<BooksModel> extractBookList(String booksJson){
        //checks if the response is null to return
        if (TextUtils.isEmpty(booksJson)){
            return null;
        }

        List<BooksModel> booksModelList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(booksJson);
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++){

                JSONObject currentBook = items.getJSONObject(i);
                JSONObject volumeInfo = currentBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");

                String[] authors = new String[authorsArray.length()];
                for (int j = 0; j < authorsArray.length(); j++){
                authors[j] = authorsArray.getString(j);

                }
                //Create an earth quack object with the extracted values
                BooksModel element = new BooksModel(title,authors);
                //Add the earthquack element to the array of earthquackes
                booksModelList.add(element);

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.v(TAG,"size " + booksModelList.size());
        return booksModelList;

    }

    /**
     * takes a url and gets the json and creates a list of books
     * @param requestUrl
     * @return
     */
     public static List<BooksModel> fetchBooksData(String requestUrl){

        URL url = createUrl(requestUrl);

        String jsonResponse = null;
         try {
          jsonResponse = makeHttpRequest(url);
         } catch (IOException e) {
             Log.e(TAG, "Problem making the HTTP request.", e);
         }

         List<BooksModel> bookList = extractBookList(jsonResponse);

         return bookList;
    }
    /**
     * method that takes the url
     * @param url
     * @return Json response
     */
    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";


        if (url == null){
            return jsonResponse;
        }
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            Log.v(TAG,url.toString());
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(15000);
            connection.setRequestMethod("GET");
            connection.connect();
            Log.e(TAG," " +connection.getResponseCode());

            if (connection.getResponseCode() == 200){
                inputStream = connection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }else {
                Log.e(TAG,"Error Response Code " + connection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(TAG,"Couldn't retrieve requested data");
        }finally {
            if (connection != null){
                connection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        Log.v(TAG, jsonResponse);
        return jsonResponse;
    }


    /**
     * converts the{@link InputStream} into string which contains the json response from the server
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static String readFromInputStream(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null){
                stringBuilder.append(line);
                line = bufferedReader.readLine();
            }
        }

        return stringBuilder.toString();
    }


    /**
     * meathd to take a string and convert it to URL
     * @param urlString
     * @return
     */
    private static URL createUrl(String urlString){
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

}
