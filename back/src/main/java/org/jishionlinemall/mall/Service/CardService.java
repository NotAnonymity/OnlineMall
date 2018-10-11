package org.jishionlinemall.mall.Service;

import org.jishionlinemall.mall.DTO.ReportLossDTO;
import org.jishionlinemall.mall.Entity.Card;
import org.springframework.transaction.annotation.Transactional;

public interface CardService {
    @Transactional
    void reportTheLoss(ReportLossDTO reportLossDTO);//将卡片状态标记为"lost"
    Card findUnusedCard();//找到未使用的卡片
    @Transactional
    void bandCardAndVIP(Card card, String openId);//将卡片与VIPId进行绑定,并更新卡片状态
    @Transactional
    void addNewCard(Long vipId);//添加新卡片
    Card findCardByOpenId(String openId);//根据openId查找正在使用中的卡片
}
