package com.yzb.card.king.ui.hotel.activtiy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.yzb.card.king.R;
import com.yzb.card.king.bean.giftcard.ApplySoreModelBean;
import com.yzb.card.king.bean.giftcard.CardRightsInfoBean;
import com.yzb.card.king.bean.giftcard.GiftsIncrementBean;
import com.yzb.card.king.bean.giftcard.GoodsApplyStoreBean;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.appdialog.GiftCombnInfoDetailDialog;
import com.yzb.card.king.ui.appwidget.appdialog.GiftCombonGoodsDetailDialog;
import com.yzb.card.king.ui.appwidget.appdialog.HotelOtherProductInfoIntroFragmentDialog;
import com.yzb.card.king.ui.appwidget.appdialog.WeiXinBarcodeDialog;
import com.yzb.card.king.ui.appwidget.popup.BottomDataListPP;
import com.yzb.card.king.ui.appwidget.popup.GoLoginDailog;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.adapter.ApplyShopCityItemAdapter;
import com.yzb.card.king.ui.hotel.persenter.GiftCombonPresent;
import com.yzb.card.king.ui.manage.UserManager;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.LogUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：卡权益产品详情
 * 作  者：Li Yubing
 * 日  期：2017/9/9
 * 描  述：
 */
@ContentView(R.layout.activity_card_sale_product_detail)
public class CardRightsProductDetailActivity extends BaseActivity implements View.OnClickListener, BaseViewLayerInterface {

    @ViewInject(R.id.ivProductImage)
    private ImageView ivProductImage;

    @ViewInject(R.id.tvImageNumber)
    private TextView tvImageNumber;

    @ViewInject(R.id.tvProductName)
    private TextView tvProductName;

    @ViewInject(R.id.tvProductDetail)
    private TextView tvProductDetail;

    @ViewInject(R.id.tvShopName)
    private TextView tvShopName;

    @ViewInject(R.id.ivPhone)
    private ImageView ivPhone;

    @ViewInject(R.id.tvShuoming)
    private TextView tvShuoming;

    @ViewInject(R.id.ivArrowApplyShop)
    private ImageView ivArrowApplyShop;

    @ViewInject(R.id.llApplyShopContent)
    private LinearLayout llApplyShopContent;

    @ViewInject(R.id.llIncrementItems)
    private LinearLayout llIncrementItems;

    @ViewInject(R.id.wvApplyShop)
    private WholeRecyclerView wvApplyShop;

    @ViewInject(R.id.llMingxi)
    private LinearLayout llMingxi;

    @ViewInject(R.id.tv_commit)
    private TextView tv_commit;

    @ViewInject(R.id.tvPayMethodType)
    private TextView tvPayMethodType;

    @ViewInject(R.id.tvTotalAmount)
    private TextView tvTotalAmount;

    @ViewInject(R.id.tvStorePrice)
    private TextView tvStorePrice;

    private GiftCombonPresent giftPresent;

    private StringBuffer quanyiSb;

    private   ApplyShopCityItemAdapter adapter;

    private  List<ApplySoreModelBean> applyShopList;

    private  BottomDataListPP pp;

    private  CardRightsInfoBean cardRightsInfoBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        initView();

