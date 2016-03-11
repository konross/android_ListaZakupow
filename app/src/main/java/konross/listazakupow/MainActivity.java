package konross.listazakupow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements View.OnClickListener,
        InputNameDialogFragment.InputNameDialogListener {

    Button dodaj1,wyjdz1;
    String itemy;
    Intent i;
    ListView MojeListy;
    Context ctx=this;
    ArrayList<String> Listy;

    DatabaseManager DBM;
    Cursor CR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dodaj1 = (Button) findViewById(R.id.dodajProd);
        wyjdz1 = (Button) findViewById(R.id.powrot);
        MojeListy = (ListView) findViewById(R.id.listView_listy);
        MojeListy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemy=Listy.get(position);
                DBM=new DatabaseManager(ctx);
                CR = DBM.getInfoList(DBM);
                CR.moveToFirst();
                do {
                    if (itemy.equals(CR.getString(1))) {
                        i = new Intent(MainActivity.this,ListActivity.class);
                        i.putExtra("numer", CR.getInt(0));
                        startActivity(i);
                        //finish();
                    }
                }while (CR.moveToNext());
            }
        });

        Listy = new ArrayList<String>();
        try{
            DBM=new DatabaseManager(ctx);
            CR = DBM.getInfoList(DBM);

            CR.moveToFirst();
            do{
                Listy.add(CR.getString(1).toString());
            }while (CR.moveToNext());
        }
        catch (CursorIndexOutOfBoundsException e){
            Log.d("blad", e.toString());
        }

        if(!Listy.isEmpty()){
            CustomArrayList adapter=new CustomArrayList(this,Listy,Listy,1);
            MojeListy.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dodajProd:
                showInputNameDialog();
                break;
            case R.id.powrot:
                finish();
                break;
            default:break;
        }
    }

    public void showInputNameDialog(){
        FragmentManager fragmentManager=getSupportFragmentManager();
        InputNameDialogFragment inputNameDialog = new InputNameDialogFragment();
        inputNameDialog.setCancelable(false);
        inputNameDialog.setDialogNazwa("Nazwa nowej listy");
        inputNameDialog.show(fragmentManager, "input dialog");
    }

    @Override
    public void onFinishInputDialog(String inputText) {
        Toast.makeText(this,"Dodano listę :)",Toast.LENGTH_LONG).show();
        DBM=new DatabaseManager(ctx);
        DBM.putInfoList(DBM, inputText);
        Listy.add(inputText);
        final CustomArrayList ar = new CustomArrayList((Activity)ctx,Listy,Listy,1);
        MojeListy.setAdapter(ar);
    }

}
