package com.nuu.entity.chat_completions.result;

import com.nuu.entity.chat_completions.Message;

public class Choice {
    public int index;
    public Message message;
    public String finish_reason;
}