        initData();
    }

    private void initData()
    {

        if (getIntent().hasExtra("activityId")) {

            giftPresent = new GiftCombonPresent(this);

            long activityId = getIntent().getLongExtra("activityId", 0l);

            initRequest(activityId);

        }
    }

    private void initRequest(long activityId)
    {
        showPDialog("正在请求数据……");

        giftPresent.sendSelectCardRightsInfoRequest(activityId);

        giftPresent.sendQueryGiftsIncrementItemsRequest(activityId, Integer.parseInt(HoteUtil.HOTEL_CARD_EQUITY_CODE));

        giftPresent.sendQueryGoodsApplyStoreRequest(activityId, Integer.parseInt(HoteUtil.HOTEL_CARD_EQUITY_CODE));

    }

    private void initView()
    {
        setTitleNmae("卡权益详情");

        findViewById(R.id.llApplyShop).setOnClickListener(this);

        tv_commit.setOnClickListener(this);

        tv_commit.setText("立即购买");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        wvApplyShop.setLayoutManager(linearLayoutManager);

         adapter = new ApplyShopCityItemAdapter();

        wvApplyShop.setAdapter(adapter);

        llMingxi.setVisibility(View.GONE);

        findViewById(R.id.tvChangeCity).setOnClickListener(this);

        findViewById(R.id.llMcarDetail).setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.llApplyShop:

                if (llApplyShopContent.getVisibility() == View.GONE) {

                    Utils.changeBackground(this, ivArrowApplyShop);

                    llApplyShopContent.setVisibility(View.VISIBLE);

                } else {

                    Utils.backBackground(this, ivArrowApplyShop);

                    llApplyShopContent.setVisibility(View.GONE);
                }

                break;
            case R.id.tv_commit://购买

                if(cardRightsInfoBean != null){

                    if (UserManager.getInstance().isLogin()) {

                        Intent intent = new Intent(this, CardRightsOrderActivity.class);

                        intent.putExtra("dataDetail",cardRightsInfoBean);

                        intent.putExtra("fuzengquanyi", quanyiSb.toString());

                        startActivity(intent);

                    }else {

                        new GoLoginDailog(this).create().show();
                    }
                }

                break;

            case R.id.ivPhone://打电话

                if (v.getTag() == null) {

                    CommonUtil.callHotelPhone(this, v.getTag() + "");
                }

                break;
            case R.id.ivProductImage://查询图片

                if (tvImageNumber.getTag() != null) {

                    String photoUrls = (String) tvImageNumber.getTag();

                    Intent intent = new Intent(this, HotelImageExpositionActivity.class);

                    intent.putExtra("imageTitleName", "卡权益套餐图片");

                    intent.putExtra("photoUrls", photoUrls);

                    startActivity(intent);
                }

                break;

            case R.id.tvChangeCity://切换城市

                if(pp != null){

                    pp.showPP(getWindow().getDecorView());
                }

                break;
            case R.id.llMcarDetail://主卡明细

                if(cardRightsInfoBean != null){

                    GiftCombnInfoDetailDialog hotelProductFragmentDialog = new GiftCombnInfoDetailDialog();

                    Bundle bundle = new Bundle();

                    bundle.putSerializable("dataDetail", cardRightsInfoBean);

                    bundle.putSerializable("fuzengquanyi", quanyiSb.toString());

                    hotelProductFragmentDialog.setArguments(bundle);

                    hotelProductFragmentDialog.show(getFragmentManager(),"roomFt");
                }

                break;

            default:
                break;
        }
    }

    @Override
    public void callSuccessViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (type == GiftCombonPresent.SELECTCARDRIGHTSINFO_CODE) {

             cardRightsInfoBean = JSONObject.parseObject(o + "", CardRightsInfoBean.class);

            tv_commit.setTag(cardRightsInfoBean);

            tvProductName.setText(cardRightsInfoBean.getGiftsName());

            Glide.with(this).load(ServiceDispatcher.getImageUrl(cardRightsInfoBean.getCardPhoto())).into(ivProductImage);

            String photoUrls = cardRightsInfoBean.getPhotoUrls();

            if (!TextUtils.isEmpty(photoUrls)) {

                int index = photoUrls.indexOf(",", -1);

                if (index == -1) {

                    tvImageNumber.setVisibility(View.INVISIBLE);

                } else {

                    String[] photoArray = photoUrls.split(",", -1);

                    int length = photoArray.length;

                    tvImageNumber.setText(length + "张");

                    tvImageNumber.setVisibility(View.VISIBLE);

                    ivProductImage.setOnClickListener(this);

                    tvImageNumber.setTag(photoUrls);

                }

            }
            StringBuffer detailSb = new StringBuffer();

            detailSb.append("主卡有效期：").append(cardRightsInfoBean.getEffMonthDesc()).append("\n");

            String selfPick = cardRightsInfoBean.getSelfPick();

            StringBuffer tempSb = new StringBuffer();

            if ("1".equals(selfPick)) {

                tempSb.append("可自取").append(",");

            } else if ("2".equals(selfPick)) {
                tempSb.append("不可自取").append(",");
            }

            String shopMail = cardRightsInfoBean.getShopMail();

            if ("1".equals(shopMail)) {

                tempSb.append("商家邮寄");

            } else if ("2".equals(shopMail)) {

                tempSb.append("商家不邮寄");
            }

            detailSb.append("实体券领取方式：").append(tempSb.toString()).append("\n");

            tvProductDetail.setText(detailSb.toString());

            tvShopName.setText(cardRightsInfoBean.getShopName());

            String tel = cardRightsInfoBean.getTel();

            if (TextUtils.isEmpty(tel)) {

                ivPhone.setVisibility(View.INVISIBLE);

            } else {

                ivPhone.setTag(tel);
                ivPhone.setVisibility(View.VISIBLE);
                ivPhone.setOnClickListener(this);
            }
            if(!TextUtils.isEmpty(cardRightsInfoBean.getUserIntro())){
                tvShuoming.setText(cardRightsInfoBean.getUserIntro());
            }

            tvPayMethodType.setText("特惠价");

            tvTotalAmount.setText(cardRightsInfoBean.getOnlinePrice() + "");

            int storePrice = cardRightsInfoBean.getStorePrice();

            String str = new String("原价    ¥" + Utils.subZeroAndDot(storePrice+""));

            int startIndex = str.indexOf("¥") + 1;

            SpannableString sp = new SpannableString(str);

            StrikethroughSpan span = new StrikethroughSpan();
            sp.setSpan(span, startIndex, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tvStorePrice.setText(sp);

        } else if (type == GiftCombonPresent.QUERYGIFTSINCREMENTITEMS_CODE) {

            llIncrementItems.removeAllViews();

            quanyiSb = new StringBuffer();

            List<GiftsIncrementBean> list = JSONArray.parseArray(o + "", GiftsIncrementBean.class);

            int size = list.size();

            for (int i = 0 ; i<size ; i++) {

                final GiftsIncrementBean bean = list.get(i);

                View view = LayoutInflater.from(this).inflate(R.layout.view_item_menu_detail_arrow, null);

                TextView tvMenuName = (TextView) view.findViewById(R.id.tvMenuName);

                TextView tvPice = (TextView) view.findViewById(R.id.tvPice);

                tvMenuName.setText(bean.getItemName());

                tvPice.setText(bean.getItemQuantity() + "份");

                llIncrementItems.addView(view);

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {

                        if(cardRightsInfoBean != null){

                        GiftCombonGoodsDetailDialog hotelProductFragmentDialog = new GiftCombonGoodsDetailDialog();

                        Bundle bundle = new Bundle();

                        bundle.putSerializable("dataDetail", cardRightsInfoBean);

                        bundle.putSerializable("GiftsIncrementBean", bean);

                        bundle.putSerializable("fuzengquanyi", quanyiSb.toString());

                        hotelProductFragmentDialog.setArguments(bundle);

                        hotelProductFragmentDialog.show(getFragmentManager(),"giftCombFt");
                    }


                    }
                });

                if( i == size -1){

                    quanyiSb.append(bean.getItemName()+bean.getItemQuantity()).append("份");

                }else {

                    quanyiSb.append(bean.getItemName()+bean.getItemQuantity()).append("份、");
                }

            }

        } else if (type == GiftCombonPresent.QUERYGOODSAPPLYSTOR_CODE) {

             List<ApplySoreModelBean> list = JSONArray.parseArray(o+"",ApplySoreModelBean.class);

             NationalCountryPresenter nationCountryPresenter = new NationalCountryPresenter();

            List<NationalCountryBean> nationalCountryBeenList = new ArrayList<NationalCountryBean>();

            for( ApplySoreModelBean applySoreModelBean : list){

                String key = applySoreModelBean.getKey();

                if(cityId.equals(key)){

                    adapter.addNewList(applySoreModelBean.getValue());
                }

                NationalCountryBean nationalCountryBean =nationCountryPresenter.selectOneDataByCityIdFromDb(applySoreModelBean.getKey());

                nationalCountryBeenList.add(nationalCountryBean);

            }

            applyShopList = list;

            //整理使用门店城市
            int size = nationalCountryBeenList.size();
            if(size > 0){

                String[] nameArry = new String[size];

                int[] valeArrayArry = new int[size];

                for(int i = 0 ; i < size ; i++){

                    NationalCountryBean bean = nationalCountryBeenList.get(i);

                    String name = bean.getCityName();

                    nameArry[i] = name;

                    int cityCity = bean.getCityId();

                    valeArrayArry[i] = cityCity;

                }

                pp = new BottomDataListPP(this, -1);

                pp.setGone();

                pp.setLogicData(nameArry, valeArrayArry);

                pp.setSelectIndex(-1);

                pp.setCallBack(callHHBack);
            }

        }

    }

    private BottomDataListPP.BottomDataListCallBack callHHBack = new BottomDataListPP.BottomDataListCallBack() {
        @Override
        public void onClickItemDataBack(String name, int nameValue, int selectIndex)
        {

            if(applyShopList == null){

                return;
            }

            for( ApplySoreModelBean applySoreModelBean : applyShopList) {

                String key = applySoreModelBean.getKey();

                String nameValueStr = nameValue+"";

                if (nameValueStr.equals(key)) {

                    adapter.addNewList(applySoreModelBean.getValue());

                    break;
                }

            }
        }
    };

    @Override
    public void callFailedViewLogic(Object o, int type)
    {
        dimissPdialog();

        if (type == GiftCombonPresent.SELECTCARDRIGHTSINFO_CODE) {

        } else if (type == GiftCombonPresent.QUERYGIFTSINCREMENTITEMS_CODE) {

        } else if (type == GiftCombonPresent.QUERYGOODSAPPLYSTOR_CODE) {

        }

    }
}
