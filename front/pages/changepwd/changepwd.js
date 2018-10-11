// pages/change-pwd/change-pwd.js
const app = getApp()
Page({
  getId: function (e) {
    var that = this;
    that.setData({
      id: e.detail.value,
    })
  },
  getOldPwd: function (e) {
    var that = this;
    that.setData({
      oldPwd: e.detail.value,
    })
  },
  getNewPwd: function (e) {
    var that = this;
    that.setData({
      newPwd: e.detail.value,
    })
  },
  
  forget:function(){
    wx.navigateTo({
      url: '../changeinfo/changeinfo',
    })
  },
  setpwd:function(){
    var that = this
    wx.getStorage({
      key: 'openId',
      success: function(res) {
        that.setData({
          openId: res.data
        })
        wx.showLoading();
        wx.request({
          url: app.globalData.url +'/vip/changePassword',
          method: 'POST',
          data: {
            openId: that.data.openId,
            studentId: that.data.id,
            oldPassword: that.data.oldPwd,
            newPassword: that.data.newPwd
          },
          success: function (res) {
            wx.hideLoading();
            if(res.data.status == 'success'){
              wx.showToast({
                title: '修改成功',
                icon: 'success',
                duration: 1000,
                success:function(){
                  setTimeout(function () {
                    wx.navigateBack({
                      delta: 1
                    })
                  }, 1000)
                }
              })
              
            } else {
              wx.hideLoading();
              wx.showToast({
                icon:'none',
                title: res.data.errMsg,
              })
            }

          },
          fail: function (res) {
            wx.hideLoading();
            wx.showToast({
              icon: 'none',
              mask: 'true',
              title: '请检查您的网络!',
            })
          }
        })
      },
    })

    
  },

  /**
   * 页面的初始数据
   */
  data: {
    id: 0,
    oldPwd: 0,
    newPwd: 0,
    openId: null
  
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
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
  
  }
})