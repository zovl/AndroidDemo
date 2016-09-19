package zovl.zhongguanhua.framework.lib.framework;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import zovl.zhongguanhua.framework.lib.utils.ToastHelper;

public abstract class TBaseActivity extends BaseActivity {

    private Context context;
    protected Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setTitle(tag);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (handler != null)
            handler.removeCallbacksAndMessages(null);
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    /**
     * @deprecated
     * @param text
     */
    private void toastShort(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastHelper.s(text);
            }
        });
    }

    /**
     * @deprecated
     * @param text
     */
    private void toastLong(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastHelper.l(text);
            }
        });
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    private AlertDialog alertDialog;

    protected void showDialog(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (alertDialog == null) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog_Alert);
                    builder.setTitle(tag);
                    builder.setCancelable(false);
                    builder.setMessage(msg);
                    alertDialog = builder.create();
                }
                alertDialog.setMessage(msg);
                alertDialog.show();
            }
        });
    }

    protected void setDialogMsg(final String msg) {
        if (alertDialog == null)
            return;
        synchronized (this) {
            handler.post(new Runnable() {
                @Override
                public void run() {

                    alertDialog.setMessage(msg);
                }
            });
        }
    }

    protected void dismissDialog() {

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (alertDialog != null) {
                    alertDialog.dismiss();
                    alertDialog = null;
                }
            }
        });
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    protected TextView getText() {
        return null;
    }

    protected String getTextString() {
        if (getText() != null &&
                getText().getText() != null) {
            return getText().getText().toString();
        }
        return "";
    }

    protected void log(String msg) {
        Log.d(tag, msg);
    }

    protected void logText(String msg) {
        Log.d(tag, msg);
        setText(msg);
    }

    protected void setText(final String text) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getText() != null && text != null) {
                    getText().setText(text + "\n" +
                            getTextString());
                }
            }
        });
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    protected ImageView getImage() {
        return null;
    }

    protected void load(final Bitmap b) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getImage() != null && b != null)
                    getImage().setImageBitmap(b);
            }
        });
    }

    protected void load(final File f) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (getImage() == null)
                    return;
                if (f == null || !f.exists())
                    return;
                InputStream is = null;
                try {
                    is = new FileInputStream(f);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                if (is == null)
                    return;
                Bitmap b = BitmapFactory.decodeStream(is);
                getImage().setImageBitmap(b);
            }
        });
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        try {
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void startActivity(String pkg, String cls) {
        Context context = AppManager.getInstance().getContext();
        ComponentName componet = new ComponentName(pkg, cls);
        Intent intent = new Intent();
        intent.setComponent(componet);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    protected void openMp4File(File file) {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= 16) {
                intent.setDataAndTypeAndNormalize(uri, "video/mp4");
            }
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void openVideoFile(File file) {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= 16) {
                intent.setDataAndTypeAndNormalize(uri, "video");
            }
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void openImageFile(File file) {
        if (file != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= 16) {
                intent.setDataAndTypeAndNormalize(uri, "image");
            }
            try {
                startActivity(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------
    // ----------------------------------------------------------------------------------------

    private static final int REQUEST_CODE_FILE = 11;

    protected void selectFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a File"), REQUEST_CODE_FILE);
        } catch (ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)  {
        Log.d(tag, "onActivityResult: requestCode=" + requestCode);
        Log.d(tag, "onActivityResult: resultCode=" + resultCode);
        if (resultCode == RESULT_OK) {
            // 选择文件
            if (requestCode == REQUEST_CODE_FILE) {
                Uri uri = data.getData();
                Log.d(tag, "onActivityResult: uri=" + uri);
                String path = null;
                if ("content".equalsIgnoreCase(uri.getScheme())) {
                    String[] projection = { "_data" };
                    Cursor cursor;
                    try {
                        cursor = getContentResolver().query(uri, projection,null, null, null);
                        int column_index = cursor.getColumnIndexOrThrow("_data");
                        if (cursor.moveToFirst()) {
                            path = cursor.getString(column_index);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if ("file".equalsIgnoreCase(uri.getScheme())) {
                    path = uri.getPath();
                }
                if (path != null) {
                    onResultFile(path);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onResultFile(String path) {
        Log.d(tag, "onResultFile: path=" + path);
    }
}
