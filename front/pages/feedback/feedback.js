// pages/feedback/feedback.js
const app = getApp()
Page({

      /**
       * 页面的初始数据
       */
      data: {
        id: 0,
        className: '',
        content: '',
        len: 0,
        maxlen: 140,
        openId: '',
      },

      /**
       * 生命周期函数--监听页面加载
       */
      onLoad: function(options) {
        var that = this;

        var id = options.id;
        that.setData({
          id: id
        })
        if (id == 0) {
          wx.setNavigationBarTitle({
            title: 'BUG反馈',
          })
          that.setData({
            className: 'BUG反馈'
          })
        } else if (id == 1) {
          wx.setNavigationBarTitle({
            title: '问题反馈',
          })
          that.setData({
            className: '功能建议'
          })
        }

        wx.getStorage({
          key: 'openId',
          success: function(res) {
            that.setData({
              openId: res.data
            })


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

      input: function(e) {
        var that = this;
        var value = e.detail.value;
        var len = parseInt(value.length);
        that.setData({
          content: value,
          len: len
        })
      },

      submit: function() {
        wx.showLoading();
        var that = this;
        var content = that.data.content;
        var className;
        if (content != '') {
          wx.request({
            url: app.globalData.url +'/user/feedback',
              method: 'POST',
              data: {
                'description': content,
                'openId': that.data.openId,
              },
              success: function(res) {
                wx.hideLoading();
                if (res.data.status == 'success') {
                  wx.showToast({
                    title: '提交成功',
                    duration: 1000,
                  })
                  setTimeout(function() {
                    wx.navigateBack({
                      delta: 1
                    })
                  }, 1000)
                } else {
                  wx.hideLoading();
                  wx.showToast({
                    title: res.data.errMsg,
                    duration: 1000,
                    icon: 'none'
                  })
                }
              },
              fail: function () {
                wx.hideLoading();
                wx.showToast({
                  title: '提交失败,请检查您的网络连接',
                  icon: 'none'
                })
              }
            })
          }else {
            wx.showToast({
              title: '内容不可为空!',
              icon: 'none'
            })
          }

        }
      })