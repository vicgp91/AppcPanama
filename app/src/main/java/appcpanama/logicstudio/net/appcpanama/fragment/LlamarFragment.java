package appcpanama.logicstudio.net.appcpanama.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import appcpanama.logicstudio.net.appcpanama.Common;
import appcpanama.logicstudio.net.appcpanama.R;
import appcpanama.logicstudio.net.appcpanama.model.Animal;
import fr.ganfra.materialspinner.MaterialSpinner;


public class LlamarFragment extends Fragment {

    private EditText inputName, inputEmail, inputUbicacion;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutUbicacion;
    private Button btnSignUp, btnLlamar;
    private TextView textViewMessage;
    private RadioButton rdbPeligro,rdbAtropellado;
    private FloatingActionButton fabCamera,fabFolder;
    private ArrayList<Animal> animales;
    private CoordinatorLayout coordinator;
    private ArrayAdapter<String> adapter;
    private static final int PHOTO = 200;
    private static final int FILE = 100;
    private Bitmap _photo = null;
    HttpClient cliente;
    HttpPost post;
    List<NameValuePair> lispair;
    static FragmentActivity c = null;
    static View view;
    private static Uri mUri;
    MaterialSpinner spinner;
    private static final String[] ANIMALS = new String[] {
            "Perezoso", "Mono Tit\u00ed", "\u00d1eque", "Coat\u00ed"
    };


    public static LlamarFragment newInstance() {
        LlamarFragment fragment = new LlamarFragment();
        return fragment;
    }

