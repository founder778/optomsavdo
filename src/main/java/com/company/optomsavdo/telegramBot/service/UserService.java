package com.company.optomsavdo.telegramBot.service;

import com.company.optomsavdo.telegramBot.entity.UserEntity;
import com.company.optomsavdo.telegramBot.enums.UserStatus;
import com.company.optomsavdo.telegramBot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Map;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    public void creatUser(String userId, Map<String,String> newUser){
        String[] userSplit = newUser.get(userId).split("#");
        UserEntity userEntity = new UserEntity();
        userEntity.setA_id(Integer.parseInt(userId));
        userEntity.setA_name(userSplit[0]);
        userEntity.setA_surname(userSplit[1]);
        userEntity.setA_phone(userSplit[2]);
        userEntity.setA_login(userSplit[3]);
        userEntity.setA_password(userSplit[4]);
        userEntity.setStatus(UserStatus.REGISTR.name());
        userRepository.save(userEntity);

    }
    public String isExistUser(String userid){
        Optional<UserEntity> user = userRepository.findById(Integer.parseInt(userid));

        if(user.isPresent() && !user.get().getStatus().equals(UserStatus.ACTIVE.name()))
        return "Ish boshlashga ruxsat etilmadi";
        else if(user.isPresent() && user.get().getStatus().equals(UserStatus.ACTIVE.name()))
            return "ok";
        return null;
    }
    public boolean check(String userId){
        UserEntity userEntity = userRepository.get(Integer.parseInt(userId));
        if(userEntity!=null){
            return false;
        }
        return true;
    }

    public SendMessage update(String userId){
        SendMessage sendMessage =new SendMessage();
        Optional<UserEntity> user= userRepository.findById(Integer.parseInt(userId));
        user.get().setStatus(UserStatus.ACTIVE.name());
        userRepository.save(user.get());
        sendMessage.setChatId("1611125588");
        sendMessage.setText("*Tasdiqlandi*");
        sendMessage.setParseMode("Markdown");
        return sendMessage;

    }
}
