package com.company.optomsavdo.telegramBot;

import com.vdurmont.emoji.EmojiParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class MakeButton {
    public static InlineKeyboardButton makebutton(String text, String callbackdata,String emoji) {
       String emojitext = EmojiParser.parseToUnicode(emoji+" " + text);
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(emojitext);
        button.setCallbackData(callbackdata);
        return button;
    }

    public static List<InlineKeyboardButton> rows(InlineKeyboardButton... inlineKeyboardButtons) {
        List<InlineKeyboardButton> newrow = new LinkedList<>();
        newrow.addAll(Arrays.asList(inlineKeyboardButtons));
        return newrow;
    }
    public static List<List<InlineKeyboardButton>> collection(List<InlineKeyboardButton>... collec){
        List<List<InlineKeyboardButton>> newcollec = new LinkedList<>();
        newcollec.addAll(Arrays.asList(collec));
        return newcollec;
    }
    public static InlineKeyboardMarkup readybutton(List<List<InlineKeyboardButton>> buttons){
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(buttons);
        return inlineKeyboardMarkup;
    }
    public static InlineKeyboardButton makebutton1(String text, String callbackdata) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackdata);
        return button;
    }


}
