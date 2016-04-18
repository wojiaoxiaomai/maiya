package com.choncheng.maya.personal;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.contants.Extra;
import com.choncheng.maya.customview.hlistview.HorizontalListView;
import com.choncheng.maya.utils.AnimUtil;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.choncheng.maya.utils.DateTime;
import com.choncheng.maya.utils.OrderUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�PersonalOrderAdapter
 * @�������� �ҵĶ���adater
 * @�����ˣ��
 * @����ʱ�䣺2015-8-11 ����7:50:15
 * @�汾�ţ�v1.0
 * 
 */
public class PersonalOrderAdapter extends BaseAdapter {
	private List<PersonalOrder> mList;
	private Context mContext;
	private OrderCallback mCallback;

	public PersonalOrderAdapter(List<PersonalOrder> mList, Context mContext,
			OrderCallback mCallback) {
		super();
		this.mList = mList;
		this.mContext = mContext;
		this.mCallback = mCallback;
	}

	public interface OrderCallback {
		public void updateOrder(int type, PersonalOrder order);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Myholder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.personal_order_item, null);
			holder = new Myholder();
			holder.dateTxt = (TextView) convertView.findViewById(R.id.date_txt);
			holder.statusTxt = (TextView) convertView
					.findViewById(R.id.status_txt);
			holder.nameTxt = (TextView) convertView.findViewById(R.id.name_txt);
			holder.priceTxt = (TextView) convertView
					.findViewById(R.id.price_txt);
			holder.goodsImage = (ImageView) convertView
					.findViewById(R.id.goods_image);
			holder.goButtn = (Button) convertView.findViewById(R.id.do_btn);
			holder.isOverImage = (ImageView) convertView
					.findViewById(R.id.is_over_image);
			holder.isOverImageForMutigoods = (ImageView) convertView
					.findViewById(R.id.horizon_listview_is_over_image);
			holder.isTikuanImage = (ImageView) convertView
					.findViewById(R.id.is_over_yitikuan_image);
			holder.isOverDeleteBtn = (ImageView) convertView
					.findViewById(R.id.is_over_del_image);

