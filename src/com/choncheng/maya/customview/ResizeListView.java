package com.choncheng.maya.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.choncheng.maya.R;

/**   
*    
* @��Ŀ���ƣ�Maya   
* @�����ƣ�ResizeListView   
* @�������� ��editext  listview
* @�����ˣ�� 
* @����ʱ�䣺2015-9-8 ����12:37:16   
* @�汾�ţ�v1.0   
*    
*/ 
public class ResizeListView extends ListView {
	private Context context;

	public ResizeListView(Context context) {
		super(context);
		this.context = context;
	}

	public ResizeListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	@Override
	public boolean dispatchKeyEventPreIme(KeyEvent event) {
		if (context != null) {
			InputMethodManager imm = (InputMethodManager) context
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			if (imm.isActive() && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				for (int i = 0; i < getChildCount(); i++) {
					View view = getChildAt(i);
					EditText editText1 = (EditText) view
							.findViewById(R.id.content_edit);
					editText1.clearFocus();
				}
			}
		}
		return super.dispatchKeyEventPreIme(event);
	}

}
