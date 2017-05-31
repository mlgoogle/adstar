package com.yundian.star.networkapi.socketapi;

import com.yundian.star.app.SocketAPIConstant;
import com.yundian.star.base.SearchReturnbeen;
import com.yundian.star.been.AdvBeen;
import com.yundian.star.been.FansHotBuyReturnBeen;
import com.yundian.star.been.MarketTypeBeen;
import com.yundian.star.been.OptionsStarListBeen;
import com.yundian.star.been.StarBuyActReferralInfo;
import com.yundian.star.been.StarExperienceBeen;
import com.yundian.star.been.StarMailListBeen;
import com.yundian.star.been.StarStarAchBeen;
import com.yundian.star.listener.OnAPIListener;
import com.yundian.star.networkapi.InformationAPI;
import com.yundian.star.networkapi.socketapi.SocketReqeust.SocketDataPacket;
import com.yundian.star.ui.main.model.NewsInforModel;

import java.util.HashMap;

/**
 * Created by ysl.
 */

public class SocketInformationAPI extends SocketBaseAPI implements InformationAPI {

    @Override
    public void newsinfo(String name, String code, int startnum, int endnum, int all, OnAPIListener<NewsInforModel> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("code", code);
        map.put("startnum", startnum);
        map.put("endnum", endnum);
        map.put("all", all);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.NewInfo,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestEntity(socketDataPacket,NewsInforModel.class,listener);
    }

    @Override
    public void advInfo(String code, int all, OnAPIListener<AdvBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("all", all);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Banner,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestEntity(socketDataPacket,AdvBeen.class,listener);
    }

    @Override
    public void searchStar(String code, OnAPIListener<SearchReturnbeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.SearchStar,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,SearchReturnbeen.class,listener);
    }

    @Override
    public void getOptionsStarList(String phone, int startnum, int endnum,int sorttype, OnAPIListener<OptionsStarListBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("startnum", startnum);
        map.put("endnum", endnum);
        map.put("sorttype", sorttype);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.OptionStarList,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,OptionsStarListBeen.class,listener);
    }

    @Override
    public void getMarketKype(String phone, OnAPIListener<MarketTypeBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.MarketType,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,MarketTypeBeen.class,listener);
    }

    @Override
    public void getMarketstar(int type, int startnum, int endnum,int sorttype, OnAPIListener<OptionsStarListBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", type);
        map.put("startnum", startnum);
        map.put("endnum", endnum);
        map.put("sorttype", sorttype);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.MarketStar,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,OptionsStarListBeen.class,listener);
    }

    @Override
    public void getStarBrief(String code, OnAPIListener<StarBuyActReferralInfo> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.StarBrief,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,StarBuyActReferralInfo.class,listener);
    }

    @Override
    public void getStarExperience(String code, OnAPIListener<StarExperienceBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.StarExperience,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,StarExperienceBeen.class,listener);
    }
    @Override
    public void getStarachive(String code, OnAPIListener<StarStarAchBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Starachive,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,StarStarAchBeen.class,listener);
    }

    @Override
    public void getSeekList(String code, int startnum, int endnum, OnAPIListener<FansHotBuyReturnBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("starcode", code);
        map.put("startnum", startnum);
        map.put("endnum", endnum);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.SeekLike,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,FansHotBuyReturnBeen.class,listener);
    }

    @Override
    public void getTransferList(String code, int startnum, int endnum, OnAPIListener<FansHotBuyReturnBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("starcode", code);
        map.put("startnum", startnum);
        map.put("endnum", endnum);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.TransferList,
                SocketAPIConstant.ReqeutType.SearchStar, map);
        requestEntity(socketDataPacket,FansHotBuyReturnBeen.class,listener);
    }

    @Override
    public void getFansComments(String phone,String starcode, OnAPIListener<Object> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phone);
        map.put("starcode", starcode);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.FansComments,
                SocketAPIConstant.ReqeutType.NewInfos, map);
        requestJsonObject(socketDataPacket,listener);
    }

    @Override
    public void getStarmaillist(long id, String token,String status, int startPos, int count, OnAPIListener<StarMailListBeen> listener) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("token", token);
        map.put("startPos", startPos);
        map.put("status", status);
        map.put("count", count);
        SocketDataPacket socketDataPacket = socketDataPacket(SocketAPIConstant.OperateCode.Starmaillist,
                SocketAPIConstant.ReqeutType.History, map);
        requestEntity(socketDataPacket,StarMailListBeen.class,listener);
    }


}