package demo.zcy.carforum.provider;

import demo.zcy.carforum.dto.AccessTokenDTO;
import demo.zcy.carforum.dto.GithubUser;

import okhttp3.*;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder().build();
        RequestBody body = RequestBody.create(mediaType,JSON.toJSONString(accessTokenDTO));

        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = okHttpClient.newCall(request).execute())
        {
            String bodyStr = response.body().string();
            String token = bodyStr.split("&")[0].split("=")[1];
            return token;            
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

	public GithubUser getUser(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
        .url("https://api.github.com/user?access_token="+accessToken)
        .build();

        try {
            Response response = client.newCall(request).execute();  
            String strA = response.body().string();
            GithubUser gitUser = JSON.parseObject(strA, GithubUser.class);
            return gitUser;

        } catch (Exception e) {
        }      

		return null;
	}

}