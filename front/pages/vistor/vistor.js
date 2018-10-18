//index.js
//获取应用实例
const app = getApp()

Page({
  policy: function () {
    wx.navigateTo({
      url: '../policy/policy',
    })
  },
  signup:function(){
    wx.navigateTo({
      url: '../signup/signup',
    })
  },
  data: {
    // motto: 'Hello World',
    userInfo: {},
    hasUserInfo: false,
    userAvatarUrl: wx.canIUse('open-data.type.userAvatarUrl'),
    userNickName: wx.canIUse('open-data.type.userNickName'),
  },
  //事件处理函数
  bindViewTap: function() {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    wx.setStorage({
      key: 'isVip',
      data: 'no',
    })
    // wx.getSetting({
    //   success: function (res) {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称
    //       wx.getUserInfo({
    //         success: function (res) {
    //           // console.log(res.userInfo)
    //           wx.setStorageSync('city', res.userInfo.city)
    //           wx.setStorageSync('province', res.userInfo.province)
    //           wx.setStorageSync('gender', res.userInfo.gender)
    //           wx.setStorageSync('nickName', res.userInfo.nickName)
    //         }
    //       })
    //     }

    //   }
    // })
  },
  // getUserInfo: function (e) {
    //打印用户信息
    // wx.setStorageSync('city', e.detail.userInfo.city)
    // console.log(e)
  //   app.globalData.userInfo = e.detail.userInfo
  //   this.setData({
  //     userInfo: e.detail.userInfo,
  //     hasUserInfo: true
  //   })
  // },
  // BUG: function () {
  //   wx.navigateTo({
  //     url: '../new/new',
  //   })
  // },
  guide:function(){
    wx.navigateTo({
      url: '../guide/guide',
    })
  },
  getQQGroup: function () {
    wx.setClipboardData({
      data: '866049796',
      success: function () {
        wx.showToast({
          title: '已复制到剪贴板上',
        })
      }
    })
  },
  feedback: function () {
    wx.navigateTo({
      url: '../feedback/feedback?id=1',
    })
  },
  developer: function (e) {
    wx.navigateTo({
      url: '../developer/developer',
    })
  }
})
