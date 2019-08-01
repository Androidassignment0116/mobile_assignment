package uk.ac.shef.oak.com6510;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Search extends AppCompatActivity {
    private EditText mEditTitle;
    private EditText mEditDescription;
    private EditText mEditDate;
    private Button mBtnSearch;
    private MyViewModel myViewModelsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_pic);
        mBtnSearch = findViewById(R.id.btn_search);
        mEditTitle = findViewById(R.id.edit_tittle);
        mEditDescription = findViewById(R.id.edit_description);
        mEditDate = findViewById(R.id.edit_date);

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maytitle = mEditTitle.getText().toString();
                String maydescription = mEditDescription.getText().toString();
                String maydate = mEditDate.getText().toString();
                Intent intent = new Intent(Search.this,SearchResults.class);
                intent.putExtra("maytitle",maytitle);
                intent.putExtra("maydescription",maydescription);
                intent.putExtra("maydate",maydate);
                startActivity(intent);
            }
        });
    }
}
