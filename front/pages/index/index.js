
const app = getApp()
Page({

  data: {
    userInfo: {},
    hasUserInfo: false,
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    openId: null,
    level: '加载中',
    credit: "加载中",
    id: '尚未补办',
    name: '加载中',
    show: true
  },
  onLoad: function () {
    wx.showLoading({
      title: '拉取用户信息',
    })
  },
  onShow: function () {
    var that = this
    // console.log("ONSHOW")  
    if (!wx.getStorageSync('openId')) {
      wx.login({
        success: res => {
          // 发送 res.code 到后 台换取 openId, sessionKey, unionId
          var code = res.code;
          if (code) {
            wx.request({
              url: 'https://api2.fyscu.xyz/login/getOpenId',
              method: 'POST',
              data: {
                code: code,
                appid: 'wx3f6d33cd55e6f064',
                secret: '8738987fbfd5da40a69a43c4b9857a4d',
              },
              success: function (res) {
                that.setData({
                  openId: res.data.openId
                })
                wx.setStorage({
                  key: 'openId',
                  data: res.data.openId,
                })
                var isVip = res.data.isVip
                var isNew = res.data.isNewUser
                if (isNew == 'yes') {
                  setTimeout(function () {
                    wx.hideLoading()
                    wx.redirectTo({
                      url: '../new/new',
                    })
                  }, 1)
                } else {
                  if (isVip == 'no') {
                    setTimeout(function () {
                      wx.hideLoading()
                      wx.redirectTo({
                        url: '../vistor/vistor',
                      })
                    }, 1)
                  }
                  wx.setStorageSync("isVip", "true")
                  wx.request({
                    url: app.globalData.url + '/vip/getInfo',
                    method: 'POST',
                    data: {
                      'openId': that.data.openId
                      //  openId: app.globalData.openId
                    },
                    success: function (res) {
                      that.setData({
                        level: (((res.data.expenditure + 0) / 300) + 1),
                        credit: (((res.data.expenditure + 0) / 300) + 1),
                        
                        id: res.data.cardNumber,
                        name: res.data.name
                      })
                      wx.hideLoading()
                    }
                  })
                }
              }
            })
          }
        }
      })
    } else {
      that.setData({
        openId: wx.getStorageSync('openId')
      })
      wx.request({
        url: app.globalData.url + '/vip/getInfo',
        method: 'POST',
        data: {
          'openId': that.data.openId
          //  openId: app.globalData.openId
        },
        success: function (res) {
          that.setData({
            credit: res.data.expenditure + 0,
            level: (((res.data.expenditure + 0) / 300) + 1).toFixed(0),
            id: res.data.cardNumber,
            name: res.data.name
          })
          wx.hideLoading()
        }
      })
    }
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
  changepwd: function () {
    wx.navigateTo({
      url: '../changepwd/changepwd',
    })
  },
  loss: function (e) {

    wx.navigateTo({
      url: '../loss/loss',
    })
  },
  developer: function (e) {
    wx.navigateTo({
      url: '../developer/developer',
    })
  },
  policy: function () {
    wx.navigateTo({
      url: '../policy/policy',
    })
  },
  qrcode:function(){
    wx.switchTab({
      url: '../qrcode/qrcode',
    })
  }
})