auth = this.auth
orderList = []

# Handler called when page is loaded
this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)
  this.getStallId(setStallId) # defined in stall/common.coffee

# The callback function of getStallId
setStallId = (stallId) ->
  this.stallId = stallId
  ajaxLoadData()

table = _.template $("#order-template").html()

ajaxLoadData = ->
  $.ajax
    url:        "/stall/orders/getAll"
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
      stallId      : this.stallId
    success:    (data) ->
      if (!data['error'])
        orderList = data['orders']
        loadOrder()
    error:      () ->
      console.log "error"

this.loadOrder = loadOrder = () ->
  console.log(orderList)
  $("#order-list").html ""
  for order in orderList
    console.log("order: ")
    console.log(order)
    $("#order-list").append(table(order))
  $('#order-list').find('.content-loader').removeClass('content-loader');



###
this.doAddPrepaidCard = ->
  that = this
  postData =
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/system/transaction/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success:  (data) ->
      console.log data['message']
      ajaxLoadData()

this.deletePrepaidCard = (prepaidCardId)->
  PrepaidCard = getPrepaidCard(prepaidCardId)
  this.showPopBox("#pop-up-confirm-delete", PrepaidCard, 400, 180)

getPrepaidCard = (prepaidCardId) ->
  for PrepaidCard in prepaidCardList
    if PrepaidCard['prepaidCardId'] == prepaidCardId
      return PrepaidCard

this.confirmDeletePrepaidCard = (prepaidCardId) ->
  that = this
  $.ajax
    url:      "/system/prepaidCards/delete/" + prepaidCardId
    type:     "post"
    dataType: "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
    success: (data) ->
      console.log data
      if (!data['error'])
        ajaxLoadData()
        that.closePopBox()
    error: () ->
      console.log "error!"
###