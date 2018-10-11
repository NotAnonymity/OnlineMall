//index.js
//获取应用实例
const app = getApp()
Page({
  policy:function(){
    wx.navigateTo({
      url: '../policy/policy',
    })
  },
  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    openId:null,
    level:null,
    credit:100,
    id:'尚未补办',
    name:null
  },
  onLoad: function () {
    var that = this 
    var openId = wx.getStorageSync('openId')
      that.setData({
        openId: openId
      })
  },
  onShow:function(){
    var that = this
    wx.request({
      url:app.globalData.url+'/vip/getInfo',
      method: 'POST',
      data: {
        'openId': that.data.openId
      },
      success: function (res) {
        that.setData({
          credit: res.data.expenditure + 0,
          level: (((res.data.expenditure + 0) / 300) + 1).toFixed(0),
          id: res.data.cardNumber,
          name: res.data.name
        })
      }
    })
  },

  onReady: function () {
    // var that = this

  },

  test: function () {
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
  advice: function () {
    wx.navigateTo({
      url: '../feedback/feedback?id=1',
    })
  },
  changeinfo: function () {
    wx.navigateTo({
      url: '../changeinfo/changeinfo',
    })
  },
  changepwd:function(){
    wx.navigateTo({
      url: '../changepwd/changepwd',
    })
  },
  loss:function(e){

          wx.navigateTo({
            url: '../loss/loss',
          })
    }
})






