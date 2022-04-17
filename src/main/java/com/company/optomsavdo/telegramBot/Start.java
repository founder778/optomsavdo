package com.company.optomsavdo.telegramBot;

import com.company.optomsavdo.telegramBot.controller.UserController;
import com.company.optomsavdo.telegramBot.repository.OrderRepository;
import com.company.optomsavdo.telegramBot.service.ButtonService;
import com.company.optomsavdo.telegramBot.service.OrderService;
import com.company.optomsavdo.telegramBot.service.ProductService;
import com.company.optomsavdo.telegramBot.service.UserService;
import com.company.optomsavdo.telegramBot.state.UserState;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

@Component
public class Start extends TelegramLongPollingBot {
    Map<String, Integer> productCount = new HashMap<>();
    Map<String, String> userStep = new HashMap<>();
    Map<String, String> newUser = new HashMap<>();
    Map<String, UserState> state = new HashMap<>();
    Map<String, String> signUp = new HashMap<>();
    Map<String, String> korzina = new HashMap<>();
    Map<String, String> oldStep = new HashMap<>();
    Map<String, String> olddata = new HashMap<>();
    Map<String, String> logInn = new HashMap<>();

    @Autowired
    UserController userController;
    @Autowired
    UserService userService;

    @Autowired
    ProductService productService;
    @Autowired
    ButtonService buttonService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Override
    public void onUpdateReceived(Update update) {

//        SendPhoto sendPhoto1 = new SendPhoto();
//        sendPhoto1.setChatId(update.getMessage().getChatId().toString());
////        sendPhoto1.setCaption("*✅ O'z biznesiga asos solmoqchi bo'lganlar uchun:\n" +
////                "\n" +
////                "\uD83D\uDCCC Respublikamizning istalgan viloyat, tuman va " +
////                " shahridan uydan chiqmagan holda ortiqcha vaqt va naqt sarflamasdan " +
////                "kop yillardan buyon xalq xizmatida bolib kelayotgan va oz sohasining " +
////                "mutaxassisi bolgan xodimlarni yigaolgan \"SINCE INNOVATION SERVICE\" " +
////                "MChJ yordamida 2 soat vaqt oraligida oz biznes yoki oquv markazingizga asos soling.\n" +
////                "\n" +
////                "\uD83D\uDD0E Bizning mutaxassislar Sizga qulaylik yaratish maqsadida ushbu " +
////                "xizmatlarni taklif etadi, bular:\n" +
////                "1⃣ biznes va/yoki markazni tashkil etish uchun qulay joy tanlash;\n" +
////                "2⃣ biznes va/yoki oquv markaziga nom tanlash;\n" +
////                "3⃣ hujjatlarni rasmiylashtirish;\n" +
////                "4⃣ tegishli fanlardan ( sotuv ishlari boyicha boyicha menedjer, muzokaralar olib " +
////                "borish boyicha menedjer, ish yuritish, marketolog, direktor yordamchisi va direktor) " +
////                "oz bazamizda mavjud mutaxassis kadrlarni tanlash;\n" +
////                "5⃣ maqsadli reklama; \n" +
////                "6⃣ biznes va/yoki oquv markazi daromadini doimoy kotarib borish boyicha 1 yillik " +
////                "( 4 kvartalga alohida - alohida ) ishlangan strategiya bilan taminlash;\n" +
////                "7⃣ 6 oylik bepul kansultatsiya;\n" +
////                "\n" +
////                "\uD83D\uDFE2 Kompaniyamiz bilan hamkorlik qilishning afzallik tomonlari:\n" +
////                "\n" +
////                "\uD83D\uDD39 mijozimiz kopligi sabab arzon narxlar;\n" +
////                "\uD83D\uDD39tajriba va bilimli xodimlar ishni togri tashkillashtirishi " +
////                "natijasida Sizni qiziqtirgan savollarga qimmatli vaqtingizni tejagan holda " +
////                "qisqa muddatda aniq va ishonchli malumotga ega bolasiz;\n" +
////                "\uD83D\uDD39 zarurat tufayli mijozlarimiz kompaniyamiz ofesiga kelib ketishlari" +
////                " uchun qulay joylashuv;\n" +
////                "\uD83D\uDD39 mijozlar ozaro tajriba almashishlari uchun zamin yaratish maqsadida " +
////                "kompaniyamiz ananasiga aylanib borayotgan yiliga ikki marotaba " +
////                "tashkil etiladigan banket marosimi;\n" +
////                "\n" +
////                "\uD83C\uDD95 Kompaniyamizning qoshimcha xizmatlari haqidagi malumotni" +
////                " olish uchun: prosta_biznes\n" +
////                "\n" +
////                "\uD83D\uDCDD Yuqoridagi xizmatlarning barchasi Siz bilan kompaniyamiz o'rtasida" +
////                " ikki tomonlama shartnoma imzolanib, unda korsatilgan talablar bajarilganidan song amalga oshiriladi.\n" +
////                "\n" +
////                "\uD83E\uDD1D Kompaniyamiz xodimlari Siz bilan ozaro manfaatli shartnoma" +
////                " imzolashdan va bizga bildirilgan ishonchdan mamnunligini bildiradi.\n" +
////                "\n" +
////                "♻️ Firma ochish; o'quv markazi ochish; IT dasturlash yo'nalishi" +
////                " bo'yicha istalgan qiyinchilikda buyurtmalar qabuli.\n" +
////                "\n" +
////                "\uD83C\uDFE2 Bizning ofesimiz: Mirzo Ulug'bek tumani, Olimlar shaharchasi 64 B.\n" +
////                "\n" +
////                "Biz bilan boglanish va kanalga azo bolish:\n" +
////                "\uD83D\uDCE9 eto_prosta_biznes\n" +
////                "\n" +
////                "☎️ +99897 766 09 77*");
//        sendPhoto1.setCaption("dasda");
//        InputFile inputFile = new InputFile("a.jpg");
//        sendPhoto1.setParseMode("Markdown");
//        sendPhoto1.setPhoto(inputFile);
//        try {
//            execute(sendPhoto1);
//            return;
//        } catch (TelegramApiException e) {
//            e.printStackTrace();
//        }

        SendMessage sendMessage = new SendMessage();
        EditMessageText editMessageText = new EditMessageText();

        if (update.hasMessage()) {

            Message messageObj = update.getMessage();
            if (messageObj.hasPhoto()) {
                List<PhotoSize> photoList = messageObj.getPhoto();
                PhotoSize photo = photoList.get(photoList.size() - 1);
                String fileId = photo.getFileId();
                SendMessage photoInfoMessage = new SendMessage();
                photoInfoMessage.setText("FileId: \n" + fileId);
                photoInfoMessage.setChatId(messageObj.getChatId().toString());
                sendMes(photoInfoMessage);
                return;
            }
            String userId = update.getMessage().getChatId().toString();
            String message = update.getMessage().getText();

            switch (message) {
                case "/start": {
                    if (userService.isExistUser(userId) != null) {
                        state.put(userId, UserState.worker);
                        userStep.put(userId, "login");
                        sendMessage.setText("*Loginni kiriting*");
                        sendMessage.setParseMode("Markdown");
                        sendMessage.setChatId(userId);
                        sendMes(sendMessage);
                        return;
                    }
                    state.put(userId, UserState.created);
                    userStep.put(userId, "name");
                    sendMessage.setText("*Botga xush kelibsiz DO`KON NOMINI kiriting*");
                    sendMessage.setParseMode("Markdown");
                    sendMessage.setChatId(userId);
                    sendMes(sendMessage);
                    return;
                }
            }
//            if(state.get(userId).equals(UserState.worker)  && userService.check(userId)){
//                sendMessage.setChatId(userId);
//                sendMessage.setText("siz admin tomonidan bloklandingiz");
//                sendMes(sendMessage);
//                return;
//            }

            if (state.containsKey(userId)) {
                if (state.get(userId).equals(UserState.created)) {
                    sendMes(userController.createUser(userId, message, userStep, newUser));
                    if (userStep.get(userId).equals("/start")) {
                        sendMessage.setChatId("1611125588");
                        String[] userSplit = newUser.get(userId).split("#");
                        sendMessage.setText("Ismi" + userSplit[0] + "    \n\n" +
                                "Yangi agent ish boshlashiga ruhsat berilsinmi");
                        sendMessage.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Ruxsat berish", "Ruxsat" + userId)))));
                        newUser.replace(userId, "admin");
                        sendMes(sendMessage);
                    }
                    return;
                } else if (state.get(userId).equals(UserState.worker)) {
//                    if(logInn.containsKey(userId)){
//                        logInn.replace(userId,message);
//                    }else {
//                        logInn.put(userId,message);
//                    }
                    sendMes(userController.isExistUser(message, userId, userStep, signUp, state));

                    return;
                }
            }
//            else if (message.equals("📦 zakazlarim")) {
//                if (!korzina.containsKey(userId)) {
//                    sendMessage.setText("*zakazlar yo`q*");
//                    sendMessage.setParseMode("Markdown");
//                    sendMessage.setChatId(userId);
//
//                    sendMes(sendMessage);
//                    return;
//                }
//                String responce = "";
//                if (korzina.get(userId).contains("=")) {
//                    String[] splitZakaz = korzina.get(userId).split("=");
//                    for (String s : splitZakaz) {
//                        String[] product = s.split("/");
//                        responce += "Nomi : " + product[0] + "  " + product[1] + "\n";
//
//                    }
//                    userStep.replace(userId, "ok");
//                    sendMessage.setText(responce);
//                    sendMessage.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Tozalash", "delete")),
//                            MakeButton.rows(MakeButton.makebutton1("Bosh menu", "menu")))));
//                    sendMessage.setChatId(userId);
//                    sendMes(sendMessage);
//                    return;
//                } else {
//                    String[] product = korzina.get(userId).split("/");
//                    sendMessage.setText("Nomi : " + product[0] + "  " + product[1] + "\n");
//                    sendMessage.setChatId(userId);
//                    sendMessage.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Tozalash", "delete")),
//                            MakeButton.rows(MakeButton.makebutton1("Bosh menu", "menu")))));
//                    userStep.replace(userId, "ok");
//                    sendMes(sendMessage);
//                    return;
//
//                }

//            }
            if ((message.equals("📋 zakaz berish"))) {
                sendMessage.setText("*Menu bo`limi*");
                sendMessage.setParseMode("Markdown");
                sendMessage.setChatId(userId);
                sendMessage.setReplyMarkup(buttonService.menuButton(buttonService.getAll()));
                userStep.replace(userId, "menu");

                sendMes(sendMessage);
                return;
            }
            if (message.equals("✅ Barchasini tasdiqlash")) {
                if (orderService.create(korzina.get(userId), userId)) {
                    sendMes(orderService.getAllzakazlar(userId));
                    userStep.replace(userId, "active");

                } else {
                    sendMessage.setText("Hech narsa zakaz qilinmadi");
                    sendMessage.setChatId(userId);
                    sendMes(sendMessage);
                }

                return;
            } else if (message.equals("📂 Zakazlar tarixi")) {
                sendMes(orderService.groupByDate(userId, userStep));
                return;
            }


        } else if (update.hasCallbackQuery()) {

            String userid = update.getCallbackQuery().getFrom().getId().toString();
            String data = update.getCallbackQuery().getData();
            String chatId = update.getCallbackQuery().getMessage().getMessageId().toString();
//            if(state.get(userid).equals(UserState.worker) && userService.check(userid)){
//                sendMessage.setChatId(userid);
//                sendMessage.setText("siz admin tomonidan bloklandingiz");
//                sendMes(sendMessage);
//                return;
//            }
            if (data.startsWith("tay")) {
                EditMessageText editMessageText1 = new EditMessageText();
                String s = data.substring(3);
                orderRepository.update2(Integer.parseInt(s));
                editMessageText1.setText("Qabul qilindi");
                editMessageText1.setChatId("1611125588");
                editMessageText1.setMessageId(Integer.valueOf(chatId));
                editMes(editMessageText1);
                return;

            }
            if (data.startsWith("Ruxsat")) {
                sendMes(userService.update(data.substring(6)));
                return;
            }
            if (userStep.get(userid).equals("menu")) {
                editMes(productService.getProductType(userStep, data, userid, chatId));
                if (!oldStep.containsKey(userid)) {
                    olddata.put(userid, data);
                } else {
                    olddata.replace(userid, data);
                }

                return;
            }
            if (userStep.get(userid).equals("menuIn")) {
                editMessageText.setMessageId(Integer.valueOf(chatId));
                editMessageText.setText(data);
                editMessageText.setChatId(userid);
                editMes(editMessageText);
                List<SendPhoto> p = productService.getProduct(productCount, userStep, data, userid, chatId);
                oldStep.replace(userid, oldStep.get(userid) + "#" + "products");
                olddata.replace(userid, olddata.get(userid) + "#" + data);
                for (SendPhoto sendPhoto : p) {
                    photoMes(sendPhoto);
                }

                return;
            }
            if (data.startsWith("plus")) {
                replayM(productService.plusProduct(productCount, userid, chatId, data));
                return;
            }
            if (data.startsWith("minus")) {
                replayM(productService.minusProduct(productCount, userid, chatId, data));
                return;
            }
            if (data.startsWith("back")) {
                sendMes(productService.getProductTypee(userStep, olddata.get(userid).split("#")[0], userid));
                return;

            }
            if (userStep.get(userid).equals("active") && data.equals("active")) {
                sendMes(orderService.retunAdmin(userid));
                EditMessageText sendMessage1 = new EditMessageText();
                sendMessage1.setChatId(userid);
                sendMessage1.setText("barchasi tasdiqlandi");
                sendMessage1.setMessageId(Integer.valueOf(chatId));
                editMes(sendMessage1);
                korzina.remove(userid);
                return;
            }

            if (userStep.get(userid).equals("zakaz") && data.startsWith("ok")) {
                EditMessageReplyMarkup replyMarkup = new EditMessageReplyMarkup();
                String[] split = data.split("/");
                if (!korzina.containsKey(userid)) {
                    korzina.put(userid, split[1] + "/" + split[2]);
                    replyMarkup.setMessageId(Integer.valueOf(chatId));
                    replyMarkup.setChatId(userid);
                    replyMarkup.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Boshqa " + olddata.get(userid).split("#")[0], "back/" + olddata.get(userid).split("#")[0])))));
                    productCount.replace(userid, 0);
                    replayM(replyMarkup);
                    return;
                }
                korzina.replace(userid, korzina.get(userid) + "=" + split[1] + "/" + split[2]);
                replyMarkup.setMessageId(Integer.valueOf(chatId));
                replyMarkup.setChatId(userid);
                replyMarkup.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Boshqa " + olddata.get(userid).split("#")[0], "back/" + olddata.get(userid).split("#")[0])))));
                productCount.replace(userid, 0);
                replayM(replyMarkup);
                productCount.replace(userid, 0);
                return;

            }

            if (userStep.get(userid).equals("active") && data.equals("delete")) {
                korzina.remove(userid);
                editMessageText.setText("*tozalandi ❌*");
                editMessageText.setParseMode("Markdown");
                editMessageText.setChatId(userid);
                editMessageText.setMessageId(Integer.valueOf(chatId));
                editMes(editMessageText);
                return;
            }

            if (userStep.get(userid).equals("istoriya")) {
                editMes(orderService.retunByDate(data, userStep, userid, chatId));
                return;
            }
            if (data.equals("menu")) {
                editMessageText.setText("*Menu bo`limi*");
                editMessageText.setParseMode("Markdown");
                editMessageText.setChatId(userid);
                editMessageText.setMessageId(Integer.valueOf(chatId));
                editMessageText.setReplyMarkup(buttonService.menuButton(buttonService.getAll()));
                userStep.replace(userid, "menu");
                editMes(editMessageText);
                return;
            }
            return;
        }


    }


