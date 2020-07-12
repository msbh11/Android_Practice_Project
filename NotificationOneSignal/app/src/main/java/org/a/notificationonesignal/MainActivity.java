package org.a.notificationonesignal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.onesignal.OneSignal;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    EditText emailSender, msg, title, emailRcv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailSender = findViewById(R.id.emailEt);
        title = findViewById(R.id.titleEt);
        msg = findViewById(R.id.msgEt);
        emailRcv = findViewById(R.id.emailRcvEt);


        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();



    }


    public void SendNotification(View view) {

        OneSignal.sendTag("email", emailSender.getText().toString().trim());

        sendNotiInfo(emailRcv.getText().toString().trim(), title.getText().toString().trim(), msg.getText().toString().trim());

    }

    private void sendNotiInfo(final String email, final String title, final String msg){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String jsonResponse;

                    URL url = new URL("https://onesignal.com/api/v1/notifications");
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setUseCaches(false);
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                    con.setRequestProperty("Authorization", "Basic OGRiOTE1NDAtN2Q1Ni00MzAwLWE3NWMtNTVhYjgxNTI2Zjkx");
                    con.setRequestMethod("POST");

                    String strJsonBody = "{"
                            +   "\"app_id\": \"4824074d-046b-467d-9c4f-a148b01ddc56\","
                            +   "\"filters\": [{\"field\": \"tag\", \"key\": \"email\",  \"value\": \" "+email+" \"}],"
                            +   "\"data\": {\"foo\": \"bar\"},"
                            +    "\"headings\": {\"en\": \""+title+"\"},"
                            +   "\"contents\": {\"en\": \""+msg+"\"}"
                            + "}";


                    System.out.println("strJsonBody:\n" + strJsonBody);

                    byte[] sendBytes = strJsonBody.getBytes("UTF-8");
                    con.setFixedLengthStreamingMode(sendBytes.length);

                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(sendBytes);

                    int httpResponse = con.getResponseCode();
                    System.out.println("httpResponse: " + httpResponse);

                    if (  httpResponse >= HttpURLConnection.HTTP_OK
                            && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                        Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    else {
                        Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                        jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                        scanner.close();
                    }
                    System.out.println("jsonResponse:\n" + jsonResponse);

                } catch(Throwable t) {
                    t.printStackTrace();

                }
            }
        });
    }
}
