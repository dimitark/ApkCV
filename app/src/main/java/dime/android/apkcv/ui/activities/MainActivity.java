package dime.android.apkcv.ui.activities;

import android.os.Bundle;

import dime.android.apkcv.App;
import dime.android.apkcv.R;

/**
 * Created by dime on 25/08/15.
 */
public class MainActivity extends BaseActivity<App> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
