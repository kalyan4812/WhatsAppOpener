package com.example.whatsappopener;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (getIntent().getAction() == Intent.ACTION_PROCESS_TEXT) {
            number = getIntent().getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT).toString();

            if (number.matches("[0-9]+")) {
                openWhatsApp(number);
            } else {
                Toast.makeText(getApplicationContext(), "PLEASE ENTER A VALID NUMBER", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openWhatsApp(String number) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.whatsapp");
        if (number.charAt(0) == '+') {
            number = number.substring(1);
        } else if (number.length() == 10) {
            number = "91" + number;
        } else {
            number = number;
        }
        i.setData(Uri.parse("https://wa.me/" + number));

        if (appInstalled("com.whatsapp")) {
            startActivity(i);

        } else {
            Toast.makeText(getApplicationContext(), "Please install whatsapp", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private boolean appInstalled(String package_name) {
        PackageManager packageManager = getPackageManager();
        boolean app_install;
        try {
            packageManager.getPackageInfo(package_name, PackageManager.GET_ACTIVITIES);
            app_install = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_install = false;
        }
        return app_install;
    }
}
