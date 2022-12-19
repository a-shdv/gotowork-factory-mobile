package com.example.coursework.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.coursework.R;
import com.example.coursework.database.firebase.ShiftFirebaseLogic;
import com.example.coursework.database.firebase.WorkerFirebaseLogic;
import com.example.coursework.database.firebase.MachineFirebaseLogic;
import com.example.coursework.database.firebase.MachineWorkersFirebaseLogic;
import com.example.coursework.database.firebase.UserFirebaseLogic;
import com.example.coursework.database.logics.UserLogic;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button button_suppliers;
    Button button_medicines;
    Button button_receipts;
    Button button_report;
    Button button_exit;

    Calendar dateFrom;
    Calendar dateTo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        UserLogic userLogic = new UserLogic(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userLogic.open();

        context = this;

        button_receipts = findViewById(R.id.button_receipts);
        button_medicines = findViewById(R.id.button_medicines);
        button_suppliers = findViewById(R.id.button_suppliers);
        button_report = findViewById(R.id.button_report);
        button_exit = findViewById(R.id.button_exit);


        button_receipts.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, MachinesActivity.class);
                    startActivity(intent);
                }
        );

        button_medicines.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, WorkersActivity.class);
                    startActivity(intent);
                }
        );

        button_suppliers.setOnClickListener(
                v -> {
                    int userId = getIntent().getExtras().getInt("userId");
                    Intent intent = new Intent(MainActivity.this, ShiftsActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
        );

        button_exit.setOnClickListener(
                v -> {
                    finish();
                    Intent intent = new Intent(MainActivity.this, EnterActivity.class);
                    startActivity(intent);
                }
        );

        button_report.setOnClickListener(
                v -> {
                    Intent intent = new Intent(MainActivity.this, ReportActivity.class);
                    startActivity(intent);
                }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        UserFirebaseLogic userFirebaseLogic = new UserFirebaseLogic();
        userFirebaseLogic.syncUsers(this);

        ShiftFirebaseLogic shiftFirebaseLogic = new ShiftFirebaseLogic();
        shiftFirebaseLogic.syncSuppliers(this);

        WorkerFirebaseLogic workerFirebaseLogic = new WorkerFirebaseLogic();
        workerFirebaseLogic.syncMedicines(this);

        MachineFirebaseLogic machineFirebaseLogic = new MachineFirebaseLogic();
        machineFirebaseLogic.syncReceipt(this);

        MachineWorkersFirebaseLogic machineWorkersFirebaseLogic = new MachineWorkersFirebaseLogic();
        machineWorkersFirebaseLogic.syncReceiptMedicines(this);

    }

}