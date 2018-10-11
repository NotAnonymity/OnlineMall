// pages/change-info/change-info.js
const app = getApp()
Page({

 
  // getName: function (e) {
  //   var that = this;
  //   that.setData({
  //     name: e.detail.value,
  //   })
  // },
  getId: function (e) {
    var that = this;
    that.setData({
      id: e.detail.value,
    })
  },
  getPhone: function (e) {
    var that = this;
    that.setData({
      phone: e.detail.value,
    })
  },
  getNewPwd: function (e) {
    var that = this;
    that.setData({
      newPwd: e.detail.value,
    })
  },
  /**
   * 页面的初始数据
   */
  data: {
    id:1,
    phone:1,
    // name:1,
    newPwd:1,
    openId:null
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    var that = this
    wx.getStorage({
      key: 'openId',
      success: function (res) {
        that.setData({
          openId: res.data
        })
      },
    })


  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  confirm: function(){
    var that = this
    console.log(that.data.id.length)
    if (that.data.id.length < 13) {
      wx.showToast({
        icon: 'none',
        title: '学号有误'
      })
    } else if (that.data.phone.length < 11) {
      wx.showToast({
        icon: 'none',
        title: '手机号码有误',
      })
    } else {
      wx.showLoading();
      wx.request({

        url: app.globalData.url +'/vip/forgetPassword',
        method: 'POST',
        data: {
          openId: that.data.openId,
          studentId: that.data.id,
          // name: that.data.name,
          newPassword: that.data.newPwd,
          phoneNumber: that.data.phone
        },
        success: function (res) {
          if (res.data.status == 'success') {
            wx.hideLoading();
            wx.showToast({
              title: '修改成功',
              success: function () {
                setTimeout(function () {
                  wx.switchTab({
                    url: '../index/index',
                  })
                }, 1000)
              }
            })
          } else {
            wx.hideLoading();
            wx.showToast({
              icon: 'none',
              title: res.data.errMsg,
              // title:'error'
            })
          }

        },
        fail: function () {
          wx.hideLoading();
          wx.showToast({
            title: '请检查网络',
            icon: 'none'
          })
        }
      })
    }


  }
})