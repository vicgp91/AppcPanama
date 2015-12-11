package appcpanama.logicstudio.net.appcpanama;

import android.app.Application;


import java.util.List;

import appcpanama.logicstudio.net.appcpanama.model.Animal;

/**
 * Created by LogicStudio on 17/09/2015.
 */
public class ClienteAppApplication  extends Application {


    private List<Animal> animalList;


    public List<Animal> getAnimalList() {
        return animalList;
    }

    public void setAnimalList(List<Animal> animalList) {
        this.animalList = animalList;
    }
}
