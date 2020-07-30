package zoomSp.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import zoomSp.domain.TimeTable;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Api(description = "Операции по взаимодействию с zoomAPI")
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("zoom")
public class ZoomController {

    private String refreshToken = "eyJhbGciOiJIUzUxMiIsInYiOiIyLjAiLCJraWQiOiI1MmE1ODU2NC04ZDQ3LTQwYmQtYjMwMS02MWFhNDIzM2ZjNDAifQ.eyJ2ZXIiOjcsImF1aWQiOiIyYTgzZjg0NTBjOTdiZTBkMzNlMWNiYTYxMDViNDMwOCIsImNvZGUiOiJQNksxeHhMNUJIX2twRVdOZXdNUXdpUURCS2NHSHZXcUEiLCJpc3MiOiJ6bTpjaWQ6UGNPeFVyYmpSNHF1aXNvUUI4dFdSdyIsImdubyI6MCwidHlwZSI6MSwidGlkIjowLCJhdWQiOiJodHRwczovL29hdXRoLnpvb20udXMiLCJ1aWQiOiJrcEVXTmV3TVF3aVFEQktjR0h2V3FBIiwibmJmIjoxNTk2MDk3NDU3LCJleHAiOjIwNjkxMzc0NTcsImlhdCI6MTU5NjA5NzQ1NywiYWlkIjoiaEp3ak5rd1ZUMnV0ZXB3bTRtSXl2USIsImp0aSI6IjlhZGEzYWMzLTZiMDAtNDY0ZC05ZjZmLTdjOTVhZTViZTA0NSJ9.TogYT_KSrQDkJuQZ6TgvLa1wBXxZe4t7TJhFJ6O8l-2GeMUC4pOn9r1aHqetiaMGcXIDDWjzL_-ywWCOi8_g8w";

    @CrossOrigin
    @ApiOperation(value = "Подключение к комнате")
    @PostMapping("id")
    public ResponseEntity<String> List(@PathVariable("id") Long id) throws IOException {
        String answer = executePost("https://zoom.us/oauth/token?grant_type=refresh_token&refresh_token=" + refreshToken);
        System.out.println(answer);
        return new ResponseEntity<>(answer, HttpStatus.OK);
    }

    public static String executePost(String targetURL) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic UGNPeFVyYmpSNHF1aXNvUUI4dFdSdzp4MkR5QjRxNVBaejgyaFlwNHZzdHpQNE5hRHZpdjc5MA==");


            connection.setUseCaches(false);
            connection.setDoOutput(true);


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