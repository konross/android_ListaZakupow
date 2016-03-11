package konross.listazakupow;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.WindowManager.LayoutParams;

/**
 * Created by Konrad on 2016-03-08.
 */
public class InputNameDialogFragment extends DialogFragment {
    EditText txtName;
    Button btn;
    static String dialogNazwa;

    public interface InputNameDialogListener{
        void onFinishInputDialog(String inputText);
    }

    public InputNameDialogFragment(){

    }

    public void setDialogNazwa(String nazwa){
        dialogNazwa=nazwa;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup contener,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.dialog_table_name,contener);

        txtName=(EditText) view.findViewById(R.id.txtNazwa);
        btn=(Button) view.findViewById(R.id.btnDodaj);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                InputNameDialogListener activity = (InputNameDialogListener) getActivity();
                activity.onFinishInputDialog(txtName.getText().toString());
                dismiss();
            }
        });

        txtName.requestFocus();
        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle(dialogNazwa);
        return view;
    }
}
