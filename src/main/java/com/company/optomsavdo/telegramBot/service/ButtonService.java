package com.company.optomsavdo.telegramBot.service;

import com.company.optomsavdo.telegramBot.MakeButton;
import com.company.optomsavdo.telegramBot.entity.MenuEntity;
import com.company.optomsavdo.telegramBot.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
@Service
public class ButtonService {
    @Autowired
    MenuRepository menuRepository;
    public  InlineKeyboardMarkup menuButton(List<String> buttons) {
        List<List<InlineKeyboardButton>> ButtonList = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i++) {

            InlineKeyboardButton inlineKeyboardButton1 = MakeButton.makebutton1(buttons.get(i).toUpperCase(),buttons.get(i).toLowerCase());
            i++;
            if(i==buttons.size() ){
                List<InlineKeyboardButton> inlineKeyboardButtonList = MakeButton.rows(inlineKeyboardButton1);
                ButtonList.add(inlineKeyboardButtonList);
                break;
            }
            else{
                InlineKeyboardButton inlineKeyboardButton2 = MakeButton.makebutton1(buttons.get(i).toUpperCase(),buttons.get(i).toLowerCase());
                List<InlineKeyboardButton> inlineKeyboardButtonList = MakeButton.rows(inlineKeyboardButton1, inlineKeyboardButton2);
                ButtonList.add(inlineKeyboardButtonList);
            }

        }
        InlineKeyboardMarkup inlineKeyboardMarkup = MakeButton.readybutton(ButtonList);
        return inlineKeyboardMarkup;
    }

    public List<String> getAll(){
        List<String> allMenu = new LinkedList<>();
        Iterable<MenuEntity> menus = menuRepository.findAll();
        Iterator<MenuEntity> iterator = menus.iterator();
        while (iterator.hasNext()){
            allMenu.add(iterator.next().getM_name());
        }
        return allMenu;
    }

}
