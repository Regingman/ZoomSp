package zoomSp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.domain.Token;
import zoomSp.repo.TokensRepo;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@Api(description = "Операции по взаимодействию с zoomAPI")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("zoom")
public class ZoomController {
    @Autowired
    private TokensRepo tokensRepo;

    private String refreshToken = "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI4YmFhYzE4Yy01Y2NkLTRlYjctYjQzYi03YzljZmFhYjExZDIifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJ0ek52ZUtEWWkzX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjoyLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MTgyMjE0LCJleHAiOjIwNjkyMjIyMTQsImlhdCI6MTU5NjE4MjIxNCwiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjhjY2Y4OTZmLWM1NTgtNGNjYS1hNzQ4LWQxZWQ5M2Y5MjYxYyJ9.dqU1vt3oiTx2C0Tg4ZcdOmZz6IMWgvhMANYavcL5oXgutn1rqbJiS9wxzoIwmIydPkqRau4ID26Pb7cQ6WuVtw.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJQNksxeHhMNUJIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MDk3NDU3LCJleHAiOjIwNjkxMzc0NTcsImlhdCI6MTU5NjA5NzQ1NywiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjlhZGEzYWMzLTZiMDAtNDY0ZC05ZjZmLTdjOTVhZTViZTA0NSJ9.TogYT_KSrQDkJuQZ6TgvLa1wBXxZe4t7TJhFJ6O8l-2GeMUC4pOn9r1aHqetiaMGcXIDDWjzL_-ywWCOi8_g8w";

    @CrossOrigin
    @ApiOperation(value = "Подключение к комнате")
    @PostMapping("{groupId}")
    public ResponseEntity<String> List(@PathVariable("groupId") Long id) throws IOException {
        if (tokensRepo.findById(1l).orElse(null) == null) {
            Token token = new Token();
            token.setAccess_token("");
            token.setRefresh_token(refreshToken);
            token.setCreateDate(LocalDateTime.now());
            token.setUpdateDate(LocalDateTime.now());
        }
        Token token = tokensRepo.findById(1l).orElse(null);
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime updateTime = token.getUpdateDate();
        if(nowTime.getMinute() >= updateTime.getMinute() + 50 ){
            String toke ="Basic UGNPeFVyYmpSNHF1aXNvUUI4dFdSdzp4MkR5QjRxNVBaejgyaFlwNHZzdHpQNE5hRHZpdjc5MA==";
            execute("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=" + token.getRefresh_token(),"POST", toke);
        }
        String toke = "Bearer " + token.getAccess_token();
        String answer = execute("https://zoom.us/v2/users/me/meetings", "POST", toke);

        // executePost("https://zoom.us/oauth/authorize?response_type=code&client_id=PcOxUrbjR4quisoQB8tWRw&redirect_uri=https://zoom-sp.herokuapp.com/");
        //String answer = executePost("https://zoom.us/oauth/token?grant_type=authorization_code&code=IVn1g629r3_kpEWNewMQwiQDBKcGHvWqA&redirect_uri=https://zoom-sp.herokuapp.com/");
        //executePost("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI1MmE1ODU2NC04ZDQ3LTQwYmQtYjMwMS02MWFhNDIzM2ZjNDAifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJQNksxeHhMNUJIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MDk3NDU3LCJleHAiOjIwNjkxMzc0NTcsImlhdCI6MTU5NjA5NzQ1NywiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjlhZGEzYWMzLTZiMDAtNDY0ZC05ZjZmLTdjOTVhZTViZTA0NSJ9.TogYT_KSrQDkJuQZ6TgvLa1wBXxZe4t7TJhFJ6O8l-2GeMUC4pOn9r1aHqetiaMGcXIDDWjzL_-ywWCOi8_g8w/");
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    public static String execute(String targetURL, String type, String toke) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("type");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", toke);
            connection.connect();
            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}