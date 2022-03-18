package com.company.optomsavdo.telegramBot.controller;

import com.company.optomsavdo.telegramBot.Start;
import com.company.optomsavdo.telegramBot.entity.UserEntity;
import com.company.optomsavdo.telegramBot.repository.UserRepository;
import com.company.optomsavdo.telegramBot.service.ButtonService;
import com.company.optomsavdo.telegramBot.service.UserService;
import com.company.optomsavdo.telegramBot.state.UserState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;

@Controller
public class UserController {
    SendMessage sendMessage = new SendMessage();
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ButtonService buttonService;

    public SendMessage createUser(String userId, String message, Map<String, String> userStep, Map<String, String> newuser) {
        String step = userStep.get(userId);
        if (message.length() < 3) {
            sendMessage.setChatId(userId);
            sendMessage.setText("*kiritgan malumotingiz xato eng kamida 4 ta xarf bo`lishi shart*");
            sendMessage.setParseMode("Markdown");

            return sendMessage;
        }
        switch (step) {
            case "name": {
                userStep.replace(userId, "surname");
                sendMessage.setText("*Familyangizni kiriting : *");
                sendMessage.setParseMode("Markdown");

                sendMessage.setChatId(userId);
                newuser.put(userId, message);
                return sendMessage;
            }
            case "surname": {
                userStep.replace(userId, "phone");
                sendMessage.setText("*Tel raqamingizni to`liq  kiriting to`liq : *");
                sendMessage.setParseMode("Markdown");

                sendMessage.setChatId(userId);
                newuser.replace(userId, newuser.get(userId) + "#" + message);
                return sendMessage;
            }
            case "phone": {
                if (message.length() != 13 && !message.startsWith("+998")) {
                    sendMessage.setText("*Raqamlar xato to`liq kiritishigizni so`raymiz*");
                    sendMessage.setParseMode("Markdown");

                    sendMessage.setChatId(userId);
                    return sendMessage;
                }
                userStep.replace(userId, "Login");
                sendMessage.setText("*Login  kiriting : *");
                sendMessage.setParseMode("Markdown");

                sendMessage.setChatId(userId);
                newuser.replace(userId, newuser.get(userId) + "#" + message);
                return sendMessage;
            }
            case "Login": {
                sendMessage.setText("*parolingizni  kiriting : *");
                sendMessage.setParseMode("Markdown");


                userStep.replace(userId, "Password");
                sendMessage.setChatId(userId);
                newuser.replace(userId, newuser.get(userId) + "#" + message);
                return sendMessage;
            }
            case "Password": {
                UserEntity userByPas = userRepository.getUserByPas(message);
                if(userByPas!=null){
                    sendMessage.setText("*Bu parol band boshqa parol kiriting*");
                    sendMessage.setParseMode("Markdown");

                    sendMessage.setChatId(userId);
                    return sendMessage;
                }
                userStep.replace(userId, "/start");
                sendMessage.setText("*Siz ro`yhatga olindingiz rahbar javobini kuting!!! *");
                sendMessage.setParseMode("Markdown");

                sendMessage.setChatId(userId);
                newuser.replace(userId, newuser.get(userId) + "#" + message);
                userService.creatUser(userId, newuser);
                return sendMessage;
            }


        }
        return null;
    }

    public SendMessage isExistUser(String message, String userId, Map<String, String> userStep, Map<String, String> signUp, Map<String, UserState> userState) {
        switch (userStep.get(userId)) {

            case "login": {
                userStep.replace(userId, "parol");
                sendMessage.setText("*parolni  kiriting : *");
                sendMessage.setParseMode("Markdown");

                sendMessage.setChatId(userId);
                signUp.put(userId, message);
                return sendMessage;
            }
            case "parol": {
                if (userRepository.getUser(signUp.get(userId), message) == null) {
                    sendMessage.setText("*parol yoki login xato qayta urining\n" +
                            "Loginni kiriting*");
                    sendMessage.setChatId(userId);
                    sendMessage.setParseMode("Markdown");

                    userStep.replace(userId,"login");
                    return sendMessage;
                }
                String existUser = userService.isExistUser(userId);
                if(!existUser.equals("ok")){
                    sendMessage.setText(existUser);
                    sendMessage.setChatId(userId);
                    return sendMessage;
                }
                sendMessage.setText("*xush kelibsiz*");
                sendMessage.setParseMode("Markdown");
                sendMessage.setReplyMarkup(Start.menuKeyboard());
                userState.replace(userId,UserState.ready);
                userStep.replace(userId,"first");
                sendMessage.setChatId(userId);
                return sendMessage;

            }

        }
        return null;
    }
}
