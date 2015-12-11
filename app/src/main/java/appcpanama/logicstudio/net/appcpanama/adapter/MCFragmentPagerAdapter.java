package appcpanama.logicstudio.net.appcpanama.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import appcpanama.logicstudio.net.appcpanama.fragment.*;

/**
 * Created by LogicStudio on 22/10/2015.
 */
public class MCFragmentPagerAdapter extends FragmentPagerAdapter {


    final int PAGE_COUNT = 3;
    private String tabTitles[] =
            new String[] { "Llamar","Lecciones","Que Hacer"};


    public MCFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment f = null;

        switch(position) {
            case 0:
                f = LlamarFragment.newInstance();
            break;
            case 1:
                f =LeccionFragment.newInstance();
                break;
            case 2:
                f =QueHacerFragment.newInstance();
                break;
        }
        return f;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
