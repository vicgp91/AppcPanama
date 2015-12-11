package appcpanama.logicstudio.net.appcpanama.fragment;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import appcpanama.logicstudio.net.appcpanama.ClienteAppApplication;
import appcpanama.logicstudio.net.appcpanama.R;
import appcpanama.logicstudio.net.appcpanama.adapter.RVImageListToolBarAdapter;
import appcpanama.logicstudio.net.appcpanama.model.Animal;
import appcpanama.logicstudio.net.appcpanama.viewitem.ListViewItem;


public class LeccionFragment extends Fragment {

    RecyclerView mRecyclerViewAnimales;
    RVImageListToolBarAdapter adapterAnimales;

    public static LeccionFragment newInstance() {
        LeccionFragment fragment = new LeccionFragment();
        return fragment;
    }

    public LeccionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_leccion, container, false);
        final FragmentActivity c = getActivity();
        final ClienteAppApplication app = (ClienteAppApplication) getActivity().getApplicationContext();
        final List<Animal> animalList = app.getAnimalList();
        final ArrayList<ListViewItem> animalsToShow = getAnimales(animalList);
        mRecyclerViewAnimales = (RecyclerView) view.findViewById(R.id.rvAnimales);
        mRecyclerViewAnimales.setHasFixedSize(true);
        mRecyclerViewAnimales.setLayoutManager(new LinearLayoutManager(c));
        adapterAnimales = new RVImageListToolBarAdapter(animalsToShow, c, false);
        mRecyclerViewAnimales.setAdapter(adapterAnimales);

        return view;
    }


    private ArrayList<ListViewItem> getAnimales( List<Animal> animales) {
        ArrayList<ListViewItem> lst = new ArrayList<ListViewItem>();
        for (Animal animal : animales) {
            ListViewItem item = new ListViewItem();
            item.image = animal.getImageAnimal();
            item.text = animal.getNombre();
            item.subText = animal.getNombreCientifico();
            lst.add(item);
        }
        return lst;
    }
}
