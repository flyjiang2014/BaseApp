package com.carking.quotationlibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.widget.TextView;

import com.carking.quotationlibrary.R;

/**
 * Created by flyjiang 2016-08-22
 * ＊功能：用于加载数据进度提示
 */
public class CustomProgressDialog extends Dialog {
    private Context context = null;
    private static CustomProgressDialog customCustomProgressDialog = null;

    public CustomProgressDialog(Context context) {
        super(context);
        this.context = context;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    public static CustomProgressDialog createLoadingDialog(Context context, String msg) {
        customCustomProgressDialog = new CustomProgressDialog(context, R.style.loading_dialog);
        customCustomProgressDialog.setContentView(R.layout.dialog_progress_layout);
       // customCustomProgressDialog.setOnKeyListener(keylistener);//手机返回按键也不可以取消
        customCustomProgressDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        customCustomProgressDialog.setCanceledOnTouchOutside(false);//dialog外部点击是否可取消
        customCustomProgressDialog.setCancelable(false); //手机返回按键也不可以取消
        TextView tvMsg = (TextView) customCustomProgressDialog.findViewById(R.id.id_tv_loadingmsg);
        tvMsg.setText(msg);
        return customCustomProgressDialog;
    }

    /**
     * 设定手机返回键也不能取消对话框
     */
/*    public static OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    } ;*/
}
