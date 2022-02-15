package by.htp.first.homework2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import static by.htp.first.homework2.Operation.arithmeticMean;
import static by.htp.first.homework2.Operation.half;
import static by.htp.first.homework2.Operation.sum;

public class CountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        ArrayList<Integer> numbers;
        int sumResult;
        double halfResult, arithmeticMeanResult;
        Intent intent = getIntent();
        if (intent != null) {
            numbers = intent.getIntegerArrayListExtra("numbers");
            sumResult = sum(numbers);
            arithmeticMeanResult = arithmeticMean(numbers);
            halfResult = half(numbers);
            Intent intent1 = new Intent();
            intent1.putExtra("sumResult", sumResult);
            intent1.putExtra("arithmeticMeanResult", arithmeticMeanResult);
            intent1.putExtra("halfResult", halfResult);
            setResult(RESULT_OK, intent1);
            finish();
        }
    }
}