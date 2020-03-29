package com.example.workmanager;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import static com.example.workmanager.MainActivity.DATA_STATUS;

public class DemoWorker  extends Worker {
    public DemoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        int  data = getInputData().getInt(MainActivity.DATA_INT,0);

       for(int i=0;i<data;i++){
           Log.i("Log",""+i);
       }

        Data dataIn = new Data.Builder().putString(DATA_STATUS,"Task Done successfully").build();

        return Result.success(dataIn);
    }
}
