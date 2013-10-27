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

this.toReady = toReady = (orderId) ->
  $.ajax
    url:        "/stall/orders/editStatus"
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
      orderId      : orderId
      newStatus    : '2'
    success:    (data) ->
      if (!data['error'])
        console.log "ready"
        doneToReady(orderId)
    error:      () ->
      console.log "error"

this.toServed = toServed = (orderId) ->
  $.ajax
    url:        "/stall/orders/editStatus"
    type:       "post"
    dataType:   "json"
    data:
      auth_username: this.auth.getUsername()
      auth_password: this.auth.getPassword()
      orderId      : orderId
      newStatus    : '3'
    success:    (data) ->
      console.log "served"
    error:      () ->
      console.log "error"

this.doneToReady = doneToReady = (orderId) ->
  $("#current-status-row").html "Current Status: Ready"


