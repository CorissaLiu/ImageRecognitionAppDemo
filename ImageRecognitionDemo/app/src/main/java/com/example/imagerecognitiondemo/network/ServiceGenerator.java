package com.example.imagerecognitiondemo.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 接口地址
 */
public class ServiceGenerator {
    /**
     * 默认地址
     */
    public static String BASE_URL = "https://aip.baidubce.com";

    /**
     * 创建服务
     *
     * @param serviceClass 服务接口
     * @param <T>          泛型规范
     * @return api接口服务
     */
    public static <T> T createService(Class<T> serviceClass) {

        //创建OkHttpClient构建器对象
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //设置请求超时的时间
        okHttpClientBuilder.connectTimeout(20000, TimeUnit.MILLISECONDS);

        //消息拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

        //setlevel用来设置日志打印的级别，共包括了四个级别：NONE,BASIC,HEADER,BODY
        //BASEIC:请求/响应行
        //HEADER:请求/响应行 + 头
        //BODY:请求/响应航 + 头 + 体
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        //为OkHttp添加消息拦截器
        okHttpClientBuilder.addInterceptor(httpLoggingInterceptor);

        //在Retrofit中设置httpclient
        //设置地址  就是上面的固定地址,如果你是本地访问的话，可以拼接上端口号  例如 +":8080"
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                //用Gson把服务端返回的json数据解析成实体
                .addConverterFactory(GsonConverterFactory.create())
                //放入OKHttp
                .client(okHttpClientBuilder.build())
                .build();
        //返回这个创建好的API服务
        return retrofit.create(serviceClass);
    }

}
