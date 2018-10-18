// pages/signup/signup.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    id: 0,
    name: 0,
    phone: 0,
    pwd1: "0000",
    pwd2: "0000",
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
  getName: function(e) {
    var that = this;
    that.setData({
      name: e.detail.value,
    })
  },
  getId: function(e) {
    var that = this;
    that.setData({
      id: e.detail.value,
    })
  },
  getPhone: function(e) {
    var that = this;
    that.setData({
      phone: e.detail.value,
    })
  },
  getPwdOne: function(e) {
    var that = this;
    that.setData({
      pwd1: e.detail.value,
    })
  },
  getPwdTwo: function(e) {
    var that = this;
    that.setData({
      pwd2: e.detail.value,
    })
  },
  signup: function() {
    var that = this
    if (that.data.pwd1 != that.data.pwd2 || that.data.pwd1.length < 4) {
      wx.showToast({
        icon: 'none',
        title: '密码输入有误',
      })
    } else if (that.data.id.length < 13 && that.data.id.length != 8) {
      wx.showToast({
        icon: 'none',
        title: '学工号有误'
      })
    } else if (that.data.phone.length < 11) {
      wx.showToast({
        icon: 'none',
        title: '手机号码有误',
      })
    } 
    else if (that.data.name.length < 1) {
      wx.showToast({
        icon: 'none',
        title: '姓名有误',
      })
    } else {
      wx.showModal({
        title: '确认注册',
        content: '您的所有注册信息一旦入库无法修改，请谨慎输入',
        showCancel: true,
        cancelText: '重新输入',
        confirmText: '确认无误',
        success: function(res) {
          if (res.cancel == false) {
            wx.showLoading();
            wx.request({
              url: app.globalData.url +'/vip/register',
              method: 'POST',
              data: {
                openId: that.data.openId,
                studentId: that.data.id,
                name: that.data.name,
                password: that.data.pwd2,
                phoneNumber: that.data.phone,
              },
              success: function(res) {
                if (res.data.status == 'success') {
                  wx.hideLoading();
                  wx.showModal({

                    title: '注册成功',
                    content: '您可以在首页点击打开VIP付款码使用自动生成的虚拟电子卡片扫码优惠支付  如有问题请联系：18011108705或18512853321',
                    showCancel: false,
                    confirmText: 'OK', 
                    success:function(){
                     
                      wx.setStorage({
                        key: 'isVip',
                        data: 'yes',
                        success:function(){
                          wx.setStorage({
                            key: 'isNew',
                            data: 'no',
                            success:function(){
                              wx.switchTab({
                                url: '../buy/buy',
                              })
                            }
                          })
                        }
                      })
                      
                    }
                  })
                    
                } else {
                  wx.hideLoading();
                  
                
                  wx.showToast({
                    icon: 'none',
                    title: res.data.errMsg,
                  })
                  setTimeout(function () {
                    if (res.data.errMsg == '注册会员失败，会员卡片不足，请联系管理员增加卡片') {
                      wx.redirectTo({
                        url: '../vistor/vistor',
                      })
                    }
                  }, 2000)
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

          }
        }
      })
    }
  }
})