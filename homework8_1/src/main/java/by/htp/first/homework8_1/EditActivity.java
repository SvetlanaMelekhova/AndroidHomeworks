package by.htp.first.homework8_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import by.htp.first.homework8_1.entity.Contact;

public class EditActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPhoneOrEmail;
    private int image;
    private Contact contact;

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

        this.contact = data.getParcelableExtra("contact");
        this.image = contact.getImage();
        int position = data.getIntExtra("position", 0);
        editTextName.setText(" ");
        editTextPhoneOrEmail.setText(" ");
        editTextName.setText(contact.getName());
        editTextPhoneOrEmail.setText(contact.getPhoneNumberOrEmail());

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
                contact.setImage(0);
                data.putExtra("editContact", (Parcelable) contact);
                data.putExtra("position", position);
                setResult(Activity.RESULT_OK, data);
                finish();
            }
        });
    }
}




