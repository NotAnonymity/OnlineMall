package org.jishionlinemall.mall.Service.serviceImpl;

import org.jishionlinemall.mall.DTO.ReportLossDTO;
import org.jishionlinemall.mall.Entity.*;
import org.jishionlinemall.mall.Service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CardServiceImpl implements CardService {
    private UserRepository userRepository;
    private VIPRepository vipRepository;
    private CardRepository cardRepository;

    @Autowired
    public void setRepository(UserRepository userRepository, VIPRepository vipRepository, CardRepository cardRepository){
        this.userRepository = userRepository;
        this.vipRepository = vipRepository;
        this.cardRepository = cardRepository;
    }

    @Override
    public void reportTheLoss(ReportLossDTO reportLossDTO){
        VIP vip = vipRepository.findByUserId(userRepository.findByOpenId(reportLossDTO.getOpenId()).getId());
        //查找正在使用中的卡片,防止用户之前进行过挂失操作
        List<Card> cardList = cardRepository.findALLByVipId(vip.getId());
        for (Card card:cardList) {
            card.setStatus("lost");
            cardRepository.saveAndFlush(card);
        }
    }

    @Override
    public Card findUnusedCard(){
        return cardRepository.findFirst1ByStatusOrderByIdAsc("unused");
    }

    @Override
    public void bandCardAndVIP(Card card, String openId){
        card.setVipId(vipRepository.findByUserId(userRepository.findByOpenId(openId).getId()).getId());
        card.setStatus("using");
        cardRepository.saveAndFlush(card);
    }

    @Override
    public Card findCardByOpenId(String openId){
        return cardRepository.findByVipIdAndStatus(vipRepository.findByUserId(userRepository.findByOpenId(openId).getId()).getId(), "using");
    }

    @Override
    public void addNewCard(Long vipId){
        Card card = new Card();
        card.setVipId(vipId);
        card.setStatus("using");
        card.setRfid(generateCardRfid(vipId));
        card.setNumber(generateCardNumber(vipId));
        cardRepository.saveAndFlush(card);
    }

    private String generateCardNumber(Long vipId){
        SimpleDateFormat time_format = new SimpleDateFormat("yyyymmss");
        return String.format("%s%s", time_format.format(new Date()), vipId.toString());
    }


    //因为根据需求，不再需要实体RFID卡片，因此生成以rfid开头的rfid号码
    private String generateCardRfid(Long vipId){
        return "rfid"+vipId.toString();
    }
}
