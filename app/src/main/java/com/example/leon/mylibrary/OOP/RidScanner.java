package com.example.leon.mylibrary.OOP;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.util.Log;

import java.io.UnsupportedEncodingException;

/**
 * Created by Leon on 5/05/2015.
 */
public class RidScanner {

    private NfcAdapter nfcAdapter;

    public RidScanner(NfcAdapter nfcAdapter) {

        this.nfcAdapter = nfcAdapter;

    }

    public boolean containsTag(Intent intent) {
        if(intent.hasExtra(NfcAdapter.EXTRA_TAG)){
            return true;
        }else{
            return false;
        }
    }

    public Parcelable[] getTagContent(Intent intent) {
        return intent.getParcelableArrayExtra(nfcAdapter.EXTRA_NDEF_MESSAGES);
    }

    public boolean containsMessages(Parcelable[] tagContent) {
        if(tagContent != null & tagContent.length > 0){
            return true;
        }else{
            return false;
        }
    }

    public NdefMessage getMessage(Parcelable[] tagContent, int index) {
        return (NdefMessage)tagContent[index];
    }

    public NdefRecord[] getMessageContent(NdefMessage message) {
        return message.getRecords();
    }

    public boolean containsRecords(NdefRecord[] messageContent) {
        if(messageContent != null & messageContent.length > 0){
            return true;
        }else{
            return false;
        }
    }

    public NdefRecord getRecord(NdefRecord[] messageContent, int index) {
        return (NdefRecord)messageContent[index];
    }

    public String getStringFromRecord(NdefRecord record) {

        final String utf8 = "UTF-8";
        final String utf16 = "UTF-16";

        String tagContent = null;
        try {
            byte[] payload = record.getPayload();
            String textEncoding = ((payload[0] & 128) == 0) ? utf8 : utf16;
            int languageSize = payload[0] & 0063;
            tagContent = new String(payload, languageSize + 1,
                    payload.length - languageSize - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e("getTextFromNdefRecord", e.getMessage(), e);
        }
        return tagContent;
    }

}
