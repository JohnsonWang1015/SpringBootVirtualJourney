package com.nuu.entity.chat_completions.result;

import com.nuu.entity.chat_completions.result.Choice;

import java.util.ArrayList;

public class ChatCompletionsResult implements java.io.Serializable{
    public String id;
    public String object;
    public int created;
    public ArrayList<Choice> choices;
    public Usage usage;
}
