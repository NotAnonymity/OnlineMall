// pages/loss/losss.js
const app = getApp()
Page({
  guashi: function() {
    var that = this;
    wx.showModal({
      title: '挂失',
      content: '补卡将从您的账户中扣除5元工本费，确认继续？',
      success: function(res) {

        if (res.cancel == false) {
          wx.showLoading();
          wx.request({
            url: app.globalData.url +'/vip/reportLoss',
            method: 'POST',
            data: {
              'openId': that.data.openId,
            },
            success: function(res) {
              if(res.data.newCardStatus=='success'&&
              res.data.lossStatus=='success'){
              wx.hideLoading();
              wx.showToast({
                title: '申请成功',
                duration: 1000,
              })
              setTimeout(function() {
                wx.navigateBack({
                  delta: 1
                })
              }, 1000)
            }
            else{
                wx.hideLoading();
                wx.showToast({
                  title: res.data.errMsg,
                  duration: 1500,
                  icon:'none'
                })
                setTimeout(function () {
                  wx.switchTab({
                    url: '../buy/buy',
                  })
                }, 1500)
            }
            },
            fail: function() {
              wx.hideLoading();
              wx.showToast({
                title: '请检查网络',
                icon: 'none'
              })
            }
          })

        } else {
          wx.navigateBack({})
        }
      },
      fail: function() {
        wx.hideNavigationBarLoading();
        wx.showToast({
          title: '提交失败,请检查您的网络连接',
          icon: 'none'
        })
      }
    })

  },

  /**
   * 页面的初始数据
   */
  data: {
    openId: null
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function(options) {
    var that = this;
    wx.getStorage({
      key: 'openId',
      success: function(res) {
        that.setData({
          openId: res.data
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

      }
    })
  }
})