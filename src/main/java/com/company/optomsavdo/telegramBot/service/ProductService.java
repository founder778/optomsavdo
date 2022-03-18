package com.company.optomsavdo.telegramBot.service;
import com.company.optomsavdo.telegramBot.MakeButton;
import com.company.optomsavdo.telegramBot.entity.ProductEntity;
import com.company.optomsavdo.telegramBot.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.File;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    ButtonService buttonService;

    public EditMessageText getProductType(Map<String, String> userStep, String menu, String userId, String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        List<String> types = productRepository.getByMenuPro(menu);
        if (types.size() == 0) {
            editMessageText.setText("*Bo`limda hozrcha hech narsa yo`q*");
            editMessageText.setParseMode("Markdown");
            editMessageText.setChatId(userId);
            editMessageText.setMessageId(Integer.valueOf(chatId));
            return editMessageText;
        }

        editMessageText.setText(menu + " Bo`limi");
        editMessageText.setChatId(userId);
        userStep.replace(userId,"menuIn");
        editMessageText.setMessageId(Integer.valueOf(chatId));
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.menuButton(types);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;

    }
    public SendMessage getProductTypee(Map<String, String> userStep, String menu, String userId) {
        SendMessage editMessageText = new SendMessage();
        List<String> types = productRepository.getByMenuPro(menu);
        if (types.size() == 0) {
            editMessageText.setText("*Bo`limda hozrcha hech narsa yo`q*");
            editMessageText.setParseMode("Markdown");
            editMessageText.setChatId(userId);
            return editMessageText;
        }

        editMessageText.setText(menu + " Bo`limi");
        editMessageText.setChatId(userId);
        userStep.replace(userId,"menuIn");
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.menuButton(types);
        editMessageText.setReplyMarkup(inlineKeyboardMarkup);
        return editMessageText;

    }

    public EditMessageText productButton(Map<String, String> userStep, String menu, String userId, String chatId){
        userStep.replace(userId,"products");
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(Integer.valueOf(chatId));
        editMessageText.setText(menu.toUpperCase());
        editMessageText.setChatId(userId);
        editMessageText.setReplyMarkup(buttonService.menuButton(this.getAll(menu)));
       return editMessageText;

    }
    public SendMessage productButtonn(Map<String, String> userStep, String menu, String userId, String chatId){
        userStep.replace(userId,"menuIn");
        SendMessage editMessageText = new SendMessage();
        editMessageText.setText(menu.toUpperCase());
        editMessageText.setChatId(userId);
        editMessageText.setReplyMarkup(buttonService.menuButton(this.getAll(menu)));
        return editMessageText;

    }

    public List<SendPhoto> getProduct(Map<String, Integer> count,Map<String, String> userStep, String menu, String userId, String chatId) {
        List<ProductEntity> products= productRepository.getByTypeName(menu);
        List<SendPhoto> photos = new LinkedList<>();
        if (!count.containsKey(userId)) {
            count.put(userId, 0);
        }
        userStep.replace(userId, "zakaz");
        for (ProductEntity product : products) {
            SendPhoto sendPhoto = new SendPhoto();
            InputFile inputFile = new InputFile(new File(product.getImg()));
            sendPhoto.setCaption("name : " + product.getP_name() + "\n" +
                    "narx : " + product.getP_price() + "\n" +
                    "" + product.getP_caption());
            InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
            InlineKeyboardButton inlineKeyboardButton = MakeButton.makebutton1("0", "count");
            InlineKeyboardButton inlineKeyboardButton1 = MakeButton.makebutton1("-", "minus_" + product.getP_name());
            InlineKeyboardButton inlineKeyboardButton2 = MakeButton.makebutton1("+", "plus_" + product.getP_name());
            InlineKeyboardButton inlineKeyboardButton3 = MakeButton.makebutton1("Buyurtma berish 📥", "ok");
//            InlineKeyboardButton inlineKeyboardButton4 = MakeButton.makebutton1("Orqaga", "back");

            List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
            List<InlineKeyboardButton> firstRow = new LinkedList<>();
            firstRow.add(inlineKeyboardButton1);
            firstRow.add(inlineKeyboardButton);
            firstRow.add(inlineKeyboardButton2);

            List<InlineKeyboardButton> secondRow = new LinkedList<>();
            secondRow.add(inlineKeyboardButton3);
//            secondRow.add(inlineKeyboardButton4);
            keyboardRowList.add(firstRow);
            keyboardRowList.add(secondRow);
            keyboard.setKeyboard(keyboardRowList);
            sendPhoto.setReplyMarkup(keyboard);

            sendPhoto.setPhoto(inputFile);
            sendPhoto.setChatId(userId);
            photos.add(sendPhoto);
        }


        return photos;

    }

    public EditMessageReplyMarkup plusProduct(Map<String ,Integer> productCount,String userId,String chatId,String data){
        String[] split = data.split("_");
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        Integer count =  productCount.get(userId) + 1;
        productCount.replace(userId,count);
        InlineKeyboardButton inlineKeyboardButton = MakeButton.makebutton1(String.valueOf(count), "count");
        InlineKeyboardButton inlineKeyboardButton1 = MakeButton.makebutton1("-", "minus_"+split[1]);
        InlineKeyboardButton inlineKeyboardButton2 = MakeButton.makebutton1("+", "plus_"+split[1]);
        InlineKeyboardButton inlineKeyboardButton3 = MakeButton.makebutton1("Buyurtma berish 📥", "ok/"+split[1]+"/"+String.valueOf(count));
//        InlineKeyboardButton inlineKeyboardButton4 = MakeButton.makebutton1("Orqaga", "back");
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new LinkedList<>();
        firstRow.add(inlineKeyboardButton1);
        firstRow.add(inlineKeyboardButton);
        firstRow.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> secondRow = new LinkedList<>();
        secondRow.add(inlineKeyboardButton3);
//        secondRow.add(inlineKeyboardButton4);
        keyboardRowList.add(firstRow);
        keyboardRowList.add(secondRow);
        keyboard.setKeyboard(keyboardRowList);
        editMessageReplyMarkup.setMessageId(Integer.valueOf(chatId));
        editMessageReplyMarkup.setReplyMarkup(keyboard);
        editMessageReplyMarkup.setChatId(userId);
        return editMessageReplyMarkup;


    }


    public EditMessageReplyMarkup zeroCount(Map<String ,Integer> productCount,String userId,String chatId,String data){
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        Integer count =  productCount.get(userId);
        InlineKeyboardButton inlineKeyboardButton = MakeButton.makebutton1(String.valueOf(count), "count");
        InlineKeyboardButton inlineKeyboardButton1 = MakeButton.makebutton1("-", "minus");
        InlineKeyboardButton inlineKeyboardButton2 = MakeButton.makebutton1("+", "plus");
        InlineKeyboardButton inlineKeyboardButton3 = MakeButton.makebutton1("Buyurtma berish 📥", "ok");
//        InlineKeyboardButton inlineKeyboardButton4 = MakeButton.makebutton1("Orqaga", "back");
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new LinkedList<>();
        firstRow.add(inlineKeyboardButton1);
        firstRow.add(inlineKeyboardButton);
        firstRow.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> secondRow = new LinkedList<>();
        secondRow.add(inlineKeyboardButton3);
//        secondRow.add(inlineKeyboardButton4);
        keyboardRowList.add(firstRow);
        keyboardRowList.add(secondRow);
        keyboard.setKeyboard(keyboardRowList);
        editMessageReplyMarkup.setMessageId(Integer.valueOf(chatId));
        editMessageReplyMarkup.setReplyMarkup(keyboard);
        editMessageReplyMarkup.setChatId(userId);
        return editMessageReplyMarkup;


    }

    public EditMessageReplyMarkup minusProduct(Map<String ,Integer> productCount,String userId,String chatId,String data){
        String[] split = data.split("_");
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup();
        Integer count = productCount.get(userId);
        if(productCount.get(userId)>0){
            count=  productCount.get(userId) - 1;
        }

        productCount.replace(userId,count);
        InlineKeyboardButton inlineKeyboardButton = MakeButton.makebutton1(String.valueOf(count), "count");
        InlineKeyboardButton inlineKeyboardButton1 = MakeButton.makebutton1("-", "minus_"+split[1]);
        InlineKeyboardButton inlineKeyboardButton2 = MakeButton.makebutton1("+", "plus_"+split[1]);
        InlineKeyboardButton inlineKeyboardButton3 = MakeButton.makebutton1("Buyurtma berish 📥", "ok/"+split[1]+"/"+String.valueOf(count));
//        InlineKeyboardButton inlineKeyboardButton4 = MakeButton.makebutton1("Orqaga", "back");
        List<List<InlineKeyboardButton>> keyboardRowList = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new LinkedList<>();
        firstRow.add(inlineKeyboardButton1);
        firstRow.add(inlineKeyboardButton);
        firstRow.add(inlineKeyboardButton2);

        List<InlineKeyboardButton> secondRow = new LinkedList<>();
        secondRow.add(inlineKeyboardButton3);
//        secondRow.add(inlineKeyboardButton4);
        keyboardRowList.add(firstRow);
        keyboardRowList.add(secondRow);
        keyboard.setKeyboard(keyboardRowList);
        editMessageReplyMarkup.setMessageId(Integer.valueOf(chatId));
        editMessageReplyMarkup.setReplyMarkup(keyboard);
        editMessageReplyMarkup.setChatId(userId);
        return editMessageReplyMarkup;


    }

    public List<String> getAll(String procuctType){
        List<String> allMenu = new LinkedList<>();
        List<ProductEntity> products= productRepository.getByTypePro(procuctType);
        for (ProductEntity product : products) {
            allMenu.add(product.getP_name());
        }
//        allMenu.add("back");
        return allMenu;
    }





}
