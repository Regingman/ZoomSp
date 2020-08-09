package zoomSp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.domain.TimeTable;
import zoomSp.domain.Token;
import zoomSp.repo.TimeTablesRepo;
import zoomSp.repo.TokensRepo;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Api(description = "Операции по взаимодействию с zoomAPI")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("zoom")
public class ZoomController {
    @Autowired
    private TokensRepo tokensRepo;
    @Autowired
    private TimeTablesRepo timeTablesRepo;

    private String refreshToken = "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI4YmFhYzE4Yy01Y2NkLTRlYjctYjQzYi03YzljZmFhYjExZDIifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJ0ek52ZUtEWWkzX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjoyLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MTgyMjE0LCJleHAiOjIwNjkyMjIyMTQsImlhdCI6MTU5NjE4MjIxNCwiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjhjY2Y4OTZmLWM1NTgtNGNjYS1hNzQ4LWQxZWQ5M2Y5MjYxYyJ9.dqU1vt3oiTx2C0Tg4ZcdOmZz6IMWgvhMANYavcL5oXgutn1rqbJiS9wxzoIwmIydPkqRau4ID26Pb7cQ6WuVtw.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJQNksxeHhMNUJIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MDk3NDU3LCJleHAiOjIwNjkxMzc0NTcsImlhdCI6MTU5NjA5NzQ1NywiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjlhZGEzYWMzLTZiMDAtNDY0ZC05ZjZmLTdjOTVhZTViZTA0NSJ9.TogYT_KSrQDkJuQZ6TgvLa1wBXxZe4t7TJhFJ6O8l-2GeMUC4pOn9r1aHqetiaMGcXIDDWjzL_-ywWCOi8_g8w";

