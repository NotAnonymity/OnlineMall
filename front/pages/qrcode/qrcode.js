const app = getApp()
var QR = require('../../utils/wxqrcode.js');


Page({
  
  data:{
    imgdata: null,
    openId: null,
  },
  onLoad: function () {
    wx.showLoading({
      title: '拉取用户信息',
    })
    
  },
  onShow:function(){
    var that = this
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

                  /////////////////////
                  function safe(date, openId) {
                    let temp = date + openId
                    let newStr = "01"
                    for (var i = 0; i < temp.length; i++) {
                      newStr += String.fromCharCode(temp.charCodeAt(i) + 1);
                    }
                    console.log(newStr)
                    return newStr
                  }
                  let date = Date.parse(new Date()).toString();
                  // var openId = wx.getStorageSync('openId')
                  var openId = that.data.openId
                  that.setData({
                    imgdata: QR.createQrCodeImg(safe(date, openId), { size: 300 })
                  })
                  wx.hideLoading()
                  ////////////////



                }
              }
            })
          }
        }
      })

      // wx.login({
      //   success: res => {
      //     // 发送 res.code 到后 台换取 openId, sessionKey, unionId
      //     var code = res.code;
      //     if (code) {
      //       wx.request({
      //         url: 'https://api2.fyscu.xyz/login/getOpenId',
      //         method: 'POST',
      //         data: {
      //           code: code,
      //           appid: 'wx3f6d33cd55e6f064',
      //           secret: '8738987fbfd5da40a69a43c4b9857a4d',
      //         },
      //         success: function (res) {
      //           that.setData({
      //             openId: res.data.openId
      //           })
      //           wx.setStorage({
      //             key: 'openId',
      //             data: res.data.openId,
      //           })
      //           var isVip = res.data.isVip
      //           var isNew = res.data.isNewUser
      //           if (isNew == 'yes') {
      //             setTimeout(function () {
      //               wx.hideLoading()
      //               wx.redirectTo({
      //                 url: '../new/new',
      //               })
      //             }, 1)
      //           } else {
      //             if (isVip == 'no') {
      //               setTimeout(function () {
      //                 wx.hideLoading()
      //                 wx.redirectTo({
      //                   url: '../vistor/vistor',
      //                 })
      //               }, 1)
      //             }

                  /////////////////////
                  // function safe(date, openId) {
                  //   let temp = date + openId
                  //   let newStr = "01"
                  //   for (var i = 0; i < temp.length; i++) {
                  //     newStr += String.fromCharCode(temp.charCodeAt(i) + 1);
                  //   }
                  //   console.log(newStr)
                  //   return newStr
                  // }
                  // let date = Date.parse(new Date()).toString();
                  // // var openId = wx.getStorageSync('openId')
                  // var openId = that.data.openId
                  // that.setData({
                  //   imgdata: QR.createQrCodeImg(safe(date, openId), { size: 300 })
                  // })
                  // wx.hideLoading()
                  ////////////////



      //           }
      //         }
      //       })
      //     }
      //   }
      // })

      // that.setData({
      //   openId: wx.getStorageSync('openId')
      // })

      wx.hideLoading()


    //////////////////////




        // function safe(date, openId) {
        //   let temp = date + openId
        //   let newStr = "01"
        //   for (var i = 0; i < temp.length; i++) {
        //     newStr+=String.fromCharCode(temp.charCodeAt(i)+1);
        //   }
        //   console.log(newStr)
        //   return newStr
        // }
        // var that = this
        // let date = Date.parse(new Date()).toString();
        // var openId = wx.getStorageSync('openId')
        // this.setData({
        //   imgdata: QR.createQrCodeImg(safe(date, openId), { size: 300 })
        // })
        // }
  },
  onPullDownRefresh:function(){


    var that = this
    function safe(date, openId) {
      let temp = date + openId
      let newStr = "01"
      for (var i = 0; i < temp.length; i++) {
        newStr += String.fromCharCode(temp.charCodeAt(i) + 1);
      }
      console.log(newStr)
      return newStr
    }
    let date = Date.parse(new Date()).toString();
    // var openId = wx.getStorageSync('openId')
    var openId = that.data.openId
    that.setData({
      imgdata: QR.createQrCodeImg(safe(date, openId), { size: 300 })
    })
    wx.showToast({
      title: '刷新成功',
    })
    wx.stopPullDownRefresh()
  },


})