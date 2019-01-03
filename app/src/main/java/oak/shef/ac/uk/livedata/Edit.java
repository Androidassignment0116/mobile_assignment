package oak.shef.ac.uk.livedata;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Edit extends AppCompatActivity {
    private EditText mEditTextTitle;
    private MyViewModel myViewModel;


    private EditText mEditTextDes;
    private String datetime;
    private Button mButtonSave;
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
            mEditTextTitle.setText(bundle.getString("Title"));
            mEditTextDes.setText(bundle.getString("Description"));
            datetime = bundle.getString("datetime");
            mButtonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Edit.this,MyView.class);
                    myViewModel.updatenewTitle(mEditTextTitle.getText().toString(),datetime);
                    myViewModel.updatenewDescription(mEditTextDes.getText().toString(),datetime);
                    startActivity(intent);
                }
            });
        }

    }
}