    @CrossOrigin
    @ApiOperation(value = "Подключение к комнате")
    @PostMapping("{groupId}")
    public ResponseEntity<String> List(@PathVariable("groupId") Long id) throws Exception {
       /* if (tokensRepo.findById(1l).orElse(null) == null) {
            Token token = new Token();
            token.setAccess_token("");
            token.setRefresh_token(refreshToken);
            token.setCreateDate(LocalDateTime.now());
            token.setUpdateDate(LocalDateTime.now());
            tokensRepo.save(token);
        }
        else{
            Token token = tokensRepo.findById(1l).orElse(null);
            token.setAccess_token("eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiJkY2ZkNDI1OS1mZjBmLTRkZWQtODhmNy04MmY5MDM0MGU2NWEifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJtMGRJNG1iOVdIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MCwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MzU5OTQ4LCJleHAiOjE1OTYzNjM1NDgsImlhdCI6MTU5NjM1OTk0OCwiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjJhNjVhM2VhLTMwZmYtNDEwNC05M2ExLTk1YjU5MTM4NWMyZCJ9.yZM0RyLyjk3UphkJwKnEFs9VJBEQlP09FGuPL2MBn8uj-5zGxu-UQimMi_scfgZJcIfLCQNoFuPVSrMQYzSO8g");
            token.setRefresh_token("eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI5YjY0MDczMy1iMTZiLTQyODktOTliMS03Zjk4NWIwOTQ0MDIifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJtMGRJNG1iOVdIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MzU5OTQ4LCJleHAiOjIwNjkzOTk5NDgsImlhdCI6MTU5NjM1OTk0OCwiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6ImU3ZDQzMDFjLWE3ZDItNGM4Yy04NTlkLTM3MWYzNzhjNzE3ZCJ9.QBGjPJ-hzWqAkB_P1eT16oz7VxyJpwRneH_-nDnHS5gJZ7Ft2UFv4PgNF6khJRaTZPTgEHvM2Hk7OVhWVYMxVQ");
            BeanUtils.copyProperties(token, 1, "id");
            tokensRepo.save(token);
        }*/


        Token token = tokensRepo.findById(1l).orElse(null);
        LocalDateTime nowTime = LocalDateTime.now();
        LocalDateTime updateTime = token.getUpdateDate();
        int day = nowTime.getDayOfWeek().getValue();
        //Long day = new Long(days);
        int numberLesson = 0;
        if (nowTime.getHour() == 8 && (nowTime.getMinute() >= 0 && nowTime.getMinute() <= 40)) {
            numberLesson = 1;
        } else if ((nowTime.getHour() == 8 && nowTime.getMinute() >= 50) || (nowTime.getHour() == 9 && nowTime.getMinute() <= 30)) {
            numberLesson = 2;
        } else if ((nowTime.getHour() == 9 && nowTime.getMinute() >= 40) || (nowTime.getHour() == 10 && nowTime.getMinute() <= 20)) {
            numberLesson = 3;
        } else if ((nowTime.getHour() == 10 && nowTime.getMinute() >= 30) || (nowTime.getHour() == 11 && nowTime.getMinute() <= 10)) {
            numberLesson = 4;
        } else if ((nowTime.getHour() == 11 && nowTime.getMinute() >= 20) || (nowTime.getHour() == 12 && nowTime.getMinute() <= 00)) {
            numberLesson = 5;
        } else if ((nowTime.getHour() == 12 && nowTime.getMinute() >= 10) || (nowTime.getHour() == 12 && nowTime.getMinute() <= 50)) {
            numberLesson = 6;
        } else if ((nowTime.getHour() == 13 && nowTime.getMinute() >= 00) || (nowTime.getHour() == 13 && nowTime.getMinute() <= 40)) {
            numberLesson = 7;
        } else if ((nowTime.getHour() == 13 && nowTime.getMinute() >= 50) || (nowTime.getHour() == 14 && nowTime.getMinute() <= 30)) {
            numberLesson = 8;
        } else if ((nowTime.getHour() == 14 && nowTime.getMinute() >= 40) || (nowTime.getHour() == 15 && nowTime.getMinute() <= 20)) {
            numberLesson = 9;
        } else if ((nowTime.getHour() == 15 && nowTime.getMinute() >= 30) || (nowTime.getHour() == 16 && nowTime.getMinute() <= 10)) {
            numberLesson = 10;
        }

        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(updateTime, nowTime);
        //sendPost();
        TimeTable timeTable = timeTablesRepo.getNowLesson(7, 10);

        System.out.println(ChronoUnit.MINUTES.between(timeTable.getUpdateDate(), nowTime));

        System.out.println(minutes);
        if (timeTable == null) {
            return new ResponseEntity<>("no lessons", HttpStatus.BAD_REQUEST);
        } else {

            if (minutes > 50) {
                System.out.println("true");
                String toke = "Basic UGNPeFVyYmpSNHF1aXNvUUI4dFdSdzp4MkR5QjRxNVBaejgyaFlwNHZzdHpQNE5hRHZpdjc5MA==";
                String jsonString = execute("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=" + token.getRefresh_token(), "POST", toke);
                JSONObject jsonObject = new JSONObject(jsonString);
                token.setAccess_token(jsonObject.getString("access_token"));
                token.setRefresh_token(jsonObject.getString("refresh_token"));
                token.setUpdateDate(LocalDateTime.now());
                BeanUtils.copyProperties(token, timeTable.getId(), "id");
                tokensRepo.save(token);
            }
            if (timeTable.getZoomLesson() == null) {
                String toke = "Bearer " + token.getAccess_token();
                System.out.println(toke);
                String jsonString = execute("https://zoom.us/v2/users/me/meetings", "POST", toke);
                System.out.println(jsonString);
                JSONObject jsonObject = new JSONObject(jsonString);
                timeTable.setZoomLesson(jsonObject.getString("join_url"));
                timeTable.setUpdateDate(nowTime);
                BeanUtils.copyProperties(timeTable, timeTable.getId(), "id");
                timeTablesRepo.save(timeTable);
                return new ResponseEntity<>(jsonObject.getString("join_url"), HttpStatus.OK);
            } else {
                if(ChronoUnit.MINUTES.between(timeTable.getUpdateDate(), nowTime)<40) {
                    return new ResponseEntity<>(timeTable.getZoomLesson(), HttpStatus.OK);
                }
            else{
                    String toke = "Bearer " + token.getAccess_token();
                    System.out.println(toke);
                    String jsonString = execute("https://zoom.us/v2/users/me/meetings", "POST", toke);
                    System.out.println(jsonString);
                    JSONObject jsonObject = new JSONObject(jsonString);
                    timeTable.setZoomLesson(jsonObject.getString("join_url"));
                    timeTable.setUpdateDate(nowTime);
                    BeanUtils.copyProperties(timeTable, timeTable.getId(), "id");
                    timeTablesRepo.save(timeTable);
                    return new ResponseEntity<>(jsonObject.getString("join_url"), HttpStatus.OK);

                }}
        }

        /*String toke = "Basic UGNPeFVyYmpSNHF1aXNvUUI4dFdSdzp4MkR5QjRxNVBaejgyaFlwNHZzdHpQNE5hRHZpdjc5MA==";
        String jsonString = execute("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=" + token.getRefresh_token(), "POST", toke);
        JSONObject jsonObject = new JSONObject(jsonString);
        System.out.println(jsonString);
        System.out.println(jsonObject.getString("access_token"));
        token.setAccess_token(jsonObject.getString("access_token"));
        BeanUtils.copyProperties(token, 1, "id");
        tokensRepo.save(token);

/*        if(nowTime.getMinute() >= updateTime.getMinute() + 50 && nowTime.getDayOfYear() >= updateTime.getDayOfYear() && nowTime.getHour()>=updateTime.getHour()){
            System.out.println("true");
            String toke ="Basic UGNPeFVyYmpSNHF1aXNvUUI4dFdSdzp4MkR5QjRxNVBaejgyaFlwNHZzdHpQNE5hRHZpdjc5MA==";
            execute("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=" + token.getRefresh_token(),"POST", toke);
        }
        String toke = "Bearer " + token.getAccess_token();
        String answer = execute("https://zoom.us/v2/users/me/meetings", "POST", toke);
*/
        // executePost("https://zoom.us/oauth/authorize?response_type=code&client_id=PcOxUrbjR4quisoQB8tWRw&redirect_uri=https://zoom-sp.herokuapp.com/");
        //String answer = executePost("https://zoom.us/oauth/token?grant_type=authorization_code&code=IVn1g629r3_kpEWNewMQwiQDBKcGHvWqA&redirect_uri=https://zoom-sp.herokuapp.com/");
        //executePost("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI1MmE1ODU2NC04ZDQ3LTQwYmQtYjMwMS02MWFhNDIzM2ZjNDAifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJQNksxeHhMNUJIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MDk3NDU3LCJleHAiOjIwNjkxMzc0NTcsImlhdCI6MTU5NjA5NzQ1NywiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjlhZGEzYWMzLTZiMDAtNDY0ZC05ZjZmLTdjOTVhZTViZTA0NSJ9.TogYT_KSrQDkJuQZ6TgvLa1wBXxZe4t7TJhFJ6O8l-2GeMUC4pOn9r1aHqetiaMGcXIDDWjzL_-ywWCOi8_g8w/");
        //    return new ResponseEntity<>("answer", HttpStatus.OK);
    }

