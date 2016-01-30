package dime.android.apkcv.ui.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import dime.android.apkcv.App;
import dime.android.apkcv.R;
import dime.android.apkcv.data.rest.bio.Bio;
import dime.android.apkcv.ui.activities.BaseActivity;
import dime.android.apkcv.ui.activities.DetailsActivity;
import dime.android.apkcv.ui.views.buble.BubbleClickListener;
import dime.android.apkcv.ui.views.buble.BubbleColorScheme;
import dime.android.apkcv.ui.views.buble.BubbleView;
import dime.android.apkcv.ui.views.loading.LoadingView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by dime on 29/08/15.
 */
public class MainFragment extends BaseFragment<App, BaseActivity> implements BubbleClickListener {
    // The UI components
    private BubbleView bubbleView;

    // The Picasso target that fetches the main image
    private Target mainImageTarget = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // Set the main image
            bubbleView.setMainImage(bitmap);
            // Hide the loading screen
            hideLoadingScreen();
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {}
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {}
    };

    // The Bio callback
    private Callback<Bio> bioCallback = new Callback<Bio>() {
        @Override
        public void success(Bio bio, Response response) {
            bubbleView.setTitle(String.format("{%s}", bio.getTitle()));
            bubbleView.setEducation(String.format("{%s}", bio.getEdu()));
            Picasso.with(baseActivity).load(bio.getImage()).into(mainImageTarget);
        }

        @Override
        public void failure(RetrofitError error) {
            // Write to the log
            Log.e(App.LOG_TAG, "RetrofitError: " + error.getMessage());
            // Show the error screen
            showErrorScreen();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate tge fragment's layout
        ViewGroup rootLayout = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        // Get reference to the UI components
        bubbleView = (BubbleView) rootLayout.findViewById(R.id.bubble_view);
        bubbleView.setBubbleClickListener(this);

        // Let the base do it's work
        postOnCreateView(rootLayout);

        // Return the root layout
        return rootLayout;
    }

    @Override
    protected void customizeLoadingView(ViewGroup loadingLayout, LoadingView loadingView) {
        // Just show the loading screen
        showLoadingScreen();
    }

    @Override
    protected void handleTryAgain() {
        // Show the loading screen again
        showLoadingScreen();
        // Try to fetch the Bio data again
        app.getRestServices().getBioService().getBio(bioCallback);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Fetch the Bio data
        app.getRestServices().getBioService().getBio(bioCallback);
    }

    @Override
    public void onResume() {
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
            baseActivity.getWindow().setStatusBarColor(colorScheme.secondaryColor);
        }
        // Create the activity intent
        Intent intent = new Intent(baseActivity.getApplicationContext(), DetailsActivity.class);
        // Add the type and the colors to the bundle
        intent.putExtra(DetailsActivity.EXTRA_TYPE_KEY, button.name());
        intent.putExtra(DetailsActivity.EXTRA_PRIMARY_COLOR_KEY, colorScheme.primaryColor);
        intent.putExtra(DetailsActivity.EXTRA_SECONDARY_COLOR_KEY, colorScheme.secondaryColor);
        // Open the activity
        startActivity(intent);
        // We don't want any transition animation
        baseActivity.overridePendingTransition(0, 0);
    }
}
