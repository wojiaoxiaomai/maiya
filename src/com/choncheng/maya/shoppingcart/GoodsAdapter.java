package com.choncheng.maya.shoppingcart;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.choncheng.maya.MyApplication;
import com.choncheng.maya.R;
import com.choncheng.maya.api.API;
import com.choncheng.maya.api.ResponseHandler;
import com.choncheng.maya.comm.entity.Goods;
import com.choncheng.maya.contants.Constants;
import com.choncheng.maya.login.LoginActivity;
import com.choncheng.maya.personal.PersonalCollection;
import com.choncheng.maya.utils.BitmapUtil;
import com.choncheng.maya.utils.CommUtils;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 
 * @��Ŀ���ƣ�Maya
 * @�����ƣ�GoodsAdapter
 * @�������� ��Ʒҳ�����banner�����Ƽ�������ת������Ʒҳ����adpter
 * @�����ˣ��
 * @����ʱ�䣺2015-8-10 ����4:20:34
 * @�汾�ţ�v1.0
 * 
 */
public class GoodsAdapter extends BaseAdapter {
	private List<Goods> mList;
	private Context mContext;

	public GoodsAdapter(List<Goods> mList, Context mContext) {
		this.mList = mList;
		this.mContext = mContext;
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
		MyHolder holder = null;
		if (convertView == null) {
			holder = new MyHolder();
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.goos_item, null);
			holder.singleGoodImage = (ImageView) convertView
					.findViewById(R.id.single_good_img);
			holder.contentTxt = (TextView) convertView
					.findViewById(R.id.content_txt);
			holder.actualPriceTxt = (TextView) convertView
					.findViewById(R.id.actual_price_txt);
			holder.beforePriceTxt = (TextView) convertView
					.findViewById(R.id.before_price_txt);
			holder.shopcartImage = (ImageView) convertView
					.findViewById(R.id.gouwuche_image);
			convertView.setTag(holder);
		} else {
			holder = (MyHolder) convertView.getTag();
		}
		Goods goods=mList.get(position);
		holder.shopcartImage.setTag(position);
		holder.shopcartImage.setOnClickListener(new ShopClickListener());
		ImageLoader.getInstance().displayImage(
				CommUtils.getImageUrl(goods.getGoods_image()),
				holder.singleGoodImage, BitmapUtil.getDefaultOption());
		holder.beforePriceTxt.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
		holder.contentTxt.setText(goods.getGoods_name());
		holder.actualPriceTxt.setText(CommUtils.getMoney(goods
				.getGoods_price()));
		holder.beforePriceTxt.setText(CommUtils.getMoney(goods
				.getOld_price()));
		//==>2015.12.9 �޸ļ۸�չʾ
		if(goods.getGoods_price().equals(goods.getOld_price())){
			holder.beforePriceTxt.setVisibility(View.GONE);
		}else{
			holder.beforePriceTxt.setVisibility(View.VISIBLE);
			holder.beforePriceTxt.setText(CommUtils.getMoney(goods.getOld_price()));
		}
		//<==2015.12.9 
		return convertView;
	}

	// �������
	private class ShopClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			Goods goods = mList.get(position);
			if (MyApplication.getInstance().isLogin()) {
				// ���빺�ﳵ

				if (goods != null) {
					if (goods.getStock() - goods.getVirtual_sales() > 0) {
						// �л���
						shopcartaddAdd(MyApplication.getInstance().getUser()
								.getUcode(), goods.getGoods_id(),
								goods.getSpec_id(), goods.getGoods_sn(), "",
								goods.getGoods_name(), goods.getGoods_image(),
								1 + "");
					} else {
						Toast.makeText(mContext, R.string.godds_empty,
								Toast.LENGTH_SHORT).show();
					}
				}

			} else {
				Intent it = new Intent(mContext, LoginActivity.class);
				it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_CLEAR_TOP);
				mContext.startActivity(it);
			}
		}
	}

	// ���빺�ﳵ
	private void shopcartaddAdd(String ucode, String goods_id, String spec_id,
			String specification, String shop_id, String goods_name,
			String goods_image, String quantity) {
		API.shopcartaddAdd(mContext, ucode, goods_id, spec_id, specification,
				shop_id, goods_name, goods_image, quantity,
				new ResponseHandler() {

					@Override
					public void onRese(String data, int state) {
						switch (state) {
						case STATE_OK:
							Toast.makeText(mContext,
									R.string.addto_shopcar_sucess,
									Toast.LENGTH_SHORT).show();
							break;
						case STATE_FAIL:
							Toast.makeText(mContext, data, Toast.LENGTH_SHORT)
									.show();
							break;
						case STATE_ERROR:
							Toast.makeText(mContext,
									R.string.http_respone_error,
									Toast.LENGTH_SHORT).show();
							break;

						default:
							break;
						}
					}
				});
	}

	private class MyHolder {
		ImageView singleGoodImage;
		TextView contentTxt;
		TextView actualPriceTxt;
		TextView beforePriceTxt;

		ImageView shopcartImage;// ���ﳵ
	}
}
