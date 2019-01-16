package uk.ac.shef.oak.com6510;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import uk.ac.shef.oak.com6510.database.PicinfoData;
/**
 * Invoke searchtheimage method in view model. Show the result.
 *
 * @author Mengjie Gao
 * @creed: assignment
 * @date 2019/1/16 15:12
 * @return
 */
public class SearchResults extends AppCompatActivity {
    private ResultAdapter mResultAdapter;
    private MyViewModel myViewModelresult;
    private String maytitle;
    private String maydescription;
    private String maytime;
    private List<PicinfoData> res = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_result_activity);
        myViewModelresult = ViewModelProviders.of(this).get(MyViewModel.class);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){

            maytitle = bundle.getString("maytitle");
            maydescription = bundle.getString("maydescription");
            maytime = bundle.getString("maydate");
        }
        else
            Toast.makeText(this,"no such file",Toast.LENGTH_SHORT).show();
        if (bundle!=null)
        {

            res.addAll( myViewModelresult.searchtheimage(maytitle,maydescription,maytime));
        }

        RecyclerView recyclerView = findViewById(R.id.grid_recycler_view);
        int numberOfColumns = 4;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        recyclerView.setHasFixedSize(true);
        mResultAdapter = new ResultAdapter();
        recyclerView.setAdapter(mResultAdapter);

        mResultAdapter.setBitmaps(res);
    }
}
