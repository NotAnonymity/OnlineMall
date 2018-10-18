// pages/order/order.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    goodsList: [],
    page: 1,
    searchKey: '',
    id: 0,
    isReachBottom: true,
    isEmpty: false
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
    var that = this
    if (that.data.isReachBottom) {
      var page = that.data.page
      wx.showNavigationBarLoading()
      wx.request({
        url: app.globalData.url+"",
        method:"POST",
        data:page,
        success: function (res) {
          wx.hideNavigationBarLoading()
          var list = res.data.ret.goodsList
          if (list.length == 0) {
            wx.showToast({
              title: '已经没有啦',
              icon: 'none'
            })
          }
          else {
            page += 1
            var goodsList = that.data.goodsList
            // for (var x in list) {
            //   list[x].displayLink = encodeURI(list[x].displayLink)
            // }
            // goodsList = goodsList.concat(list)
            that.setData({
              page: page,
              goodsList: goodsList
            })
          }
        },
        fail: function () {
          wx.hideNavigationBarLoading();
          wx.showToast({
            title: '获取失败，请检查您的网络',
            icon: 'none'
          })
        }
      })
    }
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  }
})