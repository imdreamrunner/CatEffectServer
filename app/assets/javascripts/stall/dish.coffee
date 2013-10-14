# Handler called when page is loaded
this.pageLoad ->
  this.getStallId(setStallId) # defined in stall/common.coffee

# The callback function of getStallId
setStallId = (stallId) ->
  this.stallId = stallId
  displayDish()

loadDish = (handler) ->
  this.dishId = dishId = this.params["dishId"]
  $.ajax
    url:      "/public/dishes/getOne/" + dishId
    type:     "get"
    dataType: "json"
    success:  (data) ->
      if (!data['error'])
        dish =  data['dish']
        handler(dish)

displayDish = ->
  dishDisplayTemplate = _.template $("#dish-display-template").html()
  displayDishHandler = (dish) ->
    $("#dish-display-table").html dishDisplayTemplate(dish)
    $("#dish-display").removeClass "hidden"
    $("#dish-edit").addClass "hidden"
  loadDish(displayDishHandler)

this.editDish = ->
  dishEditTemplate = _.template $("#dish-edit-template").html()
  editDishHandler = (dish) ->
    $("#dish-edit-form").html dishEditTemplate(dish)
    $("#dish-display").addClass "hidden"
    $("#dish-edit").removeClass "hidden"
  loadDish(editDishHandler)

this.saveDish = ->
  that = this
  postData = $("#dish-edit-form").serialize()
  postData += "&auth_username=" + this.auth.getUsername();
  postData += "&auth_password=" + this.auth.getPassword();
  $.ajax
    url:      "/stall/dishes/edit/" + this.dishId
    data:     postData
    dataType: "json"
    type:     "post"
    success:  (data) ->
      if (!data['error'])
        if that.javaMode()
          that.java.refreshParent()
        displayDish()