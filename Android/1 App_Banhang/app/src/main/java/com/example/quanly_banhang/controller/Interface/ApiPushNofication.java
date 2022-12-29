package com.example.quanly_banhang.controller.Interface;

import com.example.quanly_banhang.controller.model.NotiResponse;
import com.example.quanly_banhang.controller.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface ApiPushNofication {
    @Headers({
            "Content-Type: application/json",
            "Authorization: key =AAAA8NhML-g:APA91bHhVNFYNDs9whgiyrGQXcvm9Xb4vVNaX2RsCR3UHkfpzj4CzQV3KMr36MGa5r2Cqllzwy_GEEjwXskT2MvSIwQv-97FWpRtw1DYIUWHw5xR4ZmfOLYl60c94qQVpPVeTOD2Iz-r"
        }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
