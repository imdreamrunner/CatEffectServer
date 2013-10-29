# Handler called when page is loaded
this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(3)
  this.getStallId(setStallId) # defined in stall/common.coffee

# The callback function of getStallId
setStallId = (stallId) ->
  this.stallId = stallId
  loadMenu()

# Load data into web page
loadMenu = () ->
  displayMenu = (categories) ->
    categoryTemplate = _.template $("#category-template").html()
    $categoryList = $("#category-list")
    createObject = (category) ->
      categoryId = category["categoryId"]
      $categoryObject = $(categoryTemplate(category))
      $categoryObject[0].categoryId = categoryId;
      mouseEnterHandler = ->
        $("#category-"+categoryId).find(".icon .glyphicon").removeClass("glyphicon-book").addClass("glyphicon-move")
      mouseLeaveHandler = ->
        $("#category-"+categoryId).find(".icon .glyphicon").removeClass("glyphicon-move").addClass("glyphicon-book")
      $categoryObject.find(".icon").on("mouseenter", mouseEnterHandler)
      $categoryObject.find(".icon").on("mouseleave", mouseLeaveHandler)
      $categoryObject.find(".dish-list").sortable()
      $categoryList.append($categoryObject)
    for category in categories
      createObject(category)
    that = this
    sortUpdateHandler = ->
      updateList = []
      i = 0
      $divs = $("#category-list > div")
      for $div in $divs
        updateList.push
          categoryId: $div.categoryId
          sort: i
        i++
      console.log updateList
      $.ajax
        url: "/stall/categories/sort"
        data:
          auth_username: that.auth.getUsername()
          auth_password: that.auth.getPassword()
          updateList: JSON.stringify(updateList)
        dataType: "json"
        type: "post"
        success: (data) ->
          console.log(data)
      dishList = []
      i = 0
      $dishs = $(".dish-item")
      for $dish in $dishs
        id = $dish.id.split("-")[1]
        dishList.push
          dishId: parseInt(id)
          sort: i
        i++
      console.log dishList
      $.ajax
        url: "/stall/dishes/sort"
        data:
          auth_username: that.auth.getUsername()
          auth_password: that.auth.getPassword()
          updateList: JSON.stringify(dishList)
        dataType: "json"
        type: "post"
        success: (data) ->
          console.log(data)

    $("#category-list").sortable
      handle: ".icon"
    $("#category-list").bind('sortupdate', sortUpdateHandler)

  # Ajax get menu
  $.ajax
    url:      "/public/categories/getAll/" + this.stallId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data["error"])
        displayMenu data["categories"]

this.addDish = (categoryId) ->
  this.showPopBox("#pop-box-new-dish", {categoryId: categoryId}, 400, 150)

this.doAddDish = (categoryId) ->
  that = this
  postData =
    categoryId:    categoryId
    name:          $(".popbox").find("#inputName").val()
    stallId:       this.stallId
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()
  $.ajax
    url:      "/stall/dishes/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success: (data) ->
      if (!data["error"])
        that.newWindow('/stall/dish#dishId=' + data['newDish']['dishId'], 600, 600)
        location.reload()

this.addCategory =  ->
  this.showPopBox("#pop-box-new-category", {}, 400, 150)

this.doAddCategory = ->
  postData =
    name:          $(".popbox").find("#inputName").val()
    stallId:       this.stallId
    auth_username: this.auth.getUsername()
    auth_password: this.auth.getPassword()

  $.ajax
    url:      "/stall/categories/add"
    type:     "post"
    dataType: "json"
    data:     postData
    success: (data) ->
      if (!data["error"])
        location.reload()