package com.sipdroid;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.sipdroid.sipua.ConfigSip;
import org.sipdroid.sipua.SipStatus;
import org.sipdroid.sipua.SipuaConfig;
import org.sipdroid.sipua.ui.Receiver;
import org.sipdroid.sipua.ui.SettingsNew;
import org.sipdroid.sipua.ui.Sipdroid;
import org.sipdroid.sipuademo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class NewsActivity extends Activity {

    List<TextView> mViewList;
    List<TextView> mLoginViewList;

    AlertDialog permd;
    LinearLayout layout_sip;
    ImageView img_sip_flag;
    TextView txt_sip_title;
    TextView txt_sip_sub_title;
    SipUser mSipUser;
    private EditText txt_username;
    private EditText txt_password;
    private EditText txt_server;
    private EditText txt_port;
    private EditText txt_phone;
    private TextView txt_test;
    private LinearLayout layout_submit;
    private LinearLayout layout_login;
    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            boolean isSubmit = checkAllViewFullText(mViewList);
            updateSubmitButtonStatus(isSubmit, layout_submit);

            boolean isLogin = checkAllViewFullText(mLoginViewList);
            updateSubmitButtonStatus(isLogin, layout_login);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
        txt_server = findViewById(R.id.txt_server);
        txt_port = findViewById(R.id.txt_port);
        txt_phone = findViewById(R.id.txt_phone);
        layout_submit = findViewById(R.id.layout_submit);
        txt_test = findViewById(R.id.txt_test);
        layout_login = findViewById(R.id.layout_login);

        layout_sip = findViewById(R.id.layout_sip);
        img_sip_flag = findViewById(R.id.img_sip_flag);
        txt_sip_title = findViewById(R.id.txt_sip_title);
        txt_sip_sub_title = findViewById(R.id.txt_sip_sub_title);


        txt_username.addTextChangedListener(mTextWatcher);
        txt_password.addTextChangedListener(mTextWatcher);
        txt_server.addTextChangedListener(mTextWatcher);
        txt_port.addTextChangedListener(mTextWatcher);
        txt_phone.addTextChangedListener(mTextWatcher);


        mViewList = new ArrayList<>();
        mViewList.add(txt_username);
        mViewList.add(txt_password);
        mViewList.add(txt_server);
        mViewList.add(txt_port);
        mViewList.add(txt_phone);

        mLoginViewList = new ArrayList<>();
        mLoginViewList.add(txt_username);
        mLoginViewList.add(txt_password);
        mLoginViewList.add(txt_server);
        mLoginViewList.add(txt_port);


        layout_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });

        layout_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onLogin();
            }
        });
        initPermission();

        initData();

        txt_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onTest();
            }
        });

        initSip();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= 24) {
            SettingsNew.ignoreBattery(this);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            final String[] perms = {
                    Manifest.permission.READ_CALL_LOG,
                    Manifest.permission.PROCESS_OUTGOING_CALLS,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.READ_CONTACTS,
                    Manifest.permission.WRITE_CONTACTS,
                    Manifest.permission.WRITE_CALL_LOG,
                    Manifest.permission.RECORD_AUDIO
            };
            if (Integer.parseInt(Build.VERSION.SDK) >= 31)
                perms[perms.length - 1] = Manifest.permission.BLUETOOTH_CONNECT;
            for (String perm : perms)
                if (perm != null && checkSelfPermission(perm) != PackageManager.PERMISSION_GRANTED) {
                    if (permd == null || !permd.isShowing())
                        permd = new AlertDialog.Builder(this)
                                .setMessage(org.sipdroid.sipua.R.string.permhelp)
                                .setTitle("Permissions")
                                .setIcon(org.sipdroid.sipua.R.drawable.icon22)
                                .setCancelable(true)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        requestPermissions(perms, 0);
                                    }
                                })
                                .show();
                    return;
                }
        }
    }

    public boolean checkAllViewFullText(List<TextView> viewList) {
        for (TextView textView : viewList
        ) {
            String trim = textView.getText().toString().trim();
            if (TextUtils.isEmpty(trim)) {
                return false;
            }
        }

        return true;
    }

    public void updateSubmitButtonStatus(boolean isEn, LinearLayout target) {
        if (isEn) {
            target.setEnabled(true);
            target.setBackgroundResource(R.drawable.bg_call_value);
        } else {
            target.setEnabled(false);
            target.setBackgroundResource(R.drawable.bg_call_value0);
        }
    }

    private void onLogin() {
        try {
            String userName = txt_username.getText().toString().trim();
            String password = txt_password.getText().toString().trim();
            String server = txt_server.getText().toString().trim();
            String port = txt_port.getText().toString().trim();

            setStateName(userName, server);
            ConfigSip configSip = new ConfigSip();
            configSip.setServer(server);
            configSip.setDns0("8.8.8.8");
            configSip.setPort(port);
            configSip.setUsername(userName);
            configSip.setProtocol("TCP");
            configSip.setPassword(password);
            SipuaConfig.init(this, configSip);
            Receiver.engine(this).registerMore();
            Sipdroid.on(this, true);

            Toast.makeText(this, "" + getString(R.string.reg), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onSubmit() {

        String userName = txt_username.getText().toString().trim();
        String password = txt_password.getText().toString().trim();
        String server = txt_server.getText().toString().trim();
        String port = txt_port.getText().toString().trim();
        String phone = txt_phone.getText().toString().trim();
        setStateName(userName, server);

        ConfigSip configSip = new ConfigSip();
        configSip.setServer(server);
        configSip.setDns0("8.8.8.8");
        configSip.setPort(port);
        configSip.setUsername(userName);
        configSip.setProtocol("TCP");
        configSip.setPassword(password);
        SipuaConfig.init(this, configSip);
        SipuaConfig.startInCall(this, phone);
    }

    private void initData() {
        try {
            Context context = this;
            String sharedPrefsFile = SipuaConfig.getSharedPrefsFile(context);
            SharedPreferences sp = context.getSharedPreferences(sharedPrefsFile, MODE_PRIVATE);
            String username = sp.getString("username", "");
            String password = sp.getString("password", "");
            String server = sp.getString("server", "");
            String port = sp.getString("port", "");
            txt_username.setText(username);
            txt_password.setText(password);
            txt_server.setText(server);
            txt_port.setText(port);
            setStateName(username, server);

            boolean isLogin = checkAllViewFullText(mLoginViewList);
            if (isLogin){
                Receiver.engine(this).registerMore();
                Sipdroid.on(this, true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void onTest() {
//        ConfigSip configSip = new ConfigSip();
////        configSip.setServer("13.244.48.100");
//        configSip.setServer("13.246.65.101");
//        configSip.setDns0("8.8.8.8");
//        configSip.setPort("65060");
//        configSip.setUsername("1998");
//        configSip.setProtocol("TCP");
//        configSip.setPassword("!@#123Qw");
//        SipuaConfig.init(this, configSip);
    }

    private void initSip() {


        try {
            mSipUser = new SipUser(this);
            mSipUser.setCallBack(new OnSipCallBack() {
                @Override
                protected void onCallBack(SipStatus status) {
                    onSetSipView(status);
                }

                @Override
                protected void onCallBackTitle(String title) {

                }

                @Override
                protected void onAfterRegister() {

                }
            }, UUID.randomUUID().toString());
            mSipUser.initSip();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            mSipUser.unBind();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setStateName(String userName, String server) {
        txt_sip_title.setText(userName + "@" + server);
    }

    private void onSetSipView(SipStatus status) {
        try {
            if (!TextUtils.isEmpty(status.getText())) {
                txt_sip_sub_title.setText(status.getText());
            }
            if (0 != status.getmInCallResId()) {
                img_sip_flag.setImageResource(status.getmInCallResId());
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
