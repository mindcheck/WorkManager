package com.example.workmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button;
    private TextView textView;

    public  static  final String DATA_INT = "data_int";
    public  static  final String DATA_STATUS = "data_status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button)findViewById(R.id.button);
        textView = (TextView)findViewById(R.id.txt_view);

        Data data = new Data.Builder().putInt(DATA_INT,1750)
                .build();

        Constraints constraints = new Constraints.Builder().
                setRequiresCharging(true).
                build();

        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DemoWorker.class)
               // .setConstraints(constraints)
                .setInputData(data)
                .build();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkManager.getInstance().enqueue(oneTimeWorkRequest);
            }
        });


        WorkManager.getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                textView.setText(""+workInfo.getState().name());

                if(workInfo.getState().isFinished()){
                    String  data = workInfo.getOutputData().getString(MainActivity.DATA_STATUS);
                    Toast.makeText(MainActivity.this, data, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
