package by.htp.first.homework4_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneOrEmail;
    private int imageId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Intent data = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbarActivityEdit);
        setSupportActionBar(toolbar);
        ImageButton imageButton = findViewById(R.id.imageButtonBackActivityEdit);
        Button button = findViewById(R.id.buttonRemoveActivityEdit);
        editTextName = findViewById(R.id.editTextNameActivityEdit);
        editTextPhoneOrEmail = findViewById(R.id.editTextPhoneOrEmailAddressActivityEdit);

        Contact contact = data.getParcelableExtra("contact");
        this.imageId = contact.getImageId();
        int position = data.getIntExtra("position", 0);
        Log.d("hhhh", "position = "+position);
        this.editTextName.setText(contact.getName());
        this.editTextPhoneOrEmail.setText(contact.getPhoneNumberOrEmail());



        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                contact.setName(editTextName.getText().toString());
                contact.setPhoneNumberOrEmail(editTextPhoneOrEmail.getText().toString());
                data.putExtra("editContact", (Parcelable) contact);
                data.putExtra("position", position);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });


        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                contact.setName("");
                contact.setPhoneNumberOrEmail("");
                contact.setImageId(0);
                data.putExtra("editContact", (Parcelable) contact);
                data.putExtra("position", position);
                setResult(Activity.RESULT_OK, data);
                finish();

            }
        });
    }
}




