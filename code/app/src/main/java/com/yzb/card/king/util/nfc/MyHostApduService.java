package com.yzb.card.king.util.nfc;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.yzb.card.king.sys.ServiceDispatcher;
import com.yzb.card.king.ui.discount.activity.msf.DiscountQsfNfcLinkedActivity;
import com.yzb.card.king.util.LogUtil;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class MyHostApduService extends HostApduService {
    private static final String TAG = "CardService";

    public static final String SAMPLE_LOYALTY_CARD_AID = "F222222222";
    public static final String SELECT_APDU_HEADER = "00A40400";
    public static final String EXCHANGE_MSG = "00D60000";
    public static final byte[] SELECT_OK_SW = BytesUtils.HexStringToByteArray("9000");
    public static final byte[] UNKNOWN_CMD_SW = BytesUtils.HexStringToByteArray("0000");
    public static final byte[] SELECT_APDU = BuildSelectApdu(SAMPLE_LOYALTY_CARD_AID);

    private String sendMsg = "";

    private boolean payment = false;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {

        sendMsg = intent.getStringExtra("sendMsg");
        payment = intent.getBooleanExtra("payment", false);
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDeactivated(int reason)
    {
    }


    @Override
    public byte[] processCommandApdu(byte[] commandApdu, Bundle extras)
    {
        // If the APDU matches the SELECT AID command for this service,
        // send the loyalty card account number, followed by a SELECT_OK status trailer (0x9000).
        if (Arrays.equals(SELECT_APDU, commandApdu)) {

            return SELECT_OK_SW;

        } else if (BytesUtils.ArraysEquals(BytesUtils.HexStringToByteArray(EXCHANGE_MSG), commandApdu, EXCHANGE_MSG.length() / 2)) {

            Log.i(TAG, "Received APDU: " + BytesUtils.ByteArrayToHexString(commandApdu));
            String recvMsg = new String(commandApdu, StandardCharsets.UTF_8);

           int a = recvMsg.indexOf(ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER);

            String totalUrl  =  ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER;

            if(a==-1){

                int b = recvMsg.indexOf(ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER_BUT);

                if( b==-1){

                }else{
                    totalUrl = ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER_BUT;
                }

            }else{
                totalUrl = ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER;
            }

            String[] strRecvMsg = recvMsg.split(totalUrl);

            if (strRecvMsg.length == 2) {

                recvMsg = ServiceDispatcher.URL_CREATE_FAST_PAYMENT_ORDER + strRecvMsg[1];
//            int c = commandApdu[EXCHANGE_MSG.length()/2];
//            byte[] newArray = Arrays.copyOfRange(commandApdu, EXCHANGE_MSG.length()/2 + 1, EXCHANGE_MSG.length()/2 + c + 1 );
//            String recvMsg1 = new String(newArray, StandardCharsets.UTF_8);
                LogUtil.e("XYYYYY","--------processCommandApdu-------"+recvMsg);
                Intent intent = new Intent(this, DiscountQsfNfcLinkedActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("recvMsg", recvMsg);
                startActivity(intent);
            }

            byte[] sendMsgBytes = new byte[0];

            sendMsgBytes = sendMsg.getBytes(StandardCharsets.UTF_8);
            return BytesUtils.ConcatArrays(sendMsgBytes, SELECT_OK_SW);
        } else {
            return UNKNOWN_CMD_SW;
        }
    }

    public static byte[] BuildSelectApdu(String aid)
    {
        // Format: [CLASS | INSTRUCTION | PARAMETER 1 | PARAMETER 2 | LENGTH | DATA]
        return BytesUtils.HexStringToByteArray(SELECT_APDU_HEADER + String.format("%02X",
                aid.length() / 2) + aid);
    }

}