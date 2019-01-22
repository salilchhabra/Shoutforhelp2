package com.example.salil.shoutforhelp;
        import android.content.BroadcastReceiver;
        import android.content.Context;
        import android.content.Intent;
        import android.os.CountDownTimer;
        import android.telephony.SmsManager;
        import android.util.Log;
        import android.widget.Toast;


public class MyReceiver extends BroadcastReceiver
{  int l;
    private static int countPowerOff = 3;
    int l1;
    private static int countPowerOff1 = 3;

    public MyReceiver ()
    {

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
            Log.e("In on receive", "In Method:  ACTION_SCREEN_OFF");
            Log.i("count :", String.valueOf(countPowerOff));
            if (countPowerOff1 < 3) {
                StringBuffer link = new StringBuffer();
                link.append("http://maps.google.com/?q=");
                link.append(MainActivity.t4.getText().toString());
                link.append(",");
                link.append(MainActivity.t5.getText().toString());
                String text = "I am in danger Please help me I am at this location.";
                SmsManager sms = SmsManager.getDefault();
                Log.i("hii:", "hii");
                for (int i1 = 0; i1 < MainActivity.arrayList.size(); i1++) {

                    sms.sendTextMessage((String) MainActivity.arrayList.get(i1), null, text + String.valueOf(link), null, null);
                    Log.i("message:", "done");


            }
            }
            countPowerOff = 0;

            Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();

                new CountDownTimer(4000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        l = (int) (millisUntilFinished / 1000);
                        countPowerOff++;
                        Log.i("timer:", String.valueOf(l));
                        Log.i("countpoweroff:", String.valueOf(countPowerOff));
                    }

                    @Override
                    public void onFinish() {
                    }
                }.start();
             }else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
                Log.e("In on receive", "In Method:  ACTION_SCREEN_ON");
                if (countPowerOff < 3) {
                    StringBuffer link = new StringBuffer();
                    link.append("http://maps.google.com/?q=");
                    link.append(MainActivity.t4.getText().toString());
                    link.append(",");
                    link.append(MainActivity.t5.getText().toString());
                    String text = "I am in danger Please help me I am at this location.";
                    SmsManager sms = SmsManager.getDefault();
                    Log.i("hii:", "hii");
                    for (int i1 = 0; i1 < MainActivity.arrayList.size(); i1++) {

                        sms.sendTextMessage((String) MainActivity.arrayList.get(i1), null, text + String.valueOf(link), null, null);
                        Log.i("message1:", "done");

                    }
                    }
countPowerOff1=0;
            Toast.makeText(context, "message sent", Toast.LENGTH_SHORT).show();
                    new CountDownTimer(4000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished1) {
                            l1 = (int) (millisUntilFinished1 / 1000);
                            countPowerOff1++;
                            Log.i("timer1:", String.valueOf(l1));
                            Log.i("countpoweroff1:", String.valueOf(countPowerOff1));
                        }

                        @Override
                        public void onFinish() {
                        }
                    }.start();

                 }else if (intent.getAction().equals(Intent.ACTION_USER_PRESENT)) {
                    Log.e("In on receive", "In Method:  ACTION_USER_PRESENT");


                }
            }

    }
