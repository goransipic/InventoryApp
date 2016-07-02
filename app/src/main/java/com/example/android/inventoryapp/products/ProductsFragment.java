package com.example.android.inventoryapp.products;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.data.source.ProductsDataSource;
import com.example.android.inventoryapp.data.source.ProductsRepository;
import com.example.android.inventoryapp.products.adapter.ProductsAdapter;
import com.example.android.inventoryapp.products.model.Product;
import com.example.android.inventoryapp.util.ImageUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by goransi on 1.7.2016..
 */
public class ProductsFragment extends Fragment implements ProductsContract.View {

    public static final int REQUEST_CODE = 0;
    private static final String TAG = ProductsFragment.class.getSimpleName();
    private ProductsContract.Presenter mPresenter;

    private ProductsAdapter mProductsAdapter;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private Uri fileUri;
    private File mediaStorageDir;
    private AlertDialog.Builder builder;
    private TextInputLayout mTextInputLayout;

    public static ProductsFragment newInstance() {
        return new ProductsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductsAdapter = new ProductsAdapter(new ArrayList<Product>(0), mProductItemListener);
        mPresenter = new ProductsPresenter(new ProductsRepository(), this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.product_frag, container, false);

        ListView listView = (ListView) root.findViewById(R.id.products_list);
        listView.setEmptyView(root.findViewById(R.id.noTasks));
        listView.setAdapter(mProductsAdapter);

        // Set up floating action button
        FloatingActionButton fab =
                (FloatingActionButton) getActivity().findViewById(R.id.fab_add_task);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(ProductsFragment.this.getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //takePictureButton.setEnabled(false);
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CODE);
                } else {
                    addNewProduct();
                }
            }
        });

        return root;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                addNewProduct();
            }
        }

    }

    @Override
    public void addNewProduct() {

        builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        View dialogView = inflater.inflate(R.layout.dialog_new_product, null);
        mTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.dialog_product_input);

        builder.setView(dialogView)
                .setTitle(R.string.add_new_product)
                .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        final AlertDialog alertDialog = builder.create();

        alertDialog.show();

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(mTextInputLayout.getEditText().getText())) {

                    // create Intent to take a picture and return control to the calling application
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    fileUri = getOutputMediaFileUri(); // create a file to save the image
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

                    // start the image capture Intent
                    startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
                    alertDialog.dismiss();

                } else {

                    mTextInputLayout.setError("Product name cannot be empty");
                    mTextInputLayout.setErrorEnabled(true);


                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent

                String productTemp = mTextInputLayout.getEditText().getText().toString();
                String quantityRandom = String.format(Locale.US, "%d", (int) (Math.random() * 10 + 1));
                String priceRandom = String.format(Locale.US, this.getString(R.string.price), (Math.random() * 12) + 1);

                Bitmap bitmapScaled = ImageUtils.decodeSampledBitmapFromFile(mediaStorageDir.getPath() + File.separator + "IMG" + ".jpg", 100);
                byte[] bitArrayImage = ImageUtils.convertBitmapToByteArray(bitmapScaled);

                Product product = new Product(productTemp, quantityRandom, priceRandom, bitArrayImage);

                mPresenter.addNewProduct(product);
                mPresenter.loadProducts(new ProductsDataSource.LoadProductsCallback() {
                    @Override
                    public void onProductsLoaded(List<Product> products) {
                        mProductsAdapter.replaceData(products);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.loadProducts(new ProductsDataSource.LoadProductsCallback() {
            @Override
            public void onProductsLoaded(List<Product> products) {
                mProductsAdapter.replaceData(products);
            }

            @Override
            public void onDataNotAvailable() {

            }
        });
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private Uri getOutputMediaFileUri() {
        return Uri.fromFile(getOutputMediaFile());
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        mediaStorageDir = new File(ProductsFragment.this.getContext().getExternalFilesDir(null), Environment.DIRECTORY_PICTURES);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG" + ".jpg");
    }


    public interface ProductItemListener {

        void onProductClick(Product clickedProduct);

    }

    ProductItemListener mProductItemListener = new ProductItemListener() {
        @Override
        public void onProductClick(Product clickedProduct) {

            if (Integer.parseInt(clickedProduct.getQuantity()) == 0) {
                Toast.makeText(ProductsFragment.this.getContext(), "No more Products", Toast.LENGTH_LONG).show();
            }
            else {
                mPresenter.onBuyButtonClicked(clickedProduct);
                mPresenter.loadProducts(new ProductsDataSource.LoadProductsCallback() {
                    @Override
                    public void onProductsLoaded(List<Product> products) {
                        mProductsAdapter.replaceData(products);
                    }

                    @Override
                    public void onDataNotAvailable() {

                    }
                });
            }


        }
    };

}
