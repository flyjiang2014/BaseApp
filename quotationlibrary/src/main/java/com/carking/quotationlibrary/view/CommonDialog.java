package com.carking.quotationlibrary.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import com.carking.quotationlibrary.R;
/**
 * 自定义对话框
 */
public class CommonDialog extends Dialog {

    public CommonDialog(Context context) {
        super(context);
    }

    public CommonDialog(Context context, int theme) {
        super(context, theme);
    }

    public static class Builder {
        private Context context;
        private String title;
        private String message;
        private String positiveButtonText;
        private String negativeButtonText;
        private View contentView;
        private OnClickListener positiveButtonClickListener;
        private OnClickListener negativeButtonClickListener;

        private Boolean CanceledOnTouchOutside =true;

        private boolean visibleNegative = false;

        private boolean visiblePositive = false;

        private Boolean isSetTitleColour = false;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * Set the Dialog message from resource
         *
         * @param
         * @return
         */
        public Builder setMessage(int message) {
            this.message = (String) context.getText(message);
            return this;
        }

        /**
         * Set the Dialog title from resource
         *
         * @param title
         * @return
         */
        public Builder setTitle(int title) {
            this.title = (String) context.getText(title);
            return this;
        }


        /**
         * 设定 dialog 外围是否可点击
         *
         */
        public void setCanceledOnTouchOutside(Boolean canceledOnTouchOutside) {
            CanceledOnTouchOutside = canceledOnTouchOutside;
        }

        public void setIsSetTitleColour(Boolean isSetTitleColour) {
            this.isSetTitleColour = isSetTitleColour;
        }

        /**
         * Set the Dialog title from String
         *
         * @param title
         * @return
         */

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        //3

        public void setPositiveButton(String positiveButtonText,
                                      OnClickListener listener, boolean visiblePositive) {
            setPositiveButton(positiveButtonText, listener, true);
        }

        public void setNegativeButton(String negativeButtonText,
                                      OnClickListener listener, boolean visibleNegative) {
            setNegativeButton(negativeButtonText, listener, visibleNegative);
        }


        public Builder setPositiveButton(String positiveButtonText,
                                         OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }


        public Builder setNegativeButton(String negativeButtonText,
                                         OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }


        public CommonDialog create() {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final CommonDialog dialog = new CommonDialog(context, R.style.Dialog);
            View layout = inflater.inflate(R.layout.dialog_layout, null);

            dialog.addContentView(layout, new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT));
            // set the dialog title

          //  dialog.setOnKeyListener(keylistener);

            TextView tvTittle = ((TextView) layout.findViewById(R.id.tvTittle));

            if (title.equals("")) {
                tvTittle.setVisibility(View.GONE);
            } else {
                tvTittle.setText(title);
            }
            if(isSetTitleColour){

                tvTittle.setTextColor(0xffbcd4a7);
            }

            ((TextView) layout.findViewById(R.id.tvContent)).setText(message);
            // set the confirm button
            Button btnPositive = (Button) layout.findViewById(R.id.btnPositive);
            Button btnNegative = (Button) layout.findViewById(R.id.btnNegative);
            if (positiveButtonText != null && positiveButtonClickListener != null) {
                btnPositive.setText(positiveButtonText);
                btnPositive
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                positiveButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_POSITIVE);
                            }
                        });
                btnPositive.setVisibility(View.VISIBLE);
            } else {
                btnPositive.setVisibility(View.GONE);

            }


            if (negativeButtonText != null && negativeButtonClickListener != null) {

                btnNegative.setText(negativeButtonText);
                btnNegative
                        .setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {

                                negativeButtonClickListener.onClick(dialog,
                                        DialogInterface.BUTTON_NEGATIVE);
                            }
                        });
                btnNegative.setVisibility(View.VISIBLE);
            } else {
                btnNegative.setVisibility(View.GONE);
            }
            dialog.setContentView(layout);
            dialog.setCanceledOnTouchOutside(CanceledOnTouchOutside);
            return dialog;
        }
    }

    /**
     * 设定手机返回键也不能取消对话框
     */
    static OnKeyListener keylistener = new DialogInterface.OnKeyListener(){
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
    } ;
}