    public LlamarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_llamar, container, false);
        c = getActivity();
                //adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,  ANIMALS);
                // adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        animales = new ArrayList<Animal>();
        rellenarArrayListSnniper();
        Spinner mySpinner = (Spinner)view.findViewById(R.id.spinner);
        mySpinner.setAdapter(new MyCustomAdapter(c, animales));


        inputLayoutName = (TextInputLayout) view.findViewById(R.id.input_layout_name);
        //inputLayoutEmail = (TextInputLayout) view.findViewById(R.id.input_layout_email);
        inputLayoutUbicacion = (TextInputLayout) view.findViewById(R.id.input_layout_ubicacion);
        inputName = (EditText) view.findViewById(R.id.input_name);
        //inputEmail = (EditText) view.findViewById(R.id.input_email);
        inputUbicacion = (EditText) view.findViewById(R.id.input_ubicacion);
        btnSignUp = (Button) view.findViewById(R.id.btn_signup);
        coordinator = (CoordinatorLayout) view.findViewById(R.id.coordinator);

        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        //inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputUbicacion.addTextChangedListener(new MyTextWatcher(inputUbicacion));


        rdbPeligro=(RadioButton) view.findViewById(R.id.rdbOne);
        rdbAtropellado=(RadioButton) view.findViewById(R.id.rdbTwo);

        //initSpinnerMultiline(view);

        fabCamera =(FloatingActionButton) view.findViewById(R.id.fabCamera);
        fabFolder= (FloatingActionButton) view.findViewById(R.id.fabFolder);
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);

        btnLlamar=(Button)view.findViewById(R.id.llamar);
        textViewMessage= (TextView) view.findViewById(R.id.lblText);

        textViewMessage.setText("S\u00ed encontraste alg\u00fan animal en peligro reportalo al:");
       // textViewMessage.setText(Html.fromHtml("<p>S\u00ed encontraste alg\u00fan animal en peligro reportalo al: <h3 style='color:#00C853; forecolor:#00C853; font-size: 18px'>64977223</h3></p>"));


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // submitForm();
                new EnviarDatos(c).execute();
            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog(btnLlamar.getText().toString());
            }
        });


        fabCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File f = new File(Environment.getExternalStorageDirectory(),  "photo.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                mUri = Uri.fromFile(f);
                startActivityForResult(intent, PHOTO);
            }
        });

        fabFolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, FILE);
                } catch (Exception ex) {
                    Snackbar.make(coordinator, ex.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
                }
            }
        });


        return view;
    }

    private void initSpinnerMultiline(View view) {
        spinner = (MaterialSpinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
        spinner.setHint("Escoga Animal a Reportar");
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateUbicacion() {
        if (inputUbicacion.getText().toString().trim().isEmpty()) {
            inputLayoutUbicacion.setError(getString(R.string.err_msg_ubicacion));
            requestFocus(inputUbicacion);
            return false;
        } else {
            inputLayoutUbicacion.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void submitForm() {
        if (!validateName()) {
            return;
        }

       /* if (!validateEmail()) {
            return;
        }*/

        if (!validateUbicacion()) {
            return;
        }



        ShareCompat.IntentBuilder builder = ShareCompat.IntentBuilder.from(c);
        builder.setType("message/rfc822");
        builder.addEmailTo(getString(R.string.email_to));
        builder.setSubject(getString(R.string.animal_peligro_title));
        builder.setChooserTitle(getString(R.string.peligro_message));
        builder.setHtmlText("Reporte creado en: " + inputUbicacion.getText().toString() + " "
                + "por " + "<b>" + inputName.getText().toString() + "</b></br></br><p><b>Por favor de atender.</b></p>");
        builder.setType("text/html");
        builder.setStream(mUri);
        builder.startChooser();

        inputName.setText("");
        //inputEmail.setText("");
        inputUbicacion.setText("");
        ImageView img = ((ImageView) view.findViewById(R.id.imgPromo));
        img.setImageDrawable(null);
        //Snackbar.make(coordinator, "Reporte creado.", Snackbar.LENGTH_SHORT).show();
        Toast.makeText(c,  "Reporte esta siendo creado para enviar...", Toast.LENGTH_LONG).show();

        inputLayoutName.setError(null);
        inputLayoutUbicacion.setError(null);
        inputName.setFocusable(true);
        inputName.requestFocus();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }




    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    //validateName();
                    break;
                /*case R.id.input_email:
                    validateEmail();
                    break;*/
                case R.id.input_ubicacion:
                    //validateUbicacion();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            switch (requestCode) {
                case PHOTO:
                    if (resultCode == Activity.RESULT_OK) {
                        c.getContentResolver().notifyChange(mUri, null);
                        ContentResolver cr = c.getContentResolver();
                        _photo = Common.getImageThumbnail(android.provider.MediaStore.Images.Media.getBitmap(cr, mUri));
                        ((ImageView) view.findViewById(R.id.imgPromo)).setImageBitmap(_photo);
                    }
                    break;

                case FILE:
                    Uri selectedImage = data.getData();
                    InputStream imageStream = c.getContentResolver().openInputStream(selectedImage);
                    _photo = Common.getImageThumbnail(BitmapFactory.decodeStream(imageStream));
                    mUri = selectedImage;
                    ((ImageView) view.findViewById(R.id.imgPromo)).setImageBitmap(_photo);
                    break;
            }
        } catch (Exception ex) {
            Snackbar.make(coordinator, ex.getLocalizedMessage(), Snackbar.LENGTH_LONG);
        }
    }

    private void rellenarArrayListSnniper() {
        animales.add(new Animal("Perezoso", "", R.drawable.peresozo));
        animales.add(new Animal("Mono Tit\u00ed", "", R.drawable.titi));
        animales.add(new Animal("\u00d1eque", "", R.drawable.neque));
        animales.add(new Animal("Coat\u00ed", "", R.drawable.coati));
    }




    public class MyCustomAdapter extends ArrayAdapter<Animal>{

        private Context context;
        private ArrayList<Animal> animales;

        public MyCustomAdapter(Context context ,ArrayList<Animal> animales) {
            super(context, R.layout.layoutsnniperimagen, animales);
            this.context=context;
            this.animales=animales;
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {

            View item = LayoutInflater.from(context).inflate(
                    R.layout.layoutsnniperimagen, null);
            TextView label=(TextView)item.findViewById(R.id.animal);
            label.setText(animales.get(position).getNombre());
            ImageView icon=(ImageView)item.findViewById(R.id.icon);
            icon.setImageResource(animales.get(position).getImageAnimal());
            return item;
        }
    }



    public  void dialog(final String n)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                c);
        alertDialogBuilder.setTitle("Confirmar llamada");
        alertDialogBuilder.setMessage(n.toString()).setCancelable(false).
                setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Llamar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                String phoneNumber = n;
                Intent callintent = new Intent(Intent.ACTION_CALL);
                callintent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(callintent);

            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    };

    public Boolean registroReporte(){

        String result=null;
        String radioB=null;
        String animalSelected=null;
        if(rdbPeligro.isChecked()){
            radioB="Peligro";
        }if(rdbAtropellado.isChecked()){

            radioB="Atropellado";
        }
        animalSelected=animales.get(spinner.getSelectedItemPosition()-1).getNombre();
        try {
        cliente= new DefaultHttpClient();
        //post=new HttpPost("http://192.168.1.112/api/values?estadoAnimmal="+radioB+"&ciudadanoReporte="+inputName.getText().toString()+"&animal="+animalSelected+"&ubicacion="+inputUbicacion.getText().toString()+"");

            post=new HttpPost("http://192.168.1.112/api/values");
            post.setHeader("content-type", "application/json");

            JSONObject dato = new JSONObject();
            dato.put("nombreAnimal", animalSelected);
            dato.put("estadoAnimmal", radioB);
            dato.put("nombreCiudadanoReporte", inputName.getText().toString());
            dato.put("ubicacion", inputUbicacion.getText().toString());

            StringEntity entity = new StringEntity(dato.toString());
            post.setEntity(entity);


           // lispair=new ArrayList<NameValuePair>(4);
         //  lispair.add(new BasicNameValuePair("value", "TEXTO DESDE ANDROID"));

            ResponseHandler<String> handler = new BasicResponseHandler();
            //post.setEntity(new UrlEncodedFormEntity(lispair));
            result = cliente.execute(post, handler);
            dato=null;

            return true;
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }
        catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    class EnviarDatos extends AsyncTask<String,String,String> {
        private Activity context;
        EnviarDatos(Activity contex){
            this.context=contex;
        }

        @Override
        protected  String doInBackground(String... params){

            if(registroReporte()){
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(c, "Datos enviados correctamente", Toast.LENGTH_SHORT).show();
                        inputUbicacion.setText("");
                        inputName.setText("");
                    }
                });
            }
            else{
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(c, "Datos no enviados", Toast.LENGTH_SHORT).show();

                    }
                });
            }
            return null;
        }

    }





}
