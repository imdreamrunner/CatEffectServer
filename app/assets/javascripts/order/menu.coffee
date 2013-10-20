# Handler called when page is loaded
this.pageLoad ->
  this.getStall(setStall) # defined in stall/common.coffee

# The callback function of getStallId
setStall = (stall) ->
  this.stall = stall
  this.stallId = stall['stallId']
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

    mouseEnterHandler = ->
      $("#category-"+categoryId).find(".glyphicon").removeClass("glyphicon-book").addClass("glyphicon-move")

    mouseLeaveHandler = ->
      $("#category-"+categoryId).find(".glyphicon").removeClass("glyphicon-move").addClass("glyphicon-book")

    $categoryObject.find(".icon").on("mouseenter", mouseEnterHandler)
    $categoryObject.find(".icon").on("mouseleave", mouseLeaveHandler)
    $categoryList.append($categoryObject)

  for category in categories
    createObject(category)

  # event listener to onclick of order
  """
  that = this
  $('a.dish').click (e) ->
    e.preventDefault()
    newDishOrderedId = parseInt(e.delegateTarget.id.split('-')[1],0)
    for category in categories
      for dish in category.dishes
        if dish.dishId == newDishOrderedId
          newDishOrdered = dish

    contained = false
    for orderedDish in dishOrderedList
      if (orderedDish.dishId == newDishOrdered.dishId)
        contained = true
        orderedDish.quantity = orderedDish.quantity + 1
    if (!contained)
      newDishOrdered.quantity = 1
      dishOrderedList.push(newDishOrdered)
    that.showDishOrdered(dishOrderedList)
  """

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
  ###
  newDishOrderedId = dishId
  for category in categories
    for dish in category.dishes
      if dish.dishId == newDishOrderedId
        newDishOrdered = dish
  contained = false
  for orderedDish in dishOrderedList
    if (orderedDish.dishId == newDishOrdered.dishId)
      contained = true
      orderedDish.quantity = orderedDish.quantity + 1
  if (!contained)
    newDishOrdered.quantity = 1
    dishOrderedList.push(newDishOrdered)
  ###
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
    $orderedList.hide(300)
  else
    $orderedList.show(300)

  for orderItem in dishOrderedList
    createOrderedObject(orderItem)

this.deleteOrderItem = (orderItemId) ->
  for orderItem, id in dishOrderedList
    if orderItem['orderItemId'] == orderItemId
      dishOrderedList.splice(id, 1)
      break
  this.showDishOrdered()


###
this.deleteAllDish = (target) ->
  for orderedDish,id in dishOrderedList
    if (orderedDish.dishId == target)
      dishOrderedList.splice(id,1)
      break
  this.showDishOrdered(dishOrderedList)

this.deleteOneDish = (target) ->
  for orderedDish,id in dishOrderedList
    if (orderedDish.dishId == target)
      orderedDish.quantity = orderedDish.quantity - 1
      if (orderedDish.quantity == 0)
        deleteAllDish(target)
      break
  this.showDishOrdered(dishOrderedList)


###