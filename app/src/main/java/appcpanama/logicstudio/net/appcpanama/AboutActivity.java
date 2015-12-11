package appcpanama.logicstudio.net.appcpanama;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import appcpanama.logicstudio.net.appcpanama.viewitem.ListViewItem;


public class AboutActivity extends AppCompatActivity {


    private ImageView imgAnimal;
    private TextView lblNombreAnimal, lblNombreCientifico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        setToolbar();// Añadir action bar
        if (getSupportActionBar() != null) // Habilitar up button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String nombreAnimal = i.getStringExtra("nombreAnimal");
        String nombreCientifico = i.getStringExtra("nombreCientifico");

        String imagen = i.getStringExtra("imagen");
        Integer imagenAnimal = Integer.valueOf(i.getStringExtra("imagen"));

        imgAnimal =(ImageView) findViewById(R.id.img_animal);
        lblNombreAnimal =(TextView) findViewById(R.id.lblNombreAnimal);
        lblNombreCientifico =(TextView) findViewById(R.id.lblNombreCientifico);

        imgAnimal.setImageResource(imagenAnimal);
        lblNombreAnimal.setText(nombreAnimal);
        lblNombreCientifico.setText(nombreCientifico);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    public static void createInstance(Activity activity,ListViewItem detail) {
        Intent intent = getLaunchIntent(activity,detail);
        activity.startActivity(intent);
    }

    public static Intent getLaunchIntent(Context context , ListViewItem detail) {
        Intent intent = new Intent(context, AboutActivity.class);

        intent.putExtra("nombreAnimal", detail.text);
        intent.putExtra("nombreCientifico", detail.subText);
        intent.putExtra("imagen", String.valueOf(detail.image));
        return intent;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setToolbar() {
        // A�adir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        getSupportActionBar().setTitle("Acerca de...");
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }

}
