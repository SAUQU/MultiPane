package com.example.segundoauqui.multipane;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsFragment} interface
 * to handle interaction events.
 * Use the {@link DetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE = 1777;

    DataBaseHelper databaseHelper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    EditText etName;
    EditText etOrigin;
    EditText etInstrument;
    Button btnSaveData;
    ImageButton ibtnTakePicture;
    Bitmap bitmap;
    String filepath = "";
    String source = "";
    String id;
    DataBaseHelper databasehelper;
    Button btnDelete;

    private OnFragmentInteractionListener mListener;

    public DetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Integer intt) {
        if (mListener != null) {
            mListener.onFragmentInteraction(intt);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ListFragment.OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etName = (EditText) view.findViewById(R.id.etName);
        etOrigin = (EditText) view.findViewById(R.id.etOrigin);
        etInstrument = (EditText) view.findViewById(R.id.etInstruments);
        btnSaveData = (Button) view.findViewById(R.id.btnSaveData);
        btnDelete = (Button) view.findViewById(R.id.btnDelete);
        ibtnTakePicture = (ImageButton) view.findViewById(R.id.ibtnTakePicture);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        databasehelper = new DataBaseHelper(getActivity());

        id = mParam1;
        Log.d(TAG, "onActivityCreated: "+ id);
        if(id != "-1"){
            try {

            ArrayList<MyContacs> listArtists = databasehelper.getContacs(id);
            if (listArtists.size() > 0) {
                etName.setText(listArtists.get(0).getEtName());
                etOrigin.setText(listArtists.get(0).getEtOrigin());
                etInstrument.setText(listArtists.get(0).getEtInstrument());
                byte[] b = listArtists.get(0).getB();
                bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                ibtnTakePicture.setImageBitmap(bitmap);
                Log.d(TAG, "onCreate: " + id+ listArtists.get(0).getEtName());
            }

            }catch(Exception ex){
                //Toast.makeText(getActivity(),"" + ex, Toast.LENGTH_SHORT).show();
            }
        }


        ibtnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                startActivityForResult(intent, CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE);

            }
        });


        btnSaveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etName.getText().toString().isEmpty() && etOrigin.getText().toString().isEmpty() && etInstrument.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Missing Entries", Toast.LENGTH_LONG).show();
                } else {

                    byte[] lop = null;
                    try{
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
                        lop = stream.toByteArray();
                    }catch(Exception ex){}

                    MyContacs contact = new MyContacs(etName.getText().toString(), etOrigin.getText().toString(), etInstrument.getText().toString(), lop, -1);

                    databasehelper.saveNewContact(contact);
                    mListener.onFragmentInteraction(-1);

                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Log.d(TAG, "onClick:deleted "+id);
                    if (Integer.parseInt(id) >= 0) {
                        databaseHelper = new DataBaseHelper(getActivity());
                        databaseHelper.delete(id);
                        Toast.makeText(getActivity(), "Contact Deleted", Toast.LENGTH_SHORT).show();

                        etName.setText(" ");
                        etOrigin.setText(" ");
                        etInstrument.setText(" ");
                        ibtnTakePicture.setImageResource(R.drawable.ic_camera_black_24dp);
                        bitmap = null;
                        mListener.onFragmentInteraction(-1);


                    }
                } catch (Exception ex) {
                    Log.d(TAG, "onClickdelete2: "+ex.getMessage());
                }
            }
        });
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (bitmap != null) {
            outState.putString("img", "uploaded");
            outState.putString("source", source);
            outState.putString("filepath", filepath);

        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_FULLSIZE_ACTIVITY_REQUEST_CODE) {

            File file = new File(Environment.getExternalStorageDirectory() + File.separator +
                    "image.jpg");
            filepath = file.getAbsolutePath();
            Log.d(TAG, "onActivityResult: " + file.getAbsolutePath());

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                source = "landscape";
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 768, 1024);
                ibtnTakePicture.setImageBitmap(bitmap);
                ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
            } else {
                bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1024, 768);
                source = "portrait";
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap bitmap2 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                ibtnTakePicture.setImageBitmap(bitmap2);
                ibtnTakePicture.setScaleType(ImageView.ScaleType.FIT_XY);
            }


        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }





//    public interface OnFragmentInteractionListener {
//         Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
//        try {
//            if (Integer.parseInt(string) > 0) {
//                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//                ActivityContactInfo fragmentContactInfo = ActivityContactInfo.newInstance(string,"");
//                ft.replace(R.id.flFrag1, fragmentContactInfo, fragmentContactInfo.getClass().getName());
//                ft.addToBackStack(fragmentContactInfo.getClass().getName());
//                ft.commit();
//            }
//        }
//
//        catch (Exception ex){
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ActivityContactInfo fragmentContactInfo = new ActivityContactInfo();
//            ft.replace(R.id.flFrag1, fragmentContactInfo, fragmentContactInfo.getClass().getName());
//            ft.addToBackStack(fragmentContactInfo.getClass().getName());
//            ft.commit();
//        }
//    }



        //void onFragmentInteraction(String string);


    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(int inter);
    }
}



