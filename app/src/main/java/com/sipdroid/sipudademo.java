package com.sipdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.sipdroid.sipua.ui.Sipdroid;
import org.sipdroid.sipuademo.R;

public class sipudademo extends Activity {
    Button textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sipudademo);
        textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openOO1();
            }
        });
    }

    private void openOO1() {
        Intent intent = new Intent(this, Sipdroid.class);
        startActivity(intent);
    }
}
