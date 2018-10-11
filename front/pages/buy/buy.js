const app =getApp()
Page({
  getPay: function(e) {
    var that = this;
    that.setData({
      pay: e.detail.value
    })
  },
  /**
   * 页面的初始数据
   */
  data: {
    input:'',
    balance: 0.00,
    pay: null,
    timeStamp: '',
    nonceStr: '',
    dataPackage: '',
    signType: 'MD5',
    paySign: '',
    openId: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this
    wx.getStorage({
      key: 'openId',
      success: function(res) {
        that.setData({
          openId: res.data
        })
        // wx.request({
        //   url: 'https://api.fyscu.xyz/vip/getInfo',
        //   method: 'POST',
        //   data: {
        //     'openId': that.data.openId
        //   },
        //   success: function(res) {
        //     that.setData({
        //       balance: res.data.balance
        //     })

        //   },
        // })

      },
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function() {
    var that = this
    wx.getStorage({
      key: 'openId',
      success: function (res) {
        that.setData({
          openId: res.data
        })
        wx.request({
          url: app.globalData.url +'/vip/getInfo',
          method: 'POST',
          data: {
            'openId': that.data.openId
          },
          success: function (res) {
            that.setData({
              balance: res.data.balance
            })

          },
        })

      },
    })

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function() {

  },
  confrim: function() {
    var that = this
    wx.request({
      url: app.globalData.url +'/vip/rechargeExpense',
      method: 'POST',
      data: {
        openId: that.data.openId,
        fee: that.data.pay
      },
      success: function(res) {
        if (res.data.status == 'success') {
          that.setData({
            timeStamp: res.data.timeStamp,
            nonceStr: res.data.nonceStr,
            dataPackage: res.data.dataPackage,
            paySign: res.data.paySign,
          })

          wx.requestPayment({
            'timeStamp': that.data.timeStamp,
            'nonceStr': that.data.nonceStr,
            'package': that.data.dataPackage,
            'signType': 'MD5',
            'paySign': that.data.paySign,
            'success': function (res) {
              wx.showToast({
                title: '支付成功',
                icon: 'success',
                duration: 1500,
                mask: true,
                success: function (res) {
                  that.setData({
                    input:''
                  })
                  wx.request({
                    url: app.globalData.url +'/vip/getInfo',
                    method: 'POST',
                    data: {
                      'openId': that.data.openId
                    },
                    success: function (res) {
                      that.setData({
                        balance: res.data.balance
                      })

                    },
                  })
                },

              })
            },
            'fail': function (res) {
              wx.showToast({
                title: '支付失败',
                icon: "none"
              })
            }

          })
        } else {
          wx.showToast({
            title: res.data.errMsg,
            icon: 'none',
            mask: true,
          })
        }
      },
      fail: function() {
        wx.showToast({
          title: '支付失败,请检查连接后重试!',
          icon: 'none',
          mask: true,
        })
      }
    })
    //充值支付逻辑
  }
})