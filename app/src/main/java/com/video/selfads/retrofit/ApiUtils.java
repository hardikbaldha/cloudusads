package com.video.selfads.retrofit;


public class ApiUtils {
    private ApiUtils() {
    }

    public static APIService getAPIService(String str) {
        return (APIService) ApiEventClient.getClient(str).create(APIService.class);
    }

}
