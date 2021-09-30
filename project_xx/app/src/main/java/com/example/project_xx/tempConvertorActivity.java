package com.example.project_xx;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class tempConvertorActivity extends AppCompatActivity {

    TextView box2;
    EditText box1;
    Button convert;
    Spinner spn1,spn2;
    DecimalFormat df=new DecimalFormat("#.####");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            this.getSupportActionBar().hide();
        }catch (NullPointerException e){

        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_convertor);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        box1=(EditText)findViewById(R.id.box1);
        box2=(TextView)findViewById(R.id.box2);
        convert=(Button)findViewById(R.id.convertor);
        spn1=(Spinner)findViewById(R.id.spn1);
        spn2=(Spinner)findViewById(R.id.spn2);
        ArrayAdapter adapter1=ArrayAdapter.createFromResource(this,R.array.Temperature,R.layout.spinner_styling);
        spn1.setAdapter(adapter1);
        ArrayAdapter adapter2=ArrayAdapter.createFromResource(this,R.array.Temperature,R.layout.spinner_styling);
        spn2.setAdapter(adapter2);
        spn1.getBackground().setColorFilter(getResources().getColor(R.color.brown), PorterDuff.Mode.SRC_ATOP);
        spn2.getBackground().setColorFilter(getResources().getColor(R.color.brown),PorterDuff.Mode.SRC_ATOP);
    }
    public void toCon(View view){
        int opt1 = spn1.getSelectedItemPosition();
        int opt2 = spn2.getSelectedItemPosition();
        double value = Double.parseDouble(box1.getText().toString());
        double res = evaluate(opt1,opt2,value);
        df.setRoundingMode(RoundingMode.CEILING);
        box2.setText(df.format(res)+"");
    }

    public double evaluate(int opt1,int opt2,double value){
        double temp=0.0;
        if(opt1==opt2){
            return value;
        }
        else {
            switch (opt1){
                case 0:
                    switch (opt2){
                        case 0:temp=value;
                                break;
                        case 1: temp=(value*9/5)+32;
                                break;
                        case 2: temp=value+273.15;
                                break;
                    }
                    break;
                case 1:
                    double reuse1 = value-32;
                    switch (opt2) {
                        case 0:
                            temp = reuse1 * 5 / 9;
                            break;
                        case 1:
                            temp = value;
                            break;
                        case 2:
                            temp = reuse1 * 5 / 9 + 273.15;
                            break;
                    }
                    break;
                case 2:
                    double reuse2 = value-273.15;
                    switch (opt2){
                        case 0 : temp=reuse2;
                                break;
                        case 1: temp=reuse2*9/5+32;
                                break;
                        case 2: temp=value;
                                break;
                    }
                    break;
            }
            return temp;
        }
    }
}


