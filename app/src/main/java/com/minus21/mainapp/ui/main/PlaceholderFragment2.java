package com.minus21.mainapp.ui.main;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.minus21.mainapp.R;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment2 extends Fragment {
    private ArrayList<String> mArrayList = new ArrayList<String>();
    private ImageAdapter mAdapter;
    private Context context;
    private RecyclerView mRecyclerView;
    private GridLayoutManager mLayoutManager;
    private ContentResolver contentResolver;

//    private ScaleGestureDetector mScaleGestureDetector;
//    private float mScaleFactor = 1.0f;
//    private ImageView mImageView;

    private static final String ARG_SECTION_NUMBER = "section_number";
    public static PlaceholderFragment2 newInstance(int index) {
        PlaceholderFragment2 fragment = new PlaceholderFragment2();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }

        mArrayList = new ArrayList<>();
        mAdapter = new ImageAdapter(context,mArrayList);

        contentResolver = context.getContentResolver();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main2, container, false);
        mRecyclerView = (RecyclerView) root.findViewById(R.id.gallery);
        mRecyclerView.setHasFixedSize(true);

        int numberOfColumns = 4;
        mLayoutManager = new GridLayoutManager(context,numberOfColumns);

        mRecyclerView.setLayoutManager(mLayoutManager);

        mArrayList = new ArrayList<>();

        mAdapter = new ImageAdapter(context,mArrayList);
        mRecyclerView.setAdapter(mAdapter);
        updateData();

//        View v = inflater.inflate(R.layout.img_popup,container,false);
//        mImageView = (ImageView) v.findViewById(R.id.expanded_img);
//        mScaleGestureDetector = new ScaleGestureDetector(context,new ScaleListener());

        return root;
    }

    /* Update mArrayList and notify the change to the adapter */
    private void updateData(){
        mArrayList.clear();

        String[] projection = {
                MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME};

        Cursor cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, "UPPER(" + MediaStore.Images.Media.DATE_TAKEN + ") ASC");

        if (cursor.moveToFirst()) {
            do {
                Uri ContentUri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
                mArrayList.add(String.valueOf(ContentUri));
            } while (cursor.moveToNext());
        }
        cursor.close();

        /* Notify to the adapter */
        mAdapter.notifyDataSetChanged();
    }

//    public boolean onTouchEvent(MotionEvent motionEvent) {
//        mScaleGestureDetector.onTouchEvent(motionEvent);
//        return true;
//    }
//
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//        @Override
//        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
//            // ScaleGestureDetector에서 factor를 받아 변수로 선언한 factor에 넣고
//            mScaleFactor *= scaleGestureDetector.getScaleFactor();
//
//            // 최대 10배, 최소 10배 줌 한계 설정
//            mScaleFactor = Math.max(0.1f,
//                    Math.min(mScaleFactor, 10.0f));
//
//            // 이미지뷰 스케일에 적용
//            mImageView.setScaleX(mScaleFactor);
//            mImageView.setScaleY(mScaleFactor);
//            return true;
//        }
//    }
}