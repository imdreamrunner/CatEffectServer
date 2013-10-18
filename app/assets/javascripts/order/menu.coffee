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

    # event listener to onclick of orfer
    that = this
    $('a.dish').click (e) ->
      e.preventDefault()
      that.newDishOrderedId = parseInt(e.delegateTarget.id.split('-')[1],0)
      console.log "newId = " +that.newDishOrderedId
      that.newDishOrdered

      # find the dish object by the id we now have in newDishOrdered
      #我知道为什么有bug了：因为ajax貌似是在最后才运行？
      $.ajax
        url:      "/public/dishes/getOne/" + that.newDishOrderedId
        type:     "get"
        dataType: "json"
        success:  (data) ->
          if (!data["error"])
            that.newDishOrdered = data["dish"]
            console.log "inside ajax"
            console.log that.newDishOrdered

      console.log "after ajax"
      console.log that.newDishOrdered

      contained = false
      for orderedDish,id in dishOrderedList
        if (orderedDish.dishId == that.newDishOrdered.dishId)
          contained = true
          quantityList[id] = quantityList[id]+1
      if (!contained)
        dishOrderedList.push(that.newDishOrdered)
        quantityList.push(1)
        console.log dishOrderedList[1]
        console.log "quantityList = " + quantityList
      that.showDishOrdered(dishOrderedList)

  # Ajax get memu
  $.ajax
    url:      "/public/categories/getAll/" + this.stallId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data["error"])
        displayMenu data["categories"]

this.showDishOrdered = (orderedDishList) ->
  orderedDishTemplate = _.template $("#orderedDish-template").html()

  $orderedList = $("#ordered-list")

  createOrderedObject = (orderedDish) ->
    #DishId = OrderedDish["dishId"]
    $orderedDishObject = $(orderedDishTemplate(orderedDish))

    $orderedList.append($orderedDishObject)

  for orderedDish in orderedDishList
    createOrderedObject(orderedDish)

  #$('#ordered').html ""
  #for newDishOrdered,id in dishOrderedList
  #  $('#ordered').append(newDishOrdered," * ",quantityList[id],
  #      "<button type='button' onclick='deleteAllDish("+newDishOrdered+")'>Delete All</button> ",
  #      "<button type='button' onclick='deleteOneDish("+newDishOrdered+")'>Delete One</button> ")


this.deleteAllDish = (target) ->
  for orderedDish,id in dishOrderedList
    #console.log orderedDish
    if (orderedDish.dishId == target)
      dishOrderedList.splice(id,1)
      break
  this.showDishOrdered(dishOrderedList)

this.deleteOneDish = (target) ->
  for orderedDish,id in dishOrderedList
    if (orderedDish.dishId == target)
      quantityList[id] = quantityList[id] - 1
      if (quantityList[id] == 0)
        deleteAllDish(target)
      break
  this.showDishOrdered(dishOrderedList)