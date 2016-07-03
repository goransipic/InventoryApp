package com.example.android.inventoryapp.detailproduct;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.R;
import com.example.android.inventoryapp.data.source.ProductsDataSource;
import com.example.android.inventoryapp.data.source.ProductsRepository;
import com.example.android.inventoryapp.products.ProductsFragment;
import com.example.android.inventoryapp.products.model.Product;

import java.util.Locale;

public class DetailProductActivity extends AppCompatActivity {

    private ImageView mDetailImage;
    private ImageButton mButtonIncrease;
    private ImageButton mButtonDecrease;
    private TextView mTextView;

    private ProductsDataSource mProductsDataSource = new ProductsRepository();
    private ShareActionProvider mShareActionProvider;

    private Product mProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        mDetailImage = (ImageView) findViewById(R.id.detail_product_image);
        mButtonIncrease = (ImageButton) findViewById(R.id.button_increase);
        mButtonDecrease = (ImageButton) findViewById(R.id.button_decrease);
        mTextView = (TextView) findViewById(R.id.detail_quantity);


        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mProduct = (Product) intent.getExtras().get(ProductsFragment.INTENT_EXTRA);
        getSupportActionBar().setTitle(mProduct.getProduct());

        mDetailImage.setImageBitmap(BitmapFactory.decodeByteArray(mProduct.getImage(), 0, mProduct.getImage().length));
        mTextView.setText(String.format(Locale.US, "%d", mProduct.getQuantity()));

        mButtonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProduct.setQuantity(mProduct.getQuantity() + 1);
                mProductsDataSource.updateProduct(mProduct);

                mTextView.setText(String.format(Locale.US, "%d", mProduct.getQuantity()));

                setShareIntent();
                setIntent();
            }
        });

        mButtonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mTextView.getText().equals("0")) {
                    mProduct.setQuantity(mProduct.getQuantity() - 1);
                    mProductsDataSource.updateProduct(mProduct);

                    mTextView.setText(String.format(Locale.US, "%d", mProduct.getQuantity()));

                    setShareIntent();
                    setIntent();
                } else {
                    Toast.makeText(DetailProductActivity.this.getApplicationContext(), R.string.no_products_all, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setIntent() {
        Intent intent = new Intent(DetailProductActivity.this, DetailProductActivity.class);
        intent.putExtra(ProductsFragment.INTENT_EXTRA, mProduct);
        setIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        setShareIntent();

        // Return true to display menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_item_delete){

            AlertDialog.Builder builder = new AlertDialog.Builder(DetailProductActivity.this);
            builder.setMessage(getString(R.string.delete_message) + mProduct.getProduct() + " ?")
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mProductsDataSource.deleteProduct(mProduct);

                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create().show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", "company@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Order new Product (InventoryApp)");
        emailIntent.putExtra(Intent.EXTRA_TEXT, mProduct.toString());

        mShareActionProvider.setShareIntent(emailIntent);
    }
}