			holder.horizontalListView = (HorizontalListView) convertView
					.findViewById(R.id.horizon_listview);
			holder.single_goods_view = (LinearLayout) convertView
					.findViewById(R.id.single_goods_view);
			convertView.setTag(holder);
		} else {
			holder = (Myholder) convertView.getTag();
		}
		holder.horizontalListView.setTag(position);
		holder.horizontalListView
				.setOnItemClickListener(new HorizontalListViewItemClickListener());
		holder.goButtn.setTag(position);
		holder.isOverDeleteBtn.setTag(position);
		holder.isOverDeleteBtn.setOnClickListener(new DelteClickListener());
		holder.goButtn.setOnClickListener(new ClickListener());
		holder.dateTxt.setText(DateTime.getDateAndSecondbyStemp(mList.get(
				position).getCreate_time()));
		holder.statusTxt.setText(getOrderStatusbyType(OrderUtils
				.getOrderType(mList.get(position))));

		holder.priceTxt.setText(CommUtils.getMoney(CommUtils
				.getTwoPointDouble(mList.get(position).getGoods_amount()
						+ mList.get(position).getShipping_fee())
				+ ""));
		int type = OrderUtils.getOrderType(mList.get(position));
		switch (type) {
		case Constants.ORDER_NO_PAY:
			holder.goButtn.setText(R.string.pay_now);
			if (System.currentTimeMillis() / 1000 > MyApplication.getInstance()
					.getLostTime()
					+ Long.parseLong(mList.get(position).getCreate_time())) {
				// ˵�������Ѿ�ʧЧ��
				holder.goButtn.setText(R.string.cancle_order);
				holder.statusTxt.setText(R.string.order_invalid);
			}
			break;
		case Constants.ORDER_NO_GET:
			// ����ǻ����������ʾ�����У����������֧���ľ�ȷ���ջ�
			if (mList.get(position).getPay_way() == Constants.PAY_WAY_1) {
				holder.goButtn.setText(R.string.sure_get);
			} else if (mList.get(position).getPay_way() == Constants.PAY_WAY_2) {
				holder.goButtn.setText(R.string.deliverying);
			}

			break;
		case Constants.ORDER_NO_COMMENTS:
			holder.goButtn.setText(R.string.go_comments);
			break;
		case Constants.ORDER_IS_OVER:
			holder.goButtn.setText(R.string.buy_again);
			break;
		case Constants.ORDER_APPLY_ING:
			holder.goButtn.setText(R.string.cancle_tuikuan);
			break;
		default:
			break;
		}

		if (type == Constants.ORDER_NO_GET
				&& mList.get(position).getPay_way() == Constants.PAY_WAY_2) {
			// ���ջ�����������Ҫ��ʾΪ��ɫ
			holder.goButtn.setTextColor(mContext.getResources().getColor(
					R.color.textview_hint_grey));
		} else {
			holder.goButtn.setTextColor(mContext.getResources().getColor(
					R.color.green));
		}
		// ���������ɵĻ�����������Ҫ��ʾ����ͼ��,�Լ�ɾ����ť
		if (type == Constants.ORDER_IS_OVER) {
			holder.isOverDeleteBtn.setVisibility(View.VISIBLE);
			holder.statusTxt.setVisibility(View.GONE);
		} else {
			holder.isOverDeleteBtn.setVisibility(View.GONE);
			holder.statusTxt.setVisibility(View.VISIBLE);
		}
		if (!mList.get(position).getGoods_lists().isEmpty()) {

			if (mList.get(position).getGoods_lists().size() == 1) {
				holder.single_goods_view.setVisibility(View.VISIBLE);
				holder.horizontalListView.setVisibility(View.GONE);
				holder.nameTxt.setText(mList.get(position).getGoods_lists()
						.get(0).getGoods_name());

				if (type == Constants.ORDER_IS_OVER) {
					holder.isOverImage.setVisibility(View.VISIBLE);
					holder.isOverImageForMutigoods.setVisibility(View.GONE);
				} else {
					holder.isOverImage.setVisibility(View.GONE);
					holder.isOverImageForMutigoods.setVisibility(View.GONE);
				}
				ImageLoader.getInstance().displayImage(
						CommUtils.getImageUrl(mList.get(position)
								.getGoods_lists().get(0).getGoods_image()),
						holder.goodsImage, BitmapUtil.getDefaultOption());
			} else {
				// �����Ʒ��ʱ��
				holder.single_goods_view.setVisibility(View.GONE);
				holder.horizontalListView.setVisibility(View.VISIBLE);
				holder.horizontalListView.setAdapter(new ImageGalleryAdapter(
						mList.get(position).getGoods_lists()));

				if (type == Constants.ORDER_IS_OVER) {
					holder.isOverImageForMutigoods.setVisibility(View.VISIBLE);
					holder.isOverImage.setVisibility(View.GONE);
				} else {
					holder.isOverImageForMutigoods.setVisibility(View.GONE);
					holder.isOverImage.setVisibility(View.GONE);
				}
			}

		} else {
			holder.single_goods_view.setVisibility(View.GONE);
			holder.horizontalListView.setVisibility(View.GONE);
		}
		return convertView;
	}

	/*
	 * �����Ʒ�ĵ���¼�
	 */
	private class HorizontalListViewItemClickListener implements
			AdapterView.OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			int pos = (Integer) parent.getTag();
			Intent it = new Intent(mContext, PersonalOrderDetailActivity.class);
			it.putExtra(Extra.ORDER_ID, mList.get(pos).getOrder_id());
			it.putExtra(Extra.ORDER_TYPE,
					OrderUtils.getOrderType(mList.get(pos)));
			mContext.startActivity(it);
		}

	}

	// ����ɵĶ���ɾ���¼�
	private class DelteClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			PersonalOrder order = mList.get(position);
			int type = OrderUtils.getOrderType(order);
			mCallback.updateOrder(type, order);
		}

	}

	// ����¼�
	private class ClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int positon = (Integer) v.getTag();
			PersonalOrder order = mList.get(positon);
			int type = OrderUtils.getOrderType(order);
			switch (type) {
			case Constants.ORDER_NO_PAY:
				mCallback.updateOrder(type, order);
				if (System.currentTimeMillis() / 1000 > MyApplication
						.getInstance().getLostTime()
						+ Long.parseLong(order.getCreate_time())) {
					// ˵�������Ѿ�ʧЧ��,��ִ��ȡ������

				} else {
					// ������Ч������֧��

				}

				break;
			case Constants.ORDER_NO_GET:
				// ����ǻ����������ʾ������(����Ӧ����¼�)�����������֧���ľ�ȷ���ջ�
				if (order.getPay_way() == Constants.PAY_WAY_1) {
					// ����֧����ȷ���ջ�
					mCallback.updateOrder(Constants.ORDER_NO_GET, order);
				}
				break;
			case Constants.ORDER_NO_COMMENTS:

				List<PersonalOrderGoods> personalOrderGoodsList = order
						.getGoods_lists();
				List<PersonalOrderDetail> personalOrderDetailList = new ArrayList<PersonalOrderDetail>();
				if (personalOrderGoodsList != null) {
					for (int i = 0; i < personalOrderGoodsList.size(); i++) {
						PersonalOrderGoods goods = personalOrderGoodsList
								.get(i);
						PersonalOrderDetail orderDetail = new PersonalOrderDetail();
						orderDetail.setGoods_id(goods.getGoods_id());
						orderDetail.setGoods_image(goods.getGoods_image());
						orderDetail.setGoods_name(goods.getGoods_name());
						orderDetail.setGoods_price(goods.getBuy_price());
						orderDetail.setOld_price(goods.getShop_price());
						orderDetail.setQuantity(goods.getQuantity());
						orderDetail.setLevel(1);
						orderDetail.setSpec_id(goods.getSpec_id());
						orderDetail.setOrder_id(goods.getOrder_id());
						personalOrderDetailList.add(orderDetail);
					}
				}
				MyApplication.getInstance().setPersonalOrderCommentsList(
						personalOrderDetailList);
				Intent it = new Intent(mContext,
						PersonalOrderCommentsActivity.class);
				mContext.startActivity(it);
				break;
			case Constants.ORDER_IS_OVER:
				// ������ɣ��ٴι���
				Intent overIntent = new Intent(mContext,
						PersonalOrderDetailActivity.class);
				overIntent.putExtra(Extra.ORDER_ID, order.getOrder_id());
				overIntent.putExtra(Extra.ORDER_TYPE, type);
				mContext.startActivity(overIntent);
				break;

			case Constants.ORDER_APPLY_ING:
				// �˿��У�ȡ���˿�
				mCallback.updateOrder(Constants.ORDER_APPLY_ING, order);
				break;
			default:
				break;
			}
		}

	}

	private class Myholder {
		TextView dateTxt;
		TextView statusTxt;
		TextView nameTxt;
		TextView priceTxt;
		ImageView goodsImage;
		ImageView isOverImage;// ������Ʒ����ɵ�ͼ��
		ImageView isTikuanImage;// ������ͼ��
		ImageView isOverDeleteBtn;// ����ɵ���ʾɾ����ť
		Button goButtn;// ��ť������ȥ֧����ȡ��������
		HorizontalListView horizontalListView;// �����Ʒ��ʱ����ʾ
		ImageView isOverImageForMutigoods;// �����Ʒ��ʱ����ʾ��ɵı�־ͼƬ
		LinearLayout single_goods_view;// ������Ʒ

	}

	/**
	 * ͨ����ͬ״̬��ȡ��Ӧ���ı�
	 * 
	 * @param type
	 * @return
	 */
	private String getOrderStatusbyType(int type) {
		String status = "";
		switch (type) {
		case Constants.ORDER_NO_PAY:
			status = mContext.getString(R.string.no_pay);
			break;
		case Constants.ORDER_NO_GET:
			status = mContext.getString(R.string.no_get);
			break;
		case Constants.ORDER_NO_COMMENTS:
			status = mContext.getString(R.string.no_comments);
			break;
		case Constants.ORDER_IS_OVER:
			status = mContext.getString(R.string.is_over);
			break;
		case Constants.ORDER_APPLY_ING:
			status = mContext.getString(R.string.apply_ing);
			break;

		default:
			break;
		}
		return status;

	}

	private class ImageGalleryAdapter extends BaseAdapter {
		private List<PersonalOrderGoods> goodsList;

		public ImageGalleryAdapter(List<PersonalOrderGoods> goodsList) {
			this.goodsList = goodsList;
		}

		@Override
		public int getCount() {
			return goodsList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageGalleryHolder holder = null;
			if (convertView == null) {
				holder = new ImageGalleryHolder();
				convertView = LayoutInflater.from(mContext).inflate(
						R.layout.personal_order_gallery_image_item, parent,
						false);
				holder.goodsImage = (ImageView) convertView
						.findViewById(R.id.good_image);
				convertView.setTag(holder);
			} else {
				holder = (ImageGalleryHolder) convertView.getTag();
			}
			ImageLoader.getInstance().displayImage(
					CommUtils.getImageUrl(goodsList.get(position)
							.getGoods_image()), holder.goodsImage,
					BitmapUtil.getDefaultOption());
			return convertView;
		}
	}

	private class ImageGalleryHolder {
		ImageView goodsImage;
	}
}
