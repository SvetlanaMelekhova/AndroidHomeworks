package by.htp.first.homework2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity1 extends AppCompatActivity {

    private static final int REQUEST_CODE = 3;
    private TextView text;
    private final NumberGenerator generator = new NumberGenerator();
    private ArrayList<Integer> numbers;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            numbers = generator.generateNumbers();
            Intent intent = new Intent(this, CountActivity.class);
            intent.putExtra("numbers", numbers);
            startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            int sumResult;
            double halfResult, arithmeticMeanResult;
            sumResult = data.getIntExtra("sumResult", 0);
            arithmeticMeanResult = data.getDoubleExtra("arithmeticMeanResult", 0);
            halfResult = data.getDoubleExtra("halfResult", 0);
            Log.d("result", getString(R.string.generated_numbers) + "\n" + numbers.toString() + "\n" +
                    getString(R.string.sum) + " " + sumResult + "\n" +
                    getString(R.string.arithmetic_mean) + " " + String.format("%.2f", arithmeticMeanResult) + "\n" +
                    getString(R.string.devision) + " " + String.format("%.2f", halfResult));
        }
    }
}
