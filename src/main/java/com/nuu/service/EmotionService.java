package com.nuu.service;

import com.google.gson.Gson;
import com.nuu.components.CountryLanguage;
import com.nuu.entity.Question;
import com.nuu.entity.chat_completions.ChatCompletions;
import com.nuu.entity.chat_completions.Message;
import com.nuu.entity.chat_completions.result.ChatCompletionsResult;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class EmotionService {

    private final String token = "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";
    private final String chatgptURL = "https://api.openai.com/v1/chat/completion";

    @GetMapping(path = "/api/token", produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> getToken(){
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping(path = "/api/chat/emotion", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> chat(@RequestBody Question json){
        if(json == null){
            System.out.println("無問題資料");
        }else{
            Gson gson = new Gson();
            HttpClient client = HttpClients.createDefault();

            // 呼叫方法
            ChatCompletions completions = chatObject(String.format("%s", json.getQuestion().trim()));

            String jsonString = gson.toJson(completions);
            System.out.println("Json completions: "+jsonString);

            StringEntity body = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            HttpPost httpPost = new HttpPost(chatgptURL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", String.format("Bearer %s", token));
            httpPost.setEntity(body);

            HttpUriRequest request = httpPost;
            String responseString = null;
            try{
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                responseString = EntityUtils.toString(entity);
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }

            ChatCompletionsResult root = gson.fromJson(responseString, ChatCompletionsResult.class);
            String result = root.choices.get(0).message.content.trim();
            System.out.println("result = "+result);
            ResponseEntity<String> responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            return responseEntity;
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/api/chat/i18/{language}", consumes = "application/json;charset=UTF-8", produces = "text/plain;charset=UTF-8")
    public ResponseEntity<String> multipleLanguage(@RequestBody Question json, @PathVariable("language") CountryLanguage language){
        if (json == null){
            System.out.println("無問題資料");
        }else{
            Gson gson = new Gson();
            HttpClient client = HttpClients.createDefault();

//            System.out.println(language.toString());
            // 呼叫方法
            ChatCompletions completions = chatObject(String.format("\"%s\"，%s", json.getQuestion().trim(), language.toString()));

            String jsonString = gson.toJson(completions);
            System.out.println("Json completions: "+jsonString);

            StringEntity body = new StringEntity(jsonString, ContentType.APPLICATION_JSON);
            HttpPost httpPost = new HttpPost(chatgptURL);
            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setHeader("Authorization", String.format("Bearer %s", token));
            httpPost.setEntity(body);

            HttpUriRequest request = httpPost;
            String responseString = null;
            try{
                HttpResponse response = client.execute(request);
                HttpEntity entity = response.getEntity();
                responseString = EntityUtils.toString(entity);
            }catch (IOException ex){
                throw new RuntimeException(ex);
            }

            ChatCompletionsResult root = gson.fromJson(responseString, ChatCompletionsResult.class);
            String result = root.choices.get(0).message.content.trim();
            System.out.println("translation result = "+result);
            ResponseEntity<String> responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
            return responseEntity;
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    private ChatCompletions chatObject(String questionString){
        ChatCompletions completions = new ChatCompletions();
        completions.model = "gpt-3.5-turbo";
        Message message = new Message();
        message.role = "user";
        message.content = String.format(questionString);
        ArrayList<Message> messages = new ArrayList<>();
        messages.add(message);
        completions.messages = messages;
        return completions;
    }

}
