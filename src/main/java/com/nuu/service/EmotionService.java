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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;

@RestController
public class EmotionService {

//    private final String token = "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";
    @Value("${api.gpt.token}")
    private String token;
    private final String chatgptURL = "https://api.openai.com/v1/chat/completions";
//    private static ArrayList<Message> messages = new ArrayList<>();

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
            ChatCompletions completions = chatObject(String.format("%s，請問根據以上敘述或情境，判斷或推論這個情境或敘述的情緒(emotion)為何?請務必用繁體中文輸出", json.getQuestion().trim()));

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
            ChatCompletions completions = chatObject(String.format("\"%s\"，請將以上引號內敘述翻譯成%s，並只需要將翻譯句子輸出即可", json.getQuestion().trim(), language.toString()));

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

    @PostMapping(path = "/api/chat/story", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> generateStory(@RequestBody Question json){
        if(json == null){
            System.out.println("無問題資料");
        }else{
            Gson gson = new Gson();
            HttpClient client = HttpClients.createDefault();

            // 呼叫方法
            ChatCompletions completions = chatObject(String.format("小明和小美兩個人一起來到了臺北%s遊玩並體驗行程，請幫我產生兩人到這邊遊玩的故事，但故事並未結束旅程，限定100字。", json.getQuestion().trim()));

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

            // 加回去 List 產生 Context
//            Message message = new Message();
//            message.role = "user";
//            message.content = String.format(result);
//            messages.add(message);

            if(result != null){
                ResponseEntity<String> responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
                return responseEntity;
            }
        }
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/api/chat/map", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> contentQueryAttractions(@RequestBody Question json){
        if(json == null){
            System.out.println("無問題資料");
        }else{
            Gson gson = new Gson();
            HttpClient client = HttpClients.createDefault();

            // 呼叫方法
            ChatCompletions completions = chatObject(String.format("我現在人在高雄，而且%s，請問有什麼推薦的地點，請以單一名詞輸出(如冰品店、百貨公司等)並不需要列出例子且以清單方式呈現，另外不要輸出多餘的字，我只要清單內容即可，且清單內容格式以每個名詞間有一頓號排列輸出。", json.getQuestion().trim()));

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
