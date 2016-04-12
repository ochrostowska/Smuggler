package pl.oldzi.smuggler.Activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;

import pl.oldzi.smuggler.R;

public class BossActivity extends BaseMenuActivity {


    private TextInputEditText passwordET;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_layout);
        passwordET = (TextInputEditText) findViewById(R.id.bossPasswordTV);
        //textView.setText("Hello pretty!");

        }

}