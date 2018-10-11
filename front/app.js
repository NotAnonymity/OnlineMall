App({
  onLaunch: function () {
    // 登录
    wx.login({
      success: res => {
        // 发送 res.code 到后 台换取 openId, sessionKey, unionId
        var code = res.code;
        if (code) {
          wx.request({
            url: 'http://localhost:8080/login/getOpenId',
            method:'POST',
            data: {

              code: code,
              appid: 'wx3f6d33cd55e6f064',
              secret: 'testSecret',//此处隐藏密码
              // 'time': nowTime
            },
            // 将openid存入本地缓存
            success: function (res) {
              var openId = res.data.openId
              var isVip = res.data.isVip
              var isNew = res.data.isNewUser
              wx.setStorage({
                key: 'openId',
                data: openId,
              })
              // wx.setStorageSync('isNew', isNew)
              // wx.setStorageSync('isVip', isVip)
              if(isNew == 'yes'){
                setTimeout(function () {
                  wx.redirectTo({
                    url: '../new/new',
                  })
                }, 1)
              }else{
                if (isVip == 'no'){
                setTimeout(function () {
                  wx.redirectTo({
                    url: '../vistor/vistor',
                  })
                  }, 1)
                }
              }
            }
          })
        }
      }
    })   
    // 获取用户信息
    // wx.getSetting({
    //   success: res => {
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称，不会弹框
    //       wx.getUserInfo({
    //         success: res => {
    //           // 可以将 res 发送给后台解码出 unionId
    //           this.globalData.userInfo = res.userInfo

    //           // 由于 getUserInfo 是网络请求，可能会在 Page.onLoad 之后才返回
    //           // 所以此处加入 callback 以防止这种情况
    //           if (this.userInfoReadyCallback) {
    //             this.userInfoReadyCallback(res)
    //           }
    //         }
    //       })
    //     }
    //   }
    // })

  },
  globalData: {
    userInfo: null,
    url: "http://localhost:8080/"
  },

})