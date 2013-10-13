# Handler called when page is loaded
this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)
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
      mouseEnterHandler = ->
        $("#category-"+categoryId).find(".glyphicon").removeClass("glyphicon-book").addClass("glyphicon-move")
      mouseLeaveHandler = ->
        $("#category-"+categoryId).find(".glyphicon").removeClass("glyphicon-move").addClass("glyphicon-book")
      $categoryObject.find(".icon").on("mouseenter", mouseEnterHandler)
      $categoryObject.find(".icon").on("mouseleave", mouseLeaveHandler)
      $categoryList.append($categoryObject)
    for category in categories
      createObject(category)
    $("#category-list").sortable()

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
        location.reload()
        console.log "success"

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