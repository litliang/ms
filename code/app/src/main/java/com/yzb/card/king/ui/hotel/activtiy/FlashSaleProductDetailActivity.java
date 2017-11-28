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
import com.yzb.card.king.bean.giftcard.FlashSaleInfoBean;
import com.yzb.card.king.bean.giftcard.GiftsIncrementBean;
import com.yzb.card.king.bean.my.NationalCountryBean;
import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.appwidget.WholeRecyclerView;
import com.yzb.card.king.ui.appwidget.appdialog.FlashSaleProductDetailDialog;
import com.yzb.card.king.ui.appwidget.appdialog.GiftCombonGoodsDetailDialog;
import com.yzb.card.king.ui.appwidget.popup.BottomDataListPP;
import com.yzb.card.king.ui.base.BaseActivity;
import com.yzb.card.king.ui.base.BaseViewLayerInterface;
import com.yzb.card.king.ui.hotel.HoteUtil;
import com.yzb.card.king.ui.hotel.adapter.ApplyShopCityItemAdapter;
import com.yzb.card.king.ui.hotel.persenter.GiftCombonPresent;
import com.yzb.card.king.ui.my.presenter.NationalCountryPresenter;
import com.yzb.card.king.util.CommonUtil;
import com.yzb.card.king.util.Utils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 类  名：限时抢购产品详情
 * 作  者：Li Yubing
 * 日  期：2017/9/9
 * 描  述：
 */
@ContentView(R.layout.activity_flash_sale_product_detail)
public class FlashSaleProductDetailActivity extends BaseActivity implements View.OnClickListener,BaseViewLayerInterface {

    @ViewInject(R.id.ivProductImage)
    private ImageView ivProductImage;

    @ViewInject(R.id.tvImageNumber)
    private TextView tvImageNumber;

    @ViewInject(R.id.tvProductName)
    private TextView tvProductName;

    @ViewInject(R.id.tvProductDetail)
    private TextView tvProductDetail;

    @ViewInject(R.id.tvBankName)
    private TextView tvBankName;

    @ViewInject(R.id.llPrivilege)
    private LinearLayout llPrivilege;

    @ViewInject(R.id.tvPrivilege)
    private TextView tvPrivilege;

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

    private   ApplyShopCityItemAdapter adapter;

    private List<ApplySoreModelBean> applyShopList;

    private  List<GiftsIncrementBean> list;

    private BottomDataListPP pp;

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

        giftPresent.sendSelectFlashsaleInfoRequest(activityId);

        giftPresent.sendQueryGiftsIncrementItemsRequest(activityId, Integer.parseInt(HoteUtil.HOTEL_FAST_BUY_CODE));

