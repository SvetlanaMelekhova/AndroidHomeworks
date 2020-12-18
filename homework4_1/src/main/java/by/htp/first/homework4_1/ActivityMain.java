package by.htp.first.homework4_1;

import android.app.Activity;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.widget.Toolbar;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class ActivityMain extends AppCompatActivity {

    private static final int ADD = 1;
    private static final int EDIT = 2;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private Toolbar toolbar;
    private Contact contact;
    private ContactAdapter contactAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbarActivityMain);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        searchView = findViewById(R.id.searchViewActivityMain);

        if (savedInstanceState != null) {
            contactAdapter = new ContactAdapter(savedInstanceState.getParcelableArrayList("list"));
        } else {
            contactAdapter = new ContactAdapter(new ArrayList<>());
        }


        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        checkContacts(contactAdapter);
        contactAdapter.setListener(new ContactAdapter.Listener() {

            @Override
            public void onClick(int position) {
                Intent intent = new Intent(ActivityMain.this, EditActivity.class);
                contact = contactAdapter.getContactList().get(position);
                setResult(Activity.RESULT_OK, intent);
                intent.putExtra("contact", contact);
                intent.putExtra("position", position);
                setResult(Activity.RESULT_OK, intent);
                startActivityForResult(intent, EDIT);
            }
        });

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                contactAdapter.getFilter().filter(newText);
                return false;
            }
        });


        findViewById(R.id.floatingActionButtonActivityMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityMain.this, AddActivity.class);
                startActivityForResult(intent, ADD);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Contact nContact;

        if (resultCode == RESULT_OK && requestCode == ADD) {
            assert data != null;
            nContact = data.getParcelableExtra("add_contact");
            contactAdapter.add(nContact);
            checkContacts(contactAdapter);
            Log.d("hhhh", "name = "+nContact.getName());

        } else if (resultCode == RESULT_OK && requestCode == EDIT) {
            assert data != null;
            nContact = data.getParcelableExtra("editContact");
            int position = data.getIntExtra("position", 0);

            if (nContact != null) {
                contactAdapter.edit(position, nContact);
                checkContacts(contactAdapter);
            } else {
                recyclerView.invalidate();
                checkContacts(contactAdapter);
                contactAdapter.remove(data.getIntExtra("position", 0));
            }
        }
    }

    public void checkContacts(@NotNull ContactAdapter contactAdapter) {
        if (contactAdapter.getContactList().isEmpty()) {
            findViewById(R.id.emptyViewActivityMain).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.emptyViewActivityMain).setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (contactAdapter.getContactList().size() != 0) {
            outState.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) contactAdapter.getContactList());
        } else {
            outState.putParcelableArrayList("list", new ArrayList<>());
        }
    }
}




