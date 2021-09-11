package jp.co.jorudan.courseplanner.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

/**
 * Created by daN on 2018-09-09.
 */
public class ImageDownloaderTask extends AsyncTask<Void, Void, Bitmap> {

    private final String imageUrl;

    private final WeakReference<ImageView> imageViewRef;

    public ImageDownloaderTask(String url, ImageView imageView) {
        imageUrl = url;
        imageViewRef = new WeakReference<>( imageView ); //
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        try {
            InputStream inputStream = new URL( imageUrl ).openConnection().getInputStream();
            return BitmapFactory.decodeStream( inputStream );
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(@Nullable Bitmap result) {
        // Check result
        if (result == null) {
            return;
        }

        // Check ImageView
        ImageView imageView = imageViewRef.get();
        if (imageView == null) {
            return;
        }

        Bitmap resized = Bitmap.createScaledBitmap(result, 1000, 650, true);  //Image resize
        imageView.setImageBitmap( resized );
        //Bitmap resized = Bitmap.createScaledBitmap(result,(int)(result.getWidth()*0.8), (int)(result.getHeight()*0.8), true);

    }

}
