package com.company.optomsavdo.telegramBot.service;

import com.company.optomsavdo.telegramBot.MakeButton;
import com.company.optomsavdo.telegramBot.entity.OrderEntity;
import com.company.optomsavdo.telegramBot.entity.ProductEntity;
import com.company.optomsavdo.telegramBot.entity.UserEntity;
import com.company.optomsavdo.telegramBot.repository.OrderRepository;
import com.company.optomsavdo.telegramBot.repository.ProductRepository;
import com.company.optomsavdo.telegramBot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ButtonService buttonService;

    public boolean create(String order, String userId) {
       if(order!=null){
           String[] splitZakaz = order.split("=");
           for (int i = 0; i < splitZakaz.length; i++) {
               String[] product = splitZakaz[i].split("/");
               OrderEntity order1 = new OrderEntity();
               order1.setDate(String.valueOf(LocalDate.now()));
               ProductEntity product1 = productRepository.getProduct(product[0]);
               order1.setProduct(product1);
               order1.setName(product[0]);
               order1.setStatus("BLOCK");
               order1.setO_number(Integer.parseInt(product[1]));
               Optional<UserEntity> byId = userRepository.findById(Integer.parseInt(userId));
               order1.setUser(byId.get());
               orderRepository.save(order1);

           }
           return true;
       }
       return false;
    }

    public SendMessage getAllzakazlar(String userId) {
        SendMessage sendMessage = new SendMessage();
        Optional<UserEntity> byId = userRepository.findById(Integer.parseInt(userId));
        String a = "";
        String name = "";
        List<Integer> all = orderRepository.getAll(Integer.parseInt(userId));
        for (Integer integer : all) {
            Integer allPro = orderRepository.getAllPro(Integer.parseInt(userId), integer);
            a += "#" + String.valueOf(allPro);
        }

        for (Integer integer : all) {
            String productId = productRepository.getProductId(integer);

            name += "#" + productId;
        }

        String[] count = a.split("#");
        String[] proname = name.split("#");
        String text = byId.get().getA_name() + "\n";
        for (int i = 0; i < proname.length; i++) {
            if(i!=0){
                text += proname[i] + "  " + count[i] + "  yashik\n";
            }


        }
        sendMessage.setChatId(userId);
        sendMessage.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Tasdiqlash", "active")))));
//        sendMessage.setText(text + "\n" +
//                "Barchasi tasdiqlandi");

        sendMessage.setText(text);
        orderRepository.update(Integer.parseInt(userId));
        return sendMessage;


    }

    public SendMessage retunAdmin(String userId) {
        SendMessage sendMessage = new SendMessage();
        Optional<UserEntity> byId = userRepository.findById(Integer.parseInt(userId));
        String a = "";
        String name = "";
        List<Integer> all = orderRepository.getAllAdmin(Integer.parseInt(userId),String.valueOf(LocalDate.now()));
        for (Integer integer : all) {
            Integer allPro = orderRepository.getAllProAdmin(Integer.parseInt(userId), integer,String.valueOf(LocalDate.now()));
            a += "#" + String.valueOf(allPro);
        }

        for (Integer integer : all) {
            String productId = productRepository.getProductId(integer);

            name += "#" + productId;
        }

        String[] count = a.split("#");
        String[] proname = name.split("#");
        String text = byId.get().getA_name() + "\n";
        for (int i = 0; i < proname.length; i++) {
            if(i!=0){
                text += proname[i] + "  " + count[i] + "  yashik\n";
            }


        }
        sendMessage.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Qabul qilish", "tay"+userId)))));
        sendMessage.setChatId("1611125588");
        sendMessage.setText(text);
        return sendMessage;


    }

    public SendMessage update(String userId) {
        SendMessage sendMessage = new SendMessage();

        sendMessage.setText("*Bugungi zakazlar tasdiqlandi*");
        sendMessage.setParseMode("Markdown");
        sendMessage.setChatId(userId);
        return sendMessage;
    }

    public SendMessage groupByDate(String userId, Map<String,String> userStep){
        SendMessage sendMessage = new SendMessage();
        List<String> orders = orderRepository.getByDateOrder(Integer.valueOf(userId));
        InlineKeyboardMarkup inlineKeyboardMarkup = buttonService.menuButton(orders);
        sendMessage.setChatId(userId);
        sendMessage.setText("Bugungacha bo`lgan barcha zakazlar");
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        userStep.replace(userId,"istoriya");
        return sendMessage;

    }




    public EditMessageText retunByDate(String date,Map<String,String> userStep,String userId,String chatId) {
        EditMessageText editMessageText = new EditMessageText();
        String a = "";
        String name = "";
        List<Integer> all = orderRepository.getAllByDate(date,Integer.parseInt(userId));
        for (Integer integer : all) {
            Integer allPro = orderRepository.getAllBydatePro(Integer.parseInt(userId), integer,date);
            a += "#" + String.valueOf(allPro);
        }

        for (Integer integer : all) {
            String productId = productRepository.getProductId(integer);

            name += "#" + productId;
        }

        String[] count = a.split("#");
        String[] proname = name.split("#");
        String text = date + "\n";
        for (int i = 0; i < proname.length; i++) {
            if(i!=0){
                text += proname[i] + "  " + count[i] + "  yashik\n";
            }


        }
        editMessageText.setChatId(userId);
        editMessageText.setText(text);
        editMessageText.setMessageId(Integer.valueOf(chatId));
        editMessageText.setReplyMarkup(MakeButton.readybutton(MakeButton.collection(MakeButton.rows(MakeButton.makebutton1("Bosh menu","menu")))));
        userStep.replace(userId,"  ");
        return editMessageText;
    }

}
