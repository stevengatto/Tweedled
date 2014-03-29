package moms.app.android;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steve on 3/29/14.
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View listView = inflater.inflate(R.layout.home_fragment, container, false);

        //create mock list to populate ListView
        List<String> temp = new ArrayList<String>();
        for(int i=0; i<100; i++)
            temp.add(null);

        HomeAdapter adapter = new HomeAdapter(getActivity(), R.layout.poll_item, temp);
        ((ListView) listView).setAdapter(adapter);

        return listView;
    }
}