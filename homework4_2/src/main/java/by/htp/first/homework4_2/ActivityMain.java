package by.htp.first.homework4_2;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.android.material.snackbar.Snackbar;

public class ActivityMain extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SwitchCompat switchCompat = findViewById(R.id.switchCompat);

        CustomView customView = findViewById(R.id.cv_first);
        //customView.updateView(600);


        customView.setOnCustomViewActionListener(new CustomView.OnCustomViewActionListener() {
            @Override
            public void onActionDown(float x, float y) {
              //  Toast.makeText(getApplicationContext(), "x= "+x+", y= "+y,Toast.LENGTH_LONG).show();

                Snackbar.make(customView, "x= "+x+", y= "+y, Snackbar.LENGTH_LONG)
                        .show();









            }
        });

        if (switchCompat != null) {
            switchCompat.setOnCheckedChangeListener(this);
        }





    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(this, "Отслеживание переключения: " + (isChecked ? "on" : "off"),
                Toast.LENGTH_SHORT).show();




    }
}
