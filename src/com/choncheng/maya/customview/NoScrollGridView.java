package com.choncheng.maya.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**   
*    
* @��Ŀ���ƣ�Maya   
* @�����ƣ�NoScrollGridView   
* @��������   �Զ���gridview
* @�����ˣ�� 
* @����ʱ�䣺2015-8-8 ����5:08:34   
* @�汾�ţ�v1.0   
*    
*/ 
public class NoScrollGridView extends GridView {
	public NoScrollGridView(Context context) {
		super(context);

	}

	public NoScrollGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}

}
