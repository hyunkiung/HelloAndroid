package com.example.administrator.helloandroid.pkg_unittest;

import junit.framework.TestCase;

import org.junit.Assert;

/**
 * Created by Administrator on 2015-05-19.
 */
public class CalculatorTest extends TestCase {

//    private static String TAG = "로그 / CalculatorTest ";
//    private void show_Log(String msg) { Log.d(TAG, msg); }
//    private String log;

    public void setUp() throws Exception {
        super.setUp();
//        log = "테스트 시작 시 호출 되는 곳";
//        show_Log(log);
    }

    public void tearDown() throws Exception {
//        log = "테스트 종료 시 호출 되는 곳";
//        show_Log(log);
    }

    public void testSum() throws Exception {
        Calculator calc = new Calculator();
        int result = calc.sum(1, 2);

        Assert.assertEquals(result, 3);
//        Assert.assertNotSame(result, 2);
    }
}