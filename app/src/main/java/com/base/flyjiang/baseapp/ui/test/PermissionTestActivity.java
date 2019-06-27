package com.base.flyjiang.baseapp.ui.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.base.flyjiang.baseapp.R;

public class PermissionTestActivity extends AppCompatActivity {
   // implements PermissionListener
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_test);
//        AndPermission.with(this)
//        .requestCode(100)
//                .permission(Manifest.permission.WRITE_CONTACTS, Manifest.permission.READ_SMS)
//                        // rationale作用是：用户拒绝一次权限，再次申请时先征求用户同意，再打开授权对话框，避免用户勾选不再提示。
//                .rationale((requestCode, rationale) ->
//                                // 这里的对话框可以自定义，只要调用rationale.resume()就可以继续申请。
//                                AndPermission.rationaleDialog(PermissionTestActivity.this, rationale).show()
//                )
//                .send();

   }
}


  /*  @Override
    public void onSucceed(int requestCode, List<String> grantPermissions) {

    }

    @Override
    public void onFailed(int requestCode, List<String> deniedPermissions) {

    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
*/
