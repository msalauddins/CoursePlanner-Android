package jp.co.jorudan.courseplanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import jp.co.jorudan.courseplanner.utils.ImageDownloaderTask;

public class ShowImageWithVolleyActivity extends AppCompatActivity {

    ImageView imageView ;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image_with_volley);

        imageView = findViewById(R.id.loc_image) ;
        url = "https://c1.staticflickr.com/7/6066/6133046430_a8f2db365b.jpg";

        ImageDownloaderTask downloaderTask = new ImageDownloaderTask( url, imageView );
        downloaderTask.execute();

//        Downloader downloader = new Downloader();
//        downloader.doInBackground();
    }

    /*private class Downloader extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            ImageRequest request = new ImageRequest(url,
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            imageView.setImageBitmap(bitmap);
                        }
                    }, 0, 0, ImageView.ScaleType.CENTER, Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            imageView.setImageResource(R.drawable.image_placeholder);
                        }
                    });

            MySingleton.getInstance(context).addToRequestQueue(request);
            return null;
        }
    }*/
}
