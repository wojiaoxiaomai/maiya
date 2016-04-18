package com.choncheng.maya.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**   
*    
* @项目名称：Maya   
* @类名称：NoScrollGridView   
* @类描述：   自定义gridview
* @创建人：李波 
* @创建时间：2015-8-8 下午5:08:34   
* @版本号：v1.0   
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
