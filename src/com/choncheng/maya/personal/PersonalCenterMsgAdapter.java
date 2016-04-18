package com.choncheng.maya.personal;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.comm.entity.User;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.DateTime;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @项目名称：Maya
 * @类名称：PersonalCenterMsgAdapter
 * @类描述： 消息adapter
 * @创建人：李波
 * @创建时间：2015-8-22 下午7:44:35
 * @版本号：v1.0
 * 
 */
public class PersonalCenterMsgAdapter extends BaseAdapter {

	public static interface IMsgViewType {
		int IMVT_COM_MSG = 0;
		int IMVT_TO_MSG = 1;
	}

	private List<PersonalCenterMsg> mList;

	private Context mContext;

	public PersonalCenterMsgAdapter(List<PersonalCenterMsg> mList,
			Context mContext) {
		this.mList = mList;
		this.mContext = mContext;
	}

	public int getCount() {
		return mList.size();
	}

	public Object getItem(int position) {
		return mList.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public int getItemViewType(int position) {
		PersonalCenterMsg msg = mList.get(position);
		if (!TextUtils.isEmpty(msg.getU_to_s()) && msg.getU_to_s().equals("2")) {
			return IMsgViewType.IMVT_COM_MSG;
		} else {
			return IMsgViewType.IMVT_TO_MSG;
		}

	}

	public int getViewTypeCount() {
		return 2;
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		PersonalCenterMsg msg = mList.get(position);
		boolean isComMsg = false;
		if (msg.getU_to_s().equals("2")) {
			isComMsg = true;
		}

		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (isComMsg) {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.personal_msg_left, null);
			} else {
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.personal_msg_right, null);
			}

			viewHolder = new ViewHolder();
			viewHolder.tvSendTime = (TextView) convertView
					.findViewById(R.id.tv_sendtime);
			viewHolder.tvContent = (TextView) convertView
					.findViewById(R.id.tv_chatcontent);
			viewHolder.isComMsg = isComMsg;
			viewHolder.imgHead = (ImageView) convertView
					.findViewById(R.id.iv_userhead);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.tvSendTime.setText(DateTime.getChatTime(Long.parseLong(msg
				.getCreate_time()) * 1000));
		viewHolder.tvContent.setText(msg.getContent());
		User user = MyApplication.getInstance().getUser();
		if (user != null) {
			if (isComMsg) {
				ImageLoader.getInstance()
						.displayImage(
								CommUtils.getImageUrl(mList.get(position)
										.getHead_img()), viewHolder.imgHead,
								BitmapUtil.getMessageCircleOption());
			} else {
				if (user.getHead_img().startsWith(Constants.FILE_START)) {
					ImageLoader.getInstance().displayImage(user.getHead_img(),
							viewHolder.imgHead, BitmapUtil.getMessageCircleOption());
				} else {
					ImageLoader.getInstance().displayImage(
							CommUtils.getImageUrl(user.getHead_img()),
							viewHolder.imgHead, BitmapUtil.getMessageCircleOption());
				}
			}
		}

		return convertView;
	}

	static class ViewHolder {
		public TextView tvSendTime;
		public TextView tvContent;
		public boolean isComMsg = true;
		public ImageView imgHead;// 头像
	}

}
