package konross.listazakupow;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Konrad on 2016-03-08.
 */
public class CustomArrayList extends ArrayAdapter<String>{
    private final Activity context;
    private final ArrayList<String> Name;
    private final ArrayList<String>  Value;
    int a;

    public CustomArrayList(Activity context, ArrayList<String> name, ArrayList<String> value,int x) {
        super(context, R.layout.lista_element, name);
        this.context = context;
        this.Name = name;
        this.Value = value;
        this.a = x;
    }

    static class ViewContainer1 {
        public TextView txtName;
        public TextView txtValue;
    }
    static class ViewContainer2 {
        public TextView txtName2;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        View rowView = view;
        if(a==1){
            ViewContainer2 viewContainer;

            //---if the row is displayed for the first time---
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.lista_element, null, true);

                //---create a view container object---
                viewContainer = new ViewContainer2();

                //---get the references to all the views in the row---
                viewContainer.txtName2 = (TextView)
                        rowView.findViewById(R.id.txtLista);

                //---assign the view container to the rowView---
                rowView.setTag(viewContainer);
            } else {
                //---retrieve the previously assigned tag to get
                // a reference to all the views; bypass the findViewByID() process,
                // which is computationally expensive---
                viewContainer = (ViewContainer2) rowView.getTag();
            }

            //---customize the content of each row based on position---
            viewContainer.txtName2.setText(Name.get(position));
        }
        if(a==2){
            ViewContainer1 viewContainer;

            //---if the row is displayed for the first time---
            if (rowView == null) {
                LayoutInflater inflater = context.getLayoutInflater();
                rowView = inflater.inflate(R.layout.product_element, null, true);

                //---create a view container object---
                viewContainer = new ViewContainer1();

                //---get the references to all the views in the row---
                viewContainer.txtName = (TextView)
                        rowView.findViewById(R.id.txtProduct);
                viewContainer.txtValue = (TextView)
                        rowView.findViewById(R.id.txtValue);


                //---assign the view container to the rowView---
                rowView.setTag(viewContainer);
            } else {
                //---retrieve the previously assigned tag to get
                // a reference to all the views; bypass the findViewByID() process,
                // which is computationally expensive---
                viewContainer = (ViewContainer1) rowView.getTag();
            }

            //---customize the content of each row based on position---
            viewContainer.txtName.setText(Name.get(position));
            viewContainer.txtValue.setText(Value.get(position));
        }

        return rowView;
    }
}
