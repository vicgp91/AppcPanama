package appcpanama.logicstudio.net.appcpanama;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import appcpanama.logicstudio.net.appcpanama.model.Animal;

/**
 * Created by José on 17/9/15.
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Inicializa la cadena de conexion de los servicios.
        Common.RootServiceUrl = getString(R.string.base_service_url);
        Common.RootWebSiteUrl = getString(R.string.base_website_url);

        // Busca el nombre de usuario que se ha logueado con anterioridad a la aplicacion.
        setToolbar();




        ((Button) findViewById(R.id.btnLogin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    ClienteAppApplication app = (ClienteAppApplication) getApplication();
                    List<Animal> animalList =new ArrayList<>();
                    Animal peresozo = new Animal("Perezoso de dos dedos", "Choloepus hoffmanni", R.drawable.perezoso_de_2_dedos);
                    Animal peresozo2 = new Animal("Perezoso de tres dedos", "Bradipus variegatus", R.drawable.perezoso_de_3_dedos);
                    Animal titi = new Animal("Mono Tit\u00ed", "Saguinus geoffroyi ", R.drawable.titi);
                    Animal neque = new Animal("\u00d1eque", "Dasyprocta punctata", R.drawable.neque);
                    Animal coati = new Animal("Coat\u00ed (Gato s\u00f3lo)", "Nasua narica", R.drawable.coati);
                    //Animal tigrillo = new Animal("Tigrillo", "Leopardus pardalisi", R.drawable.tigrillo);
                    animalList.add(peresozo);
                    animalList.add(peresozo2);
                    //animalList.add(tigrillo);
                    animalList.add(titi);
                    animalList.add(neque);
                    animalList.add(coati);
                    app.setAnimalList(animalList);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Exception ex) {
                    Toast.makeText(getBaseContext(), ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    private void setToolbar() {
        // A�adir la Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rescate Animal");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_white_24dp);
        getSupportActionBar().setTitle("Rescate Animal");
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_ayuda: {
                        AboutAppActivity.createInstance(LoginActivity.this);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
