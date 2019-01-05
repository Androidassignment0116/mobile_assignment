package uk.ac.shef.oak.com6510;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.database.MyDAO;

public class Edit extends AppCompatActivity {
    private EditText mEditTextTitle;
    private MyViewModel myViewModel;
    private MyDAO myDAO;

    private EditText mEditTextDes;
    private String datetime;
    private Button mButtonSave;
    private Button mButtonCancel;
    private String title;
    private String description;
    private String path;
    private Float latitude;
    private Float longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_pic);
        mButtonSave = findViewById(R.id.btn_save);
        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            mEditTextTitle = findViewById(R.id.edit_tittle);
            mEditTextDes = findViewById(R.id.edit_description);
            mButtonCancel = findViewById(R.id.btn_cancel);
            mEditTextTitle.setText(bundle.getString("Title"));
            mEditTextDes.setText(description = bundle.getString("Description"));
            datetime = bundle.getString("datetime");
            path = bundle.getString("picpath");
            latitude = bundle.getFloat("latitude");
            longitude = bundle.getFloat("longitude");
            final List info = new ArrayList();



            mButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title = mEditTextTitle.getText().toString();
                    description = mEditTextDes.getText().toString();
                    myViewModel.updatenewTitle(title,datetime);
                    myViewModel.updatenewDescription(description,datetime);
                    finish();

                }
            });

            mButtonCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

        }

    }
}
