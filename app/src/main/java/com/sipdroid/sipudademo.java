package com.sipdroid;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.sipdroid.sipua.ConfigSip;
import org.sipdroid.sipua.SipuaConfig;
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
//                openOO2();

            }
        });

        Button textView1 = findViewById(R.id.textView1);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(sipudademo.this, "cccc", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openOO1() {

//            try {
//                Thread.sleep(10000);
//            }catch (Exception E){
//                E.printStackTrace();
//            }

        ConfigSip configSip = new ConfigSip();
        configSip.setServer("115.28.186.246");
        configSip.setDns0("115.28.186.246");
        configSip.setPort("65060");
        configSip.setUsername("9003");
        configSip.setProtocol("TCP");
        configSip.setPassword("@#123Qw");
        SipuaConfig.init(this, configSip);
//        SipuaConfig.startInCall(this, "10086");

        Intent intent = new Intent(this, Sipdroid.class);
        intent.putExtra(Sipdroid.numberKey, "10086");
        startActivity(intent);
    }

    private void openOO2() {

//            try {
//                Thread.sleep(10000);
//            }catch (Exception E){
//                E.printStackTrace();
//            }

        ConfigSip configSip = new ConfigSip();
//        configSip.setServer("13.244.48.100");
        configSip.setServer("13.246.65.101");

        configSip.setDns0("8.8.8.8");
        configSip.setPort("65060");
        configSip.setUsername("1001");
        configSip.setProtocol("TCP");
        configSip.setPassword("!@#123Qw");
        SipuaConfig.init(this, configSip);


        Intent intent = new Intent(this, Sipdroid.class);
        intent.putExtra(Sipdroid.numberKey, "1002");
        startActivity(intent);
    }


}
