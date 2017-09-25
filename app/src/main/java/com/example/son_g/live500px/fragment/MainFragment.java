package com.example.son_g.live500px.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.son_g.live500px.R;
import com.example.son_g.live500px.adapter.PhotoListAdapter;
import com.example.son_g.live500px.dao.PhotoItemCollectionDao;
import com.example.son_g.live500px.manager.Contextor;
import com.example.son_g.live500px.manager.HttpManager;
import com.example.son_g.live500px.manager.PhotoListManager;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by nuuneoi on 11/16/2014.
 */
public class MainFragment extends Fragment {

    ListView listView;
    PhotoListAdapter photoListAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    PhotoListManager photoListManager;
    Button btnNewPhoto;
    boolean isLoadmore = false;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        // Init 'View' instance(s) with rootView.findViewById here
        photoListManager = new PhotoListManager();
        listView = rootView.findViewById(R.id.listView);
        photoListAdapter = new PhotoListAdapter();
        listView.setAdapter(photoListAdapter);
        swipeRefreshLayout = rootView.findViewById(R.id.swipeRefreshLayout);
        btnNewPhoto = rootView.findViewById(R.id.btnNewPhoto);

        btnNewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.smoothScrollToPosition(0);
                hiddenBtnNewPhoto();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                if (i + i1 >= i2) {
                    if(photoListManager.getCount() > 0){
                        loadMoreData();
                    }
                }
            }
        });
        refreshData();
    }

    private void reloadData() {
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance().getApiService().loadPhotolist();
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_RELOAD));
    }

    private void refreshData() {
        if (photoListManager.getCount() == 0) {
            reloadData();
        } else {
            reloadDataNewer();
        }
    }

    private void reloadDataNewer() {
        int maxId = photoListManager.getMaxId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getApiService()
                .loadPhotolistAfterId(maxId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_REFRESH));
    }

    private void loadMoreData() {
        if (isLoadmore)
            return;
        isLoadmore = true;
        int minId = photoListManager.getMinId();
        Call<PhotoItemCollectionDao> call = HttpManager.getInstance()
                .getApiService()
                .loadPhotolistBeforeId(minId);
        call.enqueue(new PhotoListLoadCallback(PhotoListLoadCallback.MODE_LOAD_MORE));
    }

    class PhotoListLoadCallback implements Callback<PhotoItemCollectionDao> {

        public static final int MODE_RELOAD = 1;
        public static final int MODE_REFRESH = 2;
        public static final int MODE_LOAD_MORE = 3;

        int mode;

        public PhotoListLoadCallback(int mode) {
            this.mode = mode;
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onResponse(Call<PhotoItemCollectionDao> call, Response<PhotoItemCollectionDao> response) {
            swipeRefreshLayout.setRefreshing(false);
            if (response.isSuccessful()) {
                PhotoItemCollectionDao dao = response.body();

                int firstVisble = listView.getFirstVisiblePosition();
                View c = listView.getChildAt(0);
                int top = c == null ? 0 : c.getTop();

                if (mode == MODE_RELOAD) {
                    photoListManager.setDao(dao);
                }
                else if(mode == MODE_LOAD_MORE){
                    photoListManager.appendDaoToButtom(dao);
                    isLoadmore = false;
                } else {
                    photoListManager.appendDao(dao);
                }
                photoListAdapter.setDao(photoListManager.getDao());
                photoListAdapter.notifyDataSetChanged();

                if (mode == MODE_REFRESH) {
                    int addPosition = (dao != null && dao.getData() != null) ? dao.getData().size() : 0;
                    photoListAdapter.increaseLastPosition(addPosition);
                    if (addPosition > 0) {
                        showBtnNewPhoto();
                    }
                    listView.setSelectionFromTop(firstVisble + addPosition, top);
                } else {
                }

                Toast.makeText(Contextor.getInstance().getContext(),
                        "Load Completed",
                        Toast.LENGTH_SHORT).show();
            } else {
                if(mode == MODE_LOAD_MORE) {
                    isLoadmore = false;
                }
                try {
                    Toast.makeText(Contextor.getInstance().getContext(),
                            response.errorBody().string(),
                            Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void onFailure(Call<PhotoItemCollectionDao> call, Throwable t) {
            if(mode == MODE_LOAD_MORE) {
                isLoadmore = false;
            }
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(Contextor.getInstance().getContext(),
                    t.toString(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public void showBtnNewPhoto() {
        btnNewPhoto.setVisibility(View.VISIBLE);
    }

    public void hiddenBtnNewPhoto() {
        btnNewPhoto.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    /*
     * Save Instance State Here
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save Instance State here
    }

    /*
     * Restore Instance State Here
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore Instance State here
        }
    }
}
