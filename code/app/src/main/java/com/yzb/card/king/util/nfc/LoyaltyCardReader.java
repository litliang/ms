package com.yzb.card.king.util.nfc;

/**
 * Created by klkkkl on 2016/6/28.
 */
import android.annotation.TargetApi;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Build;
import android.util.Log;

import com.yzb.card.king.util.LogUtil;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 *
 */
@TargetApi(Build.VERSION_CODES.KITKAT)
public class LoyaltyCardReader implements NfcAdapter.ReaderCallback {
    private static final String TAG = "LoyaltyCardReader";

    private WeakReference<MsgCallback> mMsgCallback;
    String sendMsg="";

    public interface MsgCallback {
        public void onMsgReceived(String account);
    }

    public LoyaltyCardReader(MsgCallback accountCallback) {
        mMsgCallback = new WeakReference<MsgCallback>(accountCallback);
    }

    public void setSendMsg(String account){
        sendMsg = account;
    }

    @Override
    public void onTagDiscovered(Tag tag) {
        Log.i(TAG, "New tag discovered");

        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            try {
                isoDep.connect();
                Log.i(TAG, "Requesting remote AID: " + MyHostApduService.SAMPLE_LOYALTY_CARD_AID);
                byte[] command = BuildSelectApdu(MyHostApduService.SAMPLE_LOYALTY_CARD_AID);
                Log.i(TAG, "Sending: " + BytesUtils.ByteArrayToHexString(command));
                byte[] result = isoDep.transceive(command);
                if (Arrays.equals(MyHostApduService.SELECT_OK_SW, result)) {
                    Log.i(TAG, "sendMsg: " + sendMsg);
                    command = BuildExchangeApdu(sendMsg);
                    Log.i(TAG, "Sending: " + BytesUtils.ByteArrayToHexString(command));
                    result = isoDep.transceive(command);
                    int resultLength = result.length;
                    byte[] payload = Arrays.copyOf(result, resultLength-2);
                    String recvMsg = new String(payload, "UTF-8");
                    Log.i(TAG, "Received: " + recvMsg);
                    mMsgCallback.get().onMsgReceived(recvMsg);
                }
            } catch (IOException e) {
                LogUtil.e(TAG, "Error communicating with card: " + e.toString());
            }
        }
    }

    public static byte[] BuildSelectApdu(String aid) {
        return BytesUtils.HexStringToByteArray(MyHostApduService.SELECT_APDU_HEADER + String.format("%02X", aid.length() / 2) + aid);
    }

    public static byte[] BuildExchangeApdu(String msg) {
        byte[] aa = msg.getBytes(StandardCharsets.UTF_8);
        byte[] ac = new byte[1];
        ac[0] = (byte)aa.length;
        return BytesUtils.ConcatArrays(BytesUtils.HexStringToByteArray(MyHostApduService.EXCHANGE_MSG), ac, aa);
    }
}