package appcpanama.logicstudio.net.appcpanama.fragment;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;

import appcpanama.logicstudio.net.appcpanama.Common;
import appcpanama.logicstudio.net.appcpanama.R;
import fr.ganfra.materialspinner.MaterialSpinner;


public class LlamarFragment extends Fragment {


    private EditText inputName, inputEmail, inputUbicacion;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutUbicacion;
    private Button btnSignUp;
    private TextView textViewMessage;

    private FloatingActionButton fabCamera,fabFolder;

    private CoordinatorLayout coordinator;
    private ArrayAdapter<String> adapter;
    private static final int PHOTO = 200;
    private static final int FILE = 100;
    private Bitmap _photo = null;
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
        adapter = new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,  ANIMALS);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
        initSpinnerMultiline(view);

        fabCamera =(FloatingActionButton) view.findViewById(R.id.fabCamera);
        fabFolder= (FloatingActionButton) view.findViewById(R.id.fabFolder);


        textViewMessage= (TextView) view.findViewById(R.id.lblText);
        textViewMessage.setText(Html.fromHtml("<p>S\u00ed encontraste alg\u00fan animal en peligro reportalo al: <h3 style='color:#00C853; forecolor:#00C853; font-size: 18px'>64977223</h3></p>"));


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();
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
                    validateName();
                    break;
                /*case R.id.input_email:
                    validateEmail();
                    break;*/
                case R.id.input_ubicacion:
                    validateUbicacion();
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

}