    public static String execute(String targetURL, String type, String toke) {
        HttpURLConnection connection = null;
        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(type);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", toke);
            String urlParameters = "{}";
            // Send post request
            connection.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(urlParameters);
                wr.flush();
            }
            System.out.println(connection.getResponseCode());
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

    private void sendPost() throws Exception {

        // url is missing?
        //String url = "https://selfsolve.apple.com/wcResults.do";
        String url = "https://httpbin.org/post";

        HttpsURLConnection httpClient = (HttpsURLConnection) new URL(url).openConnection();

        //add reuqest header
        httpClient.setRequestMethod("POST");
        httpClient.setRequestProperty("Content-type", "application/json");
        httpClient.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiJhZjg5ZDZiZi1hYTBhLTQxMDgtYTQyOC0yNTg5NDNjZGVjOGYifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJtMGRJNG1iOVdIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MCwidGlkIjoyLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2ODc2NDQ5LCJleHAiOjE1OTY4ODAwNDksImlhdCI6MTU5Njg3NjQ0OSwiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6Ijg1ZTNlODFhLWRlOGItNDg5Yi05YzAwLTY1YjRhNTczZDVlYyJ9.O2Ol6tiGlH90pulCg-OSFjBMr4PZB-K8dMfQje8RkTJwqFCCV1yQqv93TI6wsqBgeoTIboCj4VDhD1sbltB_sA");

        String urlParameters = "{}";

        // Send post request
        httpClient.setDoOutput(true);
        try (DataOutputStream wr = new DataOutputStream(httpClient.getOutputStream())) {
            wr.writeBytes(urlParameters);
            wr.flush();
        }

        int responseCode = httpClient.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(httpClient.getInputStream()))) {

            String line;
            StringBuilder response = new StringBuilder();

            while ((line = in.readLine()) != null) {
                response.append(line);
            }

            //print result
            System.out.println(response.toString());

        }

    }
}