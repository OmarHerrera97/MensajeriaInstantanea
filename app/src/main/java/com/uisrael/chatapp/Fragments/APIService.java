package com.uisrael.chatapp.Fragments;

import com.uisrael.chatapp.Notifications.MyResponse;
import com.uisrael.chatapp.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @Headers(
            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA_gPSnuQ:APA91bG52pwsY-C4EqPHx52RNw0jwT_Qq-Oke0FNu9FOK_SXQV8erqvTIFwdbvPloppK2Ryi-1Q6CSOksJdyDCRSWL2HNpcgiGGvV_oMZvcnzOzMhuFFq_zGTqcLUPq60hZWXymOSnfh"
            }
    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);
}
