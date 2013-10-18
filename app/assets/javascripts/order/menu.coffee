# Handler called when page is loaded
this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)
  this.getStallId(setStallId) # defined in stall/common.coffee

# The callback function of getStallId
setStallId = (stallId) ->
  this.stallId = stallId
  loadMenu()

this.dishOrderedList = dishOrderedList = []
this.quantityList = quantityList = []
this.categories = categories = []

# Load data into web page
loadMenu = () ->
  displayMenu = (categories) ->
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

  # Ajax get memu
  that = this
  $.ajax
    url:      "/public/categories/getAll/" + this.stallId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data["error"])
        that.categories = data["categories"]
        displayMenu data["categories"]

this.showDishOrdered = (orderedDishList) ->

  orderedDishTemplate = _.template $("#orderedDish-template").html()

  $orderedList = $("#ordered-list")
  $orderedList.html ""
  createOrderedObject = (orderedDish) ->
    $orderedDishObject = $(orderedDishTemplate(orderedDish))

    $orderedList.append($orderedDishObject)

  for orderedDish in orderedDishList
    createOrderedObject(orderedDish)

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