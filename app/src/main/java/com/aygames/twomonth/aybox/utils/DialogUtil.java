package com.aygames.twomonth.aybox.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.aygames.twomonth.aybox.R;

public class DialogUtil {
	
	private static Dialog dialog;
	private static ImageView iv_pd;
	private static TextView tv_msg;
	private static View view;
	
	/*
	 *
	 */
	private static void init(Context context){
		dialog = new Dialog(context, R.style.customDialog);
		view = LayoutInflater.from(context).inflate(R.layout.symi_pd, null);
		iv_pd = (ImageView)view.findViewById(R.id.iv_circle);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		tv_msg = (TextView)view.findViewById(R.id.tv_msg);
		dialog.setContentView(view);
	}
	/**
	 *
	 */
	public static void showDialog(Context context,String msg){
		init(context);
		tv_msg.setText(msg);
		if (null!=dialog && !dialog.isShowing()) {
			iv_pd.startAnimation(rotaAnimation());
			dialog.show();
		}
	}

	public static void showDialog(Context ctx, boolean cansable, String msg) {
		init(ctx);
		tv_msg.setText(msg);// ��ʾ������Ϣ
		if (null != dialog && !dialog.isShowing()) {
			dialog.setCancelable(cansable);
			iv_pd.startAnimation(rotaAnimation());
			dialog.show();
		}
	}

	/**
	 * ���ضԻ���
	 */
	public static void dismissDialog() throws Exception {
		if (null != dialog && dialog.isShowing()) {
			dialog.dismiss();
			iv_pd.clearAnimation();
			dialog = null;
		}
	}

	/**
	 * ��ת����
	 * 
	 * @return
	 */
	public static Animation rotaAnimation() {
		RotateAnimation ra = new RotateAnimation(0, 355,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		ra.setInterpolator(new LinearInterpolator());
		ra.setDuration(888);
		ra.setRepeatCount(-1);
		ra.setStartOffset(0);
		ra.setRepeatMode(Animation.RESTART);
		return ra;
	}

	/**
	 * �ж϶Ի����Ƿ�����ʾ״̬
	 * 
	 * @return
	 */
	public static boolean isShowing() {
		if (null != dialog) {
			return dialog.isShowing();
		}
		return false;
	}

}
