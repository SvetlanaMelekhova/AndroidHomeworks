package by.htp.first.homework4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AddActivity extends AppCompatActivity {

    private EditText name;
    private EditText phoneOrEmail;
    private RadioButton buttonPhoneNumber;
    private RadioButton buttonEmail;
    private Contact contact;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        Toolbar toolbar = findViewById(R.id.toolbarActivityAdd);
        setSupportActionBar(toolbar);

        findViewById(R.id.imageButtonBackActivityAdd).setOnClickListener(v -> finish());

        RadioGroup radioGroup = findViewById(R.id.radioGroupActivityAdd);
        phoneOrEmail = findViewById(R.id.editTextPhoneOrEmailActivityAdd);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.radioButtonPhoneNumberActivityAdd:
                    phoneOrEmail.setHint("Phone number");
                    break;
                case R.id.radioButtonEmailActivityAdd:
                    phoneOrEmail.setHint("Email");
                    break;
            }
        });

        name = findViewById(R.id.editTextNameActivityAdd);
        buttonPhoneNumber = findViewById(R.id.radioButtonPhoneNumberActivityAdd);
        buttonEmail = findViewById(R.id.radioButtonEmailActivityAdd);

        ImageButton imageButtonOk = findViewById(R.id.imageButtonOkActivityAdd);
        imageButtonOk.setOnClickListener(v -> {

            int imageId = 0;
            String name = this.name.getText().toString();
            String phoneOrEmail = this.phoneOrEmail.getText().toString();
            boolean check = true;

            if (buttonPhoneNumber.isChecked()) {
                imageId = R.drawable.ic_baseline_contact_phone_24;
            } else if (buttonEmail.isChecked()) {
                imageId = R.drawable.ic_baseline_contact_mail_24;
            } else if (name.isEmpty() || phoneOrEmail.isEmpty() || imageId==0) {
                check = false;
                Toast.makeText(getApplicationContext(), "Правильно введите данные.", Toast.LENGTH_LONG).show();
            }

            if (check) {
                contact = new Contact(imageId, name, phoneOrEmail);
                intent.putExtra("add_contact", (Parcelable) contact);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
