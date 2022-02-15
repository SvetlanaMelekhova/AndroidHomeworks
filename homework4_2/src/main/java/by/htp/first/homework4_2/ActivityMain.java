package by.htp.first.homework4_2;


import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;

public class ActivityMain extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomView customView = findViewById(R.id.cv_first);

        //customView.updateView(600);

        customView.setOnCustomViewActionListener(new CustomView.OnCustomViewActionListener() {
            @Override
            public void onActionDown(float x, float y , int color) {
              //  Toast.makeText(getApplicationContext(), "x= "+x+", y= "+y,Toast.LENGTH_LONG).show();

                Snackbar.make(customView, "x= "+(int) x+", y= "+ (int) y, Snackbar.LENGTH_LONG)
                        .setTextColor(color)
                        .setBackgroundTint(Color.GRAY)
                        .show();
            }
        });
    }
}