        giftPresent.sendQueryGoodsApplyStoreRequest(activityId, Integer.parseInt(HoteUtil.HOTEL_FAST_BUY_CODE));

    }

    private void initView()
    {
        setTitleNmae("限时抢购详情");

        findViewById(R.id.llApplyShop).setOnClickListener(this);

        tv_commit.setOnClickListener(this);

        tv_commit.setText("立即购买");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        wvApplyShop.setLayoutManager(linearLayoutManager);

        adapter = new ApplyShopCityItemAdapter();

        wvApplyShop.setAdapter(adapter);

        llMingxi.setVisibility(View.GONE);

        findViewById(R.id.tvChangeCity).setOnClickListener(this);

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

                if(v.getTag() != null){

                    FlashSaleInfoBean cardRightsInfoBean = (FlashSaleInfoBean) v.getTag();

                    Intent intent = new Intent(this, FlashSaleProductOrderActivity.class);

                    if(list != null){

                        cardRightsInfoBean.setGiftsIncrementBeenList(list);
                    }

                    intent.putExtra("dataDetail",cardRightsInfoBean);

                    startActivity(intent);
                }

                break;

            case R.id.ivProductImage://查询图片

                if (tvImageNumber.getTag() != null) {

                    String photoUrls = (String) tvImageNumber.getTag();

                    Intent intent = new Intent(this, HotelImageExpositionActivity.class);

                    intent.putExtra("imageTitleName", "限时抢购图片");

                    intent.putExtra("photoUrls", photoUrls);

                    startActivity(intent);
                }

                break;

            case R.id.tvChangeCity://切换城市

                if(pp != null){

                    pp.showPP(getWindow().getDecorView());
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

        if (type == GiftCombonPresent.SELECTFLASHSALEINFO_CODE) {

            FlashSaleInfoBean cardRightsInfoBean = JSONObject.parseObject(o + "", FlashSaleInfoBean.class);

            tv_commit.setTag(cardRightsInfoBean);

            tvProductName.setText(cardRightsInfoBean.getActName());

            String photoUrls = cardRightsInfoBean.getPhotoUrls();

            if (!TextUtils.isEmpty(photoUrls)) {

                int index = photoUrls.indexOf(",", -1);

                if (index == -1) {

                    tvImageNumber.setVisibility(View.INVISIBLE);

                    Glide.with(this).load(ServiceDispatcher.getImageUrl(photoUrls)).into(ivProductImage);

                } else {

                    String[] photoArray = photoUrls.split(",", -1);

                    int length = photoArray.length;

                    tvImageNumber.setText(length + "张");

                    tvImageNumber.setVisibility(View.VISIBLE);

                    ivProductImage.setOnClickListener(this);

                    tvImageNumber.setTag(photoUrls);

                    Glide.with(this).load(ServiceDispatcher.getImageUrl(photoArray[0])).into(ivProductImage);

                }

            }
            StringBuffer detailSb = new StringBuffer();

            detailSb.append("有效期限：").append(cardRightsInfoBean.getEffDateDesc()).append("\n");

           long  currentLong = System.currentTimeMillis();

            String endDateStr = cardRightsInfoBean.getEndTime();

            long endDateLong = Utils.toTimestamp(endDateStr,1);

            if(endDateLong> currentLong){

                String currentDate = Utils.toData(currentLong,1);

                String spaceDate = Utils.getDistanceTime(currentDate,endDateStr);

                detailSb.append("剩余时间：").append(spaceDate).append("\n");
            }

            detailSb.append("剩余数量：").append(cardRightsInfoBean.getResidualQuantity()+"件").append("\n");

            if(!TextUtils.isEmpty(cardRightsInfoBean.getShopName())){

                detailSb.append("供应商：").append(cardRightsInfoBean.getShopName()).append("\n");
            }

            tvProductDetail.setText(detailSb.toString());

            if(!TextUtils.isEmpty(cardRightsInfoBean.getBankName())){

                tvBankName.setText(cardRightsInfoBean.getBankName());

                tvBankName.setVisibility(View.VISIBLE);
            }else {

                tvBankName.setVisibility(View.GONE);
            }

           String privilege = cardRightsInfoBean.getPrivilege();

            if(TextUtils.isEmpty(privilege)){

                llPrivilege.setVisibility(View.GONE);
            }else {

                llPrivilege.setVisibility(View.VISIBLE);

                tvPrivilege.setText(privilege);
            }

            if(!TextUtils.isEmpty(cardRightsInfoBean.getUseIntro())){
                tvShuoming.setText(cardRightsInfoBean.getUseIntro());
            }

            tvPayMethodType.setText("特惠价");

            tvTotalAmount.setText(Utils.subZeroAndDot(cardRightsInfoBean.getOnlinePrice() + ""));

            float storePrice = cardRightsInfoBean.getStorePrice();

            String str = new String("原价    ¥" + Utils.subZeroAndDot(storePrice+""));

            int startIndex = str.indexOf("¥") + 1;

            SpannableString sp = new SpannableString(str);

            StrikethroughSpan span = new StrikethroughSpan();
            sp.setSpan(span, startIndex, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

            tvStorePrice.setText(sp);

        } else if (type == GiftCombonPresent.QUERYGIFTSINCREMENTITEMS_CODE) {

            llIncrementItems.removeAllViews();

            list = JSONArray.parseArray(o + "", GiftsIncrementBean.class);

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


                        if( tv_commit.getTag() != null){

                            FlashSaleInfoBean cardRightsInfoBean  = (FlashSaleInfoBean) tv_commit.getTag();

                            if(list != null){
                                cardRightsInfoBean.setGiftsIncrementBeenList(list);
                            }


                            FlashSaleProductDetailDialog hotelProductFragmentDialog = new FlashSaleProductDetailDialog();

                            Bundle bundle = new Bundle();

                            bundle.putSerializable("dataDetail", cardRightsInfoBean);

                            bundle.putSerializable("GiftsIncrementBean", bean);

                            hotelProductFragmentDialog.setArguments(bundle);

                            hotelProductFragmentDialog.show(getFragmentManager(),"giftCombFt");
                        }

                    }
                });

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
