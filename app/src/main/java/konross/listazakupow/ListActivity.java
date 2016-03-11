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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Konrad on 2016-03-08.
 */
public class ListActivity extends FragmentActivity implements View.OnClickListener,
        InputNameDialogFragment.InputNameDialogListener {

    Button dodajProd1,powrot1;
    int ajdi,temp,temp2,poz;
    double suma;
    String itemy2,itemy3,zmiana;
    Intent intent;
    ListView MojeProdukty;
    Context ctx=this;
    ArrayList<String> Produkty;
    ArrayList<String> Prize;
    ArrayList<Integer> idProd;
    DatabaseManager DBM;
    Cursor CR;
    EditText produkt;
    TextView razem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        dodajProd1 = (Button) findViewById(R.id.dodajProd);
        powrot1 = (Button) findViewById(R.id.powrot);
        MojeProdukty = (ListView) findViewById(R.id.listView_prod);
        produkt = (EditText) findViewById(R.id.editText);
        produkt.setHint("Tutaj wpisuj produkty...");
        razem=(TextView) findViewById(R.id.textView5);

        //dodawanie cen do produktów
        MojeProdukty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemy3 = Produkty.get(position);
                temp = idProd.get(position);
                poz=position;
                DBM = new DatabaseManager(ctx);
                CR = DBM.getInfoProduct(DBM);
                CR.moveToFirst();
                do {
                    if (temp == (CR.getInt(0))) {
                        // temp=CR.getInt(0); // przechowuje id produktu
                        temp2 = CR.getInt(3); // przechowuje id listy
                        showInputNameDialog();
                    }
                } while (CR.moveToNext());
            }
        });

       /* MojeProdukty.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        }); */


        Produkty = new ArrayList<String>();
        Prize = new ArrayList<String>();
        idProd = new ArrayList<Integer>();

        intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null){
            ajdi=bundle.getInt("numer");
        }

        try{
            DBM=new DatabaseManager(ctx);
            CR = DBM.getInfoProduct(DBM);
            CR.moveToFirst();
            do{
                if(ajdi==CR.getInt(3)) {
                    idProd.add(CR.getInt(0));
                    Produkty.add(CR.getString(1).toString());
                    Prize.add(CR.getString(2).toString());
                    suma+=Double.parseDouble(CR.getString(2).toString());
                }
            }while (CR.moveToNext());
        }
        catch (CursorIndexOutOfBoundsException e){
            Log.d("blad", e.toString());
        }
        suma=new BigDecimal(suma).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        razem.setText(Double.toString(suma));

        if(!Produkty.isEmpty()){
            CustomArrayList adapter=new CustomArrayList(this,Produkty,Prize,2);
            MojeProdukty.setAdapter(adapter);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.dodajProd:
                //dodawanie produktu
                itemy2=produkt.getText().toString();
                if(itemy2.isEmpty()){
                    Toast.makeText(this, "Podaj nazwę produktu! :>", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(this, "Dodano produkt :)", Toast.LENGTH_LONG).show();
                    DBM=new DatabaseManager(ctx);
                    DBM.putInfoProduct(DBM, itemy2, "0", ajdi);
                    Produkty.add(itemy2);
                    Prize.add("0");
                    CR = DBM.getInfoProduct(DBM);
                    CR.moveToLast();
                    idProd.add(CR.getInt(0));
                    final CustomArrayList ar = new CustomArrayList((Activity)ctx,Produkty,Prize,2);
                    MojeProdukty.setAdapter(ar);
                    produkt.setText("");
                }
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
        inputNameDialog.setDialogNazwa("Cena produktu");
        inputNameDialog.show(fragmentManager, "input dialog");
    }


    @Override
    public void onFinishInputDialog(String inputText) {
        Toast.makeText(this, "Dodano cenę :)", Toast.LENGTH_LONG).show();
        DBM=new DatabaseManager(ctx);
        suma-=Double.parseDouble(Prize.get(poz).toString());
        DBM.updateInfoProduct(DBM, itemy3, inputText, temp, temp2);
        Prize.set(poz, inputText);
        suma+=Double.parseDouble(inputText);
        final CustomArrayList ar = new CustomArrayList((Activity)ctx,Produkty,Prize,2);
        MojeProdukty.setAdapter(ar);
        suma=new BigDecimal(suma).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        razem.setText(Double.toString(suma));
    }

}
