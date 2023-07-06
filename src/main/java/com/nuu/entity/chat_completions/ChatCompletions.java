package com.nuu.entity.chat_completions;

import java.util.ArrayList;

public class ChatCompletions implements java.io.Serializable{
    public String model;
    public ArrayList<Message> messages;
}