    public static ReplyKeyboard menuKeyboard() {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
        List<KeyboardRow> keyboardRowList = new ArrayList<>();

        KeyboardRow firstRow = new KeyboardRow();
//        String emojitext = EmojiParser.parseToUnicode("📦 zakazlarim");
        String emojitext1 = EmojiParser.parseToUnicode("📋 zakaz berish");
//        firstRow.add(new KeyboardButton(emojitext));
        firstRow.add(new KeyboardButton(emojitext1));

        KeyboardRow secondRow = new KeyboardRow();
        String emojitext2 = EmojiParser.parseToUnicode("✅ Barchasini tasdiqlash");
        String emojitext3 = EmojiParser.parseToUnicode("📂 Zakazlar tarixi");
        secondRow.add(new KeyboardButton(emojitext2));
        secondRow.add(new KeyboardButton(emojitext3));


        KeyboardRow thirdRow = new KeyboardRow();
        keyboardRowList.add(firstRow);
        keyboardRowList.add(secondRow);
        keyboardRowList.add(thirdRow);
        keyboard.setKeyboard(keyboardRowList);
        return keyboard;
    }


    public void sendMes(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void editMes(EditMessageText sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void photoMes(SendPhoto sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replayM(EditMessageReplyMarkup editMessageReplyMarkup) {
        try {
            execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override

    public String getBotUsername() {
        return "chinorexpress_bot:";
    }

    @Override

    public String getBotToken() {
        return "5043212360:AAFCJKLqcQkPfvFRqOtq3r32ei_tvWmRUfc";
    }


}
