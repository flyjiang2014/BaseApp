
package com.base.flyjiang.baseapp.jpush;
import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.util.Set;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author  作者 E-mail: 
 * @date 创建时间：2015-11-27  下午5:57:33 
 * @version 1.0 
 * @parameter 
 * @since  
 * @return 
 */

public class JpushUtil {
	
	
	protected static final String TAG = "JpushUtil";
	
	private static final int MSG_SET_ALIAS = 1001;
	private static final int MSG_SET_TAGS = 1002;
	private static Context mContext;
 
	
	public JpushUtil(Context mContext) {
		super();
		this.mContext = mContext;
	}
	
	
	private final TagAliasCallback mAliasCallback = new TagAliasCallback() {

        @Override
        public void gotResult(int code, String alias, Set<String> tags) {
            String logs ;
            switch (code) {
            case 0:
                logs = "Set tag and alias success";
                Log.i(TAG, logs);
                break;
                
            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                Log.i(TAG, logs);
                if (ExampleUtil.isConnected(mContext)) {
                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_ALIAS, alias), 1000 * 60);
                } else {
                	Log.i(TAG, "No network");
                }
                break;
            
            default:
                logs = "Failed with errorCode = " + code;
                Log.e(TAG, logs);
            }
            
          //  ExampleUtil.showToast(logs, mContext);
        }
	    
	};
	
	
	
	  
	 
	 private final TagAliasCallback mTagsCallback = new TagAliasCallback() {

	        @Override
	        public void gotResult(int code, String alias, Set<String> tags) {
	            String logs ;
	            switch (code) {
	            case 0:
	                logs = "Set tag and alias success";
	                Log.i(TAG, logs);
	                break;
	                
	            case 6002:
	                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
	                Log.i(TAG, logs);
	                if (ExampleUtil.isConnected(mContext)) {
	                	mHandler.sendMessageDelayed(mHandler.obtainMessage(MSG_SET_TAGS, tags), 1000 * 60);
	                } else {
	                	Log.i(TAG, "No network");
	                }
	                break;
	            
	            default:
	                logs = "Failed with errorCode = " + code;
	                Log.e(TAG, logs);
	            }
	            
	          //  ExampleUtil.showToast(logs, mContext);
	        }
	        
	    };

 private final Handler mHandler = new Handler() {
     @Override
     public void handleMessage(android.os.Message msg) {
         super.handleMessage(msg);
         switch (msg.what) {
        case MSG_SET_ALIAS:
             Log.d(TAG, "Set alias in handler.");
             JPushInterface.setAliasAndTags(mContext, (String) msg.obj, null, mAliasCallback);
             break;
             
         case MSG_SET_TAGS:
             Log.d(TAG, "Set tags in handler.");
             JPushInterface.setAliasAndTags(mContext, null, (Set<String>) msg.obj, mTagsCallback);
             break;
             
         default:
             Log.i(TAG, "Unhandled msg - " + msg.what);
         }
     }
 };
	
	public  void  setTag(final Context mContext,Set<String> tagSet) {
					
		
		/*Set<String> tagSet = new LinkedHashSet<String>();
		
		tagSet.add(tags);*/
		
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_TAGS, tagSet));
				
	}
	
	public  void   setAlias(final Context mContext,String alias) {
					
		
		mHandler.sendMessage(mHandler.obtainMessage(MSG_SET_ALIAS, alias));
				
	}
	
}
