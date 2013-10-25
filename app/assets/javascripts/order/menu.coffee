# Handler called when page is loaded
this.pageLoad ->
  this.getStall(setStall) # defined in stall/common.coffee

# The callback function of getStallId
setStall = (stall) ->
  this.stall = stall
  this.stallId = stall['stallId']
  if stall['image']
    $('body').css("background-image", "url('/assets/uploads/" + stall['image'] + "')")
  else
    $('body').css("background-image", 'url("/assets/images/order-background-dafault.jpg")')
  $('h1').html stall['name']
  loadData()

this.orderItemNextId = orderItemNextId = 1;
this.dishOrderedList = dishOrderedList = []
this.categories = categories = []

loadData = () ->
  # Ajax get memu
  $.ajax
    url:      "/public/categories/getAll/" + this.stallId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data["error"])
        categories = data["categories"]
        loadMenu()

# Load data into web page
loadMenu = () ->
  categoryTemplate = _.template $("#category-template").html()
  $categoryList = $("#category-list")
  createObject = (category) ->
    categoryId = category["categoryId"]
    $categoryObject = $(categoryTemplate(category))
    $categoryList.append($categoryObject)

  for category in categories
    createObject(category)

this.findDish = findDish = (dishId) ->
  for category in categories
    for dish in category['dishes']
      if dish['dishId'] == dishId
        return dish

this.showDish = (dishId) ->
  $('.show-dish').remove()
  dishTemplate = _.template $("#dish-template").html()
  dish = findDish(dishId)
  $popBox = $("<div class='pop-box'></div>")
  $popBox.html dishTemplate(dish)
  $('body').append($popBox)

this.cancelOrder = () ->
  $(".pop-box").remove()

this.orderDish = (dishId) ->
  orderItem =
    orderItemId:  orderItemNextId
    dishId:       dishId
    quantity:     parseInt($("#dish-" + dishId).find(".quantity").val())
    note:         $("#dish-" + dishId).find(".note").val()
  orderItemNextId++
  dishOrderedList.push(orderItem)
  $(".pop-box").remove()
  this.showDishOrdered()

this.showDishOrdered = () ->
  orderedDishTemplate = _.template $("#ordered-dish-template").html()
  $orderedList = $("#ordered-list")
  $orderedList.html ""
  createOrderedObject = (orderedDish) ->
    $orderedDishObject = $(orderedDishTemplate(orderedDish))
    $orderedList.append($orderedDishObject)

  if dishOrderedList.length == 0
    $("#ordered-list-placeholder").hide()
    $("#button-check-out").hide()
    $orderedList.hide(300)
  else
    $("#ordered-list-placeholder").show()
    $("#button-check-out").delay(300).show(300)
    $orderedList.show(300)

  for orderItem in dishOrderedList
    createOrderedObject(orderItem)

this.deleteOrderItem = (orderItemId) ->
  for orderItem, id in dishOrderedList
    if orderItem['orderItemId'] == orderItemId
      dishOrderedList.splice(id, 1)
      break
  this.showDishOrdered()

getOrderItems = ->
  orderItems = []
  for orderItem in dishOrderedList
    dish = findDish(orderItem['dishId'])
    orderItems.push
      dishId:     orderItem['dishId']
      quantity:   orderItem['quantity']
      note:       orderItem['note']
      price:      dish['finalPrice']
      listPrice:  dish['price']
  return orderItems

getCheckOutSummary = ->
  orderItems = getOrderItems()
  subtotal = 0
  for orderItem in dishOrderedList
    subtotal += dish['finalPrice'] * orderItem['quantity']
  postData =
    accountId:  1
    stallId:  this.stallId
    subtotal: subtotal
    orderItems: JSON.stringify(orderItems)
  return postData

this.showCheckOut = ->
  $("#check-out-dish-tbody").html("")
  orderItems = getOrderItems()
  rowTemplate = _.template $("#check-out-dish-template").html()
  for orderItem in orderItems
    $("#check-out-dish-tbody").append(rowTemplate(orderItem))
  $("#check-out-subtotal").html getCheckOutSummary()['subtotal']
  $("#check-out-summary").show(300)

this.checkOut = ->
  postData = getCheckOutSummary()
  $.ajax
    url:      "/order/orders/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success:  (data) ->
      console.log data
      $("#check-out-success").show();
      refresh = ->
        location.reload()
      setTimeout(refresh, 5000)


this.cancelOrder = ->
  $("#check-out-summary").hide(300)