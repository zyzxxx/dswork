package com.test;

import android.test.AndroidTestCase;
import android.util.Log;
import dswork.android.lib.util.EncryptUtil;

public class JunitTest extends AndroidTestCase 
{
	public void mytest() throws Exception
	{
		Log.i("dswork", String.valueOf(EncryptUtil.encodeBase64("000000")));
		Log.i("dswork", String.valueOf(EncryptUtil.decodeBase64("MDAwMDAw")));
	}
}
