package ua.anironglass.template.ui.activities.main;

import android.os.Bundle;

import ua.anironglass.template.R;
import ua.anironglass.template.ui.activities.base.BaseActivity;

public class MainActivity extends BaseActivity implements MainMvpView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}