auth = this.auth
orderList = []

this.pageLoad ->
  if this.javaMode()
    java.setFullScreen(true)
  this.getStallId(setStallId) # defined in stall/common.coffee

# The callback function of getStallId
setStallId = (stallId) ->
  this.stallId = stallId
  getStall(setStall)

getStall = (handler) ->
  $.ajax
    url:      "/public/stalls/getOne/" + this.stallId
    dataType: "json"
    success: (data) ->
      if (!data['error'])
        handler(data['stall'])

# The callback function of getStallId
setStall = (stall) ->
  this.stall = stall
  this.stallId = stall['stallId']
  if stall['image']
    $('body').css("background-image", "url('/assets/uploads/" + stall['image'] + "')")
  else
    $('body').css("background-image", 'url("/assets/images/order-background-dafault.jpg")')
  $('h1').html stall['name']
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
    console.log(order)
    if (order['status'] < 3)
      $("#order-list").append(table(order))
  $('#order-list').find('.content-loader').removeClass('content-loader');
  setTimeout(ajaxLoadData, 5000)