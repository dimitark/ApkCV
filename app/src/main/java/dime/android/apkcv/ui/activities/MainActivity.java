package dime.android.apkcv.ui.activities;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.ui.views.buble.BubbleView;
import dime.android.apkcv.ui.views.buble.ImageBubble;

/**
 * Created by dime on 25/08/15.
 */
public class MainActivity extends BaseActivity<App> {
    // The bubble view
    private BubbleView bubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the UI components
        bubbleView = (BubbleView) findViewById(R.id.bubble_view);

        // TODO Test
        Picasso
                .with(this)
                .load("https://scontent-fra3-1.xx.fbcdn.net/hphotos-xft1/t31.0-8/11402455_10207562504006696_7784059285010925527_o.jpg")
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        bubbleView.setMainImage(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }
                });
    }
}
