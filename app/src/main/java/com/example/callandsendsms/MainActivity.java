package com.example.callandsendsms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText phoneEdt,messageEdt;
    Button callBtn,sendSmsBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phoneEdt=findViewById(R.id.phoneNumberEdt);
        messageEdt=findViewById(R.id.messageEdt);
        callBtn=findViewById(R.id.callBtn);
        sendSmsBtn=findViewById(R.id.sendMessageBtn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getBaseContext(),Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    call();
                }else {
                    requestPermissions(new String[]{Manifest.permission.CALL_PHONE},1);
                }
            }
        });
        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                    sendSms();
                }else {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS},2);
                }
            }
        });
    }
    private  void call(){
        try{
            String phoneNumber=phoneEdt.getText().toString();
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
                Toast.makeText(this,"the length of number should be 10",Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent=new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phoneNumber));
            startActivity(intent);
        }catch (Exception e){
            Toast.makeText(this,"an error occured",Toast.LENGTH_SHORT).show();
        }
    }
    private void sendSms(){
        try {
            String phoneNumber=phoneEdt.getText().toString();
            if (TextUtils.isEmpty(phoneNumber) || phoneNumber.length() != 10) {
                Toast.makeText(this,"the length of number should be 10",Toast.LENGTH_SHORT).show();
                return;
            }
            String message=messageEdt.getText().toString();
            if (TextUtils.isEmpty(message)) {
                Toast.makeText(this,"you should write a message",Toast.LENGTH_SHORT).show();
                return;
            }
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber,null,message,null,null);
            Toast.makeText(this,"Message has been sent",Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Toast.makeText(this,"an error occured",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    call();
                }
                break;
            case 2:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    sendSms();
                }
                break;
        }
    }
}