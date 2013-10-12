loadMenu = (stallId) ->
  this.stallId = stallId

  displayMenu = (categories) ->
    categoryTemplate = _.template $("#category-template").html()
    $categoryList = $("#category-list")
    for category in categories
      $categoryList.append(categoryTemplate(category))

  $.ajax
    url:      "/public/categories/getAll/" + stallId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data["error"])
        displayMenu data["categories"]


this.pageLoad ->
  if this.javaMode()
    this.java.setMenu(2)
  this.getStallId(loadMenu)

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
        console.log "success"