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
import appcpanama.logicstudio.net.appcpanama.adapter.RVPromoListAdapter;
import appcpanama.logicstudio.net.appcpanama.model.Animal;
import appcpanama.logicstudio.net.appcpanama.viewitem.ListViewItem;

public class QueHacerFragment extends Fragment {

    RecyclerView mRecyclerViewQueHacer;
    RVPromoListAdapter adapterQueHacer;


    public static QueHacerFragment newInstance() {
        QueHacerFragment fragment = new QueHacerFragment();
        return fragment;
    }

    public QueHacerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_que_hacer, container, false);
        final FragmentActivity c = getActivity();
        final ClienteAppApplication app = (ClienteAppApplication) getActivity().getApplicationContext();

        ArrayList<ListViewItem> lst = new ArrayList<ListViewItem>();
        ListViewItem item = new ListViewItem();

        item.image = R.drawable.quehacer_1;
        item.text = "Siempre ayuda al perezoso a cruzar en la dirección que iba, no lo regreses porque seguirá intentando cruzar.";
        lst.add(item);

        item = new ListViewItem();

        item.image = R.drawable.sujetar_perezoso_2;
        item.text = "Detén el tráfico o ayúdalo a cruzar, ¿Cómo? utiliza una rama para que se agarre " +
                "o sujétalo por la espalda con tu brazo extendido.";
        lst.add(item);

        item = new ListViewItem();
        item.image = R.drawable.quehacer_3;
        item.text = "Son muy parecidos a los perezosos de tres dedos, con la diferencia que no tienen cola y en el número de dedos. " +
                "El pelaje es largo, grueso y ondulado, los cabellos son de color marrón con puntas de color crema. ";
        lst.add(item);

        item = new ListViewItem();
        item.image = R.drawable.titi;
        item.text = "Es el más pequeño de los monos que habitan nuestros bosques. Se alimenta de insectos y los frutos del bosque.";
        lst.add(item);

        item = new ListViewItem();
        item.image = R.drawable.neque;
        item.text = "Es una especie de roedor. Se alimenta principalmente de frutos, entierra semillas en su territorio. Una pareja ocupa un área de 2 a 3 hectáreas que defiende.";
        lst.add(item);

        mRecyclerViewQueHacer = (RecyclerView) view.findViewById(R.id.rvQueHacer);
        mRecyclerViewQueHacer.setHasFixedSize(true);
        mRecyclerViewQueHacer.setLayoutManager(new LinearLayoutManager(c));
        adapterQueHacer = new RVPromoListAdapter(lst, c);
        mRecyclerViewQueHacer.setAdapter(adapterQueHacer);

        return view;
    }
}
