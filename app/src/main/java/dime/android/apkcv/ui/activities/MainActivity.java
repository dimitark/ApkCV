package dime.android.apkcv.ui.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.Utils;
import dime.android.apkcv.data.rest.ResponseHandler;
import dime.android.apkcv.data.rest.RestTask;
import dime.android.apkcv.data.rest.RestTaskRunnable;
import dime.android.apkcv.data.rest.bio.Bio;
import dime.android.apkcv.ui.views.buble.BubbleClickListener;
import dime.android.apkcv.ui.views.buble.BubbleColorScheme;
import dime.android.apkcv.ui.views.buble.BubbleView;

/**
 * Created by dime on 25/08/15.
 */
public class MainActivity extends BaseActivity<App> implements BubbleClickListener {
    // The bubble view
    private BubbleView bubbleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the UI components
        bubbleView = (BubbleView) findViewById(R.id.bubble_view);
        bubbleView.setBubbleClickListener(this);


        // TODO Test
        final Target target = new Target() {
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
        };
        bubbleView.setTag(target); // Picasso uses weak references
        new RestTask<>(new ResponseHandler<Bio>() {
            @Override
            public void success(Bio bio) {
                Picasso
                        .with(MainActivity.this)
                        .load(bio.getImage())
                        .into(target);
            }

            @Override
            public void error() {
            }
        }).execute(new RestTaskRunnable<Bio>() {
            @Override
            public Bio run() {
                return app.getBioService().getBio();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reset the bubble view
        bubbleView.reset();
    }

    /**
     * A bubble has been clicked. Go, go, go!
     *
     * @param button
     */
    @Override
    public void bubbleClicked(BubbleView.BubbleButtonType button) {
        // Get the color scheme of the button
        BubbleColorScheme colorScheme = bubbleView.getColorSchemeForType(button);
        // Change the color of the status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(colorScheme.secondaryColor);
        }
        // Create the activity intent
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        // Add the type and the colors to the bundle
        intent.putExtra(DetailsActivity.EXTRA_TYPE_KEY, button.name());
        intent.putExtra(DetailsActivity.EXTRA_PRIMARY_COLOR_KEY, colorScheme.primaryColor);
        intent.putExtra(DetailsActivity.EXTRA_SECONDARY_COLOR_KEY, colorScheme.secondaryColor);
        // Open the activity
        startActivity(intent);
        // We don't want any transition animation
        overridePendingTransition(0, 0);
    }
}
